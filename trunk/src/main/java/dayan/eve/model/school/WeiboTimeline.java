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

import dayan.eve.model.WeiboUser;
import weibo4j.model.Status;

import java.util.Date;
import java.util.List;

/**
 *
 * @author xsg
 */
public class WeiboTimeline {

    private String id;
    private Date createTime;//创建时间
    private String text;//微博内容
    private WeiboSource source;//微博来源
    private Integer repostsCount;//转发次数
    private Integer commentsCount;//评论数
    private String thumbnailPicUrl;//缩略图片地址
    private String middlePicUrl;//中等尺寸图片地址
    private String originalPicUrl;//原始图片地址
    private List<String> thumbnailPicUrls;
    private List<String> middlePicUrls;
    private List<String> originalPicUrls;
    private String imageUrlsStr;
    private WeiboUser user;//微博作者
    private String username;
    private String portraitUrl;
    private Integer schoolId;
    private String originId;
    private WeiboTimeline retweetedTimeline;

    public WeiboTimeline() {
    }

    public WeiboTimeline(Status status) {
        this.id = status.getId();
        this.middlePicUrl = status.getBmiddlePic();
        this.commentsCount = status.getCommentsCount();
        this.createTime = status.getCreatedAt();
        this.repostsCount = status.getRepostsCount();
        this.text = status.getText();
        this.username = status.getUser().getScreenName();
        this.portraitUrl=status.getUser().getavatarLarge();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public WeiboSource getSource() {
        return source;
    }

    public void setSource(WeiboSource source) {
        this.source = source;
    }

    public Integer getRepostsCount() {
        return repostsCount;
    }

    public void setRepostsCount(Integer repostsCount) {
        this.repostsCount = repostsCount;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getThumbnailPicUrl() {
        return thumbnailPicUrl;
    }

    public void setThumbnailPicUrl(String thumbnailPicUrl) {
        this.thumbnailPicUrl = thumbnailPicUrl;
    }

    public String getMiddlePicUrl() {
        return middlePicUrl;
    }

    public void setMiddlePicUrl(String middlePicUrl) {
        this.middlePicUrl = middlePicUrl;
    }

    public String getOriginalPicUrl() {
        return originalPicUrl;
    }

    public void setOriginalPicUrl(String originalPicUrl) {
        this.originalPicUrl = originalPicUrl;
    }

    public WeiboUser getUser() {
        return user;
    }

    public void setUser(WeiboUser user) {
        this.user = user;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public WeiboTimeline getRetweetedTimeline() {
        return retweetedTimeline;
    }

    public void setRetweetedTimeline(WeiboTimeline retweetedTimeline) {
        this.retweetedTimeline = retweetedTimeline;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public List<String> getThumbnailPicUrls() {
        return thumbnailPicUrls;
    }

    public void setThumbnailPicUrls(List<String> thumbnailPicUrls) {
        this.thumbnailPicUrls = thumbnailPicUrls;
    }

    public List<String> getMiddlePicUrls() {
        return middlePicUrls;
    }

    public void setMiddlePicUrls(List<String> middlePicUrls) {
        this.middlePicUrls = middlePicUrls;
    }

    public List<String> getOriginalPicUrls() {
        return originalPicUrls;
    }

    public void setOriginalPicUrls(List<String> originalPicUrls) {
        this.originalPicUrls = originalPicUrls;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public String getImageUrlsStr() {
        return imageUrlsStr;
    }

    public void setImageUrlsStr(String imageUrlsStr) {
        this.imageUrlsStr = imageUrlsStr;
    }

}
