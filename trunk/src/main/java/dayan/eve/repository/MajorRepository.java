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
package dayan.eve.repository;

import dayan.eve.model.major.Major;
import dayan.eve.model.major.SearchMajor;
import dayan.eve.model.query.SearchQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author xsg
 */
@Mapper
public interface MajorRepository {

    /**
     * 搜索专业
     *
     * @param query
     * @return
     */
    public List<SearchMajor> search(SearchQuery query);
    
    /**
     * 查询学校相关的专业
     *
     * @param schoolId
     * @return
     */
    public List<SearchMajor> queryBySchool(Integer schoolId);

    /**
     * *
     * 专业详情查询
     *
     *
     * @param majorId
     * @
     * @return
     */
    public Major queryById(Integer majorId);

    /**
     *
     * @param type
     * @return
     */
    public List<Major> queryByType(String type);

    /**
     * 获得该查询条件下返回的专业总数
     *
     * @param query
     * @return
     */
    public Integer count(SearchQuery query);
    
    public List<String> queryAllNames();
    
}
