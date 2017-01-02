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
package dayan.eve.model.account;

import dayan.eve.model.easemob.Easemob;

import java.util.Date;

/**
 * @author zhuangyd
 */
public class Account implements AccountBase {

    private Integer id;
    private String hashId;
    private String mobile;
    private String password;
    private String nickname;
    private String verifyCode;
    private String avatarURL;//头像地址
    private Easemob easemob;
    private Date gmtCreate;//注册时间

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String getNickname() {
        return nickname == null ? "" : nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUserNumber() {
        return String.valueOf(getId());
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public Easemob getEasemob() {
        return easemob;
    }

    public void setEasemob(Easemob easemob) {
        this.easemob = easemob;
    }

}
