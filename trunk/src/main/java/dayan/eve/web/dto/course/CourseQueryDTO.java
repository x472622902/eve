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
package dayan.eve.web.dto.course;

import dayan.eve.web.dto.PaginationDTO;

/**
 * @author xsg
 */
public class CourseQueryDTO {

    private Boolean readMyCourse = false;
    private PaginationDTO paging;
    private String cdkey;


    public Boolean getReadMyCourse() {
        return readMyCourse;
    }

    public void setReadMyCourse(Boolean readMyCourse) {
        this.readMyCourse = readMyCourse;
    }

    public String getCdkey() {
        return cdkey;
    }

    public void setCdkey(String cdkey) {
        this.cdkey = cdkey;
    }

    public PaginationDTO getPaging() {
        return paging;
    }

    public void setPaging(PaginationDTO paging) {
        this.paging = paging;
    }
}
