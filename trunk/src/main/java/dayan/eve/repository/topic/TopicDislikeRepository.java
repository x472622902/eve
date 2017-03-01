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

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author xsg
 */
@Mapper
public interface TopicDislikeRepository {

    void insert(@Param("accountId") Integer accountId, @Param("topicId") Integer topicId);
    
    List<Integer> query(Integer accountId);

    boolean queryExisted(@Param("accountId") Integer accountId, @Param("topicId") Integer topicId);
    
}
