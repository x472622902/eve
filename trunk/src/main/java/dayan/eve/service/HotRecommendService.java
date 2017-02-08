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

import com.alibaba.fastjson.JSON;
import dayan.common.util.SchoolPlatformIdEncoder;
import dayan.eve.config.EveProperties;
import dayan.eve.model.ConstantKeys;
import dayan.eve.model.Pager;
import dayan.eve.model.SchoolTag;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.query.FollowQuery;
import dayan.eve.model.query.HotRecommendQuery;
import dayan.eve.model.query.RecommendQuery;
import dayan.eve.model.school.HotRecommend;
import dayan.eve.model.school.HotSchool;
import dayan.eve.model.school.HotSchoolGroup;
import dayan.eve.repository.AccountInfoRepository;
import dayan.eve.repository.HotRecommendRepository;
import dayan.eve.repository.ProvinceRepository;
import dayan.eve.repository.SchoolFollowRepository;
import dayan.eve.util.SchoolIdPlatformIdUtil;
import dayan.eve.util.TagUtil;
import dayan.eve.web.dto.HotSchoolReadDTO;
import dayan.eve.web.dto.ScoreSegmentDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class HotRecommendService {

    private static final Logger LOGGER = LogManager.getLogger(HotRecommendService.class);
    @Autowired
    SchoolFollowRepository schoolFollowRepository;

    @Autowired
    HotRecommendRepository hotRecommendRepository;

    @Autowired
    AccountInfoRepository accountInfoRepository;

    @Autowired
    ProvinceRepository provinceRepository;

    @Autowired
    RankScoreTransformService rankScoreTransformService;

    @Autowired
    TagUtil tagUtil;

    @Autowired
    SchoolIdPlatformIdUtil schoolIdPlatformIdUtil;

    private String schoolLogoUrlPrefix;

    @Autowired
    public HotRecommendService(EveProperties eveProperties) {
        this.schoolLogoUrlPrefix = eveProperties.getSchool().getLogoPrefix();
    }

    public void createHotRecommend() {
        FollowQuery query = new FollowQuery();

        List<Integer> schoolIds = schoolFollowRepository.querySchoolIds();//获取所有被关注的所有学校
        for (Integer schoolId : schoolIds) {
            query.setSchoolId(schoolId);
            List<AccountInfo> accountInfos = schoolFollowRepository.queryAccounts(query);
            List<HotRecommend> hrList = new LinkedList<>();
            for (String subjectType : ConstantKeys.SUBJECT_TYPES) {
                List<Integer> provinceIds = getProvinceIds(subjectType, accountInfos);
                for (Integer provinceId : provinceIds) {
                    List<AccountInfo> infos = getAccountInfos(subjectType, provinceId, accountInfos);
                    addHotRecommend(hrList, infos, schoolId, subjectType, provinceId);
                }
            }
            if (!hrList.isEmpty()) {
                System.out.println(schoolId);
                hotRecommendRepository.multiInsert(hrList);
            }
        }

    }

    public void updateHotRecommend(Integer schoolId, Integer accountId) {
        AccountInfo info = accountInfoRepository.queryOneInfo(accountId);
        if (StringUtils.isEmpty(info.getSubjectType()) || info.getProvinceId() == null || info.getScore() == null) {
            return;
        }
        HotRecommend hr = new HotRecommend();
        hr.setProvinceId(info.getProvinceId());
        hr.setSubjectType(info.getSubjectType());
        hr.setScoreSegment(info.getScore() / 10);
        hr.setSchoolId(schoolId);
        if (hotRecommendRepository.queryExisted(hr)) {
            hr.setFollowerCount(1);
            List<HotRecommend> hrList = new LinkedList<>();
            hrList.add(hr);
            LOGGER.info("hot recommend list,{}", JSON.toJSONString(hrList, true));
            hotRecommendRepository.multiInsert(hrList);
        }
    }

    private List<Integer> getProvinceIds(String subjectType, List<AccountInfo> accountInfos) {
        List<Integer> provinceIds = new LinkedList<>();
        accountInfos.stream()
                .filter(info -> info.getProvinceId() != null && subjectType.equals(info.getSubjectType())
                        && !provinceIds.contains(info.getProvinceId()))
                .forEach(info -> {
                    provinceIds.add(info.getProvinceId());
                });
        return provinceIds;
    }

    private List<AccountInfo> getAccountInfos(String subjectType, Integer provinceId, List<AccountInfo> accountInfos) {
        List<AccountInfo> infos = new LinkedList<>();
        accountInfos.stream()
                .filter(info -> info.getSubjectType() != null && info.getProvinceId() != null
                        && subjectType.equals(info.getSubjectType())
                        && provinceId.equals(info.getProvinceId()) && !infos.contains(info))
                .forEach(infos::add);
        return infos;
    }

    private void addHotRecommend(List<HotRecommend> hrList, List<AccountInfo> infos, Integer schoolId, String subjectType, Integer provinceId) {
        int[] scoreSegments = new int[100];
        infos.stream().filter(info -> info.getScore() != null)
                .forEach(info -> {
                    scoreSegments[info.getScore() / 10]++;
                });
        for (int i = 0; i < 100; i++) {
            if (scoreSegments[i] != 0) {
                HotRecommend hr = new HotRecommend(schoolId, provinceId, subjectType);
                hr.setScoreSegment(i);
                hr.setFollowerCount(scoreSegments[i]);
                hrList.add(hr);
            }
        }
    }


    public ScoreSegmentDTO readScoreSegments(HotRecommendQuery query) {
        ScoreSegmentDTO result = new ScoreSegmentDTO();
        AccountInfo accountInfo = new AccountInfo();
        if (query.getAccountId() != null) {
            accountInfo = accountInfoRepository.queryOneInfo(query.getAccountId());
        }
        setBaseValues(result, query, accountInfo);
        List<HotRecommend> hrList = hotRecommendRepository.query(query);
        LOGGER.info("score segment query info,{}", JSON.toJSONString(query, true));
        result.setList(setScoreSegmentProportion(hrList));
        return result;
    }


    public HotSchoolReadDTO readSchools(HotRecommendQuery query) {
        LOGGER.info("hot recommend query info,{}", JSON.toJSONString(query, true));
        if (query.getRank() != null) {
            RecommendQuery recommendQuery = new RecommendQuery();
            recommendQuery.setProvinceId(query.getProvinceId());
            recommendQuery.setSubjectType(query.getSubjectType());
            recommendQuery.setRank(query.getRank());
            recommendQuery = rankScoreTransformService.getScoreByRank(recommendQuery);
            if (recommendQuery.getScore() != null) {
                query.setScoreSegment(recommendQuery.getScore() / 10);
            }
        }

        Pager pager = new Pager(0, query.getPage(), query.getSize());
        HotSchoolGroup hotSchoolGroup = new HotSchoolGroup();

        Integer seg = query.getScoreSegment();
        if (seg != null) {
            query.setMaxScore((seg + ConstantKeys.SEGMENT_DIFF) * 10 + 9);
            query.setMinScore((seg - ConstantKeys.SEGMENT_DIFF) * 10);
            List<HotSchool> hotSchools = hotRecommendRepository.querySchool(query);
            setValues(hotSchools, query);
            hotSchoolGroup.setList(hotSchools);
            hotSchoolGroup.setUpperLimit((seg + ConstantKeys.SEGMENT_DIFF) * 10 + 9);
            hotSchoolGroup.setLowerLimit((seg - ConstantKeys.SEGMENT_DIFF) * 10);

            query.setSchoolId(null);
            pager.setCount(hotRecommendRepository.countSchools(query));
        }

        return new HotSchoolReadDTO(hotSchoolGroup, pager);
    }

    private List<HotRecommend> setScoreSegmentProportion(List<HotRecommend> hrList) {
        Integer followerCount = 0;
        for (HotRecommend hr : hrList) {
            followerCount += hr.getFollowerCount();
        }
        hrList = getLimitSegments(hrList);
        for (HotRecommend hr : hrList) {
            float proportion = (float) hr.getFollowerCount() / followerCount;
            //保留小数点后面四位
            hr.setProportion((float) (Math.round(proportion * 10000)) / 10000);
        }
        return hrList;
    }

    private void setValues(List<HotSchool> hotSchools, HotRecommendQuery query) {
        if (hotSchools == null || hotSchools.isEmpty()) {
            return;
        }
        Map<Integer, Integer> schoolIdPlatformIdMap = schoolIdPlatformIdUtil.getSchoolIdAndPlatformIdMap();
        SchoolPlatformIdEncoder encoder = new SchoolPlatformIdEncoder();
        for (HotSchool school : hotSchools) {
            Integer platformId = schoolIdPlatformIdMap.get(school.getId());
            if (platformId != null) {
                school.setPlatformHashId(encoder.encrypt(platformId.longValue()));
            }

            String schoolHashId = encoder.encrypt(school.getId().longValue());
            school.setSchoolHashId(schoolHashId);
            school.setLogoUrl(schoolLogoUrlPrefix + schoolHashId);
            if (school.getTagsValue() != null) {
                List<SchoolTag> schoolTags = tagUtil.getSchoolTags(school.getTagsValue());
                school.setTags(schoolTags);
            }
            school.setHashId(schoolHashId);
            query.setSchoolId(school.getId());
            school.setFollowers(schoolFollowRepository.queryFollowAccounts(query));
            Integer followerCount = schoolFollowRepository.countAccountItem(query);
            school.setFollowerCount(followerCount + school.getRecommendCount() / 10);

        }
    }

    //限定分段个数，最多是个分数段
    private List<HotRecommend> getLimitSegments(List<HotRecommend> hrList) {
        int size = hrList.size();
        if (size <= 10) {
            for (HotRecommend hr : hrList) {
                hr.setMinScore(hr.getScoreSegment() * 10);
                hr.setMaxScore(hr.getScoreSegment() * 10 + 9);
            }
            return hrList;
        }
        List<HotRecommend> limitList = new LinkedList<>();
        int minSegment = hrList.get(0).getScoreSegment();
        int maxSegment = hrList.get(size - 1).getScoreSegment();
        int interval = (maxSegment - minSegment) / 10 + 1;//分段区间范围
        for (int i = minSegment; i < maxSegment + interval; i += interval) {
            HotRecommend hr = new HotRecommend();
            hr.setMinScore(i * 10);
            hr.setMaxScore((i + interval) * 10 - 1);
            int followCount = 0;
            for (HotRecommend aHrList : hrList) {
                if (aHrList.getScoreSegment() >= (i + interval) || aHrList.getScoreSegment() < i) {
                    continue;
                }
                followCount += aHrList.getFollowerCount();
            }
            hr.setFollowerCount(followCount);
            limitList.add(hr);
        }
        return limitList;
    }

    //赋值两个必传字段provinceName和subjectType(没有则设置默认值)
    private void setBaseValues(ScoreSegmentDTO result, HotRecommendQuery query, AccountInfo accountInfo) {
        Integer provinceId = ConstantKeys.DEFAULT_PROVINCE_ID;
        if (query.getProvinceId() == null) {
            if (accountInfo.getProvinceId() != null) {
                provinceId = accountInfo.getProvinceId();
            }
        } else {
            provinceId = query.getProvinceId();
        }
        query.setProvinceId(provinceId);
        result.setProvinceName(provinceRepository.queryById(provinceId));
        String subjectType = ConstantKeys.DEFAULT_SUBJECT_TYPE;
        if (StringUtils.isEmpty(query.getSubjectType())) {
            if (!StringUtils.isEmpty(accountInfo.getSubjectType())) {
                subjectType = accountInfo.getSubjectType();
            }
        } else {
            subjectType = query.getSubjectType();
        }
        query.setSubjectType(subjectType);
        result.setSubjectType(subjectType);
    }
}
