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
package dayan.eve.model.query;


import dayan.eve.model.Pagination;
import dayan.eve.model.enumeration.AdsType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author xsg
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdsQuery extends Pagination {

    private Integer provinceId;
    private Integer subjectTypeId; // 1文科，2理科
    private AdsType type;
    private Boolean isStartpage;
    private Boolean isPopup;
    private Integer score;
    private Integer accountId;

}
