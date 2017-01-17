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
 * 符合刷选条件的学校
 *
 * @author xsg
 */
public class EligibleSchool {

    private Integer schoolId;
    private String schoolName;
    private Integer schoolRank;
    private Integer avgScoreDifference;//2011和2012的最小分减去那年的省控线的平均值
    private String provinceName;
    private String subjectType;
    private String batch;
    private Integer refScore;//预测录取分（学校投档线）
    private Integer minRecommend;//预测最小投档分
    private Integer avgScore;//算出来的2013的平均分
    private Float referScoreProbability; //参照分录取概率
    private String tags;//学校特色标签
    private String address;
    private Float probability;//录取概率
    private Float probabilityDif;//概率差
    private Integer tagsValue;
    private Integer refTypeId;
    private Integer controlLineOne;//今年省控线
    private Integer controlLineTwo;//当年省控线（算出今年排名那一年的省控线） 
    private Integer guessControlLine;// 预测省控线
    private Integer refRank;
    private String rankFrom;
    private Integer minScore;//rankFrom那一年的最小分
    private String city;
    private Boolean cs;

    public String getTags() {
        return tags;
    }

    public Integer getGuessControlLine() {
        return guessControlLine;
    }

    public void setGuessControlLine(Integer guessControlLine) {
        this.guessControlLine = guessControlLine;
    }

    public Integer getTagsValue() {
        return tagsValue;
    }

    public void setTagsValue(Integer tagsValue) {
        this.tagsValue = tagsValue;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getSchoolRank() {
        return schoolRank;
    }

    public void setSchoolRank(Integer schoolRank) {
        this.schoolRank = schoolRank;
    }

    public Integer getAvgScoreDifference() {
        return avgScoreDifference;
    }

    public void setAvgScoreDifference(Integer avgScoreDifference) {
        this.avgScoreDifference = avgScoreDifference;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
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

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Integer getRefScore() {
        return refScore;
    }

    public void setRefScore(Integer refScore) {
        this.refScore = refScore;
    }

    public Integer getMinRecommend() {
        return minRecommend;
    }

    public void setMinRecommend(Integer minRecommend) {
        this.minRecommend = minRecommend;
    }

    public Integer getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(Integer avgScore) {
        this.avgScore = avgScore;
    }

    public Float getReferScoreProbability() {
        return referScoreProbability;
    }

    public void setReferScoreProbability(Float rsp) {
        this.referScoreProbability = rsp;
    }

    public Float getProbability() {
        return probability;
    }

    public void setProbability(Float probability) {
        this.probability = probability;
    }

    public Float getProbabilityDif() {
        return probabilityDif;
    }

    public void setProbabilityDif(Float probabilityDif) {
        this.probabilityDif = probabilityDif;
    }

    public Integer getRefTypeId() {
        return refTypeId;
    }

    public void setRefTypeId(Integer refTypeId) {
        this.refTypeId = refTypeId;
    }

    public Integer getControlLineOne() {
        return controlLineOne;
    }

    public Integer getCurControlLine() {
        if (guessControlLine != null && guessControlLine != 0) {
            return guessControlLine;
        }
        return controlLineOne;
    }

    public void setControlLineOne(Integer controlLineOne) {
        this.controlLineOne = controlLineOne;
    }

    public Integer getControlLineTwo() {
        return controlLineTwo;
    }

    public void setControlLineTwo(Integer controlLineTwo) {
        this.controlLineTwo = controlLineTwo;
    }

    public Integer getRefRank() {
        return refRank;
    }

    public void setRefRank(Integer refRank) {
        this.refRank = refRank;
    }

    public String getRankFrom() {
        return rankFrom;
    }

    public void setRankFrom(String rankFrom) {
        this.rankFrom = rankFrom;
    }

    public Integer getMinScore() {
        return minScore;
    }

    public void setMinScore(Integer minScore) {
        this.minScore = minScore;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getCs() {
        return cs;
    }

    public void setCs(Integer cs) {
        this.cs = cs == 1;
    }

}
