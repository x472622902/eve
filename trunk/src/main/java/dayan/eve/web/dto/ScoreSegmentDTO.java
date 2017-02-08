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
package dayan.eve.web.dto;

import dayan.eve.model.school.HotRecommend;

import java.util.List;

/**
 * @author xsg
 */
public class ScoreSegmentDTO {

    private String provinceName;
    private String subjectType;
    private List<HotRecommend> list;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public List<HotRecommend> getList() {
        return list;
    }

    public void setList(List<HotRecommend> list) {
        this.list = list;
    }

}
