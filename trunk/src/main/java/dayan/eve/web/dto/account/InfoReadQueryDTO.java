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
package dayan.eve.web.dto.account;


import dayan.eve.web.dto.PaginationDTO;

import java.util.List;

/**
 *
 * @author xsg
 */
public class InfoReadQueryDTO {

    private String accountId;
    private String hashId;
    private List<String> easemobUsernames;
    private String easemobUsername;
    private PaginationDTO paging;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }


    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public List<String> getEasemobUsernames() {
        return easemobUsernames;
    }

    public void setEasemobUsernames(List<String> easemobUsernames) {
        this.easemobUsernames = easemobUsernames;
    }

    public String getEasemobUsername() {
        return easemobUsername;
    }

    public void setEasemobUsername(String easemobUsername) {
        this.easemobUsername = easemobUsername;
    }

    public PaginationDTO getPaging() {
        return paging;
    }

    public void setPaging(PaginationDTO paging) {
        this.paging = paging;
    }
}
