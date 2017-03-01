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
import dayan.eve.exception.EveException;
import dayan.eve.model.Constants;
import dayan.eve.model.JsonResult;
import dayan.eve.repository.AccountInfoRepository;
import dayan.eve.util.SchoolIdPlatformIdUtil;
import dayan.eve.util.WalleUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xsg
 */
@Service
public class QARobotService {

    private static final Logger LOGGER = LogManager.getLogger();

    private final AccountInfoRepository accountInfoRepository;
    private static SchoolPlatformIdEncoder idEncoder = new SchoolPlatformIdEncoder();
    private final EveProperties.Walle walle;
    private final VisitorService visitorService;
    private final WalleSchoolService walleSchoolService;
    private final WalleUtil walleUtil;
    private final SchoolIdPlatformIdUtil schoolIdPlatformIdUtil;

    @Autowired
    public QARobotService(EveProperties eveProperties, WalleUtil walleUtil, AccountInfoRepository accountInfoRepository, SchoolIdPlatformIdUtil schoolIdPlatformIdUtil, VisitorService visitorService, WalleSchoolService walleSchoolService) {
        this.walle = eveProperties.getWalle();
        this.walleUtil = walleUtil;
        this.accountInfoRepository = accountInfoRepository;
        this.schoolIdPlatformIdUtil = schoolIdPlatformIdUtil;
        this.visitorService = visitorService;
        this.walleSchoolService = walleSchoolService;
    }

    public JsonResult getAnswer(String schoolHashId, String query, String userNumber) {
        LOGGER.info("get answer info,schoolHashId:{},query:{},userNumber:{}", schoolHashId, query, userNumber);
        Integer platformId = walle.getEvePlatformId();
        if (!StringUtils.isEmpty(schoolHashId)) {
            platformId = checkPlatform(schoolHashId);
        }
        try {
            visitorService.createVisitor(accountInfoRepository.queryOneInfo(Integer.valueOf(userNumber)), platformId);
            Map<String, String> params = new HashMap<>();
            params.put("platformId", String.valueOf(platformId));
            params.put("query", query);
            params.put("userNumber", userNumber);
            String url = String.format(walle.getQaAnswer(), walleUtil.getAccessToken());
            String result = HttpClientUtil.get(url, params);
            LOGGER.info("answer result json from ,{}", result);
            JsonResult jsonResult = JSON.parseObject(result, JsonResult.class);
            if (!jsonResult.isSuccess()) {
                LOGGER.info(result);
                return new JsonResult(null, false);
            }
            JSONObject data = (JSONObject) jsonResult.getData();
            if (jsonResult.getData() == null) {
                LOGGER.info(result);
                return new JsonResult(new JSONObject());
            }
            Boolean notFound = data.getBoolean("notfound");
            if (notFound) {
                JSONArray array = walleSchoolService.readFreqQuestion(schoolHashId, true, 3);
                data.put("freqQuestion", array);
            }
            jsonResult.setData(data);
            return jsonResult;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return new JsonResult(new JSONObject());
    }

    private Integer checkPlatform(String schoolHashId) {
        Integer schoolId = idEncoder.decode(schoolHashId).intValue();
        Integer platformId = schoolIdPlatformIdUtil.getSchoolIdAndPlatformIdMap().get(schoolId);
        if (platformId == null) {
            throw new EveException(ErrorCN.Walle.NO_QUESTION_DATA);
        }
        return platformId;
    }

    private Integer checkPlatform(Integer platformId) {
        if (platformId == null) {
            platformId = walle.getEvePlatformId();
        } else if (!schoolIdPlatformIdUtil.getPlatformIdAndSchoolIdMap().containsKey(platformId)) {
            throw new EveException(ErrorCN.Walle.NO_QUESTION_DATA);
        }
        return platformId;
    }

    public JsonResult getHint(Integer platformId, String query) {
        LOGGER.info("hint ,info ,{},{}", platformId, query);
        try {
            platformId = checkPlatform(platformId);
            Map<String, String> params = new HashMap<>();
            params.put("platformId", String.valueOf(platformId));
            params.put("query", query);
            params.put("channelId", String.valueOf(Constants.Channel.APP));
            String url = String.format(walle.getQaHint(), walleUtil.getAccessToken());
            String result = HttpClientUtil.get(url, params);
            JsonResult jsonResult = JSONObject.parseObject(result, JsonResult.class);
            if (jsonResult.getData() == null) {
                jsonResult.setData(Collections.EMPTY_LIST);
            }
            LOGGER.info("hint result json from walle,{}", result);
            return jsonResult;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new JsonResult(null, false);
        }
    }

    public JsonResult getGreeting(Integer platformId) {
        LOGGER.info("greet ,platformId  :{}", platformId);
        try {
            platformId = checkPlatform(platformId);
            String url = String.format(walle.getQaGreet(), walleUtil.getAccessToken(), platformId);
            String result = HttpClientUtil.get(url);
            JsonResult jsonResult = JSONObject.parseObject(result, JsonResult.class);
            LOGGER.info("greet result json from walle,{}", result);
            return jsonResult;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new JsonResult(null, false);
        }
    }

    public void feedback(String answerId, Boolean act) {
        LOGGER.info("feedback ,info ,{},{}", answerId, act);
        try {
            JSONObject params = new JSONObject();
            params.put("answerId", answerId);
            String UP = "up";
            String DOWN = "down";
            params.put("act", act ? UP : DOWN);
            String url = String.format(walle.getFeedback(), walleUtil.getAccessToken());
            HttpClientUtil.post(url, params.toJSONString());
        } catch (URISyntaxException | IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

}
