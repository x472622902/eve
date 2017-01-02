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
package dayan.eve.model.query;


import dayan.eve.model.Pagination;

/**
 *
 * @author zhuangyd
 */
public class AccountQuery extends Pagination {

    private Integer id;
    private String accountHashId;
    private Boolean deleted;
    private String mobile;
    private Integer accountBaiduId;
    private Integer accountQQId;
    private Integer blocked = 1;
    private Integer isVerified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getAccountBaiduId() {
        return accountBaiduId;
    }

    public void setAccountBaiduId(Integer accountBaiduId) {
        this.accountBaiduId = accountBaiduId;
    }

    public Integer getAccountQQId() {
        return accountQQId;
    }

    public void setAccountQQId(Integer accountQQId) {
        this.accountQQId = accountQQId;
    }

    public Integer getBlocked() {
        return blocked;
    }

    public void setBlocked(Integer blocked) {
        this.blocked = blocked;
    }

    public String getAccountHashId() {
        return accountHashId;
    }

    public void setAccountHashId(String accountHashId) {
        this.accountHashId = accountHashId;
    }

    public Integer getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Integer isVerified) {
        this.isVerified = isVerified;
    }

}
