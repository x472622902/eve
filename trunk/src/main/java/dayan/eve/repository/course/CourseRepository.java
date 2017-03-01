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
package dayan.eve.repository.course;


import dayan.eve.model.course.Course;
import dayan.eve.model.enumeration.CourseType;
import dayan.eve.model.query.CourseQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author xsg
 */
@Mapper
public interface CourseRepository {

    List<Course> query(CourseQuery query);

    List<Course> queryMy(CourseQuery query);

    Integer count(CourseQuery query);

    Integer countMy(CourseQuery query);

    public void insert(Course course);

    public void updateBuyCount(Integer courseId);

    String queryBundleIds(Integer courseId);

    String queryType(Integer courseId);
}
