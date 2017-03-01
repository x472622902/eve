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

import com.alibaba.fastjson.JSON;
import dayan.common.util.SchoolPlatformIdEncoder;
import dayan.eve.model.Constants;
import dayan.eve.model.JsonResult;
import dayan.eve.model.JsonResultList;
import dayan.eve.model.easemob.EasemobResult;
import dayan.eve.model.easemob.EasemobType;
import dayan.eve.model.query.FollowQuery;
import dayan.eve.model.walle.SchoolNotify;
import dayan.eve.repository.SchoolFollowRepository;
import dayan.eve.repository.SchoolRepository;
import dayan.eve.service.EasemobService;
import dayan.eve.service.RequestService;
import dayan.eve.service.SchoolFollowService;
import dayan.eve.util.SchoolIdPlatformIdUtil;
import dayan.eve.web.dto.FollowDTO;
import dayan.eve.web.dto.FollowSchoolQueryDTO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xsg
 */
@RestController
@RequestMapping(value = "/api/v20/mobile/follow")
public class FollowResource {

    private static final Logger LOGGER = LogManager.getLogger();
    private final EasemobService easemobService;
    private final SchoolRepository schoolRepository;
    private final SchoolFollowRepository schoolFollowRepository;
    private static SchoolPlatformIdEncoder encoder = new SchoolPlatformIdEncoder();
    private final SchoolIdPlatformIdUtil schoolIdPlatformIdUtil;
    private final SchoolFollowService schoolFollowService;
    private final RequestService requestService;

    @Autowired
    public FollowResource(EasemobService easemobService, SchoolFollowRepository schoolFollowRepository, SchoolFollowService schoolFollowService, SchoolIdPlatformIdUtil schoolIdPlatformIdUtil, RequestService requestService, SchoolRepository schoolRepository) {
        this.easemobService = easemobService;
        this.schoolFollowRepository = schoolFollowRepository;
        this.schoolFollowService = schoolFollowService;
        this.schoolIdPlatformIdUtil = schoolIdPlatformIdUtil;
        this.requestService = requestService;
        this.schoolRepository = schoolRepository;
    }

    @ApiOperation("关注学校的用户列表")
    @RequestMapping(value = "/readAccounts", method = RequestMethod.POST)
    public JsonResultList readAccounts(@RequestBody FollowDTO followDTO) {
        return new JsonResultList(schoolFollowService.readAccounts(buildQueryOfSchool(followDTO)));
    }

    @ApiOperation("关注的学校列表")
    @RequestMapping(value = "/readSchools", method = RequestMethod.POST)
    public JsonResult readSchools(HttpServletRequest request) {
        FollowQuery query = new FollowQuery();
        query.setAccountId(requestService.getAccountIdValue(request));
        return new JsonResult(schoolFollowService.readSchools(query).getList());
    }

    @ApiOperation("walle学校向关注的用户推送消息")
    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public JsonResult callback(HttpServletRequest request) {

        String notificationJson = request.getParameter(Constants.Key.Message);
        SchoolNotify notification = JSON.parseObject(notificationJson, SchoolNotify.class);
        Map<String, String> ext = new HashMap<>();
        Integer schoolId = schoolIdPlatformIdUtil.getPlatformIdAndSchoolIdMap().get(notification.getPlatformId());
        List<Integer> userNumbers = schoolFollowRepository.queryAccountIds(schoolId);
        String schoolName = schoolRepository.queryName(schoolId);
        String schoolHashId = encoder.encrypt(schoolId.longValue());
        ext.put(Constants.Key.SchoolName, schoolName);
        ext.put(Constants.Key.SchoolHashId, schoolHashId);
        ext.put(Constants.Key.PlatformHashId, encoder.encrypt(notification.getPlatformId().longValue()));
        EasemobResult easemobResult = new EasemobResult(new JsonResult(notification), EasemobType.Notification);
        easemobResult.setExt(ext);
        easemobService.sendMessages(userNumbers, easemobResult);
        JsonResult result = new JsonResult();
        result.setJsessionid(request.getSession().getId());

        return new JsonResult();
    }

    @ApiOperation("关注")
    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    public JsonResult follow(@RequestBody FollowSchoolQueryDTO queryDTO, HttpServletRequest request) {
        FollowQuery followQuery = buildQueryOfAccount(queryDTO);
        followQuery.setAccountId(requestService.getAccountId(request));
        schoolFollowService.follow(followQuery);
        return new JsonResult();
    }

    @ApiOperation("取消关注")
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public JsonResult cancel(@RequestBody FollowSchoolQueryDTO queryDTO, HttpServletRequest request) {
        FollowQuery followQuery = buildQueryOfAccount(queryDTO);
        followQuery.setAccountId(requestService.getAccountId(request));
        schoolFollowService.cancel(followQuery);
        return new JsonResult();
    }

    private FollowQuery buildQueryOfAccount(FollowSchoolQueryDTO queryDTO) {
        FollowQuery query = new FollowQuery();
        if (!StringUtils.isEmpty(queryDTO.getSchoolHashId())) {
            query.setSchoolId(encoder.decode(queryDTO.getSchoolHashId()).intValue());
        }
        return query;
    }

    private FollowQuery buildQueryOfSchool(FollowDTO followDTO) {
        FollowQuery query = new FollowQuery();
        if (followDTO.getPlatformId() == null) {
            throw new RuntimeException();
        }
        query.setSchoolId(schoolIdPlatformIdUtil.getPlatformIdAndSchoolIdMap().get(followDTO.getPlatformId()));
        if (!StringUtils.isEmpty(followDTO.getAccountId())) {
            query.setAccountId(Integer.valueOf(followDTO.getAccountId()));
        }
        query.initPaging(followDTO.getPaging());
        return query;
    }

}
