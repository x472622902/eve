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

import dayan.eve.model.Information;
import dayan.eve.model.query.InformationQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xsg
 */
@Mapper
public interface InformationRepository {

    List<Information> query(InformationQuery query);//2.0以下版本

    List<Information> queryNews(InformationQuery query);//2.0以上版本

    Integer count();

    Integer countNews(InformationQuery query);//2.0以上版本

    void insert(Information information);

    public void delete(Integer id);

}
