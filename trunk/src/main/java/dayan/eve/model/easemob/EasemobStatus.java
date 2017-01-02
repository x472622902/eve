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
package dayan.eve.model.easemob;

/**
 *
 * @author xsg
 */
public class EasemobStatus {

    protected Boolean online;

    public EasemobStatus() {
    }

    public EasemobStatus(Boolean online) {
        this.online = online;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

}
