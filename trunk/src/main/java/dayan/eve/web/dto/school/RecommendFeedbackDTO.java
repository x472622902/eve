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

/**
 *
 * @author xsg
 */
@Data
public class RecommendFeedbackDTO {

    private String provinceId;
    private String score;
    private String subjectTypeId;
    private String starLevel;
    private String content;
    private String majorCode;
    private String rank;

}
