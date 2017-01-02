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
package dayan.eve.repository;

import dayan.eve.model.account.Account;
import dayan.eve.model.account.AuthCode;
import dayan.eve.model.query.AccountQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xuesg
 */
@Mapper
public interface AccountRepository {

    List<Account> query(AccountQuery query);

    Account queryById(Integer accountId);

    int count(AccountQuery query);

    void insert(Account account);

    List<AuthCode> queryVerified(String mobile);

    void insertVerify(Account account);

    void updateVerifyCode(Account account);//更新验证码

    void updateIsVerified(Account account);//更新是否验证

    Account queryVerifyCode(Account account);

    void updateAccount(Account account);//验证后注册用户，更新数据库表

    void delete(Account account);

    void update(Account account);

    void updatePassword(Account account);

    Integer queryIdByMobile(Account account);

    /**
     * 永久物理删除，慎用
     *
     * @param id
     */
    void deletePermanently(Integer id);

    List<Integer> queryIds();

    public void updateBlock(AccountQuery query);

//    List<Integer> queryIdByHashId(String hashId);

    List<Account> queryAccountByHashId(String hashId);

    List<String> queryHashId(Integer id);

    List<Account> queryAccountEves();

    void multiInsertHashId(List<Account> accounts);

    void updateHashId();
}
