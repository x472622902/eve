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
package dayan.eve.repository.course;


import dayan.eve.model.course.Course;
import dayan.eve.model.course.CourseTrade;
import dayan.eve.model.query.CourseQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author xsg
 */
@Mapper
public interface CourseTradeRepository {

    List<Integer> queryTradeIds(CourseQuery query);

    Boolean queryExisted(CourseQuery query);
    
    List<CourseQuery> queryTrades(CourseQuery query);
    
    List<Course> queryCourse(CourseQuery query);

    List<Course> queryByTradeNo(CourseQuery query);

    void insert(CourseQuery query);

    void update(CourseQuery query);

    void insertAlipayTrade(CourseTrade courseTrade);
}
