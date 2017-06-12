package dayan.eve.repository;

import dayan.eve.model.school.RecommendFeedback;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by xsg on 6/3/2017.
 */
@Mapper
public interface FeedbackRepository {
    void insert(RecommendFeedback feedback);
}
