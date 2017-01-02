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

import java.util.List;

/**
 *
 * @author xsg
 */
public class HotRecommendQuery extends Pagination {

    private Integer accountId;
    private Integer schoolId;
    private Integer provinceId;
    private String subjectType;
    private Integer scoreSegment;
    private List<Integer> schoolIds;
    private Integer maxScoreSegment;
    private Integer minScoreSegment;
    private Integer maxScore;
    private Integer minScore;
    private List<String> schoolTypes;
    private Integer isAdvisory;
    private Integer tagsValue;
    private List<Integer> refTypeIds;
    private List<Integer> provinceIds;//学校所在省份
    private Integer rank;

    public Integer getSchoolId() {
        return schoolId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public Integer getScoreSegment() {
        return scoreSegment;
    }

    public void setScoreSegment(Integer scoreSegment) {
        this.scoreSegment = scoreSegment;
    }

    public List<Integer> getSchoolIds() {
        return schoolIds;
    }

    public void setSchoolIds(List<Integer> schoolIds) {
        this.schoolIds = schoolIds;
    }

    public Integer getMaxScoreSegment() {
        return maxScoreSegment;
    }

    public void setMaxScoreSegment(Integer maxScoreSegment) {
        this.maxScoreSegment = maxScoreSegment;
    }

    public Integer getMinScoreSegment() {
        return minScoreSegment;
    }

    public void setMinScoreSegment(Integer minScoreSegment) {
        this.minScoreSegment = minScoreSegment;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public Integer getMinScore() {
        return minScore;
    }

    public void setMinScore(Integer minScore) {
        this.minScore = minScore;
    }

    public List<String> getSchoolTypes() {
        return schoolTypes;
    }

    public void setSchoolTypes(List<String> schoolTypes) {
        this.schoolTypes = schoolTypes;
    }

    public Integer getIsAdvisory() {
        return isAdvisory;
    }

    public void setIsAdvisory(Integer isAdvisory) {
        this.isAdvisory = isAdvisory;
    }

    public Integer getTagsValue() {
        return tagsValue;
    }

    public void setTagsValue(Integer tagsValue) {
        this.tagsValue = tagsValue;
    }

    public List<Integer> getRefTypeIds() {
        return refTypeIds;
    }

    public void setRefTypeIds(List<Integer> refTypeIds) {
        this.refTypeIds = refTypeIds;
    }

    public List<Integer> getProvinceIds() {
        return provinceIds;
    }

    public void setProvinceIds(List<Integer> provinceIds) {
        this.provinceIds = provinceIds;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

}
