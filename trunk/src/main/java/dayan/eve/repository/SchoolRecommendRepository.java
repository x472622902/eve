package dayan.eve.repository;

import dayan.eve.model.query.RecommendQuery;
import dayan.eve.model.query.ScoreQuery;
import dayan.eve.model.school.BatchScore;
import dayan.eve.model.school.EligibleSchool;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by xsg on 6/2/2017.
 */
@Mapper
public interface SchoolRecommendRepository {
    List<EligibleSchool> query(RecommendQuery recommendQuery);

    List<EligibleSchool> queryBySchool(ScoreQuery scoreQuery);

    List<BatchScore> queryBatch(ScoreQuery query);
}
