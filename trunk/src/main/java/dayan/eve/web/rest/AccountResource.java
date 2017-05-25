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

import dayan.eve.model.JsonResult;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.query.AccountQuery;
import dayan.eve.service.AccountInfoService;
import dayan.eve.service.AccountService;
import dayan.eve.service.RequestService;
import dayan.eve.service.SchoolFollowService;
import dayan.eve.util.Go4BaseUtil;
import dayan.eve.web.dto.account.InfoReadQueryDTO;
import dayan.eve.web.dto.account.InfoUpdateDTO;
import dayan.eve.web.dto.account.MobileDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xsg
 */
@RestController
@RequestMapping(value = "/api/v20/mobile/account")
@ApiModel("用户")
public class AccountResource {

    private static final Logger LOGGER = LogManager.getLogger(AccountResource.class);

    private final AccountService accountService;
    private final AccountInfoService accountInfoService;
    private final SchoolFollowService schoolFollowService;
    private final Go4BaseUtil go4BaseUtil;
    private final RequestService requestService;

    @Autowired
    public AccountResource(AccountService accountService, SchoolFollowService schoolFollowService, Go4BaseUtil go4BaseUtil, AccountInfoService accountInfoService, RequestService requestService) {
        this.accountService = accountService;
        this.schoolFollowService = schoolFollowService;
        this.go4BaseUtil = go4BaseUtil;
        this.accountInfoService = accountInfoService;
        this.requestService = requestService;
    }

    @ApiOperation("个人信息")
    @PostMapping("/info")
    public JsonResult info(HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        AccountQuery query = new AccountQuery();
        query.setId(requestService.getAccountId(request));
        jsonResult.setData(accountService.read(query).get(0));
        return jsonResult;
    }

    @ApiOperation("更新个人信息")
    @PostMapping("/updateInfo")
    public JsonResult updateInfo(@RequestBody InfoUpdateDTO infoUpdateDTO, HttpServletRequest request) throws Exception {
        AccountInfo accountInfo = buildAccountInfo(infoUpdateDTO);
        accountInfo.setAccountId(requestService.getAccountId(request));
        accountInfoService.updateInfo(accountInfo);
        return new JsonResult();
    }

    @ApiOperation("更新分享")
    @PostMapping("/updateShared")
    public JsonResult updateShared(HttpServletRequest request) {
        accountInfoService.updateShared(requestService.getAccountId(request));
        return new JsonResult();
    }

    @ApiOperation("发送短信")
    @PostMapping("/sendSMS")
    public JsonResult sendSMS(@RequestBody MobileDTO mobileDTO) throws Exception {
        go4BaseUtil.getVerificationCode(mobileDTO.getMobile());
        return new JsonResult();

    }

    @ApiOperation("读取个人信息")
    @PostMapping("/readInfo")
    public JsonResult readInfo(HttpServletRequest request) throws Exception {
        return new JsonResult(accountInfoService.readInfo(requestService.getAccountId(request)));

    }

    @ApiOperation("根据环信读用户")
    @PostMapping("/readAccountByEasemob")
    public JsonResult readByEasemob(@RequestBody InfoReadQueryDTO queryDTO) {
        return new JsonResult(accountInfoService.readAccountByEasemob(queryDTO.getEasemobUsername()));
    }

    @ApiOperation("根据环信读用户列表")
    @PostMapping("/readAccountsByEasemob")
    public JsonResult readAccountsByEasemob(@RequestBody InfoReadQueryDTO queryDTO) {
        return new JsonResult(accountInfoService.readAccountListByEasemob(queryDTO.getEasemobUsernames()));
    }


    @ApiOperation("更新头像")
    @PostMapping("/updateAvatar")
    public JsonResult updateAvatar(HttpServletRequest request, @RequestParam(value = "files")
            MultipartFile file) throws Exception {
        Integer accountId = requestService.getAccountId(request);
        return new JsonResult(accountService.updateAvatar(accountId, file));
    }

    @ApiOperation("统计经验值")
    @PostMapping("/countExp")
    public JsonResult countExp() {
        accountInfoService.countExp();
        return new JsonResult();
    }

    private AccountInfo buildAccountInfo(InfoUpdateDTO data) {
        AccountInfo accountInfo = new AccountInfo();
        if (!StringUtils.isEmpty(data.getProvinceId())) {
            accountInfo.setProvinceId(Integer.valueOf(data.getProvinceId()));
        }
        if (!StringUtils.isEmpty(data.getSubjectTypeId())) {
            accountInfo.setSubjectType("1".equals(data.getSubjectTypeId()) ? "文科" : "理科");
        }
        if (!StringUtils.isEmpty(data.getScore())) {
            accountInfo.setScore(Integer.valueOf(data.getScore()));
        }
        if (!StringUtils.isEmpty(data.getRank())) {
            accountInfo.setRank(Integer.valueOf(data.getRank()));
        }
        if (!StringUtils.isEmpty(data.getRole())) {
            accountInfo.setRole(data.getRole());
        }
        if (!StringUtils.isEmpty(data.getGender())) {
            accountInfo.setGender(data.getGender());
        }
        if (!StringUtils.isEmpty(data.getMobile()) && !StringUtils.isEmpty(data.getCode())) {
            accountInfo.setMobile(data.getMobile());
            accountInfo.setCode(data.getCode());
        }
        return accountInfo;
    }
}
