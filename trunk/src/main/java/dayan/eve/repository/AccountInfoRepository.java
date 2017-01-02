/**
 * *****************************************************
 * Copyright (C) Dayan techology Co.ltd - All Rights Reserved
 * <p/>
 * This file is part of Dayan techology Co.ltd property.
 * <p/>
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * *****************************************************
 */
package dayan.eve.repository;

import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.query.AccountInfoQuery;
import dayan.eve.model.query.AccountQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xsg
 */
@Mapper
public interface AccountInfoRepository {

    /**
     * 更新用户信息
     *
     * @param accountInfo
     */
    void updateInfo(AccountInfo accountInfo);

    /**
     * 插入用户信息
     *
     * @param accountInfo
     */
    void insertInfo(AccountInfo accountInfo);

//    void updateBaiduInfo(Account account);
//
//    void updateQQInfo(Account account);

    /**
     * 查询用户
     *
     * @param query
     * @return
     */
    List<AccountInfo> queryInfo(AccountQuery query);

    /**
     * 查询单个用户
     *
     * @param accountId
     * @return
     */
    AccountInfo queryOneInfo(Integer accountId);

    /**
     * 统计用户
     *
     * @param query
     * @return
     */
    Integer count(AccountQuery query);

    /**
     * 用户分享app后更新状态
     *
     * @param accountId
     */
    void updateShared(Integer accountId);

    /**
     * 更新验证码
     *
     * @param accountInfo
     */
    void updateVerifyCode(AccountInfo accountInfo);

    /**
     * 根据用户id查询验证码
     *
     * @param accountId
     * @return
     */
    List<AccountInfo> queryCodeByAccountId(Integer accountId);

    /**
     * 更新头像
     *
     * @param accountInfo
     */
    void updatePortraitUrl(AccountInfo accountInfo);

    /**
     * 更新连续打卡数
     *
     * @param accountIds
     */
    public void updateContinuousClockTimes(List<Integer> accountIds);


    /**
     * 查询用户列表
     *
     * @param query
     * @return
     */
    List<AccountInfo> query(AccountInfoQuery query);

    /**
     * 统计用户个数(用户后台调用)
     *
     * @param query
     * @return
     */
    Integer count2(AccountInfoQuery query);
}
