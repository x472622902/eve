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
package dayan.eve.service;

import dayan.eve.model.PageResult;
import dayan.eve.model.Pager;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.query.TopicQuery;
import dayan.eve.model.topic.Topic;
import dayan.eve.model.topic.TopicTheme;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author xsg
 */
public interface TopicService {

    //update in 20151204
    public PageResult<Topic> readTopics(TopicQuery query);

    /**
     * 发贴或者回复
     */
    public Topic createTopic(Topic topic, MultipartFile[] files);

    /**
     * 点赞
     */
    public void like(Integer accountId, Integer topicId);

    /**
     * 踩
     */
    public void dislike(Integer accountId, Integer topicId);

    public PageResult<Topic> readTimelines(TopicQuery query);

    public void delete(TopicQuery query);

    /**
     * 置顶或者取消置顶
     */
    public void setTop(Integer topicId);

    /**
     * 搜索贴
     */
    public List<Topic> search(TopicQuery query);

    /**
     * 搜索发帖用户
     */
    public List<AccountInfo> searchAccount(TopicQuery query);

    public Pager countSearch(TopicQuery query);

    /**
     * 标记印章
     */
    public void setStamp(TopicQuery query);

    /**
     * 读取所有主题
     */
    public PageResult<TopicTheme> readAllThemes(TopicQuery query);

    /**
     * 添加主题
     */
    public void addTheme(String theme);

    public void createLiveTopic(Topic topic, MultipartFile[] files, String accountHashId);

    public void updateLiveStatus(Integer topicId);
}
