package dayan.eve.repository;

import dayan.eve.model.WeiboUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xsg on 6/3/2017.
 */
@Mapper
public interface WeiboUserRepository {
    void insert(WeiboUser weiboUser);

    List<WeiboUser> queryBySchool(@Param("schoolId") Integer schoolId);
}
