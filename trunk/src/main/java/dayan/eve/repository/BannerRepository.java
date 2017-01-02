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

import dayan.eve.model.Banner;
import dayan.eve.model.query.BannerQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author xsg
 */
@Mapper
public interface BannerRepository {

    public List<Banner> query(BannerQuery query);

//    public void insert(Banner banner);


    public void delete(Integer id);

    public void update(Banner banner);
}
