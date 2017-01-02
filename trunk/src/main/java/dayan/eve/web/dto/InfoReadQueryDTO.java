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

import java.util.List;

/**
 *
 * @author xsg
 */
public class InfoReadQueryDTO {

    String accountId;
    String hashId;
    List<String> easemobUsernames;
    String easemobUsername;
    Pagination paging;

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

}
