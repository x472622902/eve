package dayan.eve.service.school;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dayan.eve.model.JsonResult;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.query.ScoreQuery;
import dayan.eve.model.school.*;
import dayan.eve.repository.AccountInfoRepository;
import dayan.eve.repository.ProvinceRepository;
import dayan.eve.repository.SchoolRecommendRepository;
import dayan.eve.repository.ScoreSchoolRepository;
import dayan.eve.util.MoUtil;
import dayan.eve.web.dto.school.RecommendScoreReadResultV20;
import dayan.eve.web.dto.school.SchoolScoreReadResultV20;
import dayan.eve.web.dto.school.ScoreAnalysisResultDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by xsg on 6/2/2017.
 */
@Service
public class SchoolScoreService {
    private static final Logger LOGGER = LogManager.getLogger(SchoolScoreService.class);
    private static final float MAX_P = 0.95f;
    private static final float MIN_P = 0.05f;
    private static final float REF_P = 0.8f;
    private static final int MAX_DIF = 10;// 最大分与参照分的分差（之前15）
    private static String MIN_YEAR = "2012";
    private static String YEAR2015 = "2015";
    private static String YEAR2016 = "2016";
    private static Integer DEFAULT_PROVINCE_ID = 11;
    private static Integer DEFAULT_SUBJECT_TYPE_ID = 2;
    private static String[] BATCH_LIST = {"提前批", "本科一批", "本科二批", "本科三批", "未知批", "专科一批", "专科二批", "专科三批", "专科"};

    private String MERGE = "由于该省今年二本线和三本线合并，经过小言智能调整，";
    private String BATCH_INTRO = "year年batch省控线controlLine分";
    private String MY_SCORE_INTRO = "，您的分数是myScore分，录取概率为probability%";
    private String ANALYSIS = "历年subjectType超过batch线scoreDiff分将有望被录取，batchIntro，merge"
            + "小言预测year年的录取平均分为refScore分左右myScoreIntro";
    private static final String DEFAULT_SUBJECT_TYPE = "理科";

    private final static int[] SPECIFIC_PROVINCE_IDS = {20, 23};//广西、四川
    private final static int[] CONTROL_LINE_OF_GX = {380, 320};//广西的文理科三批分数线,文科在前
    private final static int[] CONTROL_LINE_OF_SC = {473, 445};//四川的文理科三批分数线,文科在前

    private final ProvinceRepository provinceRepository;
    private final ScoreSchoolRepository scoreSchoolRepository;
    private final SchoolRecommendRepository schoolRecommendRepository;
    private final AccountInfoRepository accountInfoRepository;
    private final MoUtil moUtil;

    @Autowired
    public SchoolScoreService(MoUtil moUtil, SchoolRecommendRepository schoolRecommendRepository, AccountInfoRepository accountInfoRepository, ScoreSchoolRepository scoreSchoolRepository, ProvinceRepository provinceRepository) {
        this.moUtil = moUtil;
        this.schoolRecommendRepository = schoolRecommendRepository;
        this.accountInfoRepository = accountInfoRepository;
        this.scoreSchoolRepository = scoreSchoolRepository;
        this.provinceRepository = provinceRepository;
    }

    private SchoolScoreReadResultV20 convertToResult(List<SchoolScore> schoolScores) {
        if (schoolScores == null || schoolScores.isEmpty()) {
            schoolScores = new LinkedList<>();
        }
        return new SchoolScoreReadResultV20(schoolScores);
    }

    private RecommendScoreReadResultV20 coverToRecommendResult(List<EligibleSchool> schools, ScoreQuery query) {
        List<RecScoreItem> list = schools.stream()
                .map(school -> {
                    RecScoreItem item = new RecScoreItem();
                    item.setBatch(school.getBatch());
                    item.setControlLine(school.getCurControlLine() == null ? 0 : school.getCurControlLine());
                    //最小分调整为refScore-5，参照分调整为refScore+5
                    item.setMinScore(school.getRefScore() - 5);
                    item.setRefScore(school.getRefScore() + 6);
                    item.setStarCount(getCountOfStar(school.getSchoolRank()));
                    return item;
                }).collect(Collectors.toList());
        list = filterByProvince(list, query);
        return new RecommendScoreReadResultV20(list);
    }


