/**
 * *****************************************************
 * Copyright (C) Dayan techology Co.ltd - All Rights Reserved
 * <p/>
 * This file is part of Dayan techology Co.ltd property.
 * <p/>
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * *****************************************************
 */
package dayan.eve.service.school.recommend;

import dayan.eve.model.query.RecommendQuery;
import dayan.eve.model.school.EligibleSchool;
import dayan.eve.model.school.RecommendSchool;
import dayan.eve.repository.SchoolRecommendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author xsg
 */
@Service
public class RecommendSchoolService {

    private static final float MAX_P = 0.95f;//默认最大录取概率
    private static final float MIN_P = 0.05f;//默认最小录取概率
    private final SchoolRecommendRepository schoolRecommendRepository;

    @Autowired
    public RecommendSchoolService(SchoolRecommendRepository schoolRecommendRepository) {
        this.schoolRecommendRepository = schoolRecommendRepository;
    }

    /**
     * 学校推荐
     *
     * @param query 查询条件
     * @return 推荐学校列表
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<RecommendSchool> getRecommendSchools(RecommendQuery query) {
        query.setScore(query.getScore() + 5);
        //获取符合条件的全部学校
        List<EligibleSchool> eligibleSchools = schoolRecommendRepository.query(query);
        query.setScore(query.getScore() - 5);
        //按查询输入的分数计算各个学校的录取概率
        List<EligibleSchool> unorderedList = caculateProbability(eligibleSchools, query);
        //按冲稳保类型分类
        unorderedList = filterByRefType(unorderedList, query.getRefTypeIds());
        if (unorderedList.isEmpty()) {
            return new LinkedList<>();
        }
        //综合学校的录取概率和学校的排名，对学校进行排序
        List<EligibleSchool> orderedList = filterAndSortBySchoolRank(unorderedList);

        //计算推荐指数
        List<RecommendSchool> recommendSchools = setRecommendScore(orderedList, query.getScore());

        switch (query.getSortType()) {
            case RecommendScore:
                sortByRecommendScore(recommendSchools);//按推荐指数对学校进行排序
                break;
            case SchoolRank:
                sortBySchoolRank(recommendSchools);
                break;//按学校排名对学校进行排序时直接跳出，因为在上面已经排序完成
            case RefScore:
                sortByRefScore(recommendSchools);//按推荐录取分数对学校进行排序
                break;
            default:
                sortByRecommendScore(recommendSchools);//默认按推荐指数对学校进行排序
                break;
        }
        return recommendSchools;
    }

    /**
     * 计算录取概率并设置冲稳保类型
     *
     * @param schools 学校列表
     * @param query   查询条件
     * @return 学校列表
     */
    private List<EligibleSchool> caculateProbability(List<EligibleSchool> schools, RecommendQuery query) {
        List<EligibleSchool> result = new LinkedList<>();
        for (EligibleSchool school : schools) {//遍历按条件筛选符合的学校和上这所学校的概率以及一些其他的参数
            Integer score = query.getScore();
            Float probability = getProbabilityByScore(school, score);
            if (probability > 0) {
                school.setProbability(probability);
                if (probability <= 0.5f) {
                    school.setRefTypeId(1);
                } else if (probability > 0.5f && probability < 0.9f) {
                    school.setRefTypeId(2);
                } else if (probability > 0.9f) {
                    school.setRefTypeId(3);
                }
                String subjectType = query.getSubjectType();
                if (filterSchool(school, subjectType)) {
                    result.add(school);
                }
            }

        }
        return result;
    }

