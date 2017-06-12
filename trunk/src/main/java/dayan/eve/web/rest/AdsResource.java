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
import dayan.eve.model.query.AdsQuery;
import dayan.eve.service.AdsService;
import dayan.eve.service.AnnouncementService;
import dayan.eve.service.RequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xsg
 */
@RestController
@RequestMapping(value = "/api/v20/mobile/ads")
public class AdsResource {

    private static final Logger LOGGER = LogManager.getLogger();
    private final AnnouncementService announcementService;
    private final AdsService adsService;
    private final RequestService requestService;

    @Autowired
    public AdsResource(AnnouncementService announcementService, AdsService adsService, RequestService requestService) {
        this.announcementService = announcementService;
        this.adsService = adsService;
        this.requestService = requestService;
    }

    @PostMapping("/read")
    public JsonResult read(HttpServletRequest request) {
        AdsQuery query = new AdsQuery();
        query.setAccountId(requestService.getAccountIdValue(request));
        query.setIsStartpage(Boolean.FALSE);
        return new JsonResult(adsService.readAds(query));
    }

    @PostMapping("/readStartPage")
    public JsonResult readStartPage(HttpServletRequest request) {
        AdsQuery query = new AdsQuery();
        query.setIsStartpage(Boolean.TRUE);
        query.setAccountId(requestService.getAccountIdValue(request));
        return new JsonResult(adsService.readAds(query));
    }

    @PostMapping("/readPopup")
    public JsonResult readPopup(HttpServletRequest request) {
        AdsQuery query = new AdsQuery();
        query.setAccountId(requestService.getAccountIdValue(request));
        query.setIsPopup(Boolean.TRUE);
        return new JsonResult(adsService.readAds(query));
    }

    @PostMapping("/readAnnouncement")
    public JsonResult readAnnouncement() {
        return new JsonResult(announcementService.read());
    }


}
