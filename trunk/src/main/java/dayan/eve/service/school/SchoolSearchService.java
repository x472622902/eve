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
package dayan.eve.service.school;

import dayan.common.util.SchoolPlatformIdEncoder;
import dayan.eve.config.EveProperties;
import dayan.eve.model.PageResult;
import dayan.eve.model.School;
import dayan.eve.model.query.SearchQuery;
import dayan.eve.model.school.PromptSchool;
import dayan.eve.repository.SchoolAdvisoryRepository;
import dayan.eve.repository.SchoolRepository;
import dayan.eve.service.cache.SchoolCache;
import dayan.eve.util.SchoolIdPlatformIdUtil;
import dayan.eve.util.SearchPromptUtil;
import dayan.eve.util.TagUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xsg
 */
@Service
@Transactional
public class SchoolSearchService {

    private final SchoolRepository schoolRepository;
    private final SchoolAdvisoryRepository schoolAdvisoryRepository;
    private final SchoolIdPlatformIdUtil schoolIdPlatformIdUtil;
    private final TagUtil tagUtil;
    private final String schoolLogoUrlPrefix;
    private final SchoolCache schoolCache;

    @Autowired
    public SchoolSearchService(SchoolIdPlatformIdUtil schoolIdPlatformIdUtil, TagUtil tagUtil, SchoolAdvisoryRepository schoolAdvisoryRepository, SchoolRepository schoolRepository, EveProperties eveProperties, SchoolCache schoolCache) {
        this.schoolIdPlatformIdUtil = schoolIdPlatformIdUtil;
        this.tagUtil = tagUtil;
        this.schoolAdvisoryRepository = schoolAdvisoryRepository;
        this.schoolRepository = schoolRepository;
        this.schoolLogoUrlPrefix = eveProperties.getSchool().getLogoPrefix();
        this.schoolCache = schoolCache;
    }


    private List<School> getSchools(SearchQuery query) {
        List<School> schoolList;
        if (!StringUtils.isEmpty(query.getQueryStr())) {
            query.setQueryStr(query.getQueryStr().replace(" ", ""));
        } else {
            query.setQueryStr(null);
        }
        return schoolRepository.search(query);
    }

    private List<School> assign(List<School> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        Map<Integer, Integer> schoolIdPlatformIdMap = schoolIdPlatformIdUtil.getAllSchoolPlatformMap();
        SchoolPlatformIdEncoder encoder = new SchoolPlatformIdEncoder();
        for (School school : list) {
            if (schoolIdPlatformIdMap.containsKey(school.getId())) {
                school.setPlatformHashId(encoder.encrypt(schoolIdPlatformIdMap.get(school.getId()).longValue()));
            }

            String schoolHashId = encoder.encrypt(school.getId().longValue());
            school.setLogoUrl(schoolLogoUrlPrefix + schoolHashId);
            tagUtil.buildSchoolTags(school);
            school.setSchoolHashId(schoolHashId);
        }
        return list;
    }

    public List<PromptSchool> getPrompts(String key) {
        return SearchPromptUtil.getPrompt(schoolCache.getAllSchoolNames(), key)
                .stream()
                .map(this::buildPrompt)
                .collect(Collectors.toList());
    }

    private PromptSchool buildPrompt(String promptStr) {
        String[] split = promptStr.split("-");
        Integer schoolId = Integer.valueOf(split[0]);
        String schoolName = split[1];
        PromptSchool ps = new PromptSchool();
        ps.setId(schoolId);
        ps.setName(schoolName);
        ps.setIsHavingPlatformId(schoolIdPlatformIdUtil.getSchoolIdAndPlatformIdMap().containsKey(schoolId));
        ps.setCs(schoolIdPlatformIdUtil.getSchoolCSMap().containsKey(schoolId));
        return ps;
    }


    public Map<String, PromptSchool> getSchoolNameMap() {
        Map<String, PromptSchool> promptSchoolMap = new HashMap<>();

        Map<Integer, Integer> schoolIdPlatformIdMap = schoolIdPlatformIdUtil.getSchoolIdAndPlatformIdMap();
        Map<Integer, Integer> schoolCsMap = schoolIdPlatformIdUtil.getSchoolCSMap();
        List<School> schools = schoolRepository.search(new SearchQuery());
        for (School school : schools) {
            PromptSchool ps = new PromptSchool();
            ps.setId(school.getId());
            ps.setName(school.getName());
            ps.setIsHavingPlatformId(schoolIdPlatformIdMap.containsKey(school.getId()));
            ps.setCs(schoolCsMap.containsKey(ps.getId()));
            promptSchoolMap.put(ps.getName(), ps);
        }
        return promptSchoolMap;
    }

    // TODO: 3/1/2017 后台功能：热门学校
    public List<School> getAdvisorySchools() {
        SearchQuery searchQuery = new SearchQuery();
        searchQuery.setAdvisorySchoolIds(schoolAdvisoryRepository.query());
        List<School> list = schoolRepository.search(searchQuery);
        return assign(list);
    }

    public void addAdvisorySchool(Integer schoolId) {
        schoolAdvisoryRepository.insert(schoolId);
    }

    public void deleteAdvisorySchool(Integer schoolId) {
        schoolAdvisoryRepository.delete(schoolId);
    }

    public PageResult<School> searchSchools(SearchQuery query) {
        Integer count = schoolRepository.count(query);
        PageResult<School> pageResult = new PageResult<>(count, query.getPage(), query.getSize());
        if (count > 0) {
            pageResult.setList(assign(getSchools(query)));
        }
        return pageResult;
    }

    public School readSingleSchool(SearchQuery query) {
        List<School> list = getSchools(query);
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("学校不存在");
        }
        assign(list);
        return list.get(0);
    }
}