    /**
     * 分数按照公式得到相应的录取概率
     *
     * @param school
     * @param score
     * @return
     */
    private Float getProbabilityByScore(EligibleSchool school, Integer score) {
        Integer refScore = school.getRefScore();
        Integer minRec = school.getMinRecommend();
        Float p = REF_P;
        Float probability = 0.0f;
        if (score > refScore) {
            probability = (score - refScore) * 0.15f / 10 + 0.8f;
        } else if (score > minRec) {
            probability = (score - minRec) * 0.3f / 15 + 0.5f;
        } else {
            probability = 0.5f - (minRec - score) * 0.45f / 10;
        }
        probability = (probability < MAX_P) ? probability : MAX_P;
        probability = (probability < MIN_P) ? MIN_P : probability;
        return probability;
    }

    /**
     * 根据学校的排名获取学校的星级
     */
    private Integer getCountOfStar(Integer schoolRank) {
        if (schoolRank == null) {
            return 1;
        } else if (schoolRank <= 100) {
            return 5;
        } else if (schoolRank <= 500) {
            return 4;
        } else if (schoolRank <= 1000) {
            return 3;
        } else if (schoolRank <= 2000) {
            return 2;
        } else {
            return 1;
        }
    }

    private List<RecScoreItem> filterByProvince(List<RecScoreItem> rsList, ScoreQuery query) {
        String batch3 = "本科三批";
        String batch2 = "本科二批";
        if (SPECIFIC_PROVINCE_IDS[0] != query.getProvinceId() && SPECIFIC_PROVINCE_IDS[1] != query.getProvinceId()) {
            return rsList;
        }
        int subjectTypeId = "文科".equals(query.getSubjectType()) ? 0 : 1;
        int[] controlLines = SPECIFIC_PROVINCE_IDS[0] == query.getProvinceId() ? CONTROL_LINE_OF_GX : CONTROL_LINE_OF_SC;
        for (RecScoreItem rs : rsList) {
            if (batch2.equals(rs.getBatch())) {
                rs.setControlLine(controlLines[subjectTypeId]);
            }
            if (batch3.equals(rs.getBatch())) {
                rs.setBatch(batch2);
            }
        }
        return rsList;
    }

