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

import lombok.Data;

/**
 * @author xsg
 */
@Data
public class HolQueryDTO {

    private Boolean isSimplified;
    private String I;
    private String R;
    private String E;
    private String C;
    private String S;
    private String A;
    private String majorTypeCode;
    private String jobClassCode;
    private String personalityCode;
    private PaginationDTO paging;

    public HolQueryDTO() {
        this.isSimplified = false;
    }
}
