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
import dayan.eve.config.EveProperties;
import dayan.eve.model.MoPageResult;
import dayan.eve.model.PageResult;
import dayan.eve.model.major.Major;
import dayan.eve.model.major.MoMajor;
import dayan.eve.model.query.SearchQuery;
import dayan.eve.repository.MajorRepository;
import dayan.eve.service.cache.MajorCache;
import dayan.eve.util.MoUtil;
import dayan.eve.util.SearchPromptUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author xsg
 */
@Service
public class MajorSearchService {

    private final static Logger LOGGER = LogManager.getLogger(MajorSearchService.class);
    private String allMajorNameString;
    private String[] allMajorNames;
    private static Map<String, MoMajor> majorMap = new HashMap<>();

    private final MajorRepository majorRepository;
    private final MajorCache majorCache;
    private final MoUtil moUtil;

    @Autowired
    public MajorSearchService(EveProperties eveProperties, MoUtil moUtil, MajorRepository majorRepository, MajorCache majorCache) {
        this.moUtil = moUtil;
        this.majorRepository = majorRepository;
        this.majorCache = majorCache;
    }

    public List<Major> convertToItems(List<MoMajor> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        List<Major> majors = new LinkedList<>();
        for (MoMajor searchMajor : list) {
            Major item = new Major();
            item.setHashId(searchMajor.getMajorHashId());
            item.setName(searchMajor.getMajorName());
            item.setCode(searchMajor.getMajorCode());
            majors.add(item);
        }
        return majors;
    }


    public List<String> getPrompts(String key) throws Exception {
        key = key.replace(" ", "");
        return SearchPromptUtil.getPrompt(majorCache.getAllMajorNames(), key);
    }

    public PageResult<Major> searchMajors(SearchQuery query) throws Exception {
        if (!StringUtils.isEmpty(query.getQueryStr())) {
            query.setQueryStr(query.getQueryStr().replace(" ", ""));
        } else {
            query.setQueryStr(null);
        }

        PageResult<Major> result = new PageResult<>(0, query.getPage(), query.getSize());
        MoPageResult moPageResult = moUtil.getMajorList(JSON.toJSONString(query));
        result.setList(coverToMajor(moPageResult.getList()));
        result.setPager(moPageResult.getPager());

        return result;
    }

    private List<MoMajor> getMajorListFromMo() throws Exception {
        SearchQuery query = new SearchQuery();
        query.setPage(1);
        query.setSize(2000);
        return moUtil.getMajorList(JSON.toJSONString(query)).getList();

    }

    private List<Major> coverToMajor(List<MoMajor> parseArray) {
        List<Major> list = new LinkedList<>();
        for (MoMajor item : parseArray) {
            Major major = new Major();
            major.setName(item.getMajorName());
            major.setCode(item.getMajorCode());
            major.setHashId(item.getMajorHashId());
            list.add(major);
        }
        return list;
    }

}
