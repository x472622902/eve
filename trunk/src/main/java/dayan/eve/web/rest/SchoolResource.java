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
package dayan.eve.web.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import dayan.common.util.RequestUtil;
import dayan.common.util.SchoolPlatformIdEncoder;
import dayan.eve.exception.ErrorCN;
import dayan.eve.exception.EveException;
import dayan.eve.model.JsonResult;
import dayan.eve.model.JsonResultList;
import dayan.eve.model.enumeration.SortType;
import dayan.eve.model.query.*;
import dayan.eve.model.school.RecommendFeedback;
import dayan.eve.service.CourseService;
import dayan.eve.service.CourseTestResultService;
import dayan.eve.service.HotRecommendService;
import dayan.eve.service.RequestService;
import dayan.eve.service.school.*;
import dayan.eve.service.school.recommend.RecommendFeedbackService;
import dayan.eve.service.school.recommend.RecommendSchoolFacade;
import dayan.eve.service.walle.WalleSchoolService;
import dayan.eve.util.TagUtil;
import dayan.eve.web.dto.HotSchoolReadDTO;
import dayan.eve.web.dto.SearchPromptDTO;
import dayan.eve.web.dto.WalleCsQueryDTO;
import dayan.eve.web.dto.school.*;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.stream.Collectors;

import static dayan.eve.model.ConstantKeys.SchoolDetail.SCHOOL_TYPES;

/**
 * @author xsg
 */
@Aspect
@RestController
@RequestMapping(value = "/api/v20/mobile/school")
public class SchoolResource {

    private static final Logger LOGGER = LogManager.getLogger();
    private final SchoolSearchService schoolSearchService;
    private final HotRecommendService hotRecommendService;
    private final SchoolProfileService schoolProfileService;
    private final SchoolAdmissionService schoolAdmissionService;
    private final SchoolMajorService schoolMajorService;
    private final SchoolScoreService schoolScoreService;
    private final RecommendFeedbackService recommendFeedbackService;
    private final TagUtil tagUtil;
    private final RequestService requestService;
    private final WeiboService weiboService;
    private final WalleSchoolService walleSchoolService;
    private final SchoolPlanService schoolPlanService;
    private final RecommendSchoolFacade recommendSchoolFacade;

    @Autowired
    public SchoolResource(RequestService requestService, CourseService courseService, CourseTestResultService courseTestResultService, SchoolSearchService schoolSearchService, HotRecommendService hotRecommendService, SchoolScoreService schoolScoreService, TagUtil tagUtil, SchoolAdmissionService schoolAdmissionService, SchoolProfileService schoolProfileService, SchoolMajorService schoolMajorService, SchoolProfileService schoolProfileService1, SchoolAdmissionService schoolAdmissionService1, SchoolMajorService schoolMajorService1, SchoolScoreService schoolScoreService1, RecommendFeedbackService recommendFeedbackService, WeiboService weiboService, WalleSchoolService walleSchoolService, SchoolPlanService schoolPlanService, RecommendSchoolFacade recommendSchoolFacade) {
        this.schoolSearchService = schoolSearchService;
        this.hotRecommendService = hotRecommendService;
        this.tagUtil = tagUtil;
        this.requestService = requestService;
        this.schoolProfileService = schoolProfileService1;
        this.schoolAdmissionService = schoolAdmissionService1;
        this.schoolMajorService = schoolMajorService1;
        this.schoolScoreService = schoolScoreService1;
        this.recommendFeedbackService = recommendFeedbackService;
        this.weiboService = weiboService;
        this.walleSchoolService = walleSchoolService;
        this.schoolPlanService = schoolPlanService;
        this.recommendSchoolFacade = recommendSchoolFacade;
    }


    @ApiOperation("学校推荐")
    @PostMapping(value = "/recommend")
    public JsonResultList recommend(@RequestBody RecommendDTO recommendDTO, HttpServletRequest request) {

        Integer accountId = requestService.getAccountId(request);
        String requestIp = RequestUtil.getIp(request);
        RecommendQuery query = buildRecommendQuery(recommendDTO);
        return new JsonResultList(recommendSchoolFacade.recommend(query, accountId));
    }

