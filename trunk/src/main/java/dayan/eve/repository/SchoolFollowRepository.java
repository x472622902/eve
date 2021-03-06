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

import dayan.eve.model.School;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.account.FollowAccount;
import dayan.eve.model.query.FollowQuery;
import dayan.eve.model.query.HotRecommendQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xsg
 */
@Mapper
public interface SchoolFollowRepository {

    // TODO: 1/23/2017 将cu_school_follow 中的user_id和school_id设为unique key 
    void follow(FollowQuery query);

    List<AccountInfo> queryAccounts(FollowQuery query);

    AccountInfo queryAccount(Integer accountId);

    List<Integer> queryAccountIds(Integer schoolId);

    List<School> querySchools(FollowQuery query);

    Integer countAccount(FollowQuery query);

    Integer countSchool(FollowQuery query);

    public List<Integer> querySchoolIds();

    /**
     * 查询关注该学校的用户（带头像以及关注时间），每次默认5条
     */
    List<FollowAccount> queryFollowAccounts(HotRecommendQuery query);

    Integer countAccountItem(HotRecommendQuery query);
}
