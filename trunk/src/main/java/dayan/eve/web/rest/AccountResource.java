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
import dayan.eve.model.MobileRequest;
import dayan.eve.model.School;
import dayan.eve.model.account.Account;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.query.AccountQuery;
import dayan.eve.model.query.FollowQuery;
import dayan.eve.service.AccountInfoService;
import dayan.eve.service.AccountService;
import dayan.eve.service.SchoolFollowService;
import dayan.eve.util.Go4BaseUtil;
import dayan.eve.web.dto.FollowDTO;
import dayan.eve.web.dto.InfoReadQueryDTO;
import dayan.eve.web.dto.InfoUpdateDTO;
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
    AccountInfoService accountInfoV20Service;

    @Autowired
    SchoolFollowService schoolFollowService;

    @Autowired
    Go4BaseUtil go4BaseUtil;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public JsonResult info(HttpServletRequest request) {
        try {
            Integer userNumber = Integer.parseInt(request.getParameter("userNumber"));
            AccountQuery query = new AccountQuery();
            query.setId(userNumber);
            List<Account> accounts = accountService.read(query);
            return new JsonResult(accounts.get(0));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new JsonResult(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
    }

    @RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
    public JsonResult updateInfo(MobileRequest<InfoUpdateDTO> request) {
        try {
            accountInfoV20Service.updateInfo(buildAccountInfo(request));
            return new JsonResult();
        } catch (NotLoginException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new JsonResult(ex.getMessage(), false);
        } catch (Exception other) {
            LOGGER.error(other.getMessage(), other);
            return new JsonResult(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
    }

    @RequestMapping(value = "/updateShared", method = RequestMethod.POST)
    public JsonResult updateShared(HttpServletRequest request) {
        try {
            String userNumber = request.getParameter("userNumber");
            if (StringUtils.isEmpty(userNumber)) {
                throw new NotLoginException("未登录");
            }

            accountInfoV20Service.updateShared(Integer.valueOf(userNumber));
            JsonResult result = new JsonResult();
            result.setJsessionid(request.getSession().getId());
            return result;

        } catch (NotLoginException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new JsonResult(ex.getMessage(), false);
        } catch (NumberFormatException other) {
            LOGGER.error(other.getMessage(), other);
            return new JsonResult(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
    }

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

    @RequestMapping(value = "/readInfo", method = RequestMethod.POST)
    public JsonResult readInfo(HttpServletRequest request) {
        try {
            String userNumber = request.getParameter("userNumber");
            if (StringUtils.isEmpty(userNumber)) {
                throw new RuntimeException("not login");
            }

            if ("null".equals(userNumber)) {
                throw new NotLoginException("请重新登录！");
            }
            JsonResult result = new JsonResult();
            result.setData(accountInfoV20Service.readInfo(Integer.valueOf(userNumber)));
            return result;
        } catch (NotLoginException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new JsonResult(ex.getMessage(), false);
        } catch (Exception other) {
            LOGGER.error(other.getMessage(), other);
            return new JsonResult(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
    }

    @RequestMapping(value = "/readAccountByEasemob", method = RequestMethod.POST)
    public JsonResult readByEasemob(MobileRequest<InfoReadQueryDTO> request) {
        try {
            JsonResult result = new JsonResult();
            result.setData(accountInfoV20Service.readAccountByEasemob(request.getData().getEasemobUsername()));
            return result;
        } catch (NotLoginException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new JsonResult(ex.getMessage(), false);
        } catch (Exception other) {
            LOGGER.error(other.getMessage(), other);
            return new JsonResult(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
    }

    @RequestMapping(value = "/readAccountsByEasemob", method = RequestMethod.POST)
    public JsonResultList readAccountsByEasemob(MobileRequest<InfoReadQueryDTO> request) {
        try {
            JsonResultList result = new JsonResultList();
            result.setData(accountInfoV20Service.readAccountListByEasemob(request.getData().getEasemobUsernames()));
            return result;
        } catch (Exception other) {
            LOGGER.error(other.getMessage(), other);
            return new JsonResultList(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
    }

    @RequestMapping(value = "/readPersonal", method = RequestMethod.POST)
    public JsonResult readPersonal(MobileRequest<FollowDTO> request) {
        try {
            FollowQuery query = new FollowQuery();
            String accountId = request.getData().getAccountId();
            query.setAccountId(Integer.valueOf(StringUtils.isEmpty(accountId) ? request.getUsernumber() : accountId));
            query.setPage(1);
            query.setSize(4);//学校图标默认取四个
            List<School> schools = schoolFollowService.readSchools(query).getData();
            AccountInfo accountInfo = accountInfoV20Service.readInfo(query.getAccountId());
            JSONObject resultData = new JSONObject();
            resultData.put("followSchools", schools);
            resultData.put("accountInfo", accountInfo);
            JsonResult result = new JsonResult();
            result.setData(resultData);
            return result;
        } catch (NotLoginException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new JsonResult(ex.getMessage(), false);
        } catch (Exception other) {
            LOGGER.error(other.getMessage(), other);
            return new JsonResult(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
    }

    @RequestMapping(value = "/updateAvatar", method = RequestMethod.POST)
    public JsonResult updateAvatar(HttpServletRequest request, @RequestParam(value = "files") MultipartFile file) {
        try {
            String userNumber = request.getParameter("userNumber");
            if (StringUtils.isEmpty(userNumber)) {
                throw new RuntimeException("not login");
            }
            if (file == null) {
                throw new RuntimeException("portrait file is null");
            }
            Integer accountId = Integer.valueOf(userNumber);
            String avatarURL = accountService.updateAvatar(accountId, file);
            JsonResult result = new JsonResult(avatarURL);
            result.setJsessionid(request.getSession().getId());
            return result;
        } catch (Exception other) {
            LOGGER.error(other.getMessage(), other);
            return new JsonResult(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
    }

    @RequestMapping(value = "/countExp", method = RequestMethod.POST)
    public JsonResult countExp(HttpServletRequest request) {
        try {
            accountInfoV20Service.countExp();
            JsonResult result = new JsonResult();
            result.setJsessionid(request.getSession().getId());
            return result;
        } catch (Exception other) {
            LOGGER.error(other.getMessage(), other);
            return new JsonResult(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
    }

    private AccountInfo buildAccountInfo(MobileRequest<InfoUpdateDTO> request) {
        InfoUpdateDTO data = request.getData();
        AccountInfo accountInfo = new AccountInfo();
        if (StringUtils.isEmpty(request.getUsernumber())) {
            throw new RuntimeException("not login");
        }
        if ("null".equals(request.getUsernumber())) {
            throw new NotLoginException("请重新登录！");
        }
        accountInfo.setAccountId(Integer.valueOf(request.getUsernumber()));
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
