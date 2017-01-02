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

import dayan.eve.model.PageResult;
import dayan.eve.model.School;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.query.FollowQuery;

/**
 * @author xsg
 */
public interface SchoolFollowService {

    /**
     * 关注学校
     *
     * @param query
     */
    void follow(FollowQuery query);

    /**
     * 取消关注学校
     *
     * @param query
     */
    void cancel(FollowQuery query);

    /**
     * 读取用户的关注学校
     *
     * @param query
     * @return
     */
    PageResult<School> readSchools(FollowQuery query);

    /**
     * 读取关注该学校的所有用户
     *
     * @param query
     * @return
     */
    PageResult<AccountInfo> readAccounts(FollowQuery query);

}
