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
import dayan.eve.model.query.TopicQuery;
import dayan.eve.model.topic.Topic;
import dayan.eve.model.topic.TopicTheme;
import dayan.eve.service.RequestService;
import dayan.eve.service.TopicService;
import dayan.eve.web.dto.PaginationQueryDTO;
import dayan.eve.web.dto.topic.TopicCreateDTO;
import dayan.eve.web.dto.topic.TopicLikeDTO;
import dayan.eve.web.dto.topic.TopicQueryDTO;
import dayan.eve.web.dto.topic.TopicThemeQueryDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xsg
 */
@RestController
@RequestMapping(value = "/api/v20/mobile/topic")
@ApiModel("讨论区")
public class TopicResource {

    private static final Logger LOGGER = LogManager.getLogger();

    private final TopicService topicService;
    private final RequestService requestService;

    @Autowired
    public TopicResource(TopicService topicService, RequestService requestService) {
        this.topicService = topicService;
        this.requestService = requestService;
    }

    @ApiOperation("看帖")
    @RequestMapping(value = "/readTopics", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResultList readTopics(@RequestBody TopicQueryDTO topicQueryDTO, HttpServletRequest request) {
        TopicQuery query = buildQuery(topicQueryDTO);
        query.setAccountId(requestService.getAccountIdValue(request));
        return new JsonResultList(topicService.readTopics(query));
    }

    @ApiOperation("查看自己发的贴")
    @RequestMapping(value = "/readMyTopics", method = RequestMethod.POST)
    public JsonResultList readMyTopics(@RequestBody PaginationQueryDTO queryDTO, HttpServletRequest
            request) {
        Integer accountId = requestService.getAccountId(request);
        TopicQuery query = new TopicQuery();
        query.setAccountId(accountId);
        query.setMyTopic(true);
        query.initPaging(queryDTO.getPaging());
        return new JsonResultList(topicService.readTopics(query));
    }

    @ApiOperation("查看自己的回复")
    @RequestMapping(value = "/readMyReplies", method = RequestMethod.POST)
    public JsonResultList readMyReplies(@RequestBody PaginationQueryDTO queryDTO, HttpServletRequest request) {
        Integer accountId = requestService.getAccountId(request);
        TopicQuery query = new TopicQuery();
        query.setAccountId(accountId);
        query.initPaging(queryDTO.getPaging());
        return new JsonResultList(topicService.readTopics(query));
    }

    @ApiOperation("发帖")
    @RequestMapping(value = "/createTopic", method = RequestMethod.POST)
    public JsonResult createTopic(@RequestBody TopicCreateDTO topicCreateDTO, HttpServletRequest request,
                                  @RequestParam(required = false, value = "files") MultipartFile... files) {
        Integer accountId = requestService.getAccountId(request);
        Topic topic = buildTopic(topicCreateDTO);
        topic.setAccountId(accountId);
        return new JsonResult(topicService.createTopic(topic, files));
    }

    private Topic buildTopic(TopicCreateDTO topicCreateDTO) {
        Topic topic = new Topic();
        Integer topicId = null;
        if (!StringUtils.isEmpty(topicCreateDTO.getTopicId())) {
            topicId = Integer.valueOf(topicCreateDTO.getTopicId());
        }
        if (!StringUtils.isEmpty(topicCreateDTO.getParentId())) {
            topic.setParentId(Integer.valueOf(topicCreateDTO.getParentId()));
        }
        topic.setTheme(new TopicTheme());
        if (!StringUtils.isEmpty(topicCreateDTO.getThemeId())) {
            topic.getTheme().setId(Integer.valueOf(topicCreateDTO.getThemeId()));
        }
        topic.setTopicId(topicId);
        topic.setContent(topicCreateDTO.getContent());
        return topic;
    }

    @ApiOperation("赞")
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public JsonResult like(@RequestBody TopicLikeDTO topicLikeDTO, HttpServletRequest request) {
        Integer accountId = requestService.getAccountId(request);
        topicService.like(accountId, Integer.valueOf(topicLikeDTO.getTopicId()));
        return new JsonResult();
    }

    @ApiOperation("踩")
    @RequestMapping(value = "/dislike", method = RequestMethod.POST)
    public JsonResult dislike(@RequestBody TopicLikeDTO topicLikeDTO, HttpServletRequest request) {
        topicService.dislike(requestService.getAccountId(request), Integer.valueOf(topicLikeDTO.getTopicId()));
        return new JsonResult();
    }

    @ApiOperation("删帖")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public JsonResult delete(@RequestBody TopicLikeDTO topicLikeDTO, HttpServletRequest request) {
        Integer accountId = requestService.getAccountId(request);
        TopicQuery query = new TopicQuery();
        query.setTopicId(Integer.valueOf(topicLikeDTO.getTopicId()));
        query.setAccountId(accountId);
        topicService.delete(query);
        return new JsonResult();
    }

    @ApiOperation("查看主题")
    @RequestMapping(value = "/readAllThemes", method = RequestMethod.POST)
    public JsonResultList readAllThemes(@RequestBody TopicThemeQueryDTO queryDTO) {
        TopicQuery query = new TopicQuery();
        query.initPaging(queryDTO.getPaging());
        return new JsonResultList(topicService.readAllThemes(query));
    }

    private TopicQuery buildQuery(TopicQueryDTO data) {
        TopicQuery query = new TopicQuery();
        if (!StringUtils.isEmpty(data.getAccountId())) {
            query.setAccountId(Integer.valueOf(data.getAccountId()));
        }
        if (data.getId() != null) {
            query.setId(Integer.valueOf(data.getId()));
        }
        if (data.getThemeId() != null) {
            query.setThemeId(Integer.valueOf(data.getThemeId()));
        }
        if (data.getMyTopic() != null) {
            if (data.getMyTopic()) {
                query.setTopicId(null);
            } else {
                query.setTopicId(1);
            }
            query.setMyTopic(data.getMyTopic());
        } else {
            if (!StringUtils.isEmpty(data.getTopicId())) {
                query.setTopicId(Integer.valueOf(data.getTopicId()));
            }
            if (!StringUtils.isEmpty(data.getParentId())) {
                query.setParentId(Integer.valueOf(data.getTopicId()));
            }
        }
        if (data.getPopular() != null) {
            query.setPopular(data.getPopular());
        }
        if (data.getLive() != null) {
            query.setLive(data.getLive());
        }
        query.initPaging(data.getPaging());
        return query;
    }

}
