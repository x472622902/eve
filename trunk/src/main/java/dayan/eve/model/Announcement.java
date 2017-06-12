/**
 * *****************************************************
 * Copyright (C) Dayan techology Co.ltd - All Rights Reserved
 *
 * This file is part of Dayan techology Co.ltd property.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * *****************************************************
 */
package dayan.eve.model;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 *
 * @author xsg
 */
public class Announcement {

    private Integer id;
    private String tag;
    private String content;
    private String url;
    private String androidClassName;
    private Map<String, String> androidParams;
    private String iosClassName;
    private Map<String, String> iosParams;
    private String androidParamsStr;
    private String iosParamsStr;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
