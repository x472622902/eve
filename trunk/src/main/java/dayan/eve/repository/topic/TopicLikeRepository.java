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
package dayan.eve.repository.topic;

import dayan.eve.model.query.TopicQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author xsg
 */
@Mapper
public interface TopicLikeRepository {

    void insert(TopicQuery query);
    
    void update(TopicQuery query);
    
    List<TopicQuery> query(TopicQuery query);
    
    List<Integer> queryTopicIdsByAccountId(Integer accountId);

}
