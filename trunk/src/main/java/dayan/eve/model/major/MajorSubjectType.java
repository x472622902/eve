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
package dayan.eve.model.major;

import java.util.List;

/**
 *
 * @author xsg
 */
public class MajorSubjectType {

    private String subjectType;
    private Integer majorNum;
    private List<SearchMajor> majors;

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public Integer getMajorNum() {
        return majorNum;
    }

    public void setMajorNum(Integer majorNum) {
        this.majorNum = majorNum;
    }

    public List<SearchMajor> getMajors() {
        return majors;
    }

    public void setMajors(List<SearchMajor> majors) {
        this.majors = majors;
    }

}
