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
package dayan.eve.service.impl;

import com.alibaba.fastjson.JSON;
import dayan.common.util.SchoolPlatformIdEncoder;
import dayan.eve.model.PageResult;
import dayan.eve.model.Pager;
import dayan.eve.model.School;
import dayan.eve.model.SchoolTag;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.query.FollowQuery;
import dayan.eve.repository.SchoolFollowRepository;
import dayan.eve.service.SchoolFollowService;
import dayan.eve.util.SchoolIdPlatformIdUtil;
import dayan.eve.util.TagUtil;
import dayan.eve.util.WalleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class SchoolFollowServiceImpl implements SchoolFollowService {

    private static final Logger LOGGER = LogManager.getLogger(SchoolFollowService.class);
    @Autowired
    WalleUtil followUtil;

    @Autowired
    SchoolFollowRepository schoolFollowRepository;

    @Autowired
    SchoolIdPlatformIdUtil schoolIdPlatformIdUtil;

    @Autowired
    TagUtil tagUtil;

    @Value("${logo.school.url.prefix}")
    String schoolLogoUrlPrefix;

    private static SchoolPlatformIdEncoder encoder = new SchoolPlatformIdEncoder();

    List<School> assign(List<School> list) {
        if (list == null || list.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
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

    /**
     * @param query
     * @param isReadSchools 用来判断是读取关注的学校还是关注某学校的用户
     * @return
     */
    public Pager setPager(FollowQuery query, boolean isReadSchools) {
        Pager pager = new Pager();
        Integer count = 0;
        if (isReadSchools) {
            count = schoolFollowRepository.countSchool(query);
        } else {
            count = schoolFollowRepository.countAccount(query);
        }
        pager.setCount(count);
        if (query.getPage() != null) {
            pager.setPage(query.getPage());
        }
        if (query.getSize() != null) {
            pager.setSize(query.getSize());
        }
        return pager;
    }
//
//    @Autowired
//    HotRecommendV20Service hotRecommendV20Service;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void follow(FollowQuery query) {
        List<Integer> followIds = schoolFollowRepository.checkIsFollowed(query);
        Integer followId = null;
        if (!followIds.isEmpty()) {
            followId = followIds.get(0);
            if (followIds.size() > 1) {
                LOGGER.warn("query {}", JSON.toJSONString(query, true));
            }
        }
        if (followId == null) {
            schoolFollowRepository.follow(query);
        } else {
            schoolFollowRepository.refollow(followId);
        }
        Integer platformId = schoolIdPlatformIdUtil.getSchoolIdAndPlatformIdMap().get(query.getSchoolId());
        if (platformId != null) {
            followUtil.sendFollow(platformId);
        }
        // TODO: 12/30/2016  hotRecommendV20Service.updateHotRecommend
//        hotRecommendV20Service.updateHotRecommend(query.getSchoolId(), query.getAccountId());
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void cancel(FollowQuery query) {
        schoolFollowRepository.cancelFollow(query);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public PageResult<School> readSchools(FollowQuery query) {
        List<School> schools = schoolFollowRepository.querySchools(query);
        Pager pager = setPager(query, true);
        return new PageResult<>(assign(schools), pager);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public PageResult<AccountInfo> readAccounts(FollowQuery query) {
        List<AccountInfo> accounts = schoolFollowRepository.queryAccounts(query);
        Pager pager = setPager(query, false);
        return new PageResult<>(accounts, pager);
    }
}
