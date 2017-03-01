package dayan.eve.model.course;

import dayan.eve.model.AlipayTrade;

/**
 * Created by xsg on 2/17/2017.
 */
public class CourseTrade {
    private Integer accountId;
    private Integer courseId;
    private Integer cdkeyId;
    private String cdkey;
    private AlipayTrade alipayTrade;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
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

    public String getCdkey() {
        return cdkey;
    }

    public void setCdkey(String cdkey) {
        this.cdkey = cdkey;
    }
}
