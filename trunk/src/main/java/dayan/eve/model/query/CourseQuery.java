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
package dayan.eve.model.query;

import dayan.eve.model.AlipayTrade;
import dayan.eve.model.Pagination;

/**
 * @author xsg
 */
public class CourseQuery extends Pagination {

    private Integer accountId;
    private Boolean readMy = false;

    private Integer number;
    private Integer courseId;

    private String cdkey;

    private Integer cdkeyId;
    private AlipayTrade alipayTrade;
    private Integer tradeId;//交易id

    private String tradeNo;//支付宝交易订单号

    public CourseQuery() {
    }

    public CourseQuery(Integer accountId, Integer courseId) {
        this.accountId = accountId;
        this.courseId = courseId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    public Integer getCdkeyId() {
        return cdkeyId;
    }

    public void setCdkeyId(Integer cdkeyId) {
        this.cdkeyId = cdkeyId;
    }

    public AlipayTrade getAlipayTrade() {
        return alipayTrade;
    }

    public void setAlipayTrade(AlipayTrade alipayTrade) {
        this.alipayTrade = alipayTrade;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Boolean getReadMy() {
        return readMy;
    }

    public void setReadMy(Boolean readMy) {
        this.readMy = readMy;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCdkey() {
        return cdkey;
    }

    public void setCdkey(String cdkey) {
        this.cdkey = cdkey;
    }

}
