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


import dayan.eve.model.Pagination;

/**
 *
 * @author xsg
 */
public class FollowDTO {

    private Integer platformId;
    private String accountId;
    private String schoolHashId;
    private String majorHashId;
    private Pagination paging;

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public synchronized Pagination getPaging() {
        if (paging == null) {
            paging = new Pagination();
        }
        return paging;
    }

    public void setPaging(Pagination paging) {
        this.paging = paging;
    }

    public String getSchoolHashId() {
        return schoolHashId;
    }

    public void setSchoolHashId(String schoolHashId) {
        this.schoolHashId = schoolHashId;
    }

    public String getMajorHashId() {
        return majorHashId;
    }

    public void setMajorHashId(String majorHashId) {
        this.majorHashId = majorHashId;
    }

}
