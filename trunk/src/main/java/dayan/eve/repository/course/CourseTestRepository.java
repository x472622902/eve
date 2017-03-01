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


import dayan.eve.model.course.CourseTestResult;
import dayan.eve.model.query.CourseQuery;
import dayan.eve.model.query.CourseTestResultQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author xsg
 */
@Mapper
public interface CourseTestRepository {

    List<CourseTestResult> query(CourseTestResultQuery query);

    List<CourseTestResult> queryCourseIds(CourseQuery query);

    Integer count(CourseTestResultQuery query);

    CourseTestResult queryDetail(Integer testId);
    
    void insert(CourseTestResult result);
}
