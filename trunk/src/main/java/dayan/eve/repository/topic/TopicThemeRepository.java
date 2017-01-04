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
import dayan.eve.model.topic.TopicTheme;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author xsg
 */
@Mapper
public interface TopicThemeRepository {

    void insert(String name);

    /**
     * 读取所有话题
     *
     * @param query
     * @return
     */
    List<TopicTheme> queryAll(TopicQuery query);

    Integer count();

    /**
     * 更新话题下的帖子数
     *
     * @param id
     */
    void updateTopicCount(Integer id);

}
