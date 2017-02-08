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

import dayan.eve.model.school.ControlLine;
import dayan.eve.model.query.RecommendQuery;

import java.util.List;

/**
 *
 * @author xsg
 */
public interface ScoreControlLineRepository {

    /**
     * 刷选出符合条件的学校
     *
     * @param recommendQuery
     * @return
     */
    public List<ControlLine> query(RecommendQuery recommendQuery);

}
