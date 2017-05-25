/**
 * *****************************************************
 * Copyright (C) Dayan techology Co.ltd - All Rights Reserved
 * <p/>
 * This file is part of Dayan techology Co.ltd property.
 * <p/>
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * *****************************************************
 */
package dayan.eve.web.rest;

import dayan.eve.model.JsonResult;
import dayan.eve.service.CourseService;
import dayan.eve.service.CourseTestResultService;
import dayan.eve.service.RequestService;
import dayan.eve.service.SchoolSearchService;
import dayan.eve.web.dto.SearchPromptDTO;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xsg
 */
@RestController
@RequestMapping(value = "/api/v20/mobile/school")
public class SchoolResource {

    private static final Logger LOGGER = LogManager.getLogger();
    private final SchoolSearchService schoolSearchService;

    @Autowired
    public SchoolResource(RequestService requestService, CourseService courseService, CourseTestResultService courseTestResultService, SchoolSearchService schoolSearchService) {
        this.schoolSearchService = schoolSearchService;
    }

    @ApiOperation("学校搜索提示")
    @PostMapping("/search/prompt")
    public JsonResult read(@RequestBody SearchPromptDTO promptDTO, HttpServletRequest request) {
        return new JsonResult(schoolSearchService.getPrompts(promptDTO.getQueryString()));
    }


}
