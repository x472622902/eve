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
import dayan.eve.model.JsonResult;
import dayan.eve.model.Province;
import dayan.eve.model.account.Account;
import dayan.eve.service.*;
import dayan.eve.util.Go4BaseUtil;
import dayan.eve.web.dto.ActivateLogDTO;
import dayan.eve.web.dto.account.LoginDTO;
import dayan.eve.web.dto.account.MobileDTO;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xsg
 */
@RestController
@RequestMapping("/api/v20/mobile")
public class IndexResource {

    private static final Logger LOGGER = LogManager.getLogger();

    private final static String CHARSET = ";charset=UTF-8";

    private final AccountService accountService;
    private final EasemobService easemobService;
    private final ProvinceService provinceService;
    private final ActivateService activateService;
    private final BannerService bannerService;
    private final Go4BaseUtil go4BaseUtil;

    @Autowired
    public IndexResource(Go4BaseUtil go4BaseUtil, EasemobService easemobService, ProvinceService provinceService, BannerService bannerService, AccountService accountService, ActivateService activateService) {
        this.go4BaseUtil = go4BaseUtil;
        this.easemobService = easemobService;
        this.provinceService = provinceService;
        this.bannerService = bannerService;
        this.accountService = accountService;
        this.activateService = activateService;
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public JsonResult login(@RequestBody LoginDTO loginDTO) throws Exception {
        LOGGER.debug("login: " + JSON.toJSONString(loginDTO, true));
        final Account loginAccount = accountService.login(loginDTO.getLoginType(), loginDTO
                .getLoginData());
        LOGGER.info("login result info,{}", JSON.toJSONString(loginAccount, true));
        JsonResult result = new JsonResult();
        result.setData(loginAccount);
        return result;
    }

    @ApiOperation("注册")
    @PostMapping("/reg")
    public JsonResult reg(@RequestBody Account reqAccount, @RequestParam(required = false, value = "files")
            MultipartFile file) throws Exception {
        Account account = accountService.register(reqAccount, file);
        JsonResult result = new JsonResult();
        Map<String, String> m = new HashMap<>();
        m.put("userNumber", String.valueOf(account.getId()));
        result.setData(m);
        return result;
    }

    @ApiOperation("更新密码")
    @PostMapping("/updatePassword")
    public JsonResult updatePassword(@RequestBody Account account) throws Exception {
        accountService.updatePassword(account);
        return new JsonResult();
    }

    @ApiOperation("读取省份列表")
    @GetMapping("/getProvinces")
    public JsonResult getProvinces() {
        List<Province> list = provinceService.read();
        JsonResult result = new JsonResult();
        result.setData(list);
        return result;
    }

    @ApiOperation("读取省份列表jsonp格式")
    @GetMapping(value = "/getProvinces.jsonp", produces = MediaType.APPLICATION_JSON_VALUE + CHARSET)
    public JSONPObject getProvincesP(@RequestParam("callback") String callBack) {
        List<Province> list = provinceService.read();
        JsonResult result = new JsonResult();
        result.setData(list);
        return new JSONPObject(callBack, result);
    }

    @ApiOperation("手机激活")
    @PostMapping("/activate")
    public JsonResult activate(@RequestBody ActivateLogDTO activateLogDTO) {
        LOGGER.debug("login: " + JSON.toJSONString(activateLogDTO, true));
        String activate = activateService.activate(activateLogDTO);
        Map<String, String> map = new HashMap<>(1);
        map.put("deviceNumber", activate);
        return new JsonResult(map);
    }

    @ApiOperation("发送验证码")
    @PostMapping("/sendSMS")
    public JsonResult sendSMS(@RequestBody MobileDTO mobileDTO) throws Exception {
        go4BaseUtil.checkLoginAccount(mobileDTO.getMobile());
        go4BaseUtil.getVerificationCode(mobileDTO.getMobile());
        return new JsonResult();
    }

    @ApiOperation("忘记密码--发送验证码")
    @PostMapping("/sendSMSOfPwd")
    public JsonResult sendSMSOfPwd(@RequestBody MobileDTO mobileDTO) throws Exception {
        go4BaseUtil.getAccountDetailByLoginAccount(mobileDTO.getMobile());
        go4BaseUtil.getVerificationCode(mobileDTO.getMobile());
        return new JsonResult();
    }

    @ApiOperation("首页轮播图")
    @PostMapping("/readBanners")
    public JsonResult readBanners() {
        return new JsonResult(bannerService.readBanners());
    }


//    @PostMapping("/getRecommendCount")
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
//    @PostMapping("/grebWeibo")
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
//    @PostMapping("/getAccountHashId")
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
}
