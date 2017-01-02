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
package dayan.eve.repository;

import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.query.TopicDeleteQuery;
import dayan.eve.model.query.TopicQuery;
import dayan.eve.model.topic.Topic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xsg
 */
@Mapper
public interface TopicRepository {

    /**
     * 查看所有主贴或查看该贴下的所有回复
     *
     * @param query
     * @return
     */
    List<Topic> queryTopics(TopicQuery query);

    /**
     * 根据贴的id查询贴的内容
     *
     * @param topicId
     * @return
     */
    Topic queryTopicById(Integer topicId);

    /**
     * 发帖或回帖
     *
     * @param topic
     */
    void insertTopic(Topic topic);

    /**
     * 查询所有的主贴数
     *
     * @param query
     * @return
     */
    Integer countTopic(TopicQuery query);

    /**
     * 查询该主贴下的回帖数
     *
     * @param topicId
     * @return
     */
    Integer countReply(Integer topicId);

    /**
     * 查询所有自己发的贴
     *
     * @param query
     * @return
     */
    Integer countMyTopic(TopicQuery query);

    /**
     * 查询所有自己回复的贴
     *
     * @param query
     * @return
     */
    Integer countMyReply(TopicQuery query);

    /**
     * 更新主贴的回复数
     *
     * @param topicId
     */
    void updateReplyCount(Integer topicId);

    /**
     * 根据主贴的id获取发帖人的用户id
     *
     * @param topicId
     * @return
     */
    Integer queryAccountIdByTopicId(Integer topicId);

    /**
     * 根据帖子部分信息查找帖子全部信息
     *
     * @param query
     * @return
     */
    List<Topic> queryTopicsByTopic(TopicQuery query);

    void deleteTopics(TopicDeleteQuery query);

    /**
     * 更新点赞次数
     *
     * @param query
     */
    void updateLikeNum(TopicQuery query);

    Integer queryId(Topic topic);

    /**
     * 查询回复贴的评论
     *
     * @param parentId
     * @return
     */
    List<Topic> queryComments(Integer parentId);

    /**
     * 查看某用户的所有发贴和回复（包含原帖）
     *
     * @param query
     * @return
     */
    List<Topic> queryMyTopic(TopicQuery query);

    List<AccountInfo> queryAccountByTopic(TopicQuery query);

    //用于删除贴
    void delete(Integer id);

    //用于统计被删的回复数
    Integer countComment(Integer id);

    void updateReplyNum(TopicQuery query);

    /**
     * 更新帖子的被赞数、被踩数、回复数、置顶
     *
     * @param query
     */
    public void updateTopic(TopicQuery query);

    public Integer countSearch(TopicQuery query);

    public List<Topic> queryAllTopics();

    /**
     * 查询回帖定位
     *
     * @param query
     * @return
     */
    public Integer queryPosition(TopicQuery query);
}
