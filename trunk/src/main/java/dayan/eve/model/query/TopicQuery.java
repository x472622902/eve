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
package dayan.eve.model.query;


import dayan.eve.model.Pagination;
import dayan.eve.model.topic.Topic;

/**
 *
 * @author xuesg
 */
public class TopicQuery extends Pagination {

    private Integer id;
    private Integer topicId;
    private Integer accountId;
    private Integer schoolId;
    private Integer parentId;
    private Boolean isMyTopic;
    private Boolean isBlock = false;
    private Boolean isPopular;
    private Integer num;//用于更新点赞的次数
    private Integer likeUpdateNum;//点赞更新数
    private Integer dislikeUpdateNum;//被踩更新数
    private Integer replyUpdateNum;//回复更新数
    private Boolean isPinTop;
    private String nickname;
    private String content;
    private Boolean isLike;//true是赞，false是踩
    private String stampUrl;//印章
    private Integer themeId;
    private Boolean isLive;
    private Boolean isLiveTop = false;
    private Topic.LiveStatus liveStatus;
    private Integer exAccountId;//不包括的用户id

    public Boolean getIsLiveTop() {
        return isLiveTop;
    }

    public Topic.LiveStatus getLiveStatus() {
        return liveStatus;
    }

    public Integer getExAccountId() {
        return exAccountId;
    }

    public void setExAccountId(Integer exAccountId) {
        this.exAccountId = exAccountId;
    }

    public void setLiveStatus(Topic.LiveStatus liveStatus) {
        this.liveStatus = liveStatus;
    }

    public void setIsLiveTop(Boolean isLiveTop) {
        this.isLiveTop = isLiveTop;
    }

    public Boolean getIsLive() {
        return isLive;
    }

    public void setIsLive(Boolean isLive) {
        this.isLive = isLive;
    }

    public TopicQuery(Integer id) {
        this.id = id;
    }

    public TopicQuery() {
    }

    public String getStampUrl() {
        return stampUrl;
    }

    public void setStampUrl(String stampUrl) {
        this.stampUrl = stampUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Boolean isIsMyTopic() {
        return isMyTopic;
    }

    public void setIsMyTopic(Boolean isMyTopic) {
        this.isMyTopic = isMyTopic;
    }

    public Boolean getIsBlock() {
        return isBlock;
    }

    public void setIsBlock(Boolean isBlock) {
        this.isBlock = isBlock;
    }

    public Boolean getIsPopular() {
        return isPopular;
    }

    public void setIsPopular(Boolean isPopular) {
        this.isPopular = isPopular;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getLikeUpdateNum() {
        return likeUpdateNum;
    }

    public void setLikeUpdateNum(Integer likeUpdateNum) {
        this.likeUpdateNum = likeUpdateNum;
    }

    public Integer getReplyUpdateNum() {
        return replyUpdateNum;
    }

    public void setReplyUpdateNum(Integer replyUpdateNum) {
        this.replyUpdateNum = replyUpdateNum;
    }

    public Boolean getIsPinTop() {
        return isPinTop;
    }

    public void setIsPinTop(Boolean isPinTop) {
        this.isPinTop = isPinTop;
    }

    public Boolean getIsLike() {
        return isLike;
    }

    public void setIsLike(Boolean isLike) {
        this.isLike = isLike;
    }

    public Integer getDislikeUpdateNum() {
        return dislikeUpdateNum;
    }

    public void setDislikeUpdateNum(Integer dislikeUpdateNum) {
        this.dislikeUpdateNum = dislikeUpdateNum;
    }

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

}
