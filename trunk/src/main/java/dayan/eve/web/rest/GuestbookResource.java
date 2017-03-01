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
import com.alibaba.fastjson.JSONObject;
import dayan.common.util.SchoolPlatformIdEncoder;
import dayan.eve.model.*;
import dayan.eve.model.easemob.EasemobResult;
import dayan.eve.model.easemob.EasemobType;
import dayan.eve.model.query.GuestbookQuery;
import dayan.eve.repository.SchoolRepository;
import dayan.eve.service.EasemobService;
import dayan.eve.service.RequestService;
import dayan.eve.service.walle.GuestBookService;
import dayan.eve.util.SchoolIdPlatformIdUtil;
import dayan.eve.web.dto.walle.GuestbookDTO;
import dayan.eve.web.dto.walle.GuestbookQueryDTO;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xsg
 */
@RestController
@RequestMapping(value = "/api/v20/mobile/guestbook")
public class GuestbookResource {

    private static final Logger LOGGER = LogManager.getLogger();
    private static SchoolPlatformIdEncoder idEncoder = new SchoolPlatformIdEncoder();

    private final EasemobService easemobService;
    private final SchoolRepository schoolRepository;
    private final SchoolIdPlatformIdUtil schoolIdPlatformIdUtil;
    private final GuestBookService guestBookService;
    private final RequestService requestService;

    @Autowired
    public GuestbookResource(EasemobService easemobService, RequestService requestService, SchoolRepository schoolRepository, GuestBookService guestBookService, SchoolIdPlatformIdUtil schoolIdPlatformIdUtil) {
        this.easemobService = easemobService;
        this.requestService = requestService;
        this.schoolRepository = schoolRepository;
        this.guestBookService = guestBookService;
        this.schoolIdPlatformIdUtil = schoolIdPlatformIdUtil;
    }

    @ApiOperation("留言板留言")
    @RequestMapping(value = "/sendGuestbook", method = RequestMethod.POST)
    public JsonResultList sendGuestbook(@Valid @RequestBody GuestbookDTO guestbookDTO, HttpServletRequest request) {

        GuestbookQuery query = new GuestbookQuery();
        query.setSchoolId(idEncoder.decode(guestbookDTO.getSchoolHashId()).intValue());
        query.setQuestion(guestbookDTO.getQuestion());
        Integer accountId = requestService.getAccountId(request);
        guestBookService.sendGuestbook(accountId, query);
        return new JsonResultList();
    }

    /**
     * 留言板问题回答回调
     */
    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public JsonResult callback(HttpServletRequest request) {

        String answerJson = request.getParameter(Constants.Key.Answer);
        GuestbookAnswer answer = JSON.parseObject(answerJson, GuestbookAnswer.class);
        Map<String, String> ext = new HashMap<>();
        Integer schoolId = schoolIdPlatformIdUtil.getPlatformIdAndSchoolIdMap().get(answer.getPlatformId());
        String schoolName = schoolRepository.queryName(schoolId);
        String schoolHashId = idEncoder.encrypt(schoolId.longValue());
        ext.put(Constants.Key.SchoolName, schoolName);
        ext.put(Constants.Key.SchoolHashId, schoolHashId);
        ext.put(Constants.Key.PlatformHashId, idEncoder.encrypt(answer.getPlatformId().longValue()));
        EasemobResult easemobResult = new EasemobResult(new JsonResult(answer), EasemobType.GuestBook);
        easemobResult.setExt(ext);
        easemobService.sendMessage(answer.getUserNumber().toString(), easemobResult);
        return new JsonResult();
    }

    @ApiOperation("读取学校留言板留言")
    @RequestMapping(value = "/readGuestbook", method = RequestMethod.POST)
    public JsonResultList readGuestbook(@RequestBody GuestbookQueryDTO queryDTO) {

        GuestbookQuery query = new GuestbookQuery();
        query.setSchoolId(idEncoder.decode(queryDTO.getSchoolHashId()).intValue());
        query.initPaging(queryDTO.getPaging());
        String resultStr;
        JsonResultList resultList = new JsonResultList();
        try {
            resultStr = guestBookService.readGuestbook(query);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return resultList;
        }
        JSONObject result = JSONObject.parseObject(resultStr);
        if (result.getBoolean("success")) {
            JSONObject data = result.getJSONObject("data");
            resultList.setPager(data.getObject("pager", Pager.class));
            resultList.setData(data.get("result"));
        }
        return resultList;
    }
}
