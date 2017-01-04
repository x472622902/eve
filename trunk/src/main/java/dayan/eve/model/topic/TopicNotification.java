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


import dayan.eve.model.account.AccountInfo;

import java.util.Date;

/**
 * @author xsg
 */
public class TopicNotification {

    public enum Action {

        Like, Comment
    }

    private Action action;
    private Topic topic;
    private Topic originTopic;
    private AccountInfo accountInfo;
    private Date time;
    private String title = "您的帖子已有回复";
    private String message;
    private Integer topicId;
    private Boolean isMyTopic = true;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = "您的帖子：[" + message + "]已有回复，快去查看吧！";
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Boolean getIsMyTopic() {
        return isMyTopic;
    }

    public void setIsMyTopic(Boolean isMyTopic) {
        this.isMyTopic = isMyTopic;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Topic getOriginTopic() {
        return originTopic;
    }

    public void setOriginTopic(Topic originTopic) {
        this.originTopic = originTopic;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}
