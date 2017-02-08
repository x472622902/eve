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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xsg
 */
@RestController
@RequestMapping(value = "/api/v20/mobile/clock")
public class ClockResource {

    private static final Logger LOGGER = LogManager.getLogger();

    private final ClockService clockService;
    private final RequestService requestService;

    @Autowired
    public ClockResource(ClockService clockService, RequestService requestService) {
        this.clockService = clockService;
        this.requestService = requestService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public JsonResult clockIn(@RequestBody ClockDTO clockDTO, HttpServletRequest request) {
        ClockQuery query = new ClockQuery();
        query.setAccountId(requestService.getUserNumber(request));
        query.setContent(clockDTO.getContent());
        clockService.clockIn(query);
        JsonResult result = new JsonResult();
        result.setJsessionid(request.getSession().getId());
        return result;
    }

    @RequestMapping(value = "/readStatus", method = RequestMethod.POST)
    public JsonResult check(HttpServletRequest request) {
        JsonResult result = new JsonResult(clockService.readStatus(requestService.getUserNumber(request)));
        result.setJsessionid(request.getSession().getId());
        return result;
    }

    @RequestMapping(value = "/readRank", method = RequestMethod.POST)
    public JsonResultList readTodayClockRank(@RequestBody ClockDTO clockDTO, HttpServletRequest request) {
        ClockQuery query = new ClockQuery();
        query.setAccountId(requestService.getAccountId(request));
        query.setReadContinuousRank(clockDTO.getReadContinuousRank());
        query.setReadTodayRank(clockDTO.getReadTodayRank());
        query.setReadTotalRank(clockDTO.getReadTotalRank());
        query.initPaging(clockDTO.getPaging());
        return new JsonResultList(clockService.readRank(query));
    }

    @RequestMapping(value = "/readMyClocks", method = RequestMethod.POST)
    public JsonResultList readMyClocks(@RequestBody ClockDTO clockDTO, HttpServletRequest request) {
        ClockQuery query = new ClockQuery();
        query.setAccountId(requestService.getUserNumber(request));
        query.initPaging(clockDTO.getPaging());
        JsonResultList result = new JsonResultList(clockService.readClocks(query));
        result.setPager(clockService.count(query));
        return result;
    }

}
