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
import dayan.eve.model.Pager;
import dayan.eve.model.major.Major;
import dayan.eve.model.major.MoMajor;
import dayan.eve.model.query.SearchQuery;
import dayan.eve.repository.MajorRepository;
import dayan.eve.util.MoUtil;
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
public class MajorSearchService {

    private final static Logger LOGGER = LogManager.getLogger(MajorSearchService.class);
    private String allMajorNameString;
    private String[] allMajorNames;
    private static Map<String, MoMajor> majorMap = new HashMap<>();
    private Integer SEARCH_PROMPT_NUM;

    @Autowired
    MajorRepository majorRepository;

    @Autowired
    MoUtil moUtil;

    @Autowired
    public MajorSearchService(EveProperties eveProperties) {
        this.SEARCH_PROMPT_NUM = eveProperties.getSchool().getSearchPromptNum();
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


    private String getAllNameString() throws Exception {
        if (allMajorNameString == null) {
            List<MoMajor> array = getMajorListFromMo();
            StringBuilder sb = new StringBuilder();
            sb.append("|");
            for (MoMajor major : array) {
                majorMap.put(major.getMajorName(), major);
                sb.append(major.getMajorName()).append("|");
            }
            allMajorNameString = sb.toString();
        }
        return allMajorNameString;
    }

    public List<String> getPrompts(String queryString) throws Exception {
        queryString = queryString.replace(" ", "");
        String allNameString = getAllNameString();
        List<String> prompts = new ArrayList<>();
        int middle;
        int left;
        int right = 0;
        while (prompts.size() <= SEARCH_PROMPT_NUM) {
            //提示个数上限20个
            middle = allNameString.indexOf(queryString, right);
            if (middle == -1) {
                break;
            }
            right = allNameString.indexOf("|", middle);
            left = allNameString.lastIndexOf("|", middle);

            prompts.add(allNameString.substring(left + 1, right));
        }
        return prompts;
    }

    public PageResult<Major> searchMajors(SearchQuery query) throws Exception {
        if (!StringUtils.isEmpty(query.getQueryStr())) {
            query.setQueryStr(query.getQueryStr().replace(" ", ""));
        } else {
            query.setQueryStr(null);
        }

        PageResult<Major> result = new PageResult<>(new Pager(0, query.getPage(), query.getSize()));
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
