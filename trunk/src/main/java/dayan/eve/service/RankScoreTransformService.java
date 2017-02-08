/**
 * *****************************************************
 * Copyright (C) Dayan techology Co.ltd - All Rights Reserved
 * <p>
 * This file is part of Dayan techology Co.ltd property.
 * <p>
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * *****************************************************
 */
package dayan.eve.service;

import dayan.eve.model.query.RecommendQuery;
import dayan.eve.model.school.ControlLine;
import dayan.eve.repository.ScoreControlLineRepository;
import dayan.eve.repository.ScoreRankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RankScoreTransformService {

    private final String year2013 = "2013";
    private final String year2015 = "2015";
    private final String[] batchs = {"本科一批", "本科二批", "本科三批", "专科", "专科一批", "专科二批", "专科三批", "专科提前批"};

    @Autowired
    ScoreRankRepository scoreRankRepository;

    @Autowired
    ScoreControlLineRepository scoreControlLineRepository;

    public RecommendQuery getScoreByRank(RecommendQuery query) {
        query.setYear(year2013);
        Integer score = scoreRankRepository.queryMaxScore(query);
        if (score != null) {
            List<ControlLine> controlLines2013 = scoreControlLineRepository.query(query);
            query.setYear(year2015);
            List<ControlLine> controlLines2015 = scoreControlLineRepository.query(query);
            Map<String, Integer> map2013 = getBatchMap(controlLines2013);
            Map<String, Integer> map2015 = getBatchMap(controlLines2015);
            query.setScore(score);
            for (String batch : batchs) {
                Integer cl2013 = map2013.get(batch);
                Integer cl2015 = map2015.get(batch);
                if (cl2013 != null && cl2015 != null) {
                    if (score > cl2013 || score > cl2015) {
                        query.setScore(score + cl2015 - cl2013);
                        break;
                    }
                }
            }
        }
        return query;

    }

    private Map<String, Integer> getBatchMap(List<ControlLine> list) {
        Map<String, Integer> map = new HashMap<>();
        for (ControlLine cl : list) {
            map.put(cl.getBatch(), cl.getControlLine());
        }
        return map;

    }

}
