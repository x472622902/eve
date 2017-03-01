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
package dayan.eve.repository.course;


import dayan.eve.model.course.CDKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author xsg
 */
@Mapper
public interface CourseCDKeyRepository {

    void insert(CDKey key);

    void multiInsert(List<CDKey> list);

    void delete(Integer id);

    void update(CDKey key);

    List<CDKey> query(CDKey key);
    
    Integer queryId(CDKey key);
}
