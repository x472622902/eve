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
import dayan.eve.exception.ErrorCN;
import dayan.eve.exception.EveException;
import dayan.eve.model.*;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.query.FollowQuery;
import dayan.eve.model.query.TopicQuery;
import dayan.eve.model.topic.Topic;
import dayan.eve.service.AccountInfoService;
import dayan.eve.service.RequestService;
import dayan.eve.service.school.SchoolFollowService;
import dayan.eve.service.TopicService;
import dayan.eve.web.dto.PersonalQueryDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xsg
 */
@Aspect
@ApiModel("个人中心")
@RestController
@RequestMapping("/api/v20/mobile/personal")
public class PersonalResource {

    private static final Logger LOGGER = LogManager.getLogger(PersonalResource.class);

    private final AccountInfoService accountInfoService;
    private final TopicService topicService;
    private final SchoolFollowService schoolFollowService;
    private final RequestService requestService;

    @Autowired
    public PersonalResource(SchoolFollowService schoolFollowService, RequestService requestService, TopicService topicService, AccountInfoService accountInfoService) {
        this.schoolFollowService = schoolFollowService;
        this.requestService = requestService;
        this.topicService = topicService;
        this.accountInfoService = accountInfoService;
    }

    @ApiOperation("个人信息")
    @PostMapping("/readInfo")
    public JsonResult readInfo(@RequestBody PersonalQueryDTO queryDTO, HttpServletRequest request) {
        FollowQuery query = new FollowQuery();
        query.setAccountId(StringUtils.isEmpty(queryDTO.getAccountId()) ?
                getAccountId(queryDTO.getAccountId(), request) : Integer.valueOf(queryDTO.getAccountId()));
        query.setPage(1);
        query.setSize(ConstantKeys.PERSONAL_SCHOOL_ICON_NUM);
        List<School> schools = schoolFollowService.readSchools(query).getList();
        AccountInfo accountInfo = accountInfoService.readInfo(query.getAccountId());
        JSONObject resultData = new JSONObject();
        resultData.put("followSchools", schools);
        resultData.put("accountInfo", accountInfo);
        return new JsonResult(resultData);
    }

    @ApiOperation("讨论区动态")
    @PostMapping("/readTopics")
    public JsonResultList readTopics(@RequestBody PersonalQueryDTO queryDTO, HttpServletRequest request) {
        TopicQuery query = new TopicQuery();
        query.setAccountId(
                StringUtils.isEmpty(queryDTO.getAccountId()) ?
                        getAccountId(queryDTO.getAccountId(), request) : Integer.valueOf(queryDTO.getAccountId())
        );
        query.initPaging(queryDTO.getPaging());
        JsonResultList result = new JsonResultList();
        PageResult<Topic> pageResult = topicService.readTimelines(query);
        result.setData(pageResult.getList());
        result.setPager(pageResult.getPager());
        return result;
    }

    private Integer getAccountId(String dataAccountId, HttpServletRequest request) {
        Integer accountId = requestService.getAccountIdValue(request);
        if (StringUtils.isEmpty(dataAccountId) && accountId == null) {
            throw new EveException(ErrorCN.Login.UN_LOGIN);
        }
        return accountId;
    }

}
