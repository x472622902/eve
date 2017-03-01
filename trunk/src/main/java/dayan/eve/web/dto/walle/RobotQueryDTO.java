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
package dayan.eve.web.dto.walle;

/**
 * @author zhuangyd
 */
public class RobotQueryDTO {

    private String query;
    private String schoolHashId;
    private String platformHashId;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getPlatformHashId() {
        return platformHashId;
    }

    public void setPlatformHashId(String platformHashId) {
        this.platformHashId = platformHashId;
    }

    public String getSchoolHashId() {
        return schoolHashId;
    }

    public void setSchoolHashId(String schoolHashId) {
        this.schoolHashId = schoolHashId;
    }

}
