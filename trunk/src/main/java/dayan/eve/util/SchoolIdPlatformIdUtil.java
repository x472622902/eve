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
import dayan.eve.config.EveProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xsg
 */
@Component
public class SchoolIdPlatformIdUtil {

    private final Logger LOGGER = LogManager.getLogger(SchoolIdPlatformIdUtil.class);

    @Autowired
    WalleUtil walleUtil;

    private String platformUrl;

    private EveProperties.Walle walle;

    @Autowired
    public SchoolIdPlatformIdUtil(EveProperties eveProperties) {
        this.platformUrl = eveProperties.getWalle().getPlatform();
    }

    private static Map<Integer, Integer> schoolIdAndPlatformIdMap = new HashMap<>();
    private static Map<Integer, Integer> allSchoolIdAndPlatformIdMap = new HashMap<>();
    private static Map<Integer, Integer> allPlatformIdAndSchoolIdMap = new HashMap<>();
    private static Map<Integer, Integer> platformIdAndSchoolIdMap = new HashMap<>();
    private static Map<Integer, Integer> schoolCSMap = new HashMap<>();//学校客服map，schoolId作为key，有客服则加入map

    /**
     * 学校的id作为key
     *
     * @return
     */
    public Map<Integer, Integer> getSchoolIdAndPlatformIdMap() {
        if (schoolIdAndPlatformIdMap == null || schoolIdAndPlatformIdMap.isEmpty()) {
            update();
        }
        return schoolIdAndPlatformIdMap;
    }

    // TODO: 1/14/2017 集成redis 
    public void update() {
        try {
            String url = platformUrl + "?access_token=" + walleUtil.getAccessToken();
            String result = HttpClientUtil.get(url);
            JSONObject obj = JSONObject.parseObject(result);
            JSONArray jsonArray = obj.getJSONArray("data");
            schoolIdAndPlatformIdMap = new HashMap<>();
            allSchoolIdAndPlatformIdMap = new HashMap<>();
            platformIdAndSchoolIdMap = new HashMap<>();
            schoolCSMap = new HashMap<>();
            for (Object object : jsonArray) {
                JSONObject jsonobj = (JSONObject) object;
                Integer schoolId = jsonobj.getInteger("schoolId");
                Integer platformId = jsonobj.getInteger("id");
                allSchoolIdAndPlatformIdMap.put(schoolId, platformId);
                allPlatformIdAndSchoolIdMap.put(platformId, schoolId);
                if (jsonobj.getBoolean("qa")) {
                    schoolIdAndPlatformIdMap.put(schoolId, platformId);
                    platformIdAndSchoolIdMap.put(platformId, schoolId);
                }
                if (jsonobj.getBoolean("cs")) {
                    schoolCSMap.put(schoolId, platformId);
                }
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    public Map<Integer, Integer> getSchoolCSMap() {
        if (schoolCSMap == null || schoolCSMap.isEmpty()) {
            update();
        }
        return schoolCSMap;
    }

    public Map<Integer, Integer> getAllSchoolPlatformMap() {
        if (allSchoolIdAndPlatformIdMap == null || allSchoolIdAndPlatformIdMap.isEmpty()) {
            update();
        }
        return allSchoolIdAndPlatformIdMap;
    }

    public Map<Integer, Integer> getAllPlatformSchoolMap() {
        if (allPlatformIdAndSchoolIdMap == null || allPlatformIdAndSchoolIdMap.isEmpty()) {
            update();
        }
        return allPlatformIdAndSchoolIdMap;
    }

    public void clearSchoolIdAndPlatformIdMap() {
        schoolIdAndPlatformIdMap.clear();
        schoolCSMap.clear();
        platformIdAndSchoolIdMap.clear();
    }

    /**
     * 平台id作为key
     *
     * @return
     */
    public Map<Integer, Integer> getPlatformIdAndSchoolIdMap() {
        if (platformIdAndSchoolIdMap == null || platformIdAndSchoolIdMap.isEmpty()) {
            update();
        }
        return platformIdAndSchoolIdMap;
    }
}
