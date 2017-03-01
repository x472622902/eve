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

import dayan.eve.model.query.TopicQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xsg
 */
@Mapper
public interface TopicLikeRepository {

    void insert(@Param("accountId") Integer accountId, @Param("topicId") Integer topicId);

    void update(TopicQuery query);

    List<TopicQuery> query(TopicQuery query);

    Boolean queryExisted(@Param("accountId") Integer accountId, @Param("topicId") Integer topicId);

    List<Integer> queryTopicIdsByAccountId(Integer accountId);

}
