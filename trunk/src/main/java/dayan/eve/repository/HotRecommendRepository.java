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

import dayan.eve.model.query.HotRecommendQuery;
import dayan.eve.model.school.HotRecommend;
import dayan.eve.model.school.HotSchool;

import java.util.List;

/**
 *
 * @author
 */
public interface HotRecommendRepository {

    void multiInsert(List<HotRecommend> list);

    /**
     * 每当推荐后根据学校id，省份id，文理科更新被推荐次数
     *
     * @param query
     */
    void update(HotRecommendQuery query);

    /**
     * 查询满足条件的分数段
     *
     * @param query
     * @return
     */
    public List<HotRecommend> query(HotRecommendQuery query);

    /**
     * 查询满足条件的学校
     *
     * @param query
     * @return
     */
    public List<HotSchool> querySchool(HotRecommendQuery query);

    /**
     * 查询学校总数
     *
     * @param query
     * @return
     */
    public Integer countSchools(HotRecommendQuery query);

    /**
     * 查询满足条件的id，用于该条件下的分数段是否存在
     *
     * @param hr
     * @return
     */
    public Boolean queryExisted(HotRecommend hotRecommend);
}
