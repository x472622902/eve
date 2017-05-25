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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dayan.common.util.HttpClientUtil;
import dayan.common.util.SchoolPlatformIdEncoder;
import dayan.eve.config.EveProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xsg
 */
@Lazy
@Component
public class SchoolIdPlatformIdUtil {

    private final Logger LOGGER = LogManager.getLogger(SchoolIdPlatformIdUtil.class);

    @Autowired
    private WalleUtil walleUtil;
    private String platformUrl;
    private static SchoolPlatformIdEncoder idEncoder = new SchoolPlatformIdEncoder();

    @Autowired
    public SchoolIdPlatformIdUtil(EveProperties eveProperties) {
        this.platformUrl = eveProperties.getWalle().getPlatform();
    }

    public JSONArray getWallePlatformList() {
        LOGGER.info("get platform list from walle");
        JSONArray jsonArray = new JSONArray();
        try {
            String url = String.format(platformUrl, walleUtil.getAccessToken());
            String result = HttpClientUtil.get(url);
            JSONObject obj = JSONObject.parseObject(result);
            jsonArray = obj.getJSONArray("data");
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return jsonArray;
    }


    //开通平台的学校map
    @Cacheable(value = "schoolIdAndPlatformIdMap", cacheManager = "guavaCacheManager")
    public Map<Integer, Integer> getSchoolIdAndPlatformIdMap() {
        Map<Integer, Integer> schoolIdAndPlatformIdMap = new HashMap<>();
        getWallePlatformList().stream()
                .filter(obj -> ((JSONObject) obj).getBoolean("qa"))
                .forEach(obj -> {
                    JSONObject jsonObject = (JSONObject) obj;
                    schoolIdAndPlatformIdMap.put(jsonObject.getInteger("schoolId"), jsonObject.getInteger("id"));
                });
        return schoolIdAndPlatformIdMap;
    }

    @Cacheable(value = "platformIdAndSchoolIdMap", cacheManager = "guavaCacheManager")
    public Map<Integer, Integer> getPlatformIdAndSchoolIdMap() {
        Map<Integer, Integer> platformIdAndSchoolIdMap = new HashMap<>();
        getWallePlatformList().stream()
                .filter(obj -> ((JSONObject) obj).getBoolean("qa"))
                .forEach(obj -> {
                    JSONObject jsonObject = (JSONObject) obj;
                    platformIdAndSchoolIdMap.put(jsonObject.getInteger("id"), jsonObject.getInteger("schoolId"));
                });
        return platformIdAndSchoolIdMap;
    }

    @Cacheable(value = "schoolCSMap", cacheManager = "guavaCacheManager")
    public Map<Integer, Integer> getSchoolCSMap() {
        Map<Integer, Integer> schoolCSMap = new HashMap<>();
        getWallePlatformList().stream()
                .filter(obj -> ((JSONObject) obj).getBoolean("cs"))
                .forEach(obj -> {
                    JSONObject jsonObject = (JSONObject) obj;
                    schoolCSMap.put(jsonObject.getInteger("schoolId"), jsonObject.getInteger("id"));
                });
        return schoolCSMap;
    }


    @Cacheable(value = "allSchoolIdAndPlatformIdMap", cacheManager = "guavaCacheManager")
    public Map<Integer, Integer> getAllSchoolPlatformMap() {
        Map<Integer, Integer> allSchoolIdAndPlatformIdMap = new HashMap<>();
        getWallePlatformList()
                .forEach(obj -> {
                    JSONObject jsonObject = (JSONObject) obj;
                    allSchoolIdAndPlatformIdMap.put(jsonObject.getInteger("schoolId"), jsonObject.getInteger("id"));
                });
        return allSchoolIdAndPlatformIdMap;
    }

    @Cacheable(value = "allPlatformIdAndSchoolIdMap", cacheManager = "guavaCacheManager")
    public Map<Integer, Integer> getAllPlatformSchoolMap() {
        Map<Integer, Integer> allPlatformIdAndSchoolIdMap = new HashMap<>();
        getWallePlatformList()
                .forEach(obj -> {
                    JSONObject jsonObject = (JSONObject) obj;
                    allPlatformIdAndSchoolIdMap.put(jsonObject.getInteger("id"), jsonObject.getInteger("schoolId"));
                });
        return allPlatformIdAndSchoolIdMap;
    }

    public Integer getPlatformIdBySchoolId(Integer schoolId) {
        return getSchoolIdAndPlatformIdMap().get(schoolId);
    }

    public Integer getPlatformIdBySchoolHashId(String schoolHashId) {
        Integer schoolId = idEncoder.decode(schoolHashId).intValue();
        return getSchoolIdAndPlatformIdMap().get(schoolId);
    }
}
