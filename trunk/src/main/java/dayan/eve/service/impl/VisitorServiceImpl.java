/**
 * *****************************************************
 * Copyright (C) Dayan techology Co.ltd - All Rights Reserved
 * <p>
 * This file is part of Dayan techology Co.ltd property.
 * <p>
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * *****************************************************
 */
package dayan.eve.service.impl;

import com.alibaba.fastjson.JSONObject;
import dayan.common.util.HttpClientUtil;
import dayan.common.util.SchoolPlatformIdEncoder;
import dayan.eve.config.EveProperties;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.repository.AccountInfoRepository;
import dayan.eve.service.VisitorService;
import dayan.eve.util.SchoolIdPlatformIdUtil;
import dayan.eve.util.WalleUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
class VisitorServiceImpl implements VisitorService {

    private static final Logger LOGGER = LogManager.getLogger(VisitorServiceImpl.class);

    private EveProperties.Walle walle;

    @Autowired
    public VisitorServiceImpl(EveProperties eveProperties) {
        this.walle = eveProperties.getWalle();
    }

    @Autowired
    WalleUtil walleUtil;

    @Autowired
    AccountInfoRepository accountInfoRepository;

    private static SchoolPlatformIdEncoder idEncoder = new SchoolPlatformIdEncoder();

    @Autowired
    SchoolIdPlatformIdUtil schoolIdPlatformIdUtil;


    @Override
    public void createVisitor(AccountInfo accountInfo, Integer platformId) {
        JSONObject obj = new JSONObject();
        if (accountInfo.getScore() != null) {
            obj.put("score", accountInfo.getScore().toString());
        }
        obj.put("subjectType", accountInfo.getSubjectType());
        obj.put("province", accountInfo.getProvince());
        obj.put("mobile", accountInfo.getMobile());
        obj.put("userNumber", accountInfo.getAccountId());
        obj.put("platformId", platformId.toString());
        obj.put("hashId", accountInfo.getHashId());
        LOGGER.info("visitor ,info {}", obj.toJSONString());
        String url = walle.getVisitor() + "?access_token=" + walleUtil.getAccessToken();
        try {
            String result = HttpClientUtil.post(url, obj.toJSONString());
            LOGGER.info("visitor create ,result {}", result);
        } catch (URISyntaxException | IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    @Override
    public void updateVisitor(AccountInfo accountInfo) {
        JSONObject params = new JSONObject();
        JSONObject obj = new JSONObject();
        if (accountInfo.getScore() != null) {
            obj.put("score", accountInfo.getScore().toString());
        }
        obj.put("subjectType", accountInfo.getSubjectType());
        obj.put("province", accountInfo.getProvince());
        obj.put("mobile", accountInfo.getMobile());
        obj.put("usernumber", accountInfo.getAccountId());
        params.put("visitorString", obj.toJSONString());
        String url = walle.getVisitor() + "?access_token=" + walleUtil.getAccessToken();
        try {
            String post = HttpClientUtil.put(url, params.toJSONString(), walleUtil.getAccessToken());
        } catch (URISyntaxException | IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    @Override
    public void createVisitor(String accountId, String schoolHashId) {
        int platformId;
        if (StringUtils.isEmpty(schoolHashId)) {
            platformId = walle.getEvePlatformId();
        } else {
            Integer schoolId = idEncoder.decode(schoolHashId).intValue();
            platformId = schoolIdPlatformIdUtil.getSchoolIdAndPlatformIdMap().get(schoolId);
        }
        createVisitor(accountInfoRepository.queryOneInfo(Integer.valueOf(accountId)), platformId);
    }

}
