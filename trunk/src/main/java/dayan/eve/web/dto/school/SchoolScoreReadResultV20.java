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
package dayan.eve.web.dto.school;


import dayan.eve.model.school.SchoolScore;

import java.util.List;

/**
 *
 * @author xsg
 */
public class SchoolScoreReadResultV20 {

    private Float probability;
    private Integer myScore;
    private Integer refScore;
    private List<SchoolScore> list;

    public SchoolScoreReadResultV20(List<SchoolScore> list) {
        this.list = list;
    }

    public SchoolScoreReadResultV20() {
    }

    public List<SchoolScore> getList() {
        return list;
    }

    public void setList(List<SchoolScore> list) {
        this.list = list;
    }

    public Integer getMyScore() {
        return myScore;
    }

    public void setMyScore(Integer myScore) {
        this.myScore = myScore;
    }

    public Float getProbability() {
        return probability;
    }

    public void setProbability(Float probability) {
        this.probability = probability;
    }

    public Integer getRefScore() {
        return refScore;
    }

    public void setRefScore(Integer refScore) {
        this.refScore = refScore;
    }

}