    private RecommendQuery buildRecommendQuery(RecommendDTO recommendDTO) {
        if (StringUtils.isEmpty(recommendDTO.getProvinceId()) || (StringUtils.isEmpty(recommendDTO.getScore()) && StringUtils.isEmpty(recommendDTO.getRank())) || StringUtils.isEmpty(recommendDTO.getSubjectTypeId())) {
            throw new EveException(ErrorCN.DEFAULT_SERVER_ERROR);
        }
        RecommendQuery query = new RecommendQuery();

        if (!StringUtils.isEmpty(recommendDTO.getSortType())) {
            if (SortType.RecommendScore.toString().equals(recommendDTO.getSortType())) {
                query.setSortType(SortType.RecommendScore);
            } else if (SortType.SchoolRank.toString().equals(recommendDTO.getSortType())) {
                query.setSortType(SortType.SchoolRank);
            } else {
                query.setSortType(SortType.RefScore);
            }
        } else {
            query.setSortType(SortType.RecommendScore);
        }
        query.setProvinceId(Integer.valueOf(recommendDTO.getProvinceId()));
        query.setProvinceIds(recommendDTO.getProvinceIds());
        if (StringUtils.isEmpty(recommendDTO.getRank())) {
            query.setScore(Integer.valueOf(recommendDTO.getScore()));
        } else {
            query.setRank(Integer.valueOf(recommendDTO.getRank()));
        }
        query.setSubjectType("1".equals(recommendDTO.getSubjectTypeId()) ? "文科" : "理科");
        if (recommendDTO.getRefTypeIds() != null && !recommendDTO.getRefTypeIds().isEmpty()) {
            query.setRefTypeIds(recommendDTO.getRefTypeIds());
        }
        query.setCs(recommendDTO.getCs());
        if (recommendDTO.getSchoolTypeIds() != null && !recommendDTO.getSchoolTypeIds().isEmpty()) {
            query.setSchoolTypes(recommendDTO.getSchoolTypeIds().stream()
                    .map(schoolTypeId -> SCHOOL_TYPES[Integer.valueOf(schoolTypeId) - 1])
                    .collect(Collectors.toList()));
        }
        if (recommendDTO.getTagIds() == null || recommendDTO.getTagIds().isEmpty()) {
            query.setTagsValue(null);
        } else {
            query.setTagsValue(tagUtil.getTagsValue(recommendDTO.getTagIds()));
        }
        query.setAdvisory(recommendDTO.getIsAdvisory());
        query.setMajorCode(recommendDTO.getMajorCode());
        query.setRequestIp(recommendDTO.getRequestIp());
        query.initPaging(recommendDTO.getPaging());
        return query;
    }

    @ApiOperation("学校搜索提示")
    @PostMapping("/search/prompt")
    public JsonResult read(@RequestBody SearchPromptDTO promptDTO, HttpServletRequest request) {
        return new JsonResult(schoolSearchService.getPrompts(promptDTO.getQueryString()));
    }

    @ApiOperation("学校搜索")
    @PostMapping("/search")
    public JsonResultList search(@RequestBody SchoolSearchDTO searchDTO, HttpServletRequest request) {
        return new JsonResultList(schoolSearchService.searchSchools(buildSearchQuery(searchDTO)));
    }

    private SearchQuery buildSearchQuery(SchoolSearchDTO searchDTO) {
        SearchQuery query = new SearchQuery();
        if (!StringUtils.isEmpty(searchDTO.getSchoolHashId())) {
            query.setSchoolId(new SchoolPlatformIdEncoder().decode(searchDTO.getSchoolHashId()).intValue());
        }
        query.setQueryStr(searchDTO.getQuery());
        if (searchDTO.getSchoolTypeIds() != null && !searchDTO.getSchoolTypeIds().isEmpty()) {
            query.setSchoolTypes(searchDTO.getSchoolTypeIds().stream()
                    .map(schoolTypeId -> SCHOOL_TYPES[Integer.valueOf(schoolTypeId) - 1])
                    .collect(Collectors.toCollection(LinkedList::new)));
        }
        query.setIsAdvisory(searchDTO.getIsAdvisory());
        query.setCs(searchDTO.getCs());
        if (searchDTO.getProvinceIds() != null && !searchDTO.getProvinceIds().isEmpty()) {
            query.setProvinceIds(searchDTO.getProvinceIds().stream()
                    .filter(provinceId -> !StringUtils.isEmpty(provinceId))
                    .map(Integer::valueOf)
                    .collect(Collectors.toCollection(LinkedList::new)));
        }
        query.setReadAll(searchDTO.getReadAll());
        if (searchDTO.getTagIds() != null && !searchDTO.getTagIds().isEmpty()) {
            query.setTagsValue(tagUtil.getTagsValue(searchDTO.getTagIds()));
        }
        query.initPaging(searchDTO.getPaging());

        return query;
    }


