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
package dayan.eve.model.school;

/**
 *
 * @author xsg
 */
public class RecScoreItem {

    private String batch;
    private Integer minScore;
    private Integer refScore;
    private Integer controlLine;
    private Integer starCount;//难度星级

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Integer getMinScore() {
        return minScore;
    }

    public void setMinScore(Integer minScore) {
        this.minScore = minScore;
    }

    public Integer getRefScore() {
        return refScore;
    }

    public void setRefScore(Integer refScore) {
        this.refScore = refScore;
    }

    public Integer getControlLine() {
        return controlLine;
    }

    public void setControlLine(Integer controlLine) {
        this.controlLine = controlLine;
    }

    public Integer getStarCount() {
        return starCount;
    }

    public void setStarCount(Integer starCount) {
        this.starCount = starCount;
    }

}
