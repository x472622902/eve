package dayan.eve.repository;

import dayan.eve.model.query.WeiboQuery;
import dayan.eve.model.school.WeiboTimeline;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by xsg on 6/3/2017.
 */
@Mapper
public interface WeiboRepository {

    List<WeiboTimeline> query(WeiboQuery query);

    Integer count(WeiboQuery query);

    List<String> queryId(WeiboQuery query);

    void multiInsert(List<WeiboTimeline> weiboTimelines);
}
