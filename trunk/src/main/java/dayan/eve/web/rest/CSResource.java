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

import com.alibaba.fastjson.JSON;
import dayan.common.util.SchoolPlatformIdEncoder;
import dayan.eve.model.Constants;
import dayan.eve.model.JsonResult;
import dayan.eve.model.easemob.EasemobResult;
import dayan.eve.model.easemob.EasemobType;
import dayan.eve.model.robot.Answer;
import dayan.eve.repository.SchoolRepository;
import dayan.eve.service.EasemobService;
import dayan.eve.service.RequestService;
import dayan.eve.service.walle.QARobotService;
import dayan.eve.util.SchoolIdPlatformIdUtil;
import dayan.eve.web.dto.RobotQueryDTO;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author leiky
 */
@RestController
@RequestMapping(value = "/api/v20/mobile/cs")
public class CSResource {

    private final static Logger LOGGER = LogManager.getLogger(CSResource.class);
    private final QARobotService robotService;
    private final EasemobService easemobService;
    private final SchoolRepository schoolRepository;
    private final RequestService requestService;
    private final SchoolIdPlatformIdUtil schoolIdPlatformIdUtil;
    private static SchoolPlatformIdEncoder idEncoder = new SchoolPlatformIdEncoder();

    @Autowired
    public CSResource(QARobotService robotService, EasemobService easemobService, SchoolRepository schoolRepository, RequestService requestService, SchoolIdPlatformIdUtil schoolIdPlatformIdUtil) {
        this.robotService = robotService;
        this.easemobService = easemobService;
        this.schoolRepository = schoolRepository;
        this.requestService = requestService;
        this.schoolIdPlatformIdUtil = schoolIdPlatformIdUtil;
    }

    @ApiOperation("请求连接人工客服")
    @PostMapping("/access")
    public JsonResult access(@RequestBody RobotQueryDTO queryDTO, HttpServletRequest request) {
        String usernumber = requestService.getAccountId(request).toString();
        String result = robotService.accessCS(queryDTO, usernumber);
        return JSON.parseObject(result, JsonResult.class);
    }

    @ApiOperation("人工客服回调")
    @PostMapping("/callback")
    public JsonResult callback(HttpServletRequest request) {

        String userNumber = request.getParameter(Constants.Key.UserNumber);
        String answerJson = request.getParameter(Constants.Key.Answer);
        Answer answer = JSON.parseObject(answerJson, Answer.class);

        Map<String, String> ext = new HashMap<>();
        Integer schoolId = schoolIdPlatformIdUtil.getPlatformIdAndSchoolIdMap().get(answer.getPlatformId());
        String schoolName = schoolRepository.queryName(schoolId);
        String schoolHashId = idEncoder.encrypt(schoolId.longValue());
        ext.put(Constants.Key.SchoolName, schoolName);
        ext.put(Constants.Key.SchoolHashId, schoolHashId);
        ext.put(Constants.Key.PlatformHashId, idEncoder.encrypt(answer.getPlatformId().longValue()));
        EasemobResult easemobResult = new EasemobResult(new JsonResult(answer), EasemobType.QAMessage);
        easemobResult.setExt(ext);

        easemobService.sendMessage(userNumber, easemobResult);

        return new JsonResult();
    }

    @ApiOperation("关闭人工客服")
    @PostMapping("/close")
    public JsonResult close(@RequestBody RobotQueryDTO queryDTO, HttpServletRequest request) throws Exception {
        String usernumber = requestService.getAccountId(request).toString();
        robotService.closeCS(usernumber, new SchoolPlatformIdEncoder().decode(queryDTO.getPlatformHashId()));
        return new JsonResult();
    }


}