    /**
     * 前一百名的理科（文科）学校投档分减去省控线如果小于20（10）分的话， 说明数据有点问题，则这个推荐的学校要被过滤掉。
     *
     * @param school      学校
     * @param subjectType 文理科
     * @return 有问题则返回true
     */
    private Boolean filterSchool(EligibleSchool school, String subjectType) {
        Integer avgScoreDif = school.getAvgScoreDifference();
        Integer schoolRank = school.getSchoolRank();
        if (schoolRank != null && avgScoreDif != null) {
            if (subjectType.equals("理科")) {
                if (schoolRank < 100 && avgScoreDif < 20) {
                    return false;
                }
            } else {
                if (schoolRank < 100 && avgScoreDif < 10) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 分数按照公式得到相应的录取概率
     *
     * @param school 学校
     * @param score  我的分数
     * @return 录取概率
     */
    public Float getProbabilityByScore(EligibleSchool school, Integer score) {
        Integer refScore = school.getRefScore();
        Integer minRec = school.getMinRecommend();
        Float probability;

        //我的分数>学校预测分
        if (score > refScore) {
            probability = (score - refScore) * 0.15f / 10 + 0.8f;

            //学校预测最小录取分<我的分数<学校预测分
        } else if (score > minRec) {
            probability = (score - minRec) * 0.3f / 15 + 0.5f;

            //学校预测最小录取分-10<我的分数<学校预测最小录取分-10
        } else {
            probability = 0.5f - (minRec - score) * 0.45f / 10;
        }

        probability = (probability < MAX_P) ? probability : MAX_P;
        probability = (probability < MIN_P) ? MIN_P : probability;
        return probability;
    }

    /**
     * 按照该学校在所有推荐学校里的排名、录取概率和所有的推荐学校个数得到该学校的推荐指数。
     *
     * @param schoolRank  学校排名
     * @param probability 已计算出的录取概率
     * @param scoreDif    我的分数和学校的预测录取分的差
     * @return 推荐指数
     */
    private Float getRecommendScore(float probability, Integer schoolRank, Integer scoreDif) {
        float recommendScore = 0;
        int maxRank = 4031;//数据库中所有学校的最大排名
        if (probability > 0.8f) {//录取类型： 保
            float rankWeight = (maxRank - schoolRank) * (60 - 0.8f * scoreDif) / maxRank;
            rankWeight = rankWeight > 0 ? rankWeight : 0;
            recommendScore = probability * 40 + rankWeight;
        } else if (probability > 0.5 && probability < 0.8) {//录取类型： 稳
            float rankWeight = (maxRank - schoolRank) * 50f / maxRank;
            rankWeight = rankWeight > 0 ? rankWeight : 0;
            recommendScore = probability * 50 + rankWeight;
        } else if (probability <= 0.5) {//录取类型： 冲
            float rankWeight = (maxRank - schoolRank) * 55f / maxRank;
            rankWeight = rankWeight > 0 ? rankWeight : 0;
            recommendScore = probability * 60 + rankWeight;
        }
        return recommendScore;
    }

    /**
     * 得到有学校排名的学校列表，且按学校排名排序
     *
     * @param esList 无序的学校列表
     * @return 按学校排名排序的有序列表（按排名大小倒序排列）
     */
    private List<EligibleSchool> filterAndSortBySchoolRank(List<EligibleSchool> esList) {
        List<EligibleSchool> list = new LinkedList<>();
        for (EligibleSchool school : esList) {
            Integer schoolRank = school.getSchoolRank();
            if (schoolRank != null) {
                list.add(school);
            }
        }
        Collections.sort(list, (o1, o2) -> o2.getSchoolRank().compareTo(o1.getSchoolRank()));
        return list;
    }

    /**
     * 按计算的推荐指数排序
     *
     * @param schools 学校列表
     */
    private void sortByRecommendScore(List<RecommendSchool> schools) {
        Collections.sort(schools, (o1, o2) -> o2.getRecommendScore().compareTo(o1.getRecommendScore()));
    }

    /**
     * 根据冲稳保类型筛选
     *
     * @param schools    学校列表
     * @param refTypeIds 筛选参数（冲、稳、保）
     * @return 根据冲稳保类型筛选得到的学校列表
     */
    private List<EligibleSchool> filterByRefType(List<EligibleSchool> schools, List<Integer> refTypeIds) {

        List<EligibleSchool> list = new LinkedList<>();
        if (refTypeIds == null || refTypeIds.isEmpty()) {
            return schools;
        }
        if (refTypeIds.contains(1)) {
            for (EligibleSchool s : schools) {
                if (s.getRefTypeId() == 1) {
                    list.add(s);
                }
            }
        }
        if (refTypeIds.contains(2)) {
            for (EligibleSchool s : schools) {
                if (s.getRefTypeId() == 2) {
                    s.setRefTypeId(2);
                    list.add(s);
                }
            }
        }
        if (refTypeIds.contains(3)) {
            for (EligibleSchool s : schools) {
                if (s.getRefTypeId() == 3) {
                    list.add(s);
                }
            }
        }
        return list;
    }

    /**
     * 计算推荐指数
     *
     * @param schools 学校列表
     * @param score   我的分数
     * @return 计算出指数的学校列表
     */
    private List<RecommendSchool> setRecommendScore(List<EligibleSchool> schools, Integer score) {
        List<RecommendSchool> recommendSchools = new LinkedList<>();
        for (EligibleSchool es : schools) {
            RecommendSchool rs = new RecommendSchool(es);
            rs.setRecommendScore(getRecommendScore(es.getProbability(), es.getSchoolRank(), (score - es.getRefScore())));
            recommendSchools.add(rs);
        }
        return recommendSchools;
    }

    private void sortByRefScore(List<RecommendSchool> recommendSchools) {
        Collections.sort(recommendSchools, (o1, o2) -> o2.getRefScore().compareTo(o1.getRefScore()));
    }

    private void sortBySchoolRank(List<RecommendSchool> recommendSchools) {
        Collections.sort(recommendSchools, (o1, o2) -> o1.getSchoolRank().compareTo(o2.getSchoolRank()));
    }
}
