/**
 * *****************************************************
 * Copyright (C) Dayan techology Co.ltd - All Rights Reserved
 * <p/>
 * This file is part of Dayan techology Co.ltd property.
 * <p/>
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * *****************************************************
 */
package dayan.eve.service.walle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dayan.common.util.HttpClientUtil;
import dayan.common.util.SchoolPlatformIdEncoder;
import dayan.eve.config.EveProperties;
import dayan.eve.exception.ErrorCN;
import dayan.eve.model.ConstantKeys;
import dayan.eve.model.PageResult;
import dayan.eve.model.Pager;
import dayan.eve.model.School;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.easemob.EasemobStatus;
import dayan.eve.model.query.SearchQuery;
import dayan.eve.model.query.WalleQuery;
import dayan.eve.model.walle.CS;
import dayan.eve.model.walle.WalleCs;
import dayan.eve.repository.AccountInfoRepository;
import dayan.eve.repository.AccountRepository;
import dayan.eve.service.AccountInfoService;
import dayan.eve.service.school.SchoolSearchService;
import dayan.eve.util.Go4BaseUtil;
import dayan.eve.util.SchoolIdPlatformIdUtil;
import dayan.eve.util.WalleUtil;
import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class WalleSchoolService {

    private static final Logger LOGGER = LogManager.getLogger(WalleSchoolService.class);
    private static final String KEY = "cs";
    private static SchoolPlatformIdEncoder idEncoder = new SchoolPlatformIdEncoder();
    private EveProperties.Walle walleProperties;
    private PassiveExpiringMap<String, List<WalleCs>> csOnlineCache;

    @Autowired
    public WalleSchoolService(EveProperties eveProperties) {
        this.walleProperties = eveProperties.getWalle();
        csOnlineCache = new PassiveExpiringMap<>(walleProperties.getCsOnlineCacheSeconds());
    }

    @Autowired
    SchoolSearchService schoolSearchService;

    @Autowired
    SchoolIdPlatformIdUtil schoolIdPlatformIdUtil;

    @Autowired
    WalleUtil walleUtil;

    @Autowired
    Go4BaseUtil go4BaseUtil;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountInfoRepository accountInfoRepository;

    @Autowired
    AccountInfoService accountInfoService;

    public JSONArray readHotQuestion(String schoolHashId) {
        Map<String, String> params = new HashMap<>();
        Integer schoolId = idEncoder.decode(schoolHashId).intValue();
        Integer platformId = schoolIdPlatformIdUtil.getSchoolIdAndPlatformIdMap().get(schoolId);
        if (platformId == null) {
            throw new RuntimeException(ErrorCN.Walle.NO_QUESTION_DATA);
        }
        params.put("platformId", platformId.toString());
        params.put("number", walleProperties.getHotQuestionNum().toString());
        String url = walleProperties.getHot() + "?access_token=" + walleUtil.getAccessToken();
        JSONArray array = new JSONArray();
        try {
            String result = HttpClientUtil.get(url, params);
            array = JSONObject.parseObject(result).getJSONArray("data");
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return array;
    }

    public JSONArray readFreqQuestion(String schoolHashId, Boolean refresh, Integer number) {
        number = number == null ? walleProperties.getFreqQuestionNum() : number;
        Map<String, String> params = new HashMap<>();
        Integer platformId = walleProperties.getEvePlatformId();
        if (!isEmpty(schoolHashId)) {
            Integer schoolId = idEncoder.decode(schoolHashId).intValue();
            Integer cPlatformId = schoolIdPlatformIdUtil.getSchoolIdAndPlatformIdMap().get(schoolId);//当前平台id
            if (cPlatformId == null) {//如果未开通平台，则抛出异常
                throw new RuntimeException();
            }
            platformId = cPlatformId;
        }
        params.put("platformId", platformId.toString());
        params.put("refresh", refresh.toString());
        String url = String.format(walleProperties.getFreq(), walleProperties.getAccessToken());
        String result = null;
        JSONArray array = new JSONArray();
        try {
            result = HttpClientUtil.get(url, params);
            LOGGER.info("result from walle,{}", result);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return array;
        }
        JSONObject jsonObject;
        try {
            jsonObject = JSON.parseObject(result);
        } catch (Exception e) {
            LOGGER.error("walle数据返回不是json格式");
            return array;
        }
        if (!jsonObject.getBoolean("success")) {
            return array;
        }
        array = jsonObject.getJSONArray("data");
        return getListBySize(array, number);
    }

    /**
     * 根据传入number取前几条
     */
    private JSONArray getListBySize(JSONArray array, Integer number) {
        JSONArray result = new JSONArray();
        if (array == null || array.isEmpty()) {
            return result;
        }
        number = number > array.size() ? array.size() : number;
        for (int i = 0; i < number; i++) {
            result.add(array.get(i));
        }
        return result;
    }

    public String readCustomerService(String schoolHashId) throws Exception {
        Integer schoolId = idEncoder.decode(schoolHashId).intValue();
        Integer platformId = schoolIdPlatformIdUtil.getSchoolCSMap().get(schoolId);
        if (platformId == null) {
            JSONObject result = new JSONObject();
            result.put("accountInfos", Collections.emptyList());
            return result.toJSONString();
        }
        List<AccountInfo> accountInfoList = new LinkedList<>();
        String accessToken = walleUtil.getAccessToken();
        String csResult = null;
        String url = String.format(walleProperties.getCsStaff(), walleProperties.getAccessToken(), platformId);
        try {
            csResult = HttpClientUtil.get(url);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        JSONArray list = JSONObject.parseObject(csResult).getJSONArray("data");
        JSONObject result = new JSONObject();
        result.put("platformId", platformId);
        if (list == null || list.isEmpty()) {
            result.put("accountInfos", Collections.emptyList());
            return result.toString();
        }
        for (Object account : list) {
            JSONObject cs = (JSONObject) account;
            String accountHashId = cs.getString("hashId");
            String csNickname = cs.getString("csNickname");
            String csAvatarUrl = cs.getString("csAvatarUrl");
            AccountInfo accountInfo = go4BaseUtil.getAccountDetailByHashId(accountHashId);
            Boolean status = cs.getBoolean("status");
            EasemobStatus easemobStatus = new EasemobStatus();
            if (status == null) {
                easemobStatus.setOnline(false);
            } else {
                easemobStatus.setOnline(status);
            }

            //来自数据库
            AccountInfo info = accountInfoService.readOrCreate(accountInfo);

            accountInfo.setAccountId(info.getAccountId());
            accountInfo.setId(info.getAccountId());
            accountInfo.setStatus(easemobStatus);
            accountInfo.setCsNickname(csNickname);

            //如果用户名不一致，更新
            if (!accountInfo.getNickname().equals(info.getNickname()) || !csAvatarUrl.equals(info.getPortraitUrl())) {
                accountInfo.setPortraitUrl(csAvatarUrl);
                accountInfo.setAvatarURL(csAvatarUrl);
                accountRepository.update(accountInfo);
                accountInfoRepository.updateInfo(accountInfo);
            }
            accountInfoList.add(accountInfo);
        }
        result.put("account info list", accountInfoList);
        LOGGER.info("cs online ，{}", JSON.toJSONString(result, true));
        return result.toJSONString();
    }

    @Transactional
    public PageResult<CS> readAllCs(WalleQuery query) throws Exception {

        Integer page = query.getPage();
        Integer size = query.getSize();
        PageResult<CS> result = new PageResult<>(0, page, size);

        if (!csOnlineCache.containsKey(KEY)) {
            try {
                getCSOnlineFromWalle();
                List<WalleCs> list = csOnlineCache.get(KEY);
                if (list != null && !list.isEmpty()) {
                    List<CS> csList = getCSList(list, page, size, query.getReadAllCs(), query.getAccountId());
                    Pager pager = new Pager(list.size(), page, size);
                    result.setList(csList);
                    result.setPager(new Pager(list.size(), page, size));
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return result;
    }

    public void reloadAllCs() {
        csOnlineCache.clear();
        try {
            getCSOnlineFromWalle();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    private List<CS> getCSList(List<WalleCs> walleCsList, int page, int size, boolean readAllCs, Integer accountId) {
        List<CS> csList = new LinkedList<>();
        Integer count = walleCsList.size();
        if ((page - 1) * size > count) {
            return csList;
        }
        int start;
        int end;
        if (readAllCs) {
            start = (page - 1) * size;
            end = count > (page * size) ? (page * size) : count;
        } else {
            if (count <= walleProperties.getCsOnlineDisplayNum()) {
                start = 0;
                end = count;
            } else {
                start = (int) (Math.random() * count);
                end = start + walleProperties.getCsOnlineDisplayNum();
            }
        }

        Map<String, Integer> csHashIdMap = new HashMap<>();
        WalleCs topTeacher = getOnlineTopTeacher(walleCsList, accountId);
        if (topTeacher != null) {
            csList.add(getCsByWalleCs(topTeacher));
        }

        if (!readAllCs && topTeacher != null) {
            end--;
        }

        for (int i = start; i < end; i++) {
            WalleCs walleCs = walleCsList.get(i % count);
            if (csHashIdMap.containsKey(walleCs.getHashId())) {
                break;
            }
            csHashIdMap.put(walleCs.getHashId(), 1);
            if (walleProperties.getCsTopHashId().equals(walleCs.getHashId())) {
                csHashIdMap.put(walleProperties.getCsTopHashId(), 1);
                end++;
                continue;
            }
            CS cs = getCsByWalleCs(walleCs);
            if (cs == null) {
                end++;
                continue;
            }
            csList.add(cs);
            csHashIdMap.put(walleCs.getHashId(), 1);
        }
        return csList;
    }

    private CS getCsByWalleCs(WalleCs walleCs) {
        School school = getSchoolByPlatformId(walleCs.getPlatformId());

        String platform = school == null ? ConstantKeys.EVE_PLATFORM_CN : school.getName();
        if (platform == null) {
            return null;
        }
        AccountInfo accountInfo = getAccountInfo(walleCs.getHashId(), walleCs.getCsNickname());
        if (accountInfo == null) {
            return null;
        }

        if (!ConstantKeys.EVE_PLATFORM_CN.equals(platform)) {
            accountInfo.getExt().setSchoolName(platform);
        }

        CS newCS = new CS(accountInfo);
        newCS.setSchool(school);
        newCS.setPlatform(platform);
        return newCS;
    }

    private WalleCs getOnlineTopTeacher(List<WalleCs> walleCsList, Integer accountId) {
        if (checkAccountInfo(accountId)) {
            for (WalleCs cs : walleCsList) {
                if (walleProperties.getCsTopHashId().equals(cs.getHashId())) {
                    return cs;
                }
            }
        }
        return null;
    }

    private School getSchoolByPlatformId(Integer platformId) {
        School school = null;
        boolean isEvePlatform = Objects.equals(platformId, walleProperties.getEvePlatformId());
        if (!isEvePlatform && schoolIdPlatformIdUtil.getAllPlatformSchoolMap() != null) {
            Integer schoolId = schoolIdPlatformIdUtil.getAllPlatformSchoolMap().get(platformId);
            if (schoolId != null) {
                try {
                    SearchQuery query = new SearchQuery();
                    query.setSchoolId(schoolId);
                    school = schoolSearchService.readSingleSchool(query);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            }

        }
        return school;
    }


    private AccountInfo getAccountInfo(String accountHashId, String csNickname) {
        AccountInfo accountInfo;
        try {
            accountInfo = go4BaseUtil.getAccountDetailByHashId(accountHashId);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return null;
        }
        //来自数据库
        AccountInfo info = accountInfoService.readOrCreate(accountInfo);

        accountInfo.setAccountId(info.getAccountId());
        accountInfo.setId(info.getAccountId());
        accountInfo.setStatus(new EasemobStatus(true));
        accountInfo.setPortraitUrl(accountInfo.getAvatarURL());
        accountInfo.setCsNickname(csNickname);
        //如果用户名不一致，更新
        if (!accountInfo.getNickname().equals(info.getNickname())) {
            accountRepository.update(accountInfo);
            accountInfoRepository.updateInfo(accountInfo);
        }
        return accountInfo;
    }

    private synchronized void getCSOnlineFromWalle() throws Exception {
        if (csOnlineCache.containsKey(KEY)) {
            return;
        }
        String accessToken = walleUtil.getAccessToken();

        String url = walleProperties.getCsAllOnline() + "?access_token=" + accessToken;
        String csResult = HttpClientUtil.get(url);
        LOGGER.info("cs online read from walle,{}", csResult);
        JSONObject jSONObject = JSONObject.parseObject(csResult);
        String list = jSONObject.getString("data");
        csOnlineCache.put(KEY, JSONArray.parseArray(list, WalleCs.class));
    }

    /**
     * 判断是否符合浙江（必须满足）和分数300分以下或者分数不填。 用于显示置顶的老师在线
     *
     * @param accountId 用户id
     * @return 符合条件返回true
     */
    private boolean checkAccountInfo(Integer accountId) {
        //如果没登录，返回false
        if (accountId == null) {
            return false;
        }
        AccountInfo accountInfo = accountInfoRepository.queryOneInfo(accountId);
        //如果不是浙江，返回false
        if (accountInfo.getProvinceId() == null || accountInfo.getProvinceId() != 11) {
            return false;
        }
        //如果分数大于300，返回false
        return !(accountInfo.getScore() != null && accountInfo.getScore() > 300);
    }

}
