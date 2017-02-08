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


import dayan.eve.model.Pager;
import dayan.eve.model.school.HotSchoolGroup;

/**
 * @author xsg
 */
public class HotSchoolReadDTO {

    private HotSchoolGroup schools;
    private Pager pager;

    public HotSchoolReadDTO() {
    }

    public HotSchoolReadDTO(HotSchoolGroup schools, Pager pager) {
        this.schools = schools;
        this.pager = pager;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public HotSchoolGroup getSchools() {
        return schools;
    }

    public void setSchools(HotSchoolGroup schools) {
        this.schools = schools;
    }
}
