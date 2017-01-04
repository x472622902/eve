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


import dayan.eve.model.topic.TopicImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author xsg
 */
@Mapper
public interface TopicImageRepository {

    void insert(List<TopicImage> list);

    /**
     * 根据帖子id取帖子图片地址
     *
     * @param topic
     * @return
     */
    List<TopicImage> query(Integer topic);

}
