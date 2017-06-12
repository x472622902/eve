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
package dayan.eve.web.dto.school;

import dayan.eve.model.school.RecScoreItem;

import java.util.List;

/**
 *
 * @author xsg
 */
public class RecommendScoreReadResultV20 {

    List<RecScoreItem> list;

    public RecommendScoreReadResultV20(List<RecScoreItem> list) {
        this.list = list;
    }

    public RecommendScoreReadResultV20() {
    }

    public List<RecScoreItem> getList() {
        return list;
    }

    public void setList(List<RecScoreItem> list) {
        this.list = list;
    }

}
