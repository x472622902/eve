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
import dayan.eve.model.query.SchoolQuery;
import dayan.eve.model.query.SearchQuery;
import dayan.eve.model.school.RecommendSchool;
import dayan.eve.web.dto.school.SchoolAdmissionDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author xsg
 */
@Mapper
public interface SchoolRepository {

    /**
     * 按学校的Id查询得到学校的tags，name，address
     *
     * @param schoolId
     * @return
     */
    public RecommendSchool queryById(Integer schoolId);

    /**
     *
     * @param schoolId
     * @return
     */
    public String queryName(Integer schoolId);

    /**
     * 按学校的name查询得到学校的tags，id，address
     *
     * @param name
     * @return
     */
    public RecommendSchool queryByName(String name);

    /**
     * 查询的学校的基本概况
     *
     * @param schoolId
     * @return
     */
    public School queryProfile(Integer schoolId);

    /**
     * 查询学校的招生信息
     *
     * @param schoolId
     * @return
     */
    public SchoolAdmissionDTO queryAdmission(Integer schoolId);

    /**
     * 搜索学校
     *
     * @param query
     * @return
     */
    public List<School> search(SearchQuery query);

    /**
     *
     * @param query
     * @return
     */
    public Integer count(SearchQuery query);

    /**
     * 获取所有学校名字
     *
     * @return
     */
    public List<String> queryAllNames();

    /**
     * 更新开通咨询平台的学校
     *
     * @param query
     */
    public void updateAdvisory(SearchQuery query);

    /**
     * 更新有客服的学校
     *
     * @param query
     */
    public void updateCs(SearchQuery query);

    public void clearCs();

    public void cleanAdvisory();

    List<School> query(SchoolQuery query);

}
