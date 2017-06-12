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
import dayan.eve.model.JsonResultList;
import dayan.eve.model.query.ClockQuery;
import dayan.eve.service.ClockService;
import dayan.eve.service.RequestService;
import dayan.eve.web.dto.ClockDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xsg
 */
@Aspect
@RestController
@RequestMapping("/api/v20/mobile/clock")
public class ClockResource {

    private static final Logger LOGGER = LogManager.getLogger();

    private final ClockService clockService;
    private final RequestService requestService;

    @Autowired
    public ClockResource(ClockService clockService, RequestService requestService) {
        this.clockService = clockService;
        this.requestService = requestService;
    }

    @PostMapping("")
    public JsonResult clockIn(@RequestBody ClockDTO clockDTO, HttpServletRequest request) {
        ClockQuery query = new ClockQuery();
        query.setAccountId(requestService.getAccountId(request));
        query.setContent(clockDTO.getContent());
        clockService.clockIn(query);
        return new JsonResult();
    }

    @PostMapping("/readStatus")
    public JsonResult check(HttpServletRequest request) {
        return new JsonResult(clockService.readStatus(requestService.getAccountId(request)));
    }

    @PostMapping("/readRank")
    public JsonResult readTodayClockRank(@RequestBody ClockDTO clockDTO, HttpServletRequest request) {
        ClockQuery query = new ClockQuery();
        query.setAccountId(requestService.getAccountIdValue(request));
        query.setReadContinuousRank(clockDTO.getReadContinuousRank());
        query.setReadTodayRank(clockDTO.getReadTodayRank());
        query.setReadTotalRank(clockDTO.getReadTotalRank());
        query.initPaging(clockDTO.getPaging());
        return new JsonResult(clockService.readRank(query));
    }

    @PostMapping("/readMyClocks")
    public JsonResultList readMyClocks(@RequestBody ClockDTO clockDTO, HttpServletRequest request) {
        ClockQuery query = new ClockQuery();
        query.setAccountId(requestService.getAccountId(request));
        query.initPaging(clockDTO.getPaging());
        JsonResultList result = new JsonResultList(clockService.readClocks(query));
        result.setPager(clockService.count(query));
        return result;
    }

}
