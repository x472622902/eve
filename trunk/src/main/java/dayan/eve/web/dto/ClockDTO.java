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
package dayan.eve.web.dto;


/**
 * @author xsg
 */
public class ClockDTO {

    private String content;
    private PaginationDTO paging;
    private Boolean readClockIn;
    private Boolean readClockOut;
    private Boolean readTodayRank = false;
    private Boolean readContinuousRank = false;
    private Boolean readTotalRank = false;

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

    public Boolean getReadTodayRank() {
        return readTodayRank;
    }

    public void setReadTodayRank(Boolean readTodayRank) {
        this.readTodayRank = readTodayRank;
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

    public PaginationDTO getPaging() {
        return paging;
    }

    public void setPaging(PaginationDTO paging) {
        this.paging = paging;
    }
}
