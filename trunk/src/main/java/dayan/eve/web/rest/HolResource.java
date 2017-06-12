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
import dayan.eve.model.JsonResult;
import dayan.eve.model.JsonResultList;
import dayan.eve.model.query.HolPersonalityQuery;
import dayan.eve.model.query.HolQuery;
import dayan.eve.service.HolService;
import dayan.eve.web.dto.HolQueryDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xsg
 */
@RestController
@RequestMapping(value = "/api/v20/mobile/hol")
public class HolResource {

    private final HolService holService;

    @Autowired
    public HolResource(HolService holService) {
        this.holService = holService;
    }

    @ApiOperation("获取霍兰德测试题目")
    @PostMapping( "/readQuestion")
    public JsonResult readQuestion(@RequestBody HolQueryDTO queryDTO) {
        return JSONObject.parseObject(holService.readQuestion(queryDTO.getIsSimplified()), JsonResult.class);
    }

    @ApiOperation("根据霍兰德测试得到人格")
    @PostMapping("/readPersonality")
    public JsonResult readPersonality(@RequestBody HolQueryDTO queryDTO) {
        return new JsonResult(JSONObject.parse(holService.readPersonality(buildHolPersonalityQuery(queryDTO))));
    }

    private HolPersonalityQuery buildHolPersonalityQuery(HolQueryDTO queryDTO) {
        HolPersonalityQuery query = new HolPersonalityQuery();
        query.setNumOfA(Integer.valueOf(queryDTO.getA()));
        query.setNumOfC(Integer.valueOf(queryDTO.getC()));
        query.setNumOfE(Integer.valueOf(queryDTO.getE()));
        query.setNumOfI(Integer.valueOf(queryDTO.getI()));
        query.setNumOfR(Integer.valueOf(queryDTO.getR()));
        query.setNumOfS(Integer.valueOf(queryDTO.getS()));
        return query;
    }


    @ApiOperation("根据人格推荐职业大类")
    @PostMapping("/readJobClass")
    public JsonResultList readJobClass(@RequestBody HolQueryDTO queryDTO) {
        HolQuery query = new HolQuery();
        query.setPersonalityCode(queryDTO.getPersonalityCode());
        query.initPaging(queryDTO.getPaging());
        String holJobClassReadResult = holService.readJobClass(query);
        return JSONObject.parseObject(holJobClassReadResult, JsonResultList.class);
    }

    @ApiOperation("根据职业大类推荐专业大类")
    @PostMapping("/readMajorTypes")
    public JsonResultList readMajorTypes(@RequestBody HolQueryDTO queryDTO) {
        HolQuery query = new HolQuery();
        query.setJobClassCode(queryDTO.getJobClassCode());
        query.initPaging(queryDTO.getPaging());
        return JSONObject.parseObject(holService.readMajorTypes(query), JsonResultList.class);
    }


    @ApiOperation("根据职业推荐专业大类")
    @PostMapping("/readMajors")
    public JsonResultList readMajors(@RequestBody HolQueryDTO queryDTO) {
        HolQuery query = new HolQuery();
        query.setMajorTypeCode(queryDTO.getMajorTypeCode());
        query.initPaging(queryDTO.getPaging());
        return JSONObject.parseObject(holService.readMajors(query), JsonResultList.class);
    }


}
