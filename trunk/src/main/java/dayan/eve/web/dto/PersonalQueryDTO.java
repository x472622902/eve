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


/**
 * @author xsg
 */
public class PersonalQueryDTO {

    private String accountId;
    private PaginationDTO paging;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public PaginationDTO getPaging() {
        return paging;
    }

    public void setPaging(PaginationDTO paging) {
        this.paging = paging;
    }
}