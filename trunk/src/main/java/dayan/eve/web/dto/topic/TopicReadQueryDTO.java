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
package dayan.eve.web.dto.topic;

import dayan.eve.web.dto.PaginationDTO;

/**
 * @author xuesg
 */
public class TopicReadQueryDTO {

    private String id;
    private String accountId;
    private Boolean isMyTopic;
    private String topicId;
    private PaginationDTO paging;
    private Boolean isPopular;
    private String parentId;
    private String themeId;
    private Boolean isLive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Boolean getMyTopic() {
        return isMyTopic;
    }

    public void setMyTopic(Boolean myTopic) {
        isMyTopic = myTopic;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public PaginationDTO getPaging() {
        return paging;
    }

    public void setPaging(PaginationDTO paging) {
        this.paging = paging;
    }

    public Boolean getPopular() {
        return isPopular;
    }

    public void setPopular(Boolean popular) {
        isPopular = popular;
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

    public Boolean getLive() {
        return isLive;
    }

    public void setLive(Boolean live) {
        isLive = live;
    }

}
