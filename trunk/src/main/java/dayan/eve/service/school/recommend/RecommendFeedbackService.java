package dayan.eve.service.school.recommend;

import com.alibaba.fastjson.JSON;
import dayan.common.util.MD5Util;
import dayan.eve.model.school.RecommendFeedback;
import dayan.eve.repository.FeedbackRepository;
import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xsg on 6/2/2017.
 */
@Service
public class RecommendFeedbackService {
    private static final Logger LOGGER = LogManager.getLogger(RecommendFeedbackService.class);

    private PassiveExpiringMap feedbackCache = new PassiveExpiringMap(10000);
    private final FeedbackRepository feedbackRepository;

    @Autowired
    public RecommendFeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }


    private boolean isCreatedIn10s(RecommendFeedback feedback) {
        String key = MD5Util.StringMD5(feedback.getProvinceId() + "-" + feedback.getSubjectType()
                + "-" + feedback.getScore() + "-" + feedback.getContent());
        if (feedbackCache.containsKey(key)) {
            return true;
        } else {
            feedbackCache.put(key, feedback);
            return false;
        }
    }

    public void createFeedback(RecommendFeedback feedback) {
        if (isCreatedIn10s(feedback)) {
            LOGGER.warn("feedback created in 10s. {}", JSON.toJSONString(feedback, true));
            return;
        }
        feedbackRepository.insert(feedback);
    }
}
