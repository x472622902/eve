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
package dayan.eve.repository.topic;

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
     */
    List<Topic> queryTopics(TopicQuery query);

    /**
     * 根据贴的id查询贴的内容
     */
    Topic queryTopicById(Integer topicId);

    /**
     * 发帖或回帖
     */
    void insertTopic(Topic topic);

    /**
     * 查询所有的主贴数
     */
    Integer countTopic(TopicQuery query);

    /**
     * 查询该主贴下的回帖数
     */
    Integer countReply(Integer topicId);

    /**
     * 查询所有自己发的贴
     */
    Integer countMyTopic(TopicQuery query);

    /**
     * 查询所有自己回复的贴
     */
    Integer countMyReply(TopicQuery query);

    /**
     * 更新主贴的回复数
     */
    void updateReplyCount(Integer topicId);

    /**
     * 根据主贴的id获取发帖人的用户id
     */
    Integer queryAccountIdByTopicId(Integer topicId);

    /**
     * 根据帖子部分信息查找帖子全部信息
     */
    List<Topic> queryTopicsByTopic(TopicQuery query);

    void deleteTopics(TopicDeleteQuery query);

    /**
     * 更新点赞次数
     */
    Integer queryId(Topic topic);

    /**
     * 查询回复贴的评论
     */
    List<Topic> queryComments(Integer parentId);

    /**
     * 查看某用户的所有发贴和回复（包含原帖）
     */
    List<Topic> queryMyTopic(TopicQuery query);

    List<AccountInfo> queryAccountByTopic(TopicQuery query);

    //用于删除贴
    void delete(Integer id);

    //用于统计被删的回复数
    Integer countComment(Integer id);

    void updateReplyNum(Integer topicId);

    /**
     * 更新帖子的被赞数、被踩数、回复数、置顶
     */
    void updateTopic(TopicQuery query);

    Integer countSearch(TopicQuery query);

    List<Topic> queryAllTopics();

    /**
     * 查询回帖定位
     */
    Integer queryPosition(TopicQuery query);

    void updateLikeNum(Integer topicId);

    void updateDislikeNum(Integer topicId);
}
