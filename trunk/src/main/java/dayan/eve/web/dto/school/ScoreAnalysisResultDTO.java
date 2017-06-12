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

import dayan.eve.model.school.HistoryScore;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xsg
 */
public class ScoreAnalysisResultDTO {

    private List<HistoryScore> scoreList = new ArrayList<>();
    private String analysis;
    private String batch;
    private String provinceName;
    private String subjectType;

    public List<HistoryScore> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<HistoryScore> scoreList) {
        this.scoreList = scoreList;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

}
