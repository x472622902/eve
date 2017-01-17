/**
 * *****************************************************
 * Copyright (C) Dayan techology Co.ltd - All Rights Reserved
 * <p/>
 * This file is part of Dayan techology Co.ltd property.
 * <p/>
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * *****************************************************
 */
package dayan.eve.model.school;

/**
 * @author xsg
 */
public class RecommendSchool {

    private Integer schoolId;
    private String schoolName;
    private String batch;
    private Float recommendScore;
    private Integer minScore;
    private Float probability;
    private String tags;
    private String address;
    private Integer schoolReferRank;//学校投档线对应的排名
    private String querySchoolName;
    private Integer tagsValue;
    private Integer refTypeId;
    private String city;
    private Integer schoolRank;
    private Integer refScore;
    private Integer controlLine;
    private Boolean cs;

    public RecommendSchool() {
    }

    public RecommendSchool(EligibleSchool es) {
        this.address = es.getAddress();
        this.tagsValue = es.getTagsValue();
        this.schoolId = es.getSchoolId();
        this.schoolName = es.getSchoolName();
        this.probability = es.getProbability();
        this.batch = es.getBatch();
        this.refTypeId = es.getRefTypeId();
        this.minScore = es.getMinRecommend();
        this.city = es.getCity();
        this.schoolRank = es.getSchoolRank();
        this.refScore = es.getRefScore();
        this.controlLine = es.getCurControlLine();
        this.cs = es.getCs();
    }

    public Integer getTagsValue() {
        return tagsValue;
    }

    public void setTagsValue(Integer tagsValue) {
        this.tagsValue = tagsValue;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getSchoolReferRank() {
        return schoolReferRank;
    }

    public void setSchoolReferRank(Integer schoolReferRank) {
        this.schoolReferRank = schoolReferRank;
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

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Float getRecommendScore() {
        return recommendScore;
    }

    public void setRecommendScore(Float recommendScore) {
        this.recommendScore = recommendScore;
    }

    public Float getProbability() {
        return probability;
    }

    public void setProbability(Float probability) {
        this.probability = probability;
    }

    public Integer getRefTypeId() {
        return refTypeId;
    }

    public void setRefTypeId(Integer refTypeId) {
        this.refTypeId = refTypeId;
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

    public Integer getSchoolRank() {
        return schoolRank;
    }

    public void setSchoolRank(Integer schoolRank) {
        this.schoolRank = schoolRank;
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

    public Boolean getCs() {
        return cs;
    }

    public void setCs(Boolean cs) {
        this.cs = cs;
    }

}
