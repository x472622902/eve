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
package dayan.eve.model.robot;

import java.io.Serializable;

/**
 *
 * @author zhuangyd
 */
public abstract class BaseMessage implements Serializable {

    protected Integer platformId; //*
    protected String customSessionId; //*
    protected String csSessionId; //*

    public BaseMessage() {
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public String getCustomSessionId() {
        return customSessionId;
    }

    public void setCustomSessionId(String customSession) {
        this.customSessionId = customSession;
    }

    public String getCsSessionId() {
        return csSessionId;
    }

    public void setCsSessionId(String csSession) {
        this.csSessionId = csSession;
    }

    @Override
    public String toString() {
        return "BaseMessage{" + "platformId=" + platformId + ", customSessionId=" + customSessionId + ", csSessionId=" + csSessionId + '}';
    }

}
