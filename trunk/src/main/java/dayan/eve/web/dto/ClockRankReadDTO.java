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
package dayan.eve.web.dto;

import dayan.eve.model.ClockTimer;

import java.util.List;

/**
 *
 * @author xsg
 */
public class ClockRankReadDTO {

    private Integer rank;
    private Integer continuousClockTimes;
    private Integer clockCount;
    private List<ClockTimer> list;

    public List<ClockTimer> getList() {
        return list;
    }

    public void setList(List<ClockTimer> list) {
        this.list = list;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getContinuousClockTimes() {
        return continuousClockTimes;
    }

    public void setContinuousClockTimes(Integer continuousClockTimes) {
        this.continuousClockTimes = continuousClockTimes;
    }

    public Integer getClockCount() {
        return clockCount;
    }

    public void setClockCount(Integer clockCount) {
        this.clockCount = clockCount;
    }

}
