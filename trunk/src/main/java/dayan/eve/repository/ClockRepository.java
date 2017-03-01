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
package dayan.eve.repository;

import dayan.eve.model.ClockTimer;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.query.ClockQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author xsg
 */
@Mapper
public interface ClockRepository {

    void insert(ClockTimer clockTimer);

    void update(ClockTimer clockTimer);

    List<ClockTimer> query(ClockQuery query);

    List<ClockTimer> queryClocks(ClockQuery query);

    List<AccountInfo> queryAccount(ClockQuery query);

    Integer countClock(ClockQuery query);

    Integer countAccount(ClockQuery query);

    Integer queryClockRank(ClockQuery query);

    Integer queryAccountRank(ClockQuery query);

    public List<Integer> queryAccountIds();

}
