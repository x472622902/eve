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
package dayan.eve.web.rest;

import com.alibaba.fastjson.JSONObject;
import dayan.common.util.SchoolPlatformIdEncoder;
import dayan.eve.model.JsonResult;
import dayan.eve.model.JsonResultList;
import dayan.eve.model.PageResult;
import dayan.eve.model.query.SearchQuery;
import dayan.eve.service.MajorProfileService;
import dayan.eve.service.MajorSearchService;
import dayan.eve.util.MoUtil;
import dayan.eve.web.dto.SearchPromptDTO;
import dayan.eve.web.dto.major.MajorProfileQueryDTO;
import dayan.eve.web.dto.major.MajorSearchQueryDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xsg
 */
@RestController
@RequestMapping(value = "/api/v20/mobile/major")
@ApiModel("专业")
public class MajorResource {

    private static final Logger LOGGER = LogManager.getLogger();

    private final SchoolPlatformIdEncoder idEncoder = new SchoolPlatformIdEncoder();
    private final MajorSearchService majorSearchService;
    private final MajorProfileService majorProfileService;
    private final MoUtil moUtil;

    @Autowired
    public MajorResource(MajorProfileService majorProfileService, MoUtil moUtil, MajorSearchService majorSearchService) {
        this.majorProfileService = majorProfileService;
        this.moUtil = moUtil;
        this.majorSearchService = majorSearchService;
    }

    @ApiOperation("搜索")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public JsonResultList search(@RequestBody MajorSearchQueryDTO queryDTO, HttpServletRequest request) throws Exception {
        SearchQuery query = new SearchQuery();
        query.setQueryStr(queryDTO.getQuery());
        if (!StringUtils.isEmpty(queryDTO.getDegreeTypeId())) {
            query.setDegreeTypeId(Integer.valueOf(queryDTO.getDegreeTypeId()));
        }
        query.initPaging(queryDTO.getPaging());
        JsonResultList result = new JsonResultList();
        PageResult pageResult = majorSearchService.searchMajors(query);
        result.setData(pageResult.getList());
        result.setPager(pageResult.getPager());
        return result;
    }

    @ApiOperation("搜索提示")
    @RequestMapping(value = "/searchPrompt", method = RequestMethod.POST)
    public JsonResultList searchPrompt(@RequestBody SearchPromptDTO queryDTO) throws Exception {
        return new JsonResultList(majorSearchService.getPrompts(queryDTO.getQueryString()));
    }

    @ApiOperation("详情带相近专业")
    @RequestMapping(value = "/readProfile", method = RequestMethod.POST)
    public JsonResult readMajorProfile(@RequestBody MajorProfileQueryDTO queryDTO) {
        Integer majorId = idEncoder.decode(queryDTO.getMajorHashId()).intValue();
        return new JsonResult(majorProfileService.readMajorProfile(majorId));
    }

    @ApiOperation("相关大学")
    @RequestMapping(value = "/readSchool", method = RequestMethod.POST)
    public JsonResult readSchools(@RequestBody MajorProfileQueryDTO queryDTO) throws Exception {
        return JSONObject.parseObject(moUtil.getSchoolByMajorHashId(queryDTO.getMajorHashId()), JsonResult.class);
    }

    @ApiOperation("详情带学校排名")
    @RequestMapping(value = "/readDetail", method = RequestMethod.POST)
    public JsonResult readDetail(@RequestBody MajorProfileQueryDTO queryDTO) throws Exception {
        return JSONObject.parseObject(moUtil.getMajorDetail(queryDTO.getMajorHashId()), JsonResult.class);
    }

}
