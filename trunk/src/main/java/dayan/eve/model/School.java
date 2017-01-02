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
package dayan.eve.model;

import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 *
 * @author xsg
 */
public class School {

    private String platformHashId;
    private Integer id;
    private String name;
    private String code;
    private Integer provinceId;
    private Integer tagsValue;
    private String intro;
    private String foundDate;//创建时间
    private String belong;//隶属于
    private String studentNum;//学生人数
    private String url;//学校官网地址
    private String caeNum;//院士人数
    private String keySubjectNum;//重点专业个数
    private String type;//学校类型
    private String ddgNum;//博士点个数
    private String mdgNum;//硕士点个数
    private String address;//学校地址
    private String genderRaTioStr;//男女比例
    private String studentSourceStr;//学生来源
    private JSONArray genderRaTio;
    private JSONArray studentSource;
    private String tuition;//学费信息;
    private String employment;//就业信息
    private String admissionsUrl;//招生地址
    private String admissionsPhone;//招生电话
    private Integer isFollowed;//是否关注
    private List<Integer> tagIds;
    private List<SchoolTag> tags;//学校标签
    private String city;
    private List<String> imageUrls;//学校相关图片地址
    private String hashId;
    private String logoUrl;
    private List<WeiboUser> weiboUsers;
    private String schoolHashId;
    private Boolean cs;//是否有人工客服
    private List<String> names;//曾用名

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
    

    public Integer getIsFollowed() {
        return isFollowed;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setIsFollowed(Integer isFollowed) {
        this.isFollowed = isFollowed;
    }

    public String getPlatformHashId() {
        return platformHashId;
    }

    public void setPlatformHashId(String platformHashId) {
        this.platformHashId = platformHashId;
    }

    public List<Integer> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Integer> tagIds) {
        this.tagIds = tagIds;
    }

    public Integer getId() {
        return id;
    }

    public Integer getTagsValue() {
        return tagsValue;
    }

    public void setTagsValue(Integer tagsValue) {
        this.tagsValue = tagsValue;
    }

    public List<SchoolTag> getTags() {
        return tags;
    }

    public void setTags(List<SchoolTag> tags) {
        this.tags = tags;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getTags() {
//        return tags;
//    }
//
//    public void setTags(String tags) {
//        this.tags = tags;
//    }
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(String foundDate) {
        this.foundDate = foundDate;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public String getCaeNum() {
        return caeNum;
    }

    public void setCaeNum(String caeNum) {
        this.caeNum = caeNum;
    }

    public String getKeySubjectNum() {
        return keySubjectNum;
    }

    public void setKeySubjectNum(String keySubjectNum) {
        this.keySubjectNum = keySubjectNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDdgNum() {
        return ddgNum;
    }

    public void setDdgNum(String ddgNum) {
        this.ddgNum = ddgNum;
    }

    public String getMdgNum() {
        return mdgNum;
    }

    public void setMdgNum(String mdgNum) {
        this.mdgNum = mdgNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStudentSourceStr() {
        return studentSourceStr;
    }

    public void setStudentSourceStr(String studentSourceStr) {
        this.studentSourceStr = studentSourceStr;
    }

    public String getTuition() {
        return tuition;
    }

    public void setTuition(String tuition) {
        this.tuition = tuition;
    }

    public String getEmployment() {
        return employment;
    }

    public void setEmployment(String employment) {
        this.employment = employment;
    }

    public String getGenderRaTioStr() {
        return genderRaTioStr;
    }

    public void setGenderRaTioStr(String genderRaTioStr) {
        this.genderRaTioStr = genderRaTioStr;
    }

    public String getAdmissionsUrl() {
        return admissionsUrl;
    }

    public void setAdmissionsUrl(String admissionsUrl) {
        this.admissionsUrl = admissionsUrl;
    }

    public String getAdmissionsPhone() {
        return admissionsPhone;
    }

    public void setAdmissionsPhone(String admissionsPhone) {
        this.admissionsPhone = admissionsPhone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public List<WeiboUser> getWeiboUsers() {
        return weiboUsers;
    }

    public void setWeiboUsers(List<WeiboUser> weiboUsers) {
        this.weiboUsers = weiboUsers;
    }

    public String getSchoolHashId() {
        return schoolHashId;
    }

    public void setSchoolHashId(String schoolHashId) {
        this.schoolHashId = schoolHashId;
    }

    public Boolean getCs() {
        return cs;
    }

    public void setCs(Integer cs) {
        this.cs = cs == 1;
    }

    public void setCs(Boolean cs) {
        this.cs = cs;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public JSONArray getGenderRaTio() {
        return genderRaTio;
    }

    public void setGenderRaTio(JSONArray genderRaTio) {
        this.genderRaTio = genderRaTio;
    }

//    public void setGenderRaTio(String genderRaTio) {
//        JSONArray array = new JSONArray();
//        if (genderRaTio != null) {
//            array = JSONArray.parseArray(genderRaTio);
//        }
//        this.genderRaTio = array;
//    }
    public void setStudentSource(JSONArray studentSource) {
        this.studentSource = studentSource;
    }

    public JSONArray getStudentSource() {
        return studentSource;
    }
//    public void setStudentSource(String studentSource) {
//        JSONArray array = new JSONArray();
//        if (studentSource != null) {
//            array = JSONArray.parseArray(studentSource);
//        }
//        this.studentSource = array;
//    }

}
