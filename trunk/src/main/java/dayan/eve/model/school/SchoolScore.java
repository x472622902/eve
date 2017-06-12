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
public class SchoolScore {

    private Integer avgScore;
    private Integer minScore;
    private Integer controlLine = 0;
    private String batch;
    private String year;
    private Integer guessControlLine;

    public Integer getGuessControlLine() {
        return guessControlLine;
    }

    public void setGuessControlLine(Integer guessControlLine) {
        this.guessControlLine = guessControlLine;
    }

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

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
