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

import dayan.common.util.SchoolPlatformIdEncoder;
import dayan.eve.config.EveProperties;
import dayan.eve.model.PageResult;
import dayan.eve.model.Pager;
import dayan.eve.model.School;
import dayan.eve.model.SchoolTag;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.query.FollowQuery;
import dayan.eve.repository.SchoolFollowRepository;
import dayan.eve.util.SchoolIdPlatformIdUtil;
import dayan.eve.util.TagUtil;
import dayan.eve.util.WalleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SchoolFollowService {

    private static final Logger LOGGER = LogManager.getLogger(SchoolFollowService.class);
    private static SchoolPlatformIdEncoder encoder = new SchoolPlatformIdEncoder();
    private String schoolLogoUrlPrefix;
    private final WalleUtil followUtil;
    private final SchoolIdPlatformIdUtil schoolIdPlatformIdUtil;
    private final TagUtil tagUtil;
    private final HotRecommendService hotRecommendService;
    private final SchoolFollowRepository schoolFollowRepository;

    @Autowired
    public SchoolFollowService(EveProperties eveProperties, SchoolIdPlatformIdUtil schoolIdPlatformIdUtil, HotRecommendService hotRecommendService, SchoolFollowRepository schoolFollowRepository, WalleUtil followUtil, TagUtil tagUtil) {
        this.schoolLogoUrlPrefix = eveProperties.getSchool().getLogoPrefix();
        this.schoolIdPlatformIdUtil = schoolIdPlatformIdUtil;
        this.hotRecommendService = hotRecommendService;
        this.schoolFollowRepository = schoolFollowRepository;
        this.followUtil = followUtil;
        this.tagUtil = tagUtil;
    }

    private List<School> assign(List<School> list) {
        Map<Integer, Integer> schoolIdPlatformIdMap = schoolIdPlatformIdUtil.getSchoolIdAndPlatformIdMap();
        for (School school : list) {
            Integer platformId = schoolIdPlatformIdMap.get(school.getId());
            if (platformId != null) {
                school.setPlatformHashId(encoder.encrypt(platformId.longValue()));
            }

            String schoolHashId = encoder.encrypt(school.getId().longValue());
            school.setLogoUrl(schoolLogoUrlPrefix + schoolHashId);
            if (school.getTagsValue() != null) {
                List<SchoolTag> schoolTags = tagUtil.getSchoolTags(school.getTagsValue());
                school.setTags(schoolTags);
            }
            school.setSchoolHashId(schoolHashId);
        }
        return list;
    }

    public void follow(FollowQuery query) {
        schoolFollowRepository.follow(query);
        Integer platformId = schoolIdPlatformIdUtil.getSchoolIdAndPlatformIdMap().get(query.getSchoolId());
        if (platformId != null) {
            followUtil.sendFollow(platformId);
        }
        hotRecommendService.updateHotRecommend(query.getSchoolId(), query.getAccountId());
    }

    public void cancel(FollowQuery query) {
        schoolFollowRepository.follow(query);
    }

    public PageResult<School> readSchools(FollowQuery query) {
        Integer count = schoolFollowRepository.countSchool(query);
        PageResult<School> result = new PageResult<>(new Pager(count, query.getPage(), query.getSize()));
        if (count > 0) {
            result.setList(assign(schoolFollowRepository.querySchools(query)));
        }
        return result;
    }

    public PageResult<AccountInfo> readAccounts(FollowQuery query) {
        Integer count = schoolFollowRepository.countAccount(query);
        PageResult<AccountInfo> result = new PageResult<>(new Pager(count, query.getPage(), query.getSize()));
        if (count > 0) {
            result.setList(schoolFollowRepository.queryAccounts(query));
        }
        return result;
    }
}
