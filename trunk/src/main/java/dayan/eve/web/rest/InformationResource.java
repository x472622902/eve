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

import dayan.eve.model.JsonResultList;
import dayan.eve.model.query.InformationQuery;
import dayan.eve.service.InformationService;
import dayan.eve.web.dto.InformationQueryDTO;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xsg
 */
@RestController
@RequestMapping(value = "/api/v20/mobile/information")
public class InformationResource {

    private static final Logger LOGGER = LogManager.getLogger();

    private final InformationService informationService;

    @Autowired
    public InformationResource(InformationService informationService) {
        this.informationService = informationService;
    }

    @ApiOperation("资讯列表")
    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public JsonResultList readInformations(@RequestBody InformationQueryDTO queryDTO) {
        return new JsonResultList(informationService.read(buildInfoQuery(queryDTO)));
    }

    private InformationQuery buildInfoQuery(InformationQueryDTO queryDTO) {
        InformationQuery query = new InformationQuery();
        query.initPaging(queryDTO.getPaging());
        query.setIsPopular(queryDTO.getPopular() == null ? false : queryDTO.getPopular());
        return query;
    }

}