    @ApiOperation("读取学校微博")
    @PostMapping("/readWeibo")
    public JsonResultList readWeibo(@RequestBody SchoolWeiboQueryDTO queryDTO) {
        WeiboQuery query = new WeiboQuery();
        SchoolPlatformIdEncoder idEncoder = new SchoolPlatformIdEncoder();
        if (!StringUtils.isEmpty(queryDTO.getSchoolHashId())) {
            query.setSchoolId(idEncoder.decode(queryDTO.getSchoolHashId()).intValue());
        }
        query.initPaging(queryDTO.getPaging());
        return new JsonResultList(weiboService.readWeiboTimelines(query));
    }


    @ApiOperation("读取人工客服列表")
    @PostMapping("/readCS")
    public JsonResult readCS(@RequestBody SchoolInfoQueryDTO queryDTO) throws Exception {
        return new JsonResult(JSONObject.parse(walleSchoolService.readCustomerService(queryDTO.getSchoolHashId())));
    }

    @ApiOperation("读取在线人工客服列表")
    @PostMapping("/readAllCS")
    public JsonResultList readAllCSOnline(@RequestBody WalleCsQueryDTO queryDTO, HttpServletRequest request) throws Exception {
        WalleQuery query = new WalleQuery();
        query.initPaging(queryDTO.getPaging());
        query.setAccountId(requestService.getAccountIdValue(request));
        query.setReadAllCs(queryDTO.getReadAllCs());
        return new JsonResultList(walleSchoolService.readAllCs(query));
    }

    @ApiOperation("读取招生计划")
    @PostMapping("/readAdmissionPlan")
    public JsonResult readAdmissionPlan(@RequestBody SchoolInfoQueryDTO queryDTO) {
        String schoolHashId = queryDTO.getSchoolHashId();
        if (StringUtils.isEmpty(schoolHashId)) {
            throw new EveException(ErrorCN.DEFAULT_SERVER_ERROR);
        }
        return JSONObject.parseObject(schoolPlanService.readPlan(schoolHashId), JsonResult.class);
    }

    @ApiOperation("读取总招生计划")
    @PostMapping("/readAdmissionSumPlan")
    public JsonResult readAdmissionSumPlan(@RequestBody SchoolInfoQueryDTO queryDTO) {
        String schoolHashId = queryDTO.getSchoolHashId();
        if (StringUtils.isEmpty(schoolHashId)) {
            throw new EveException(ErrorCN.DEFAULT_SERVER_ERROR);
        }
        return JSONObject.parseObject(schoolPlanService.readSumPlan(schoolHashId), JsonResult.class);
    }

    @ApiOperation("读取分数段")
    @PostMapping("/readScoreSegments")
    public JsonResult readScoreSegments(@RequestBody HotRecommendDTO hotRecommendDTO) {
        return new JsonResult(hotRecommendService.readScoreSegments(buildScoreSegmentQuery(hotRecommendDTO)));
    }

    @ApiOperation("热门推荐")
    @PostMapping("/hotRecommend")
    public JsonResultList hotRecommend(@RequestBody HotRecommendDTO hotRecommendDTO) {
        HotSchoolReadDTO hotSchoolReadDTO = hotRecommendService.readSchools(buildScoreSegmentQuery(hotRecommendDTO));
        JsonResultList resultList = new JsonResultList();
        resultList.setPager(hotSchoolReadDTO.getPager());
        resultList.setData(hotSchoolReadDTO.getSchools());
        return resultList;
    }

