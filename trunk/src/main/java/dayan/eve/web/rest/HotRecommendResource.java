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

import dayan.common.util.SchoolPlatformIdEncoder;
import dayan.eve.model.JsonResult;
import dayan.eve.model.JsonResultList;
import dayan.eve.model.query.HotRecommendQuery;
import dayan.eve.service.HotRecommendService;
import dayan.eve.service.RequestService;
import dayan.eve.util.TagUtil;
import dayan.eve.web.dto.HotRecommendQueryDTO;
import dayan.eve.web.dto.HotSchoolReadDTO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

import static dayan.eve.model.ConstantKeys.SchoolDetail.SCHOOL_TYPES;

/**
 * @author xsg
 */
@RestController
@RequestMapping(value = "/api/v20/mobile/hot-recommend")
public class HotRecommendResource {


    private final HotRecommendService hotRecommendService;
    private final TagUtil tagUtil;
    private final RequestService requestService;

    @Autowired
    public HotRecommendResource(HotRecommendService hotRecommendService, TagUtil tagUtil, RequestService requestService) {
        this.hotRecommendService = hotRecommendService;
        this.tagUtil = tagUtil;
        this.requestService = requestService;
    }

    @ApiOperation("读取分数段")
    @PostMapping("/readScoreSegments")
    public JsonResult readScoreSegments(@RequestBody HotRecommendQueryDTO queryDTO, HttpServletRequest request) {
        HotRecommendQuery query = buildQuery(queryDTO);
        query.setAccountId(requestService.getAccountIdValue(request));
        return new JsonResult(hotRecommendService.readScoreSegments(query));
    }


    @ApiOperation("读取学校")
    @PostMapping("/readSchool")
    public JsonResultList readSchool(@RequestBody HotRecommendQueryDTO queryDTO, HttpServletRequest request) {
        HotRecommendQuery query = buildQuery(queryDTO);
        query.setAccountId(requestService.getAccountIdValue(request));
        JsonResultList result = new JsonResultList();
        HotSchoolReadDTO hotSchoolReadDTO = hotRecommendService.readSchools(query);
        result.setData(hotSchoolReadDTO.getSchools());
        result.setPager(hotSchoolReadDTO.getPager());
        return result;
    }

    private HotRecommendQuery buildQuery(HotRecommendQueryDTO queryDTO) {
        HotRecommendQuery query = new HotRecommendQuery();
        SchoolPlatformIdEncoder idEncoder = new SchoolPlatformIdEncoder();
        if (!StringUtils.isEmpty(queryDTO.getSchoolHashId())) {
            query.setSchoolId(idEncoder.decode(queryDTO.getSchoolHashId()).intValue());
        }
        if (!StringUtils.isEmpty(queryDTO.getScore())) {
            query.setScoreSegment(Integer.valueOf(queryDTO.getScore()) / 10);
        }
        if (StringUtils.isEmpty(queryDTO.getRank())) {
            query.setRank(Integer.valueOf(queryDTO.getRank()));
        }
        query.setProvinceId(Integer.valueOf(queryDTO.getProvinceId()));
        query.setSubjectType("1".equals(queryDTO.getSubjectTypeId()) ? "文科" : "理科");
        if (queryDTO.getProvinceIds() != null && !queryDTO.getProvinceIds().isEmpty()) {
            query.setProvinceIds(queryDTO.getProvinceIds().stream().map(Integer::valueOf).collect(Collectors.toList()));
        }
        if (queryDTO.getRefTypeIds() != null && !queryDTO.getRefTypeIds().isEmpty()) {
            query.setRefTypeIds(queryDTO.getRefTypeIds().stream().map(Integer::valueOf).collect(Collectors.toList()));
        }
        if (queryDTO.getSchoolTypeIds() != null && !queryDTO.getSchoolTypeIds().isEmpty()) {
            query.setSchoolTypes(queryDTO.getSchoolTypeIds().stream()
                    .map(schoolTypeId -> SCHOOL_TYPES[Integer.valueOf(schoolTypeId) - 1])
                    .collect(Collectors.toList()));
        }
        if (queryDTO.getTagIds() == null || queryDTO.getTagIds().isEmpty()) {
            query.setTagsValue(null);
        } else {
            query.setTagsValue(tagUtil.getTagsValue(queryDTO.getTagIds()));
        }
        query.setIsAdvisory(queryDTO.getIsAdvisory());
        return query;
    }
}
