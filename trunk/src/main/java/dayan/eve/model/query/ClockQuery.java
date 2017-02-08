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
package dayan.eve.model.query;


import dayan.eve.model.Pagination;

import java.util.Date;

/**
 * @author xsg
 */
public class ClockQuery extends Pagination {

    private Integer accountId;
    private Date clockInTime;
    private Date clockOutTime;
    private String content;//打卡内容
    private Boolean readClockIn;
    private Boolean readClockOut;
    private Date limitTime;
    private Boolean readToday;
    private Boolean readYesterday;
    private Boolean readContinuousRank = false;
    private Boolean readTotalRank = false;
    private Boolean readTodayRank = false;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getReadClockIn() {
        return readClockIn;
    }

    public void setReadClockIn(Boolean readClockIn) {
        this.readClockIn = readClockIn;
    }

    public Boolean getReadClockOut() {
        return readClockOut;
    }

    public void setReadClockOut(Boolean readClockOut) {
        this.readClockOut = readClockOut;
    }

    public Date getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(Date limitTime) {
        this.limitTime = limitTime;
    }

    public Boolean getReadToday() {
        return readToday;
    }

    public void setReadToday(Boolean readToday) {
        this.readToday = readToday;
    }

    public Boolean getReadYesterday() {
        return readYesterday;
    }

    public void setReadYesterday(Boolean readYesterday) {
        this.readYesterday = readYesterday;
    }

    public Boolean getReadContinuousRank() {
        return readContinuousRank;
    }

    public void setReadContinuousRank(Boolean readContinuousRank) {
        this.readContinuousRank = readContinuousRank;
    }

    public Boolean getReadTotalRank() {
        return readTotalRank;
    }

    public void setReadTotalRank(Boolean readTotalRank) {
        this.readTotalRank = readTotalRank;
    }

    public Boolean getReadTodayRank() {
        return readTodayRank;
    }

    public void setReadTodayRank(Boolean readTodayRank) {
        this.readTodayRank = readTodayRank;
    }

}
