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
import dayan.eve.exception.NotLoginException;
import dayan.eve.model.JsonResult;
import dayan.eve.model.JsonResultList;
import dayan.eve.model.School;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.query.AccountQuery;
import dayan.eve.model.query.FollowQuery;
import dayan.eve.service.AccountInfoService;
import dayan.eve.service.AccountService;
import dayan.eve.service.RequestService;
import dayan.eve.service.SchoolFollowService;
import dayan.eve.util.Go4BaseUtil;
import dayan.eve.web.dto.InfoReadQueryDTO;
import dayan.eve.web.dto.InfoUpdateDTO;
import dayan.eve.web.dto.PersonalQueryDTO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xsg
 */
@RestController
@RequestMapping(value = "/api/v20/mobile/account")
public class AccountResource {

    private static final Logger LOGGER = LogManager.getLogger(AccountResource.class);

    @Autowired
    AccountService accountService;

    @Autowired
    AccountInfoService accountInfoService;

    @Autowired
    SchoolFollowService schoolFollowService;

    @Autowired
    Go4BaseUtil go4BaseUtil;

    @Autowired
    RequestService requestService;

    @ApiOperation("个人信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public JsonResult info(HttpServletRequest request) {
        return requestService.getUserNumber(request)
                .map(accountId -> {
                    JsonResult jsonResult = new JsonResult();
                    try {
                        AccountQuery query = new AccountQuery();
                        query.setId(accountId);
                        jsonResult.setData(accountService.read(query).get(0));
                    } catch (Exception e) {
                        jsonResult.setSuccess(false);
                        jsonResult.setInfo(ErrorCN.DEFAULT_SERVER_ERROR);
                        LOGGER.error(e.getMessage(), e);
                    }
                    return jsonResult;
                })
                .orElse(new JsonResult(ErrorCN.Login.UN_LOGIN, false));
    }

    @ApiOperation("更新个人信息")
    @RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
    public JsonResult updateInfo(@RequestBody InfoUpdateDTO infoUpdateDTO, HttpServletRequest request) {
        return requestService.getUserNumber(request)
                .map(accountId -> {
                    JsonResult jsonResult = new JsonResult();
                    try {
                        AccountInfo accountInfo = buildAccountInfo(infoUpdateDTO);
                        accountInfo.setAccountId(accountId);
                        accountInfoService.updateInfo(accountInfo);
                    } catch (Exception e) {
                        jsonResult.setSuccess(false);
                        jsonResult.setInfo(ErrorCN.DEFAULT_SERVER_ERROR);
                        LOGGER.error(e.getMessage(), e);
                    }
                    return jsonResult;
                })
                .orElse(new JsonResult(ErrorCN.Login.UN_LOGIN, false));
    }

    @ApiOperation("更新分享")
    @RequestMapping(value = "/updateShared", method = RequestMethod.POST)
    public JsonResult updateShared(HttpServletRequest request) {
        return requestService.getUserNumber(request)
                .map(accountId -> {
                    JsonResult jsonResult = new JsonResult();
                    try {
                        accountInfoService.updateShared(accountId);
                    } catch (Exception e) {
                        jsonResult.setSuccess(false);
                        jsonResult.setInfo(ErrorCN.DEFAULT_SERVER_ERROR);
                        LOGGER.error(e.getMessage(), e);
                    }
                    return jsonResult;
                })
                .orElse(new JsonResult(ErrorCN.Login.UN_LOGIN, false));
    }

