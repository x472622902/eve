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

import dayan.eve.web.dto.PaginationDTO;
import lombok.Data;

import java.util.List;

/**
 *
 * @author xsg
 */
@Data
public class HotRecommendDTO {

    private String provinceId;
    private String subjectTypeId;
    private String score;
    private String schoolHashId;
    private PaginationDTO paging;
    private List<String> schoolTypeIds;
    private Boolean isAdvisory;
    private List<String> tagIds;
    private List<String> refTypeIds;
    private List<String> provinceIds;
    private String rank;
}
