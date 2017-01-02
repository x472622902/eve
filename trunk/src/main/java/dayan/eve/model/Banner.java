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
package dayan.eve.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import dayan.eve.model.topic.Topic;

import java.util.Map;

/**
 *
 * @author xsg
 */
public class Banner {

    public enum Type {

        Topic, School, News, Unknown, Clock, Custom
    }

    private Integer id;
    private Integer topicId;
    private Integer schoolId;
    private Integer newsId;
    private String bannerUrl;
    private Topic topic;
    private Information news;
    private School school;
    private String androidClassName;
    private Map<String, String> androidParams;
    private String iosClassName;
    private Map<String, String> iosParams;
    private Type type;
    private String androidParamsStr;
    private String iosParamsStr;

    public String getAndroidParamsStr() {
        if (androidParams == null || androidParams.isEmpty()) {
            return null;
        }
        return JSON.toJSONString(androidParams);

    }

    public String getIosParamsStr() {
        if (iosParams == null || iosParams.isEmpty()) {
            return null;
        }
        return JSON.toJSONString(iosParams);
    }

    public String getAndroidClassName() {
        return androidClassName;
    }

    public void setAndroidParamsStr(String androidParamsStr) {
        this.androidParams = JSONObject.parseObject(androidParamsStr, Map.class);
    }

    public void setIosParamsStr(String iosParamsStr) {
        this.iosParams = JSONObject.parseObject(iosParamsStr, Map.class);
    }

    public void setAndroidClassName(String androidClassName) {
        this.androidClassName = androidClassName;
    }

    public Map<String, String> getAndroidParams() {
        return androidParams;
    }

    public void setAndroidParams(Map<String, String> androidParams) {
        this.androidParams = androidParams;
    }

    public String getIosClassName() {
        return iosClassName;
    }

    public void setIosClassName(String iosClassName) {
        this.iosClassName = iosClassName;
    }

    public Map<String, String> getIosParams() {
        return iosParams;
    }

    public void setIosParams(Map<String, String> iosParams) {
        this.iosParams = iosParams;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public dayan.eve.model.School getSchool() {
        return school;
    }

    public void setSchool(dayan.eve.model.School school) {
        this.school = school;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Information getNews() {
        return news;
    }

    public void setNews(Information news) {
        this.news = news;
    }
}
