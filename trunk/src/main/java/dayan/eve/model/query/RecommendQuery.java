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
import dayan.eve.model.enumeration.SortType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xsg
 */
public class RecommendQuery extends Pagination {

    private Integer rank;
    private String provinceName;
    private Integer provinceId;
    private Integer score;
    private String subjectType;
    private Integer majorId;
    private List<String> schoolTypes;
    private Boolean isAdvisory;
    private Integer tagsValue;
    private List<Integer> refTypeIds;
    private List<Integer> provinceIds;//学校所在省份
    private String year;
    private String majorTypeCode;
    private List<Integer> schoolIds;//开设该专业小类的学校id列表
    private String majorCode;
    private String requestIp;
    private SortType sortType;
    private Boolean cs;

    public Integer getTagsValue() {
        return tagsValue;
    }

    public void setTagsValue(Integer tagsValue) {
        this.tagsValue = tagsValue;
    }

    public List<String> getSchoolTypes() {
        return schoolTypes;
    }

    public void setSchoolTypes(List<String> schoolTypes) {
        this.schoolTypes = schoolTypes;
    }

    public Boolean getAdvisory() {
        return isAdvisory;
    }

    public void setAdvisory(Boolean advisory) {
        isAdvisory = advisory;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public List<Integer> getRefTypeIds() {
        return refTypeIds;
    }

    public void setRefTypeIds(List<String> refTypeIds) {
        if (refTypeIds == null || refTypeIds.isEmpty()) {
            this.refTypeIds = null;
        } else {
            this.refTypeIds = refTypeIds.stream().map(Integer::valueOf).collect(Collectors.toList());
        }
    }

    public List<Integer> getProvinceIds() {
        return provinceIds;
    }

    public void setProvinceIds(List<String> provinceIds) {
        if (provinceIds == null || provinceIds.isEmpty()) {
            this.provinceIds = null;

        } else {
            List<Integer> list = new ArrayList<>();
            for (String IdStr : provinceIds) {
                list.add(Integer.valueOf(IdStr));
            }
            this.provinceIds = list;
        }
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMajorTypeCode() {
        return majorTypeCode;
    }

    public void setMajorTypeCode(String majorTypeCode) {
        this.majorTypeCode = majorTypeCode;
    }

    public List<Integer> getSchoolIds() {
        return schoolIds;
    }

    public void setSchoolIds(List<Integer> schoolIds) {
        this.schoolIds = schoolIds;
    }

    public String getMajorCode() {
        return majorCode;
    }

    public void setMajorCode(String majorCode) {
        this.majorCode = majorCode;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public SortType getSortType() {
        return sortType;
    }

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    public Boolean getCs() {
        return cs;
    }

    public void setCs(Boolean cs) {
        this.cs = cs;
    }

}