    private SchoolScoreReadResultV20 setRefScoreAndProbability(Integer myScore, SchoolScoreReadResultV20 result, ScoreQuery scoreQuery) {
        List<EligibleSchool> schools = schoolRecommendRepository.queryBySchool(scoreQuery);
        if (schools.isEmpty()) {
            result.setProbability(0f);
            result.setRefScore(0);
        } else {
            result.setProbability(getProbabilityByScore(schools.get(0), myScore));
            result.setRefScore(schools.get(0).getRefScore());
        }
        return result;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public RecommendScoreReadResultV20 readRecScore(ScoreQuery query) {
        LOGGER.info("read recommend score query,{}", JSON.toJSONString(query, true));
        List<EligibleSchool> schools = schoolRecommendRepository.queryBySchool(query);
        return coverToRecommendResult(schools, query);
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public SchoolScoreReadResultV20 readScore(ScoreQuery query, Integer accountId) {
        LOGGER.info("read score query,{}", JSON.toJSONString(query, true));
        query.setMinYear(MIN_YEAR);
        JsonResult jsonResult = null;
        try {
            jsonResult = JSONObject.parseObject(moUtil.getSchoolScore(query), JsonResult.class);
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
        List<SchoolScore> schoolScores = null;
        if (jsonResult != null) {
            JSONObject data = (JSONObject) jsonResult.getData();
            schoolScores = JSONArray.parseArray(data.getString("list"), SchoolScore.class);
        }
        SchoolScoreReadResultV20 result = convertToResult(schoolScores);
        if (accountId != null) {
            AccountInfo accountInfo = accountInfoRepository.queryOneInfo(accountId);
            if (accountInfo != null && accountInfo.getScore() != null) {
                Integer myScore = accountInfo.getScore();
                result.setMyScore(myScore);
                result = setRefScoreAndProbability(myScore, result, query);
            }
        }
        return result;
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ScoreAnalysisResultDTO scoreAnalyse(ScoreQuery query, Integer accountId) {
        LOGGER.info("school score analysis query,{}", JSON.toJSONString(query, true));

        ScoreAnalysisResultDTO result = new ScoreAnalysisResultDTO();
        Integer myScore = query.getScore();
        AccountInfo accountInfo = new AccountInfo();
        //当我的分数为空时，则从用户信息中读取分数
        if (myScore == null && accountId != null) {
            accountInfo = accountInfoRepository.queryOneInfo(accountId);
            myScore = accountInfo.getScore();
        }
        query.setScore(myScore);
        setBaseValues(result, query, accountInfo);
        List<BatchScore> batches;
        Integer bestBatchId;
        if (query.getBatch() == null) {
            //先是从推荐表里拿2016的省控线
            batches = schoolRecommendRepository.queryBatch(query);
            if (batches.isEmpty()) {
                return result;
            }
            bestBatchId = getBestBatchId(batches, myScore);
            if (bestBatchId == null) {
                //如果推荐表里没有批次数据，再从cu_score_school_mix表中取
                batches = scoreSchoolRepository.queryBatch(query);
                bestBatchId = getBestBatchId(batches, myScore);
            }
        } else {
            bestBatchId = getBatchMap().get(query.getBatch());
        }
        if (bestBatchId == null) {
            return result;
        }
        query.setBatchId(bestBatchId);
        query.setBatch(BATCH_LIST[bestBatchId - 1]);
        query.setMinYear(MIN_YEAR);//设置最小年份2012，只读2012以上的分数线
        JsonResult jsonResult = null;
        try {
            jsonResult = JSONObject.parseObject(moUtil.getSchoolScore(query), JsonResult.class);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        List<SchoolScore> schoolScores = null;
        if (jsonResult != null) {
            JSONObject data = (JSONObject) jsonResult.getData();
            schoolScores = JSONArray.parseArray(data.getString("list"), SchoolScore.class);
        }

        if (schoolScores == null || schoolScores.isEmpty()) {
            return result;
        }

        List<HistoryScore> hisScores = new LinkedList<>();

        EligibleSchool es = null;
        //TODO 等2016分数线出来时需加上分数预测，现在暂时取消
        //由于最多只能推荐2015年的数据，所以当获取学校现在已有的数据最大年是不是2015年，有则不算推荐数据
        if (YEAR2016.equals(schoolScores.get(0).getYear())) {
            hisScores = getScoresBySize(schoolScores, 4);
            hisScores.get(hisScores.size() - 1).setMyScore(myScore);
        } else {
            List<EligibleSchool> schools = schoolRecommendRepository.queryBySchool(query);
            if (schools != null && !schools.isEmpty()) {
                HistoryScore recScore = new HistoryScore();
                recScore.setMyScore(myScore);
                recScore.setYear(YEAR2016);
                hisScores = getScoresBySize(schoolScores, 3);
                es = schools.get(0);
                recScore.setRefScore(es.getRefScore()); //预测分向下浮动5分
                if (es.getControlLineOne() != null && es.getControlLineOne() != 0) {
                    recScore.setControlLine(es.getControlLineOne());
                }
                hisScores.add(recScore);
            }
        }

        result.setScoreList(hisScores);
        result.setBatch(BATCH_LIST[bestBatchId - 1]);
        setValues(result, query, es);
        return result;
    }


    private Map<String, Integer> getBatchMap() {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < BATCH_LIST.length; i++) {
            map.put(BATCH_LIST[i], i + 1);
        }
        return map;
    }

    /*
     *获得符合该分数下最高的批次，如果分数没有，则获取该学校最高的批次
     */
    private Integer getBestBatchId(List<BatchScore> batchScores, Integer myScore) {
        Map<String, Integer> batchMap = getBatchMap();
        Integer batchId = -1;
        if (myScore == null || myScore == 0) {
            batchId = getBatchId(batchScores);
        } else {
            for (BatchScore batch : batchScores) {
                if (myScore >= batch.getControlLine() && batchMap.containsKey(batch.getBatch())) {
                    int tmpId = batchMap.get(batch.getBatch());
                    if (batchId != -1) {
                        batchId = batchId > tmpId ? tmpId : batchId;
                    } else {
                        batchId = tmpId;
                    }
                }
            }
            if (batchId == -1) {
                batchId = getBatchId(batchScores);
            }
        }
        return batchId == -1 ? null : batchId;
    }

    private Integer getBatchId(List<BatchScore> batches) {
        Map<String, Integer> batchMap = getBatchMap();
        Integer batchId = -1;
        for (BatchScore batch : batches) {
            if (batchMap.containsKey(batch.getBatch())) {
                int tmpId = batchMap.get(batch.getBatch());
                if (batchId != -1) {
                    batchId = batchId > tmpId ? tmpId : batchId;
                } else {
                    batchId = tmpId;
                }
            }
        }
        return batchId;
    }

    private List<HistoryScore> getScoresBySize(List<SchoolScore> schoolScores, int size) {
        List<HistoryScore> hisScores = new LinkedList<>();
        for (SchoolScore schoolScore : schoolScores) {
            HistoryScore hs = new HistoryScore();
            hs.setMinScore(schoolScore.getMinScore());
            hs.setControlLine(schoolScore.getControlLine());
            hs.setAvgScore(schoolScore.getAvgScore());
            hs.setYear(schoolScore.getYear());
            hisScores.add(hs);
            if (hisScores.size() == size) {
                break;
            }
        }
        Collections.reverse(hisScores);
        return hisScores;
    }

    private void setValues(ScoreAnalysisResultDTO result, ScoreQuery query, EligibleSchool es) {
        String provinceName = provinceRepository.queryById(query.getProvinceId());
        String analysisTemplate = ANALYSIS;
        if (es != null) {
            es.setRefScore(es.getRefScore());
            if (query.getScore() == null) {
                analysisTemplate = analysisTemplate.replace("myScoreIntro", "");
            } else {
                analysisTemplate = analysisTemplate.replace("myScoreIntro", MY_SCORE_INTRO);
                Float probability = getProbabilityByScore(es, query.getScore()) * 100;
                Integer pInteger = probability.intValue();
                analysisTemplate = analysisTemplate.replace("myScore", query.getScore().toString());
                analysisTemplate = analysisTemplate.replace("probability", pInteger.toString());
            }
            if (!checkInt(es.getControlLineOne())) {
                analysisTemplate = analysisTemplate.replace("batchIntro", BATCH_INTRO);
            } else {
                analysisTemplate = analysisTemplate.replace("batchIntro", "");
            }
            if (!checkInt(es.getControlLineOne()) && !checkInt(es.getGuessControlLine()) && !"专科".equals(es.getBatch())) {
                analysisTemplate = analysisTemplate.replace("merge", MERGE);
            } else {
                analysisTemplate = analysisTemplate.replace("merge", "");
            }
            analysisTemplate = analysisTemplate.replace("scoreDiff", es.getRefScore() - es.getCurControlLine() + "");
            analysisTemplate = analysisTemplate.replace("controlLine", es.getControlLineOne().toString());
            analysisTemplate = analysisTemplate.replace("subjectType", query.getSubjectType());
            analysisTemplate = analysisTemplate.replaceAll("batch", es.getBatch());
            analysisTemplate = analysisTemplate.replace("refScore", es.getRefScore().toString());
            analysisTemplate = analysisTemplate.replaceAll("year", YEAR2016);
            result.setAnalysis(analysisTemplate);
        }
        result.setProvinceName(provinceName);
        result.setSubjectType(query.getSubjectType());
    }

    /**
     * 判断是否是null或者0，是则返回true
     */
    private boolean checkInt(Integer score) {
        return (score == null || score == 0);
    }

    //获取相近的批次以及分数数据
    private EligibleSchool getNearBatch(Integer score, List<EligibleSchool> schools) {
        for (EligibleSchool school : schools) {
            if (score > school.getRefScore()) {
                return school;
            }
        }
        return schools.get(schools.size() - 1);
    }

    //赋值两个必传字段provinceName和subjectType
    private void setBaseValues(ScoreAnalysisResultDTO result, ScoreQuery query, AccountInfo accountInfo) {
        Integer provinceId = DEFAULT_PROVINCE_ID;
        if (query.getProvinceId() == null) {
            if (accountInfo.getProvinceId() != null) {
                provinceId = accountInfo.getProvinceId();
            }
        } else {
            provinceId = query.getProvinceId();
        }
        query.setProvinceId(provinceId);
        result.setProvinceName(provinceRepository.queryById(provinceId));
        String subjectType = DEFAULT_SUBJECT_TYPE;
        if (query.getSubjectTypeId() == null) {
            if (!StringUtils.isEmpty(accountInfo.getSubjectType())) {
                subjectType = accountInfo.getSubjectType();
            }
        } else {
            subjectType = query.getSubjectTypeId() == 1 ? "文科" : "理科";
        }
        query.setSubjectTypeId("文科".equals(subjectType) ? 1 : 2);
        query.setSubjectType(subjectType);
        result.setSubjectType(subjectType);
    }
}
