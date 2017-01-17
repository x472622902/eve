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

import dayan.eve.model.account.AccountInfo;

/**
 *
 * @author xsg
 */
public class GuestBook {

    private Integer platformId;
    private String name;
    private String subjectType;
    private String score;
    private String province;
    private String mobile;
    private String question;
    private String userNumber;

    public GuestBook() {
    }
    public GuestBook(AccountInfo accountInfo) {
        this.mobile = accountInfo.getMobile();
        this.name=accountInfo.getNickname();
        this.province= accountInfo.getProvince();
        if (accountInfo.getScore() != null) {
            this.score = accountInfo.getScore().toString();
        }
        this.subjectType = accountInfo.getSubjectType();
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

}
