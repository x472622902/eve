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
package dayan.eve.model.query;


import dayan.eve.model.Pagination;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author xsg
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HotRecommendQuery extends Pagination {
    private Integer accountId;
    private Integer schoolId;
    private Integer provinceId;
    private String subjectType;
    private Integer scoreSegment;
    private List<Integer> schoolIds;
    private Integer maxScoreSegment;
    private Integer minScoreSegment;
    private Integer maxScore;
    private Integer minScore;
    private List<String> schoolTypes;
    private Boolean isAdvisory;
    private Integer tagsValue;
    private List<Integer> refTypeIds;
    private List<Integer> provinceIds;//学校所在省份
    private Integer rank;
}
