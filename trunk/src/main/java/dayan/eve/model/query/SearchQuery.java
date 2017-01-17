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
 * @author xsg
 */
public class SearchQuery extends Pagination {

    private Integer schoolId;
    private String queryStr;
    private Integer tagsValue;
    private List<Integer> provinceIds;
    private List<Integer> tagIds;
    private List<String> subjectTypes;//专业大类的集合
    private Integer degreeTypeId;
    private String degreeType;
    private List<String> schoolTypes;
    private Integer isAdvisory;
    private Boolean cs;
    private List<Integer> advisorySchoolIds;
    private List<Integer> schoolIds;

    private Boolean readAll = false;

    public Boolean getReadAll() {
        return readAll;
    }

    public void setReadAll(Boolean readAll) {
        this.readAll = readAll;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public SearchQuery(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public SearchQuery() {
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public void setQueryStr(String queryStr) {
        this.queryStr = queryStr;
    }

    public String getQueryStr() {
        return queryStr;
    }

    public Integer getTagsValue() {
        return tagsValue;
    }

    public void setTagsValue(Integer tagsValue) {
        this.tagsValue = tagsValue;
    }

    public List<Integer> getProvinceIds() {
        return provinceIds;
    }

    public void setProvinceIds(List<Integer> provinceIds) {
        this.provinceIds = provinceIds;
    }

    public List<Integer> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Integer> tagIdList) {
        this.tagIds = tagIdList;
    }

    public List<String> getSubjectTypes() {
        return subjectTypes;
    }

    public void setSubjectTypes(List<String> subjectTypes) {
        this.subjectTypes = subjectTypes;
    }

    public Integer getDegreeTypeId() {
        return degreeTypeId;
    }

    public void setDegreeTypeId(Integer degreeTypeId) {
        this.degreeTypeId = degreeTypeId;
    }

    public String getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(String degreeType) {
        this.degreeType = degreeType;
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

    public List<Integer> getAdvisorySchoolIds() {
        return advisorySchoolIds;
    }

    public void setAdvisorySchoolIds(List<Integer> advisorySchoolIds) {
        this.advisorySchoolIds = advisorySchoolIds;
    }

    public Boolean getCs() {
        return cs;
    }

    public void setCs(Boolean cs) {
        this.cs = cs;
    }

    public List<Integer> getSchoolIds() {
        return schoolIds;
    }

    public void setSchoolIds(List<Integer> schoolIds) {
        this.schoolIds = schoolIds;
    }
}
