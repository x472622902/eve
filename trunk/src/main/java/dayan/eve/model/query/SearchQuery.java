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
public class SearchQuery extends Pagination {

    private Integer schoolId;
    private Integer tagsValue;
    private Integer degreeTypeId;
    private String degreeType;
    private String queryStr;
    private Boolean isAdvisory;
    private Boolean cs;
    private Boolean readAll;
    private List<Integer> provinceIds;
    private List<Integer> tagIds;
    private List<String> subjectTypes;//专业大类的集合
    private List<String> schoolTypes;
    private List<Integer> advisorySchoolIds;
    private List<Integer> schoolIds;
}
