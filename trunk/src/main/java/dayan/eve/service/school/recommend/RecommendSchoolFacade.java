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
package dayan.eve.service.school.recommend;

import com.alibaba.fastjson.JSON;
import dayan.common.util.SchoolPlatformIdEncoder;
import dayan.eve.config.EveProperties;
import dayan.eve.model.PageResult;
import dayan.eve.model.Pager;
import dayan.eve.model.query.RecommendQuery;
import dayan.eve.model.school.RecommendSchool;
import dayan.eve.model.school.RecommendSchoolItem;
import dayan.eve.model.school.SchoolRecommendLog;
import dayan.eve.repository.LogSchoolRecommendRepository;
import dayan.eve.repository.SchoolMajorRepository;
import dayan.eve.service.RankScoreTransformService;
import dayan.eve.util.SchoolIdPlatformIdUtil;
import dayan.eve.util.TagUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xsg
 */
@Service
public class RecommendSchoolFacade {

    private static final Logger LOGGER = LogManager.getLogger(RecommendSchoolFacade.class);
    private final RecommendSchoolService recommendService;
    private final RankScoreTransformService changeService;
    private final SchoolIdPlatformIdUtil schoolIdPlatformIdUtil;
    private final TagUtil tagUtil;
    private final SchoolMajorRepository schoolMajorMapper;
    private final LogSchoolRecommendRepository logSchoolRecommendRepository;
    private String schoolLogoUrlPrefix;

    @Autowired
    public RecommendSchoolFacade(SchoolMajorRepository schoolMajorMapper, TagUtil tagUtil, RankScoreTransformService changeService, SchoolIdPlatformIdUtil schoolIdPlatformIdUtil, RecommendSchoolService recommendService, LogSchoolRecommendRepository logSchoolRecommendRepository, EveProperties eveProperties) {
        this.schoolMajorMapper = schoolMajorMapper;
        this.tagUtil = tagUtil;
        this.changeService = changeService;
        this.schoolIdPlatformIdUtil = schoolIdPlatformIdUtil;
        this.recommendService = recommendService;
        this.logSchoolRecommendRepository = logSchoolRecommendRepository;
        this.schoolLogoUrlPrefix = eveProperties.getSchool().getLogoPrefix();
    }

    private List<RecommendSchoolItem> convertToItems(List<RecommendSchool> list) {
        List<RecommendSchoolItem> items = new ArrayList<>();
        Map<Integer, Integer> schoolIdPlatformIdMap = schoolIdPlatformIdUtil.getSchoolIdAndPlatformIdMap();
        SchoolPlatformIdEncoder encoder = new SchoolPlatformIdEncoder();
        //最小分调整为refScore-5，参照分调整为refScore+5
        list.stream().filter(rs -> rs.getSchoolId() != null).forEach(rs -> {
            RecommendSchoolItem item = new RecommendSchoolItem();
            if (schoolIdPlatformIdMap != null) {
                Integer platformId = schoolIdPlatformIdMap.get(rs.getSchoolId());
                if (platformId == null) {
                    item.setPlatformHashId(null);
                } else {
                    item.setPlatformHashId(encoder.encrypt(platformId.longValue()));
                }
            }
            String hashId = encoder.encrypt(rs.getSchoolId().longValue());
            item.setAddress(rs.getAddress());
            item.setLogoUrl(schoolLogoUrlPrefix + hashId);
            item.setTags(tagUtil.getSchoolTags(rs.getTagsValue()));
            item.setSchoolHashId(hashId);
            item.setName(rs.getSchoolName());
            item.setRecommendScore(rs.getRecommendScore().intValue());
            item.setProbability(rs.getProbability());
            item.setRefTypeId(rs.getRefTypeId());
            item.setCity(rs.getCity());
            item.setSchoolRank(rs.getSchoolRank());
            //最小分调整为refScore-5，参照分调整为refScore+5
            item.setMinScore(rs.getRefScore() - 10);
            item.setRefScore(rs.getRefScore());
            item.setBatch(rs.getBatch());
            item.setControlLine(rs.getControlLine());
            item.setCs(rs.getCs());
            items.add(item);
        });
        return items;
    }

    private List<RecommendSchool> subList(List<RecommendSchool> list, int start, int end) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        if (list.size() < start) {
            return Collections.emptyList();
        }
        if (list.size() < end) {
            return list.subList(start, list.size());
        }
        return list.subList(start, end);
    }

    private SchoolRecommendLog getRecommendLog(RecommendQuery query, Integer accountId) {
        SchoolRecommendLog log = new SchoolRecommendLog();
        if (accountId != null) {
            log.setAccountId(accountId);
        }
        log.setMajorCode(query.getMajorCode());
        log.setProvinceId(query.getProvinceId());
        log.setRank(query.getRank());
        log.setRequestIp(query.getRequestIp());
        log.setScore(query.getScore());
        log.setSubjectType(query.getSubjectType());
        log.setFilter(JSON.toJSONString(query));
        return log;
    }

    public PageResult<RecommendSchoolItem> recommend(RecommendQuery query, Integer accountId) {
        if (query.getPage() == 1) {
            SchoolRecommendLog recommendLog = getRecommendLog(query, accountId);
            logSchoolRecommendRepository.insertLog(recommendLog);
            LOGGER.info("recommend school. {}", JSON.toJSONString(recommendLog, true));
        }
        if (!StringUtils.isEmpty(query.getMajorCode())) {
            String majorTypeCode = query.getMajorCode().substring(0, 4);
            List<Integer> schoolIds = schoolMajorMapper.queryByMajorTypeCode(majorTypeCode);
            if (schoolIds != null && !schoolIds.isEmpty()) {
                query.setSchoolIds(schoolIds);
            }
        }
        if (query.getRank() != null) {
            query = changeService.getScoreByRank(query);
        }
        List<RecommendSchool> list = new LinkedList<>();
        if (query.getScore() != null) {
            list = recommendService.getRecommendSchools(query);
        }

        Pager pager = new Pager(list.size(), query.getPage(), query.getSize());
        List<RecommendSchool> subList = subList(list, query.getStart(), query.getStart() + query.getSize());
        return new PageResult<RecommendSchoolItem>(convertToItems(subList), pager);
    }

}
