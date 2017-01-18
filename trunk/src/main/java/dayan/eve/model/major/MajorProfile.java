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
public class MajorProfile extends Major {

    private List<Major> relativeMajors;

    public MajorProfile() {
    }
    public MajorProfile(Major major, List<Major> majors) {
        this.setRelativeMajors(majors);
        setCode(major.getCode());
        setDegree(major.getDegree());
        setDegreeType(major.getDegreeType());
        setYear(major.getYear());
        setName(major.getName());
        setEmployment(major.getEmployment());
        setIntro(major.getIntro());
        setCode(major.getCode());
        setType(major.getType());
        setId(major.getId());
        setSubjectType(major.getSubjectType());
    }

    public List<Major> getRelativeMajors() {
        return relativeMajors;
    }

    public void setRelativeMajors(List<Major> relativeMajors) {
        this.relativeMajors = relativeMajors;
    }

}
