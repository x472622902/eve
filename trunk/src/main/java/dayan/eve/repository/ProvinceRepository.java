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

import dayan.eve.model.Province;
import dayan.eve.model.query.ProvinceQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author zhuangyd
 */
@Mapper
public interface ProvinceRepository {

    List<Province> query(ProvinceQuery query);

    String queryById(Integer id);

}
