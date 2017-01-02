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
package dayan.eve.model.account;

import dayan.common.util.SchoolPlatformIdEncoder;

/**
 *
 * @author xsg
 */
public class AccountInfoExt {

    private static SchoolPlatformIdEncoder idEncoder = new SchoolPlatformIdEncoder();

    String schoolHashId;
    Integer platformId;
    Integer schoolId;
    String schoolName;

    public AccountInfoExt(String schoolHashId, Integer platformId, Integer roleId) {
        this.schoolHashId = schoolHashId;
        this.platformId = platformId;
        this.schoolId = idEncoder.decode(schoolHashId).intValue();
    }

    public AccountInfoExt(AccountInfoExt ext) {
        this.schoolHashId = ext.getSchoolHashId();
        this.platformId = ext.getPlatformId();
        this.schoolId = idEncoder.decode(ext.getSchoolHashId()).intValue();
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public AccountInfoExt() {
    }

    public String getSchoolHashId() {
        return schoolHashId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
        if (schoolId != null) {
            this.schoolHashId = idEncoder.encrypt(schoolId.longValue());
        }
    }

    public void setSchoolHashId(String schoolHashId) {
        this.schoolHashId = schoolHashId;
        if (schoolHashId != null) {
            this.schoolId = idEncoder.decode(schoolHashId).intValue();
        }
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

}
