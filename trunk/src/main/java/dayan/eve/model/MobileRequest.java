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

import com.alibaba.fastjson.JSON;

/**
 *
 * @author zhuangyd
 * @param <T>
 */
public class MobileRequest<T> {

    protected String ver;
    protected String deviceNumber;
    protected String jsessionid;
    protected Integer channelId = 5;
    protected Integer platformId;
    protected String usernumber;
    protected T data;
    
    private Class<T> type;

    
    public MobileRequest() {
    }
    
    public MobileRequest(Class<T> type) {
        this.type = type;
    }


    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getJsessionid() {
        return jsessionid;
    }

    public void setJsessionid(String jsessionid) {
        this.jsessionid = jsessionid;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getUsernumber() {
        return usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }
    
    public T parseData(String data) {
        if (data == null) {
            return null;
        }
        return (T) JSON.parseObject(data, type);
    }

}
