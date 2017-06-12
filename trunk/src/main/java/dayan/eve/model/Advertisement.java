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
package dayan.eve.model;

import dayan.eve.model.enumeration.AdsType;
import lombok.Data;

/**
 *
 * @author xsg
 */
@Data
public class Advertisement {

    private Integer id;
    private String imageUrl;
    private String url;
    private Boolean isStartpage;//是否启动图广告
    private Boolean isPopup;//是否弹窗广告
    private Integer subjectTypeId;
    private Integer provinceId;
    private AdsType type;
    private Integer minScore;//分数下限
    private Integer maxScore;//分数上限


}
