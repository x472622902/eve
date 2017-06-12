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
package dayan.eve.model.school;

import com.alibaba.fastjson.JSON;
import dayan.eve.model.SchoolTag;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author xsg
 */
public class RecommendSchoolItem {

    private String schoolHashId;
    private String platformHashId;
    private String name;
    private String logoUrl;
    private List<SchoolTag> tags;
    private String address;
    private Integer recommendScore;
    private Float probability;
    private Integer refTypeId;
    private Integer refScore;
    private Integer minScore;
    private String batch;
    private Integer schoolRank;
    private String city;
    private Integer controlLine;
    private Boolean cs;

    public String getPlatformHashId() {
        return platformHashId;
    }

    public void setPlatformHashId(String platformHashId) {
        this.platformHashId = platformHashId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public List<SchoolTag> getTags() {
        return tags;
    }

    public void setTags(String tag) {
        if (StringUtils.isEmpty(tag)) {
            return;
        }
        List<String> list = JSON.parseArray(tag, String.class);
        this.tags = list.stream().map(SchoolTag::new).collect(Collectors.toList());
    }

    public void setTags(List<SchoolTag> tags) {
        this.tags = tags;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSchoolHashId() {
        return schoolHashId;
    }

    public void setSchoolHashId(String schoolHashId) {
        this.schoolHashId = schoolHashId;
    }

    public Integer getRecommendScore() {
        return recommendScore;
    }

    public void setRecommendScore(Integer recommendScore) {
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

    public Integer getRefScore() {
        return refScore;
    }

    public void setRefScore(Integer refScore) {
        this.refScore = refScore;
    }

    public Integer getMinScore() {
        return minScore;
    }

    public void setMinScore(Integer minScore) {
        this.minScore = minScore;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Integer getSchoolRank() {
        return schoolRank;
    }

    public void setSchoolRank(Integer schoolRank) {
        this.schoolRank = schoolRank;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
