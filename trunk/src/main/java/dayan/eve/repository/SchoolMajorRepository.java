package dayan.eve.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by xsg on 6/3/2017.
 */
@Mapper
public interface SchoolMajorRepository {
    List<Integer> queryByMajorTypeCode(String majorTypeCode);
}
