package dayan.eve.model.account;

import java.util.Date;

/**
 * Created by xsg on 1/23/2017.
 */
public class FollowAccount {
    private Integer accountId;
    private String portraitUrl;
    private Date followDate;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public Date getFollowDate() {
        return followDate;
    }

    public void setFollowDate(Date followDate) {
        this.followDate = followDate;
    }

}
