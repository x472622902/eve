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
import dayan.eve.model.JsonResultList;
import dayan.eve.model.query.ClockQuery;
import dayan.eve.model.query.SignQuery;
import dayan.eve.service.ClockService;
import dayan.eve.service.RequestService;
import dayan.eve.service.SignService;
import dayan.eve.web.dto.ClockDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xsg
 */
@Aspect
@Controller
@RequestMapping("/api/v20/mobile/sign")
public class SignResource {

    private static final Logger LOGGER = LogManager.getLogger();

    private final SignService signService;
    private final ClockService clockService;
    private final RequestService requestService;

    @Autowired
    public SignResource(SignService signService, ClockService clockService, RequestService requestService) {
        this.signService = signService;
        this.clockService = clockService;
        this.requestService = requestService;
    }

    @PostMapping("/sign")
    public JsonResult sign(@RequestBody ClockDTO clockDTO, HttpServletRequest request) throws Exception {
        SignQuery query = new SignQuery();
        query.setAccountId(requestService.getAccountId(request));
        query.setContent(clockDTO.getContent());
        signService.sign(query);
        return new JsonResult();
    }

    @PostMapping("/check")
    public JsonResult check(HttpServletRequest request) throws Exception {
        return new JsonResult(signService.check(requestService.getAccountId(request)));
    }

    @PostMapping("/readAccounts")
    public JsonResultList readAccounts(@RequestBody ClockDTO clockDTO) throws Exception {
        SignQuery query = new SignQuery();
        query.initPaging(clockDTO.getPaging());
        return new JsonResultList(signService.readAccounts(query));
    }

    @PostMapping("/readDays")
    public JsonResult readDays() throws Exception {
        return new JsonResult(signService.readDays());
    }

    @PostMapping("/clockIn")
    public JsonResult clockIn(@RequestBody ClockDTO clockDTO, HttpServletRequest request) {
        ClockQuery query = new ClockQuery();
        query.setAccountId(requestService.getAccountId(request));
        query.setContent(clockDTO.getContent());
        clockService.clockIn(query);
        return new JsonResult();
    }

}