    private HotRecommendQuery buildScoreSegmentQuery(HotRecommendDTO hotRecommendDTO) {
        HotRecommendQuery query = new HotRecommendQuery();
        SchoolPlatformIdEncoder idEncoder = new SchoolPlatformIdEncoder();
        if (!StringUtils.isEmpty(hotRecommendDTO.getSchoolHashId())) {
            query.setSchoolId(idEncoder.decode(hotRecommendDTO.getSchoolHashId()).intValue());
        }
        if (!StringUtils.isEmpty(hotRecommendDTO.getScore())) {
            query.setScoreSegment(Integer.valueOf(hotRecommendDTO.getScore()) / 10);
        }
        if (!StringUtils.isEmpty(hotRecommendDTO.getRank())) {
            query.setRank(Integer.valueOf(hotRecommendDTO.getRank()));
        }
        if (!StringUtils.isEmpty(hotRecommendDTO.getProvinceId())) {
            query.setProvinceId(Integer.valueOf(hotRecommendDTO.getProvinceId()));
        }
        query.setSubjectType("1".equals(hotRecommendDTO.getSubjectTypeId()) ? "文科" : "理科");
        query.initPaging(hotRecommendDTO.getPaging());
        if (hotRecommendDTO.getProvinceIds() != null && !hotRecommendDTO.getProvinceIds().isEmpty()) {
            query.setProvinceIds(hotRecommendDTO.getProvinceIds().stream()
                    .map(Integer::valueOf)
                    .collect(Collectors.toCollection(LinkedList::new)));
        }
        if (hotRecommendDTO.getRefTypeIds() != null && !hotRecommendDTO.getRefTypeIds().isEmpty()) {
            query.setRefTypeIds(hotRecommendDTO.getRefTypeIds().stream()
                    .map(Integer::valueOf)
                    .collect(Collectors.toCollection(LinkedList::new)));
        }
        if (hotRecommendDTO.getSchoolTypeIds() != null && !hotRecommendDTO.getSchoolTypeIds().isEmpty()) {
            query.setSchoolTypes(hotRecommendDTO.getSchoolTypeIds().stream()
                    .map(schoolTypeId -> SCHOOL_TYPES[Integer.valueOf(schoolTypeId) - 1])
                    .collect(Collectors.toList()));
        }
        if (hotRecommendDTO.getTagIds() == null || hotRecommendDTO.getTagIds().isEmpty()) {
            query.setTagsValue(null);
        } else {
            query.setTagsValue(tagUtil.getTagsValue(hotRecommendDTO.getTagIds()));
        }
        query.setIsAdvisory(hotRecommendDTO.getIsAdvisory());
        return query;
    }

    @ApiOperation("读取招生政策")
    @PostMapping("/readAdmission")
    public JsonResult readAdmission(@RequestBody SchoolInfoQueryDTO queryDTO) {
        Integer schoolId = getSchoolId(queryDTO.getSchoolHashId());
        return new JsonResult(schoolAdmissionService.readSchoolAdmission(schoolId));
    }

    private Integer getSchoolId(String schoolHashId) {
        if (schoolHashId == null) {
            throw new RuntimeException("schoolHashId is null");
        }
        Long id = new SchoolPlatformIdEncoder().decode(schoolHashId);
        return id.intValue();
    }

    @ApiOperation("读取专业")
    @PostMapping("/readMajor")
    public JsonResult readMajor(@RequestBody SchoolInfoQueryDTO queryDTO) {
        String majors = schoolMajorService.readMajors(queryDTO.getSchoolHashId());
        return JSONObject.parseObject(majors, JsonResult.class);
    }

    @ApiOperation("读取历年分数")
    @PostMapping("/readScore")
    public JsonResult readScore(@RequestBody SchoolScoreQueryDTO queryDTO, HttpServletRequest request) {
        Integer accountId = requestService.getAccountIdValue(request);
        return new JsonResult(schoolScoreService.readScore(buildScoreQuery(queryDTO), accountId).getList());
    }

    @ApiOperation("分数分析")
    @PostMapping("/scoreAnalysis")
    public JsonResult scoreAnalysis(@RequestBody SchoolScoreQueryDTO queryDTO, HttpServletRequest request) {
        Integer accountId = requestService.getAccountId(request);
        return new JsonResult(schoolScoreService.scoreAnalyse(buildScoreQuery(queryDTO), accountId));
    }

