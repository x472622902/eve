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
package dayan.eve.service.school;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dayan.eve.model.WeiboUser;
import dayan.eve.model.query.WeiboQuery;
import dayan.eve.model.school.WeiboTimeline;
import dayan.eve.repository.CodeRepository;
import dayan.eve.repository.SchoolRepository;
import dayan.eve.repository.WeiboRepository;
import dayan.eve.repository.WeiboUserRepository;
import dayan.eve.util.WeiboUtil;
import dayan.weibo.util.WeiboTimelineUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weibo4j.model.Status;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static dayan.eve.model.ConstantKeys.UTF8MB4;

@Service
public class WeiboDataService {

    private static final Logger LOGGER = LogManager.getLogger(WeiboDataService.class);

    @Autowired
    WeiboRepository weiboRepository;

    @Autowired
    WeiboUserRepository weiboUserRepository;

    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    CodeRepository codeRepository;

    private String getImagesStr(String picUrl) {
        List<String> picUrlList = new LinkedList<>();
        JSONArray array = JSONArray.parseArray(picUrl);
        if (array.isEmpty()) {
            return null;
        }
        picUrlList.addAll(array.stream().map(obj -> ((JSONObject) obj).getString("thumbnail_pic")).collect(Collectors.toList()));
        return JSON.toJSONString(picUrlList);
    }

    public void getWeiboFriendTimeline() {
        int size = 100;
        int limitTimes = 3;//每小时限制抓取次数
        Integer pageStart = 1;
        //获取微博用户和学校对应的map
        Map<String, Integer> weiUserSchoolMap = getUserSchoolMap();
        codeRepository.setCode(UTF8MB4);

        //最新的微博id
        WeiboQuery query = new WeiboQuery();
        query.setSize(size);
        String lastWeiboId = weiboRepository.queryId(query).get(0);

        boolean stop = false;

        for (int i = pageStart; i < pageStart + limitTimes; i++) {
            List<WeiboTimeline> weiboTimelines = new LinkedList<>();
            List<Status> statusList = new LinkedList<>();

            System.out.println("current pager info : page : " + i + ",size : " + size);
            try {
                statusList = WeiboTimelineUtil.getFriendsTimeline(WeiboUtil.getAccessToken().getAccessToken(), i, size).getStatuses();
            } catch (Exception e) {
                System.out.println("weibo api grab limit！");
                return;
            }
            List<String> weiboIds = getWeiboIds(statusList);
            if (weiboIds == null || weiboIds.isEmpty()) {
                continue;
            }

            Map<String, Integer> existedIdMap = getExistedIdMap(weiboIds);
            List<WeiboTimeline> retweetedTimelines = new LinkedList<>();
            for (Status status : statusList) {
                if (status.getUser() == null) {
                    continue;
                }
                if (status.getId().equals(lastWeiboId)) {
                    stop = true;
                    break;
                }
                if (existedIdMap.containsKey(status.getId())) {
                    continue;
                }
                System.out.println(JSON.toJSONString(status.getUser().getScreenName(), true));
                WeiboTimeline weiboTimeline = new WeiboTimeline(status);
                String weiboUserName = status.getUser().getName();
                weiboTimeline.setImageUrlsStr(getImagesStr(status.getPicUrlstr()));
                //根据微博名判断是否学校的官方微博
                if (weiUserSchoolMap.containsKey(weiboUserName)) {
                    Integer schoolId = weiUserSchoolMap.get(weiboUserName);
                    weiboTimeline.setSchoolId(schoolId);
                    weiboTimelines.add(weiboTimeline);
                    if (status.getRetweetedStatus() != null) {
                        if (status.getRetweetedStatus().getUser() == null) {
                            continue;
                        }
                        WeiboTimeline retweetTimeline = new WeiboTimeline(status.getRetweetedStatus());
                        retweetTimeline.setOriginId(weiboTimeline.getId());
                        retweetTimeline.setImageUrlsStr(getImagesStr(status.getRetweetedStatus().getPicUrlstr()));
                        retweetedTimelines.add(retweetTimeline);
                    }
                }
            }

            if (!weiboTimelines.isEmpty()) {
                System.out.println("insert weibo start!");
                weiboRepository.multiInsert(weiboTimelines);
                System.out.println("insert weibo end!");
            }
            if (!retweetedTimelines.isEmpty()) {
                System.out.println("insert weibo start!");
                weiboRepository.multiInsert(retweetedTimelines);
                System.out.println("insert weibo end!");
            }
            if (stop) {
                break;
            }
        }

    }

    private Map<String, Integer> getUserSchoolMap() {
        List<WeiboUser> allUsers = weiboUserRepository.queryBySchool(null);
        Map<String, Integer> userSchoolMap = new HashMap<>();
        for (WeiboUser user : allUsers) {
            userSchoolMap.put(user.getScreenName(), user.getSchoolId());
        }
        return userSchoolMap;
    }

    private List<String> getWeiboIds(List<Status> statuses) {
        List<String> result = new LinkedList<>();
        for (Status s : statuses) {
            result.add(s.getId());
        }
        return result;
    }

    private Map<String, Integer> getExistedIdMap(List<String> weiboIds) {
        //查询已存在的微博id
        WeiboQuery weiboQuery = new WeiboQuery();
        weiboQuery.setSize(100);

        List<String> existedWeiboIds = weiboRepository.queryId(weiboQuery);
        Map<String, Integer> map = new HashMap<>();
        for (String existedWeiboId : existedWeiboIds) {
            map.put(existedWeiboId, 1);
        }
        return map;
    }

}
