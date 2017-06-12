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
package dayan.eve.model.major;

import lombok.Data;

/**
 *
 * @author xsg
 */
@Data
public class Major {

    private Integer id;
    private String hashId;
    private String name;
    private String type;
    private String degree;
    private String degreeType;
    private String code;
    private String intro;
    private String employment;
    private String year;
    private String subjectType;

    public Major() {
    }


    public Major(MoMajor moMajor) {
        this.name = moMajor.getMajorName();
        this.hashId = moMajor.getMajorHashId();
        this.code = moMajor.getMajorCode();
    }

}
