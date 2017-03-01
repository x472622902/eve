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
package dayan.eve.model.topic;

import dayan.eve.model.account.AccountInfoExt;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author xsg
 */
public class Topic {

    public enum LiveStatus {

        Wait, Living, End
    }

    private Integer id;
    private String content;
    private Integer topicId;
    private Integer parentId;
    private Integer replyCount;//回复数
    private Date topicDate;//创贴时间
    private Date modifyDate;//帖子更新时间
    private Integer likeNum;//点赞数
    private Boolean isLiked = false;
    private Boolean isDisliked = false;
    private Integer schoolId;
    private String schoolName;
    private List<String> imageUrls = new LinkedList<>();//大图地址
    private List<String> thumbnailUrls = new LinkedList<>();//缩略图地址
    private List<String> middleImageUrls = new LinkedList<>();//中图地址
    private Topic originTopic;//原帖
    private Boolean isPinTop = false;//是否置顶
    private String stampUrl;//印章
    private List<Topic> comments;//楼层回复
    private Boolean isDeleted;
    private Integer dislikeNum;//被踩数
    private TopicTheme theme = new TopicTheme();//主题
    private Integer position;//定位

    //用户部分
    private Integer accountId;
    private String accountHashId;
    private String nickname;
    private String province;
    private String portraitUrl;
    private String subjectType;
    private Integer accountLevel;
    private String gender;
    private AccountInfoExt accountExt;

    //直播室
    private Boolean isLive = false;//是否直播贴
    private LiveStatus liveStatus;//直播状态
    private String csDisplayName;//人工客服显示名
    private String liveTime;//直播时间

    public String getLiveTime() {
        return liveTime;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public void setLiveTime(String liveTime) {
        this.liveTime = liveTime;
    }

    public AccountInfoExt getAccountExt() {
        return accountExt;
    }

    public void setAccountExt(AccountInfoExt accountExt) {
        this.accountExt = accountExt;
    }

    public String getCsDisplayName() {
        return csDisplayName;
    }

    public void setCsDisplayName(String csDisplayName) {
        this.csDisplayName = csDisplayName;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public Boolean getIsLive() {
        return isLive;
    }

    public void setIsLive(Boolean isLive) {
        this.isLive = isLive;
    }

    public void setIsLive(Integer isLive) {
        this.isLive = isLive == 1;
    }

    public LiveStatus getLiveStatus() {
        return liveStatus;
    }

    //    public void setLiveStatus(String liveStatus) {
//        switch (liveStatus) {
//            case "Wait":
//                this.liveStatus = LiveStatus.Wait;
//                break;
//            case "Living":
//                this.liveStatus = LiveStatus.Living;
//                break;
//            case "End":
//                this.liveStatus = LiveStatus.End;
//                break;
//        }
//    }
    public void setLiveStatus(LiveStatus liveStatus) {
        this.liveStatus = liveStatus;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public String getNickname() {
        return nickname == null ? "" : nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Date getTopicDate() {
        return topicDate;
    }

    public void setTopicDate(Date topicDate) {
        this.topicDate = topicDate;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<String> getThumbnailUrls() {
        return thumbnailUrls;
    }

    public void setThumbnailUrls(List<String> thumbnailUrls) {
        this.thumbnailUrls = thumbnailUrls;
    }

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

    public Topic getOriginTopic() {
        return originTopic;
    }

    public void setOriginTopic(Topic originTopic) {
        this.originTopic = originTopic;
    }

    public String getAccountHashId() {
        return accountHashId;
    }

    public void setAccountHashId(String accountHashId) {
        this.accountHashId = accountHashId;
    }

    public Integer getAccountLevel() {
        return accountLevel;
    }

    public void setAccountLevel(Integer accountLevel) {
        this.accountLevel = accountLevel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Boolean getIsPinTop() {
        return isPinTop;
    }

    public void setIsPinTop(Integer isPinTop) {
        this.isPinTop = isPinTop == 1;
    }

    public String getStampUrl() {
        return stampUrl;
    }

    public void setStampUrl(String stampUrl) {
        this.stampUrl = stampUrl;
    }

    public List<Topic> getComments() {
        return comments;
    }

    public void setComments(List<Topic> comments) {
        this.comments = comments;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted == 1;
    }

    public Integer getDislikeNum() {
        return dislikeNum;
    }

    public void setDislikeNum(Integer dislikeNum) {
        this.dislikeNum = dislikeNum;
    }

    public Boolean getIsDisliked() {
        return isDisliked;
    }

    public void setIsDisliked(Boolean isDisliked) {
        this.isDisliked = isDisliked;
    }

    public List<String> getMiddleImageUrls() {
        return middleImageUrls;
    }

    public void setMiddleImageUrls(List<String> middleImageUrls) {
        this.middleImageUrls = middleImageUrls;
    }

    public TopicTheme getTheme() {
        return theme;
    }

    public void setTheme(TopicTheme theme) {
        this.theme = theme;
    }

    public Boolean getLiked() {
        return isLiked;
    }

    public void setLiked(Boolean liked) {
        isLiked = liked;
    }

    public Boolean getDisliked() {
        return isDisliked;
    }

    public void setDisliked(Boolean disliked) {
        isDisliked = disliked;
    }

    public Boolean getPinTop() {
        return isPinTop;
    }

    public void setPinTop(Boolean pinTop) {
        isPinTop = pinTop;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getLive() {
        return isLive;
    }

    public void setLive(Boolean live) {
        isLive = live;
    }
}
