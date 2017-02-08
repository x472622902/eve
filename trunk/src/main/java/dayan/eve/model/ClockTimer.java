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

import dayan.eve.model.account.AccountInfo;

import java.util.Date;

/**
 *
 * @author xsg
 */
public class ClockTimer {

    private Integer id;
    private Integer accountId;
    private AccountInfo accountInfo;
    private Date clockInTime;
    private Date clockOutTime;
    private String clockInContent;
    private String clockOutContent;
    private Boolean clockDaylong;//是否早晚都打卡

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public Date getClockInTime() {
        return clockInTime;
    }

    public void setClockInTime(Date clockInTime) {
        this.clockInTime = clockInTime;
    }

    public Date getClockOutTime() {
        return clockOutTime;
    }

    public void setClockOutTime(Date clockOutTime) {
        this.clockOutTime = clockOutTime;
    }

    public String getClockInContent() {
        return clockInContent;
    }

    public void setClockInContent(String clockInContent) {
        this.clockInContent = clockInContent;
    }

    public String getClockOutContent() {
        return clockOutContent;
    }

    public void setClockOutContent(String clockOutContent) {
        this.clockOutContent = clockOutContent;
    }

    public Boolean getClockDaylong() {
        return clockDaylong;
    }

    public void setClockDaylong(Boolean clockDaylong) {
        this.clockDaylong = clockDaylong;
    }

    public void setClockDaylong(Integer clockDaylong) {
        this.clockDaylong = clockDaylong == 1;
    }

    public class Status {

        private Boolean isClockAvaliable = false;//早上能否打卡
        private String buttonContent;//按钮显示内容
        private String clockContent;//签到内容

        public Boolean getIsClockAvaliable() {
            return isClockAvaliable;
        }

        public void setIsClockAvaliable(Boolean isClockAvaliable) {
            this.isClockAvaliable = isClockAvaliable;
        }

        public String getButtonContent() {
            return buttonContent;
        }

        public void setButtonContent(String buttonContent) {
            this.buttonContent = buttonContent;
        }

        public String getClockContent() {
            return clockContent;
        }

        public void setClockContent(String clockContent) {
            this.clockContent = clockContent;
        }

    }
}
