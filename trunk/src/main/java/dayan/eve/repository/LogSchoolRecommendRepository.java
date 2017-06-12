package dayan.eve.repository;

import dayan.eve.model.query.HotRecommendQuery;
import dayan.eve.model.school.SchoolRecommendLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by xsg on 6/3/2017.
 */
@Mapper
public interface LogSchoolRecommendRepository {


    void insertLog(SchoolRecommendLog recommendLog);

    /**
     * 根据省份和文理科取日志
     *
     * @param query
     * @return
     */
    List<SchoolRecommendLog> queryLogs(HotRecommendQuery query);

    /**
     * 将已经计算过的is_dealed标记成1
     *
     * @param query
     */
    void updateLog(HotRecommendQuery query);


//    void insertRecommendCount(List<RecommendCount> list);
}
