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

import dayan.eve.model.account.Account;
import dayan.eve.model.account.AccountBase;
import dayan.eve.model.enumeration.LoginType;
import dayan.eve.model.query.AccountQuery;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *
 * @author xuesg
 */
public interface AccountService {

    Account register(Account account, MultipartFile file) throws Exception;

    Account login(LoginType loginType, AccountBase account) throws Exception;

    public List<Account> read(AccountQuery accountQuery);

    void updatePassword(Account account) throws Exception;

    /**
     * 更新头像
     *
     * @param accountId
     * @param file
     * @return 返回头像地址
     * @throws Exception
     */
    String updateAvatar(Integer accountId, MultipartFile file) throws Exception;

    void updateBlock(Integer blocked, Integer accountId);
}
