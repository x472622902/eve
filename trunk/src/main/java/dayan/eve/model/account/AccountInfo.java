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

import com.alibaba.fastjson.JSONObject;
import dayan.eve.model.easemob.EasemobStatus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author xsg
 */
public class AccountInfo extends Account {

    protected Integer infoId;
    protected Integer accountId;
    protected String province;
    protected Integer provinceId;
    protected Integer score;
    protected String subjectType;
    protected Integer subjectTypeId;
    protected String gender;//性别
    protected String portraitUrl;//头像地址
    protected Date followDate;//关注时间
    protected Boolean isShared;//是否分享app
    protected String code;//手机的验证码
    protected Date verifyDate;//手机的验证时间
    protected Integer rank;//排名
    protected EasemobStatus status;//环线信息
    protected String csNickname;//客服昵称
    private Integer vipLevel;//vip等级

    //积分部分
    protected Integer exp;//经验值
    protected Integer level;//等级
    protected Integer levelExp;//当前等级初始积分
    protected Integer nextLevelExp;//下一个等级初始积分

    //打卡
    protected Integer continuousClockTimes;//连续打卡天数
    protected Integer clockCount;//累计打卡天数
    protected String lastClockContent;//最新打卡内容

    //walle信息
    protected AccountInfoExt ext = new AccountInfoExt();

    public AccountInfoExt getExt() {
        return ext;
    }

    public void setExt(String ext) {
        if (ext != null) {
            this.ext = JSONObject.parseObject(ext, AccountInfoExt.class);
        }
    }

    //用户角色
    protected String role;

    protected List<String> pluginKeys = new LinkedList<>();

    public String getDateStr() {
        if (getFollowDate() != null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.format(getFollowDate());
        }
        return null;
    }

    public Date getFollowDate() {
        return followDate;
    }

    public void setFollowDate(Date followDate) {
        this.followDate = followDate;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public Integer getSubjectTypeId() {
        return subjectTypeId;
    }

    public void setSubjectTypeId(Integer subjectTypeId) {
        this.subjectTypeId = subjectTypeId;
        this.subjectType = subjectTypeId == 1 ? "文科" : "理科";
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
        this.subjectTypeId = subjectType.equals("文科") ? 1 : 2;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public Boolean getIsShared() {
        return isShared;
    }

    public void setIsShared(Integer isShared) {
        this.isShared = isShared != 0;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(Date verifyDate) {
        this.verifyDate = verifyDate;
    }

    public List<String> getPluginKeys() {
        return pluginKeys;
    }

    public void setPluginKeys(List<String> pluginKeys) {
        this.pluginKeys = pluginKeys;
    }


    @Override
    public String getUserNumber() {
        if (getAccountId() == null && getId() == null) {
            return null;
        }
        if (getId() != null) {
            return String.valueOf(getId());
        }
        return String.valueOf(getAccountId());
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public EasemobStatus getStatus() {
        return status;
    }

    public void setStatus(EasemobStatus status) {
        this.status = status;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getLevelExp() {
        return levelExp;
    }

    public void setLevelExp(Integer levelExp) {
        this.levelExp = levelExp;
    }

    public Integer getNextLevelExp() {
        return nextLevelExp;
    }

    public void setNextLevelExp(Integer nextLevelExp) {
        this.nextLevelExp = nextLevelExp;
    }

    public String getCsNickname() {
        return csNickname;
    }

    public void setCsNickname(String csNickname) {
        this.csNickname = csNickname;
    }

    public Integer getContinuousClockTimes() {
        return continuousClockTimes;
    }

    public void setContinuousClockTimes(Integer continuousClockTimes) {
        this.continuousClockTimes = continuousClockTimes;
    }

    public Integer getClockCount() {
        return clockCount;
    }

    public void setClockCount(Integer clockCount) {
        this.clockCount = clockCount;
    }

    public String getLastClockContent() {
        return lastClockContent;
    }

    public void setLastClockContent(String lastClockContent) {
        this.lastClockContent = lastClockContent;
    }

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }


}
