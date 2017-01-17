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
import dayan.eve.model.PageResult;
import dayan.eve.model.Pager;
import dayan.eve.model.School;
import dayan.eve.model.SchoolTag;
import dayan.eve.model.query.SearchQuery;
import dayan.eve.model.school.PromptSchool;
import dayan.eve.repository.SchoolAdvisoryRepository;
import dayan.eve.repository.SchoolRepository;
import dayan.eve.util.SchoolIdPlatformIdUtil;
import dayan.eve.util.TagUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author xsg
 */
@Service
@Transactional
public class SchoolSearchService {

    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    SchoolIdPlatformIdUtil schoolIdPlatformIdUtil;

    @Autowired
    TagUtil tagUtil;

    @Value("${logo.school.url.prefix}")
    String schoolLogoUrlPrefix;

    @Autowired
    SchoolAdvisoryRepository schoolAdvisoryRepository;

    private static Map<String, PromptSchool> schoolNameMap;


    private List<School> getSchools(SearchQuery query) {
        List<School> schoolList;
        if (!StringUtils.isEmpty(query.getQueryStr())) {
            query.setQueryStr(query.getQueryStr().replace(" ", ""));
        } else {
            query.setQueryStr(null);
        }

        schoolList = schoolRepository.search(query);
        return schoolList;
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
            if (school.getTagsValue() != null) {
                List<SchoolTag> schoolTags = tagUtil.getSchoolTags(school.getTagsValue());
                school.setTags(schoolTags);
            }
            school.setSchoolHashId(schoolHashId);
        }
        return list;
    }

    private String allSchoolNameString;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    private String getAllNameString() {
        if (allSchoolNameString == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("|");
            SearchQuery query = new SearchQuery();
            List<School> schools = schoolRepository.search(query);
            for (School school : schools) {
                sb.append(school.getName()).append("|");
            }
            allSchoolNameString = sb.toString();
        }
        return allSchoolNameString;
    }

    public List<PromptSchool> getPrompts(String queryString) {
        if ("|".equals(queryString)) {
            return Collections.emptyList();
        }
        String allNameString = getAllNameString();
        Map<String, PromptSchool> schoolNameMap = getSchoolNameMap();
        List<PromptSchool> prompts = new LinkedList<>();
        int middle;
        int left;
        int right = 0;
        while (prompts.size() < 21) {
            //提示个数上限20个
            middle = allNameString.indexOf(queryString, right);
            if (middle == -1) {
                break;
            }
            right = allNameString.indexOf("|", middle);
            left = allNameString.lastIndexOf("|", middle);

            String name = allNameString.substring(left + 1, right);
            PromptSchool promptSchool = schoolNameMap.get(name);
            if (promptSchool != null) {
                prompts.add(promptSchool);
            }
        }
        return prompts;
    }

    private Map<String, PromptSchool> getSchoolNameMap() {
        if (schoolNameMap == null || schoolNameMap.isEmpty()) {
            updateSchoolNameMap();
        }
        return schoolNameMap;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    private void updateSchoolNameMap() {
        Map<String, PromptSchool> newMap = new HashMap<>();

        Map<Integer, Integer> schoolIdPlatformIdMap = schoolIdPlatformIdUtil.getSchoolIdAndPlatformIdMap();
        Map<Integer, Integer> schoolCsMap = schoolIdPlatformIdUtil.getSchoolCSMap();
        SearchQuery query = new SearchQuery();
        List<School> schools = schoolRepository.search(query);
        for (School school : schools) {
            PromptSchool ps = new PromptSchool();
            ps.setId(school.getId());
            ps.setName(school.getName());
            ps.setIsHavingPlatformId(schoolIdPlatformIdMap.containsKey(school.getId()));
            ps.setCs(schoolCsMap.containsKey(ps.getId()));
            newMap.put(ps.getName(), ps);
        }
        schoolNameMap = newMap;
    }

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
        List<School> list = getSchools(query);
        Integer count = schoolRepository.count(query);
        return new PageResult<>(assign(list), new Pager(count, query.getPage(), query.getSize()));
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