    @ApiOperation("发送短信")
    @RequestMapping(value = "/sendSMS", method = RequestMethod.POST)
    public JsonResult sendSMS(HttpServletRequest request) {
        try {
            String dataStr = request.getParameter("data");
            JSONObject data = JSONObject.parseObject(dataStr);
            String mobile = data.getString("mobile");
            go4BaseUtil.getVerificationCode(mobile);
            JsonResult result = new JsonResult();
            result.setJsessionid(request.getSession().getId());
            return result;

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new JsonResult(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
    }

    @ApiOperation("读取个人信息")
    @RequestMapping(value = "/readInfo", method = RequestMethod.POST)
    public JsonResult readInfo(HttpServletRequest request) {
        return requestService.getUserNumber(request)
                .map(accountId -> {
                    JsonResult jsonResult = new JsonResult();
                    try {
                        jsonResult.setData(accountInfoService.readInfo(accountId));
                    } catch (Exception e) {
                        jsonResult.setSuccess(false);
                        jsonResult.setInfo(ErrorCN.DEFAULT_SERVER_ERROR);
                        LOGGER.error(e.getMessage(), e);
                    }
                    return jsonResult;
                })
                .orElse(new JsonResult(ErrorCN.Login.UN_LOGIN, false));
    }

    @ApiOperation("根据环信读用户")
    @RequestMapping(value = "/readAccountByEasemob", method = RequestMethod.POST)
    public JsonResult readByEasemob(@RequestBody InfoReadQueryDTO queryDTO) {
        try {
            JsonResult result = new JsonResult();
            result.setData(accountInfoService.readAccountByEasemob(queryDTO.getEasemobUsername()));
            return result;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new JsonResult(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
    }

    @ApiOperation("根据环信读用户列表")
    @RequestMapping(value = "/readAccountsByEasemob", method = RequestMethod.POST)
    public JsonResultList readAccountsByEasemob(@RequestBody InfoReadQueryDTO queryDTO) {
        try {
            JsonResultList result = new JsonResultList();
            result.setData(accountInfoService.readAccountListByEasemob(queryDTO.getEasemobUsernames()));
            return result;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new JsonResultList(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
    }

    @ApiOperation("读取个人中心")
    @RequestMapping(value = "/readPersonal", method = RequestMethod.POST)
    public JsonResult readPersonal(@RequestBody PersonalQueryDTO queryDTO, HttpServletRequest request) {
        try {
            Integer accountId = requestService.getAccountId(request);
            if (StringUtils.isEmpty(queryDTO.getAccountId()) && accountId == null) {
                throw new NotLoginException(ErrorCN.Login.UN_LOGIN);
            }
            FollowQuery query = new FollowQuery();
            query.setAccountId(StringUtils.isEmpty(queryDTO.getAccountId()) ? accountId : Integer.valueOf(queryDTO.getAccountId()));
            query.setPage(1);
            query.setSize(4);//学校图标默认取四个
            List<School> schools = schoolFollowService.readSchools(query).getData();
            AccountInfo accountInfo = accountInfoService.readInfo(query.getAccountId());
            JSONObject resultData = new JSONObject();
            resultData.put("followSchools", schools);
            resultData.put("accountInfo", accountInfo);
            JsonResult result = new JsonResult();
            result.setData(resultData);
            return result;
        } catch (NotLoginException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new JsonResult(ErrorCN.Login.UN_LOGIN, false);
        } catch (Exception other) {
            LOGGER.error(other.getMessage(), other);
            return new JsonResult(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
    }

    @ApiOperation("更新头像")
    @RequestMapping(value = "/updateAvatar", method = RequestMethod.POST)
    public JsonResult updateAvatar(HttpServletRequest request, @RequestParam(value = "files")
            MultipartFile file) {
        return requestService.getUserNumber(request)
                .map(accountId -> {
                    JsonResult jsonResult = new JsonResult();
                    try {
                        jsonResult.setData(accountService.updateAvatar(accountId, file));
                    } catch (Exception e) {
                        jsonResult.setSuccess(false);
                        jsonResult.setInfo(ErrorCN.DEFAULT_SERVER_ERROR);
                        LOGGER.error(e.getMessage(), e);
                    }
                    return jsonResult;
                })
                .orElse(new JsonResult(ErrorCN.Login.UN_LOGIN, false));
    }

    @ApiOperation("统计经验值")
    @RequestMapping(value = "/countExp", method = RequestMethod.POST)
    public JsonResult countExp() {
        try {
            accountInfoService.countExp();
            return new JsonResult();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new JsonResult(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
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
