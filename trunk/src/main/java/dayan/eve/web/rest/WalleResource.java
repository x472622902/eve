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

import dayan.common.util.SchoolPlatformIdEncoder;
import dayan.eve.model.JsonResult;
import dayan.eve.service.RequestService;
import dayan.eve.service.walle.QARobotService;
import dayan.eve.service.walle.VisitorService;
import dayan.eve.service.walle.WalleSchoolService;
import dayan.eve.util.SchoolIdPlatformIdUtil;
import dayan.eve.web.dto.walle.FeedbackDTO;
import dayan.eve.web.dto.walle.FreqQuestionQueryDTO;
import dayan.eve.web.dto.walle.RobotQueryDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author leiky
 */
//@Aspect
@RestController
@RequestMapping("/api/v20/mobile/walle")
public class WalleResource {

    private static final Logger LOGGER = LogManager.getLogger(WalleResource.class);

    private static SchoolPlatformIdEncoder idEncoder = new SchoolPlatformIdEncoder();
    private final QARobotService qaRobotService;
    private final WalleSchoolService walleSchoolService;
    private final SchoolIdPlatformIdUtil schoolIdPlatformIdUtil;
    private final VisitorService visitorService;
    private final RequestService requestService;

    @Autowired
    public WalleResource(VisitorService visitorService, QARobotService qaRobotService, SchoolIdPlatformIdUtil schoolIdPlatformIdUtil, WalleSchoolService walleSchoolService, RequestService requestService) {
        this.visitorService = visitorService;
        this.qaRobotService = qaRobotService;
        this.schoolIdPlatformIdUtil = schoolIdPlatformIdUtil;
        this.walleSchoolService = walleSchoolService;
        this.requestService = requestService;
    }

    @PostMapping("/getAnswer")
    public JsonResult getAnswer(@RequestBody RobotQueryDTO queryDTO, HttpServletRequest request) {
        Integer accountId = requestService.getAccountId(request);
        return qaRobotService.getAnswer(queryDTO.getSchoolHashId(), queryDTO.getQuery(), accountId.toString());
    }

    @PostMapping("/getHint")
    public JsonResult getHint(@RequestBody RobotQueryDTO queryDTO) {
        Integer platformId = StringUtils.isEmpty(queryDTO.getSchoolHashId()) ? null : schoolIdPlatformIdUtil
                .getPlatformIdBySchoolHashId(queryDTO.getSchoolHashId());
        return qaRobotService.getHint(platformId, queryDTO.getQuery());
    }

    @PostMapping("/getGreeting")
    public JsonResult getGreeting(@RequestBody RobotQueryDTO queryDTO) {
        Integer platformId = StringUtils.isEmpty(queryDTO.getSchoolHashId()) ? null : schoolIdPlatformIdUtil
                .getPlatformIdBySchoolHashId(queryDTO.getSchoolHashId());
        return qaRobotService.getGreeting(platformId);
    }

    //读取学校热门问题
    @PostMapping("/readHotQuestion")
    public JsonResult readHotQuestion(@RequestBody RobotQueryDTO queryDTO) {
        return new JsonResult(walleSchoolService.readHotQuestion(queryDTO.getSchoolHashId()));

    }

    //读取常见问题
    @PostMapping("/readFreqQuestion")
    public JsonResult readFreqQuestion(@RequestBody FreqQuestionQueryDTO queryDTO) {
        return new JsonResult(walleSchoolService.readFreqQuestion(queryDTO.getSchoolHashId(), queryDTO.getRefresh(), null));
    }

    //问题答案反馈
    @PostMapping("/feedback")
    public JsonResult feedback(@RequestBody FeedbackDTO feedbackDTO) {
        qaRobotService.feedback(feedbackDTO.getAnswerId(), feedbackDTO.getAct());
        return new JsonResult();
    }

    /**
     * 连接人工客服时创建访客信息
     *
     * @param request
     * @return
     */
    @PostMapping("/createVisitor")
    public JsonResult createVisitor(@RequestBody RobotQueryDTO queryDTO, HttpServletRequest request) {
        Integer accountId = requestService.getAccountId(request);
        visitorService.createVisitor(accountId.toString(), queryDTO.getSchoolHashId());
        return new JsonResult();
    }

}
