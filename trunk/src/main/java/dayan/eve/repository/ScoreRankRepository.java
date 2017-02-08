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

import dayan.eve.model.query.RecommendQuery;

/**
 *
 * @author xsg
 */
public interface ScoreRankRepository {

    /**
     * 根据排名获取分数
     *
     * @param recommendQuery
     * @return
     */
    public Integer queryMaxScore(RecommendQuery recommendQuery);

}
