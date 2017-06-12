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
public class HistoryScore {

    private Integer minScore;//录取分
    private Integer controlLine;//省控线
    private Integer refScore;//预测分
    private Integer myScore;//我的分数
    private Integer avgScore;
    private String year;

    public Integer getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(Integer avgScore) {
        this.avgScore = avgScore;
    }

    public Integer getMinScore() {
        return minScore;
    }

    public void setMinScore(Integer minScore) {
        this.minScore = minScore;
    }

    public Integer getControlLine() {
        return controlLine;
    }

    public void setControlLine(Integer controlLine) {
        this.controlLine = controlLine;
    }

    public Integer getRefScore() {
        return refScore;
    }

    public void setRefScore(Integer refScore) {
        this.refScore = refScore;
    }

    public Integer getMyScore() {
        return myScore;
    }

    public void setMyScore(Integer myScore) {
        this.myScore = myScore;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
