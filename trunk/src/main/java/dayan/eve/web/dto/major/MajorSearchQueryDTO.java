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
package dayan.eve.web.dto.major;


import dayan.eve.web.dto.PaginationDTO;

/**
 * @author xuesg
 */
public class MajorSearchQueryDTO {

    private String query;
    private String degreeTypeId;
    private PaginationDTO paging;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getDegreeTypeId() {
        return degreeTypeId;
    }

    public void setDegreeTypeId(String degreeTypeId) {
        this.degreeTypeId = degreeTypeId;
    }

    public PaginationDTO getPaging() {
        return paging;
    }

    public void setPaging(PaginationDTO paging) {
        this.paging = paging;
    }

}
