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
package dayan.eve.web.dto;

import java.util.List;

/**
 * @author xsg
 */
public class InfoUpdateDTO {

    private String provinceId;
    private String subjectTypeId;
    private String score;
    private String mobile;
    private String code;
    private String portraitBase64Str;
    private List<String> pluginKeys;
    private String role;
    private String rank;
    private String gender;

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getSubjectTypeId() {
        return subjectTypeId;
    }

    public void setSubjectTypeId(String subjectTypeId) {
        this.subjectTypeId = subjectTypeId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPortraitBase64Str() {
        return portraitBase64Str;
    }

    public void setPortraitBase64Str(String portraitBase64Str) {
        this.portraitBase64Str = portraitBase64Str;
    }

    public List<String> getPluginKeys() {
        return pluginKeys;
    }

    public void setPluginKeys(List<String> pluginKeys) {
        this.pluginKeys = pluginKeys;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
