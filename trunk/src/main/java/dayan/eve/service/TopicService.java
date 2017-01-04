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

import dayan.eve.model.Pager;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.query.TopicQuery;
import dayan.eve.model.topic.Topic;
import dayan.eve.model.topic.TopicTheme;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *
 * @author xsg
 */
public interface TopicService {

    //update in 20151204
    public List<Topic> readTopics(TopicQuery query);

    public Pager count(TopicQuery query);

    /**
     * 发贴或者回复
     *
     * @param topic
     * @param files 图片文件
     * @return
     */
    public Topic createTopic(Topic topic, MultipartFile[] files);

    /**
     * 点赞
     *
     * @param query
     */
    public void like(TopicQuery query);

    /**
     * 踩
     *
     * @param query
     */
    public void dislike(TopicQuery query);

    public List<Topic> readTimelines(TopicQuery query);

    public void delete(TopicQuery query);

    /**
     * 置顶或者取消置顶
     *
     * @param topicId
     */
    public void setTop(Integer topicId);

    /**
     * 搜索贴
     *
     * @param query
     * @return
     */
    public List<Topic> search(TopicQuery query);

    /**
     * 搜索发帖用户
     *
     * @param query
     * @return
     */
    public List<AccountInfo> searchAccount(TopicQuery query);

    public Pager countSearch(TopicQuery query);

    /**
     * 标记印章
     *
     * @param query
     */
    public void setStamp(TopicQuery query);

    /**
     * 读取所有主题
     *
     * @param query
     * @return
     */
    public List<TopicTheme> readAllThemes(TopicQuery query);

    /**
     * 添加主题
     *
     * @param theme
     */
    public void addTheme(String theme);

    /**
     * 统计所有主题个数
     *
     * @param query
     * @return
     */
    public Pager countThemes(TopicQuery query);

    public void createLiveTopic(Topic topic, MultipartFile[] files, String accounHashId);

    public void updateLiveStatus(Integer topicId);
}
