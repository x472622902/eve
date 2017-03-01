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

import dayan.eve.model.course.CourseTestResult;
import dayan.eve.model.query.CourseTestResultQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author xsg
 */
@Mapper
public interface CoursePersonalityRepository {

    Integer queryId(String personalityCode);

    List<CourseTestResult> queryDetails(CourseTestResultQuery query);

    void update(CourseTestResult courseTestResult);

}
