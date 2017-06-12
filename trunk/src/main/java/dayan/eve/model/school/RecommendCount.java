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
public class RecommendCount {

    private Integer schoolId;
    private Integer provinceId;
    private String subjectType;
    private Integer count;

    public RecommendCount() {
    }

    public RecommendCount(Integer schoolId, Integer provinceId, String subjectType, Integer count) {
        this.schoolId = schoolId;
        this.provinceId = provinceId;
        this.subjectType = subjectType;
        this.count = count;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
