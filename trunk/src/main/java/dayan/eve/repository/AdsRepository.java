package dayan.eve.repository;

import dayan.eve.model.Advertisement;
import dayan.eve.model.query.AdsQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by xsg on 6/3/2017.
 */
@Mapper
public interface AdsRepository {
    List<Advertisement> query(AdsQuery query);

    Advertisement queryRandomOne(AdsQuery query);

    public void insert(Advertisement ad);

    /**
     * 更新广告次数
     *
     * @param id
     */
    void updateReadTimes(Integer id);

    void delete(Integer id);

    public void update(Advertisement ad);
}
