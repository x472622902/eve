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
package dayan.eve.web.dto.topic;

import dayan.eve.model.Pagination;

/**
 *
 * @author xuesg
 */
public class TopicReadQueryDTO {

    private String id;
    private String accountId;
    private Boolean isMyTopic;
    private String topicId;
    private Pagination paging;
    private Boolean isPopular;
    private String parentId;
    private String themeId;
    private Boolean isLive;

    public String getId() {
        return id;
    }

    public Boolean getIsLive() {
        return isLive;
    }

    public void setIsLive(Boolean isLive) {
        this.isLive = isLive;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean isIsMyTopic() {
        return isMyTopic;
    }

    public void setIsMyTopic(Boolean isMyTopic) {
        this.isMyTopic = isMyTopic;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public synchronized Pagination getPaging() {
        if (paging == null) {
            paging = new Pagination();
        }
        return paging;
    }

    public void setPaging(Pagination paging) {
        this.paging = paging;
    }

    public Boolean getIsPopular() {
        return isPopular;
    }

    public void setIsPopular(Boolean isPopular) {
        this.isPopular = isPopular;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

}
