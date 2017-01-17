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
package dayan.eve.repository;

import dayan.eve.model.School;
import dayan.eve.model.query.SearchQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author xsg
 */
@Mapper
public interface SchoolAdvisoryRepository {

    List<Integer> query();

    List<School> querySchools(SearchQuery query);

    void insert(Integer schoolId);

    void delete(Integer schoolId);
}