    private ScoreQuery buildScoreQuery(SchoolScoreQueryDTO queryDTO) {
        Integer schoolId = new SchoolPlatformIdEncoder().decode(queryDTO.getSchoolHashId()).intValue();
        ScoreQuery scoreQuery = new ScoreQuery();
        scoreQuery.setSchoolId(schoolId);
        scoreQuery.setSchoolHashId(queryDTO.getSchoolHashId());
        if (queryDTO.getYear() != null) {
            scoreQuery.setYear(queryDTO.getYear());
        }
        if (!StringUtils.isEmpty(queryDTO.getScore())) {
            scoreQuery.setScore(Integer.valueOf(queryDTO.getScore()));
        }
        if (!StringUtils.isEmpty(queryDTO.getProvinceId())) {
            scoreQuery.setProvinceId(Integer.valueOf(queryDTO.getProvinceId()));
        }
        if (!StringUtils.isEmpty(queryDTO.getSubjectTypeId())) {
            scoreQuery.setSubjectTypeId(Integer.valueOf(queryDTO.getSubjectTypeId()));
            scoreQuery.setSubjectType("1".equals(queryDTO.getSubjectTypeId()) ? "文科" : "理科");
        }
        scoreQuery.setBatch(queryDTO.getBatch());
        LOGGER.info("school score read ,query info:" + JSON.toJSONString(scoreQuery, true));
        return scoreQuery;
    }

    @ApiOperation("读取推荐分数")
    @PostMapping("/readRecScore")
    public JsonResult readRecScore(@RequestBody SchoolScoreQueryDTO queryDTO) {
        return new JsonResult(schoolScoreService.readRecScore(buildRecScoreQuery(queryDTO)).getList());
    }

    private ScoreQuery buildRecScoreQuery(SchoolScoreQueryDTO data) {
        if (data.getSchoolHashId() == null || data.getProvinceId() == null || data.getSubjectTypeId() == null) {
            throw new EveException(ErrorCN.DEFAULT_SERVER_ERROR);
        }
        Integer schoolId = new SchoolPlatformIdEncoder().decode(data.getSchoolHashId()).intValue();
        ScoreQuery scoreQuery = new ScoreQuery();
        scoreQuery.setSchoolId(schoolId);
        scoreQuery.setProvinceId(Integer.valueOf(data.getProvinceId()));
        scoreQuery.setSubjectType("1".equals(data.getSubjectTypeId()) ? "文科" : "理科");
        return scoreQuery;
    }

    @ApiOperation("读取学校详情")
    @PostMapping("/readProfile")
    public JsonResult readProfile(@RequestBody SchoolInfoQueryDTO queryDTO) {
        return new JsonResult(schoolProfileService.readSchoolProfile(queryDTO.getSchoolHashId()));
    }

    @ApiOperation("推荐意见反馈")
    @PostMapping("/recommendFeedback")
    public JsonResult recommendFeedback(@RequestBody RecommendFeedbackDTO recommendFeedbackDTO, HttpServletRequest request) {
        RecommendFeedback recommendFeedback = coverToFeedback(recommendFeedbackDTO);
        recommendFeedback.setAccountId(requestService.getAccountIdValue(request));
        recommendFeedbackService.createFeedback(recommendFeedback);
        return new JsonResult();
    }

    private RecommendFeedback coverToFeedback(RecommendFeedbackDTO feedbackDTO) {
        RecommendFeedback feedback = new RecommendFeedback();
        if (!StringUtils.isEmpty(feedbackDTO.getProvinceId())) {
            feedback.setProvinceId(Integer.valueOf(feedbackDTO.getProvinceId()));
        }
        if (!StringUtils.isEmpty(feedbackDTO.getScore())) {
            feedback.setScore(Integer.valueOf(feedbackDTO.getScore()));
        } else if (!StringUtils.isEmpty(feedbackDTO.getRank())) {
            feedback.setRank(Integer.valueOf(feedbackDTO.getRank()));
        } else {
            throw new EveException(ErrorCN.DEFAULT_SERVER_ERROR);
        }
        if (!StringUtils.isEmpty(feedbackDTO.getSubjectTypeId())) {
            feedback.setSubjectType(feedbackDTO.getSubjectTypeId().equals("1") ? "文科" : "理科");
        }
        if (!StringUtils.isEmpty(feedbackDTO.getStarLevel())) {
            feedback.setStarLevel(Integer.valueOf(feedbackDTO.getStarLevel()));
        }
        if (!StringUtils.isEmpty(feedbackDTO.getContent())) {
            feedback.setContent(feedbackDTO.getContent());
        }
        if (!StringUtils.isEmpty(feedbackDTO.getMajorCode())) {
            feedback.setMajorCode(feedbackDTO.getMajorCode());
        }
        return feedback;
    }

}
