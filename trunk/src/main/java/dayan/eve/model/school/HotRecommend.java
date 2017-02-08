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
public class HotRecommend {

    private Integer schoolId;
    private Integer provinceId;
    private String subjectType;
    private Integer scoreSegment;//分数段
    private Integer followerCount;//关注者个数
    private Integer recommendCount;//该校被推荐次数
    private Integer searchCount;//该校被搜索次数
    private String schoolName;
    private float proportion;//百分比
    private Integer maxScore;
    private Integer minScore;

    public HotRecommend() {
    }

    public HotRecommend(Integer schoolId, Integer provinceId, String subjectType) {
        this.schoolId = schoolId;
        this.provinceId = provinceId;
        this.subjectType = subjectType;
    }

    public Integer getSchoolId() {
        return schoolId;
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

    public Integer getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }

    public Integer getRecommendCount() {
        return recommendCount;
    }

    public void setRecommendCount(Integer recommendCount) {
        this.recommendCount = recommendCount;
    }

    public Integer getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(Integer searchCount) {
        this.searchCount = searchCount;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public float getProportion() {
        return proportion;
    }

    public void setProportion(float proportion) {
        this.proportion = proportion;
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

}
