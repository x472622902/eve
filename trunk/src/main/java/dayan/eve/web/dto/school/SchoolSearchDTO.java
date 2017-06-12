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
 * @author xuesg
 */
@Data
public class SchoolSearchDTO {

    private String query;
    private List<String> provinceIds;
    private List<String> tagIds;
    private PaginationDTO paging;
    private String schoolHashId;
    private List<String> schoolTypeIds;
    private Boolean isAdvisory;
    private Boolean cs;
    private Boolean readAll;
}
