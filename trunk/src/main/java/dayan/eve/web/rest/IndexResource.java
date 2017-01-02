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
import dayan.eve.exception.ErrorCN;
import dayan.eve.model.JsonResult;
import dayan.eve.model.JsonResultList;
import dayan.eve.model.MobileRequest;
import dayan.eve.model.Province;
import dayan.eve.model.account.Account;
import dayan.eve.service.*;
import dayan.eve.util.Go4BaseUtil;
import dayan.eve.web.dto.ActivateLogDTO;
import dayan.eve.web.dto.LoginDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xsg
 */
@RestController
@RequestMapping(value = "/api/v20/mobile")
public class IndexResource {

    private static final Logger LOGGER = LogManager.getLogger();

    private final static String CHARSET = ";charset=UTF-8";

    @Autowired
    AccountService accountService;

    @Autowired
    ProvinceService provinceService;

    @Autowired
    ActivateService activateService;

    @Autowired
    EasemobService easemobService;

    @Autowired
    BannerService bannerService;

    @Autowired
    Go4BaseUtil go4BaseUtil;

//    @Autowired
//    HotRecommendDataV20Service hotRecommendDataV20Service;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResult login(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        try {
            LOGGER.debug("login: " + JSON.toJSONString(loginDTO, true));
            final Account loginAccount = accountService.login(loginDTO.getLoginType(), loginDTO
                    .getLoginData());
            LOGGER.info("login result info,{}", JSON.toJSONString(loginAccount, true));
            JsonResult result = new JsonResult();
            result.setData(loginAccount);
            return result;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new JsonResult(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public JsonResult reg(@RequestBody Account reqAccount, HttpServletRequest request, @RequestParam(required = false,
            value = "files")
            MultipartFile file) {

        try {
            Account account = accountService.register(reqAccount, file);
            JsonResult result = new JsonResult();
            Map<String, String> m = new HashMap<>();
            m.put("userNumber", String.valueOf(account.getId()));
            result.setData(m);
            return result;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new JsonResult(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public JsonResult updatePassword(@RequestBody Account account, HttpServletRequest request) {
        try {
            accountService.updatePassword(account);
            return new JsonResult();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new JsonResult(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
    }

    @RequestMapping(value = "/getProvinces", method = RequestMethod.GET)
    public JsonResult getProvinces() {
        try {
            List<Province> list = provinceService.read();
            JsonResult result = new JsonResult();
            result.setData(list);
            return result;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new JsonResult(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
    }

    @RequestMapping(value = "/getProvinces.jsonp"
            , method = RequestMethod.GET
            , produces = MediaType.APPLICATION_JSON_VALUE + CHARSET)
    public JSONPObject getProvincesP(HttpServletRequest request, @RequestParam("callback") String callBack) {
        try {
            List<Province> list = provinceService.read();
            JsonResult result = new JsonResult();
            result.setData(list);
            result.setJsessionid(request.getSession().getId());
            return new JSONPObject(callBack, result);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new JSONPObject(callBack, new JsonResult(ErrorCN.DEFAULT_SERVER_ERROR, false));
        }
    }

    @RequestMapping(value = "/activate", method = RequestMethod.POST)
    public JsonResult activate(MobileRequest<ActivateLogDTO> request) {
        try {
            LOGGER.debug("login: " + JSON.toJSONString(request, true));
            String activate = activateService.activate(request.getData());
            Map<String, String> map = new HashMap<>(1);
            map.put("deviceNumber", activate);
            return new JsonResult(map);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new JsonResult(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
    }

    @RequestMapping(value = "/sendSMS", method = RequestMethod.POST)
    public JsonResult sendSMS(HttpServletRequest request) {
        try {
            String dataStr = request.getParameter("data");
            String mobile = JSON.parseObject(dataStr).getString("mobile");
            go4BaseUtil.checkLoginAccount(mobile);
            go4BaseUtil.getVerificationCode(mobile);
            return new JsonResult();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new JsonResult(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
    }

    @RequestMapping(value = "/sendSMSOfPwd", method = RequestMethod.POST)
    public JsonResult sendSMSOfPwd(HttpServletRequest request) {
        try {
            String dataStr = request.getParameter("data");
            String mobile = JSON.parseObject(dataStr).getString("mobile");
            go4BaseUtil.getAccountDetailByLoginAccount(mobile);
            go4BaseUtil.getVerificationCode(mobile);
            return new JsonResult(request.getSession().getId());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new JsonResult(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
    }

//    @RequestMapping(value = "/getRecommendCount", method = RequestMethod.POST)
//    public @ResponseBody
//    JsonResult getRecommendCount(HttpServletRequest request) {
//        try {
//            hotRecommendDataV20Service.getRecommendTimes();
//            return new JsonResult(request.getSession().getId());
//        } catch (Exception ex) {
//            LOGGER.error(ex.getMessage(), ex);
//            return new JsonResult("ooooops, I'm done..", false);
//        }
//    }

//    @Autowired
//    WeiboDataV20Service weiboDataV20Service;
//
//    @RequestMapping(value = "/grebWeibo", method = RequestMethod.POST)
//    public
//    @ResponseBody
//    JsonResult grebWeibo(HttpServletRequest request) {
//        try {
//            weiboDataV20Service.getWeiboFriendTimeline();
//            return new JsonResult(request.getSession().getId());
//        } catch (Exception ex) {
//            LOGGER.error(ex.getMessage(), ex);
//            return new JsonResult("ooooops, I'm done..", false);
//        }
//    }

    //    @Autowired
//    Go4Service go4Service;
//
//    @RequestMapping(value = "/getAccountHashId", method = RequestMethod.POST)
//    public @ResponseBody
//    JsonResult getAccountHashId(HttpServletRequest request) {
//        try {
//            go4Service.updateAccountHashId();
//            return new JsonResult(request.getSession().getId());
//        } catch (Exception ex) {
//            LOGGER.error(ex.getMessage(), ex);
//            return new JsonResult("ooooops, I'm done..", false);
//        }
//    }
    @RequestMapping(value = "/readBanners", method = RequestMethod.POST)
    public JsonResultList readBanners() {
        try {
            return new JsonResultList(bannerService.readBanners());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new JsonResultList(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
    }

}
