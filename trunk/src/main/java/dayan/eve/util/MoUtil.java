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
package dayan.eve.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import dayan.common.util.HttpClientUtil;
import dayan.common.util.MD5Util;
import dayan.eve.config.CacheConfig;
import dayan.eve.config.EveProperties;
import dayan.eve.model.Constants;
import dayan.eve.model.MoPageResult;
import dayan.eve.model.query.HolPersonalityQuery;
import dayan.eve.model.query.ScoreQuery;
import dayan.eve.model.query.SearchQuery;
import dayan.eve.redis.respository.SingleValueRedis;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xsg
 */
@Component
public class MoUtil {
    // TODO: 1/18/2017 可参考hal中的mo service
    private final static String TOKEN_REQUEST_HEADER = "X-ACCESS-TOKEN";
    private EveProperties.Mo moProperties;

    @Autowired
    public MoUtil(EveProperties eveProperties) {
        this.moProperties = eveProperties.getMo();
    }

    @Autowired
    SingleValueRedis singleValueRedis;

    private String getAccessToken() throws Exception {
        String token = singleValueRedis.get(SingleValueRedis.SingleKey.MoToken);
        if (StringUtils.isEmpty(token)) {
            JSONObject params = new JSONObject();
            params.put("username", moProperties.getUsername());
            String password = MD5Util.StringMD5(moProperties.getPassword()).toLowerCase();
            params.put("password", password);
            JSONObject jsonResult = JSON.parseObject(HttpClientUtil.post(moProperties.getTokenUrl(), params.toJSONString()));
            if (!jsonResult.getBoolean("success")) {
                throw new RuntimeException("get token from mo failed");
            }
            token = jsonResult.getJSONObject("data").getString("access_token");
            singleValueRedis.put(SingleValueRedis.SingleKey.MoToken, token, Constants.DAY_MINUTES);
        }
        return token;
    }

    private String getResult(String url) throws Exception {
        Map<String, String> header = new HashMap<>();
        header.put(TOKEN_REQUEST_HEADER, getAccessToken());
        String jsonResultString = HttpClientUtil.get(url, header);
        JSONObject result = JSONObject.parseObject(jsonResultString);

        if (!result.getBoolean("success")) {
            throw new RuntimeException(result.getString("errorCN"));
        }
        return jsonResultString;
    }

    @Cacheable(CacheConfig.ONE_DAY_CACHE)
    public String getMajorBySchoolHashId(String schoolHashId) throws Exception {
        String url = moProperties.getSchoolMajor() + schoolHashId;
        return getResult(url);
    }

    @Cacheable(CacheConfig.ONE_DAY_CACHE)
    public String getSchoolByMajorHashId(String majorHashId) throws Exception {
        String url = moProperties.getSchoolList() + "?majorHashId=" + majorHashId;
        return getResult(url);
    }

    @Cacheable(CacheConfig.ONE_DAY_CACHE)
    public String getMajorDetail(String majorHashId) throws Exception {
        String url = moProperties.getMajorDetail() + majorHashId;
        return getResult(url);
    }

    @Cacheable(CacheConfig.ONE_DAY_CACHE)
    public String getSchoolDetail(String schoolHashId) throws Exception {
        String url = moProperties.getSchoolDetail() + schoolHashId;
        return getResult(url);
    }

    // TODO: 2/28/2017 这种缓存形式不行 ，缓存配置有问题
    @Cacheable(CacheConfig.ONE_DAY_CACHE)
    public MoPageResult getMajorList(String queryStr) throws Exception {
        SearchQuery query = JSON.parseObject(queryStr, SearchQuery.class);
        String url = moProperties.getMajorList() + "?page=" + query.getPage() + "&size=" + query.getSize();
        if (query.getDegreeTypeId() != null) {
            url += "&batchId=" + query.getDegreeTypeId();
        }
        if (query.getQueryStr() != null) {
            url += "&query=" + query.getQueryStr();
        }

        JSONObject result = JSONObject.parseObject(getResult(url));
        return result.getObject("data", MoPageResult.class);
    }

    @Cacheable(CacheConfig.ONE_DAY_CACHE)
    public String getHolQuestion(boolean isSimplified) throws Exception {
        String url = moProperties.getHolQuestion() + "?isSimplified=" + (isSimplified ? 1 : 0);
        return getResult(url);
    }

    @Cacheable(CacheConfig.ONE_DAY_CACHE)
    public String getHolPersonality(HolPersonalityQuery query) throws Exception {
        String urlTemplate = moProperties.getHolPersonality() + "?A=%s&C=%s&E=%s&I=%s&R=%s&S=%s";
        String url = String.format(urlTemplate, query.getNumOfA(), query.getNumOfC(), query.getNumOfE(), query
                .getNumOfI(), query.getNumOfR(), query.getNumOfS());
        return getResult(url);
    }

    @Cacheable(CacheConfig.ONE_DAY_CACHE)
    public String getHolJobClass(String code, int page, int size) throws Exception {
        String urlTemplate = moProperties.getHolJobClass() + "?jobClassCode=%s&page=%s&size=%s";
        String url = String.format(urlTemplate, code, page, size);
        return getResult(url);
    }

    @Cacheable(CacheConfig.ONE_DAY_CACHE)
    public String getHolMajorType(String code, int page, int size) throws Exception {
        String urlTemplate = moProperties.getHolMajorType() + "?jobClassCode=%s&page=%s&size=%s";
        String url = String.format(urlTemplate, code, page, size);
        return getResult(url);
    }

    @Cacheable(CacheConfig.ONE_DAY_CACHE)
    public String getHolMajors(String code, int page, int size) throws Exception {

        String urlTemplate = moProperties.getHolMajors() + "?majorTypeCode=%s&page=%s&size=%s";
        String url = String.format(urlTemplate, code, page, size);
        return getResult(url);
    }

    @Cacheable(CacheConfig.ONE_DAY_CACHE)
    public String getSchoolPlan(String schoolHashId) throws Exception {
        String url = moProperties.getSchoolPlan() + "?schoolHashId=" + schoolHashId;
        return getResult(url);
    }

    @Cacheable(CacheConfig.ONE_DAY_CACHE)
    public String getSchoolSumPlan(String schoolHashId) throws Exception {
        String url = moProperties.getSchoolSumPlan() + "?schoolHashId=" + schoolHashId;
        return getResult(url);
    }

    @Cacheable(CacheConfig.ONE_DAY_CACHE)
    public String getSchoolNames(String schoolHashId) throws Exception {
        String url = moProperties.getSchoolNames() + "?schoolHashId=" + schoolHashId;
        return getResult(url);
    }

    @Cacheable(CacheConfig.ONE_DAY_CACHE)
    public String getSchoolScore(ScoreQuery query) throws Exception {
        String urlTemplate = moProperties.getSchoolScore() +
                "?schoolHashId=%s&provinceId=%s&subjectTypeId=%s&page=1&size=100";
        String url = String.format(urlTemplate, query.getSchoolHashId(), query.getProvinceId(), query
                .getSubjectTypeId());
        if (query.getBatchId() != null) {
            url = url + "&batchId=" + query.getBatchId();
        }
        if (query.getMinYear() != null) {
            url = url + "&minYear=" + query.getMinYear();
        }
        return getResult(url);
    }

}
