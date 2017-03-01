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
import dayan.common.util.SchoolPlatformIdEncoder;
import dayan.eve.config.EveProperties;
import dayan.eve.model.Constants;
import dayan.eve.model.GuestBook;
import dayan.eve.redis.respository.SingleValueRedis;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author xsg
 */
@Lazy
@Component
public class WalleUtil {
    private EveProperties.Walle walle;

    @Autowired
    public WalleUtil(EveProperties eveProperties) {
        this.walle = eveProperties.getWalle();
    }

    @Autowired
    SchoolIdPlatformIdUtil schoolIdPlatformIdUtil;

    @Autowired
    SingleValueRedis singleValueRedis;

    private static List<String> schoolHashIdsWithGuestbook;

    private static final Logger LOGGER = LogManager.getLogger(WalleUtil.class);

    public void sendFollow(Integer platformId) {
        JSONObject obj = new JSONObject();
        obj.put("platformId", platformId);
        obj.put("message", "有一个“小言高考APP” 的用户关注了您的学校");
        JSONObject params = new JSONObject();
        params.put("messageString", obj.toJSONString());
        try {
            HttpClientUtil.post(walle.getMessageHandlerSend(), params.toJSONString());
        } catch (IOException | URISyntaxException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    public void sendCancel(Integer platformId) {
        JSONObject obj = new JSONObject();
        obj.put("platformId", platformId);
        obj.put("message", "有一个“小言高考APP” 的用户取消了关注您的学校");
        JSONObject params = new JSONObject();
        params.put("messageString", JSON.toJSONString(obj));
        try {
            HttpClientUtil.post(walle.getMessageHandlerSend(), params.toJSONString());
        } catch (URISyntaxException | IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    public void sendGuestbook(GuestBook guestBook) {
        try {
            JSONObject params = new JSONObject();
            params.put("messageString", JSON.toJSONString(guestBook));
            HttpClientUtil.post(walle.getGuestbookSendQuestion(), params.toJSONString());
        } catch (URISyntaxException | IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    // TODO: 2/24/2017 add to redis ,filter in guestbook service
    public List<String> getSchoolHashIdsWithGuestbook() {
        if (schoolHashIdsWithGuestbook == null) {
            try {
                SchoolPlatformIdEncoder idEncoder = new SchoolPlatformIdEncoder();
                String result = HttpClientUtil.post(walle.getGuestbookGetSchool(), null);
                JSONObject obj = JSON.parseObject(result);
                schoolHashIdsWithGuestbook = new LinkedList<>();
                Map<Integer, Integer> map = schoolIdPlatformIdUtil.getPlatformIdAndSchoolIdMap();
                List<Integer> platformIds = (List<Integer>) obj.get("data");
                for (Integer platformId : platformIds) {
                    if (map.get(platformId) != null) {
                        schoolHashIdsWithGuestbook.add(idEncoder.encrypt(map.get(platformId).longValue()));
                    }
                }
            } catch (URISyntaxException | IOException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }
        return schoolHashIdsWithGuestbook;
    }

    public void clearSchoolHashIdsWithGuestbook() {
        schoolHashIdsWithGuestbook = null;
    }

    public String checkLogin(String username, String password) throws Exception {
        JSONObject params = new JSONObject();
        params.put("name", username);
        params.put("pwd", password);
        return HttpClientUtil.post(walle.getLogin(), params.toJSONString());
    }

    public String getStatistic(String url, Map<String, String> params) throws URISyntaxException, IOException {
        return HttpClientUtil.post(url, JSON.toJSONString(params));
    }

//    public static String ACCESS_TOKEN;
//
//    public static String REFRESH_TOKEN;
//
//    public String getAccessToken() {
//
//        if (StringUtils.isEmpty(ACCESS_TOKEN)) {
//            getNewAccessToken();
//        }
//        return ACCESS_TOKEN;
//    }


//    public void updateAccessToken() {
//        String REFRESH_TOKEN_URL = "%s?client_id=%s&grant_type=%s&refresh_token=%s";
//        String url = String.format(REFRESH_TOKEN_URL, walle.getAccessToken(), walle.getClientId(), walle.getRefreshGrantType(),
//                REFRESH_TOKEN);
//        String post = null;
//        try {
//            post = HttpClientUtil.post(url, "");
//        } catch (URISyntaxException | IOException ex) {
//            LOGGER.error(ex.getMessage());
//        }
//        JSONObject jSONObject = JSONObject.parseObject(post);
//        ACCESS_TOKEN = jSONObject.getString("access_token");
//        REFRESH_TOKEN = jSONObject.getString("refresh_token");
//    }

    public String getAccessToken() {
        String token = null;
        try {
            token = singleValueRedis.get(SingleValueRedis.SingleKey.WalleToken);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        if (token != null) return token;
        String TOKEN_URL = "%s?client_id=%s&username=%s&password=%s&grant_type=%s";
        String url = String.format(TOKEN_URL, walle.getAccessToken(), walle.getClientId(), walle.getUsername(), walle.getPassword(), walle.getGrantType());
        String post = null;
        try {
            post = HttpClientUtil.post(url, "");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        JSONObject jSONObject = JSONObject.parseObject(post);
        token = jSONObject.getString("access_token");
        singleValueRedis.put(SingleValueRedis.SingleKey.WalleToken, token, Constants.DAY_MINUTES);
        return token;
    }

}
