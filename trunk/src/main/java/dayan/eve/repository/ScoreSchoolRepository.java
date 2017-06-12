package dayan.eve.repository;

import dayan.eve.model.query.ScoreQuery;
import dayan.eve.model.school.BatchScore;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by xsg on 6/2/2017.
 */
@Mapper
public interface ScoreSchoolRepository {
    List<BatchScore> queryBatch(ScoreQuery query);
}
