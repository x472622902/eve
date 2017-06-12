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

/**
 *
 * @author xsg
 */
@Data
public class SchoolWeiboQueryDTO {

    private String schoolHashId;
    private PaginationDTO paging;

}
