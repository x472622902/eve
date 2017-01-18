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
package dayan.eve.service;

import dayan.eve.model.Pager;
import dayan.eve.model.account.Account;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.query.AccountInfoQuery;

import java.util.List;

/**
 *
 * @author xsg
 */
public interface AccountInfoService {

    /**
     * 创建用户信息
     *
     * @param account
     */
    void createInfo(Account account);

    /**
     * 读取用户信息
     *
     * @param accountId
     * @return
     */
    AccountInfo readInfo(Integer accountId);

    /**
     * 读取用户信息
     *
     * @param accountHashId
     * @return
     */
    AccountInfo readInfo(String accountHashId);

    /**
     * 根据环线用户名读取用户信息
     *
     * @param easemobUsername
     * @return
     */
    AccountInfo readAccountByEasemob(String easemobUsername);

    /**
     * 根据环线用户名列表读取对应的用户信息列表
     *
     * @param easemobUsernames
     * @return
     */
    List readAccountListByEasemob(List<String> easemobUserNames);

    void updateInfo(AccountInfo accountInfo) throws Exception;

    /**
     * 更新用户为分享
     *
     * @param accountId
     */
    void updateShared(Integer accountId);

    /**
     * 更新用户的经验
     *
     * @param accountId
     * @param updateExp 更新的经验
     * @return
     */
    Integer updateExp(Integer accountId, Integer updateExp);

    /**
     * 统计经验值
     */
    void countExp();

    AccountInfo readOrCreate(AccountInfo accountInfo);

    void updateFromGo4();

    List<AccountInfo> read(AccountInfoQuery query);

    Pager count(AccountInfoQuery query);
}
