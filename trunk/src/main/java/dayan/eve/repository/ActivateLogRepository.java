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

import dayan.eve.model.ActivateLog;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * @author zhuangyd
 */
@Mapper
public interface ActivateLogRepository {

    void insert(ActivateLog activateLog);
}
