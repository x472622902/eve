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
package dayan.eve.model.walle;

import com.alibaba.fastjson.JSON;
import dayan.eve.model.School;
import dayan.eve.model.account.AccountInfo;

/**
 *
 * @author xsg
 */
public class CS extends AccountInfo {

    private School school;
    private String platform;

    public CS() {
    }

    public CS(AccountInfo info) {
        setAccountId(info.getAccountId());
        setNickname(info.getNickname());
        setPortraitUrl(info.getPortraitUrl());
        setEasemob(info.getEasemob());
        setCsNickname(info.getCsNickname());
        setHashId(info.getHashId());
        setExt(JSON.toJSONString(info.getExt()));
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

}
