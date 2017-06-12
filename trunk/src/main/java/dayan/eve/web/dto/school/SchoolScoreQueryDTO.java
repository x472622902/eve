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
package dayan.eve.web.dto.school;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xuesg
 */
@Data
public class SchoolScoreQueryDTO {

    @NotNull
    private String schoolHashId;
    private String provinceId;
    private String year;
    private String subjectTypeId;
    private String batch;
    private String score;

}
