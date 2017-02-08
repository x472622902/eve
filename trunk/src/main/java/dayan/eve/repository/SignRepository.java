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

import dayan.eve.model.Sign;
import dayan.eve.model.query.SignQuery;

import java.util.Date;
import java.util.List;

/**
 *
 * @author xsg
 */
public interface SignRepository {

    void insert(Sign sign);

    Date querySignTime(Integer accountId);

    List<Sign> queryByTime(SignQuery query);
    
    Integer countToday();
}
