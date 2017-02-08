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
package dayan.eve.model.walle;

/**
 * @author xsg
 */
public class SchoolNotify extends DsaSign {

    protected Integer platformId;
    private String content;


    public SchoolNotify() {

    }

    public SchoolNotify(SchoolNotify schoolNotify) {
        this.platformId = schoolNotify.getPlatformId();
        this.content = schoolNotify.getContent();
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
