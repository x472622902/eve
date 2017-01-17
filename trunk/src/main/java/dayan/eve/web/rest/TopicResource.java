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

import dayan.eve.exception.ErrorCN;
import dayan.eve.model.JsonResult;
import dayan.eve.model.JsonResultList;
import dayan.eve.model.PageResult;
import dayan.eve.model.query.TopicQuery;
import dayan.eve.model.topic.Topic;
import dayan.eve.model.topic.TopicTheme;
import dayan.eve.service.RequestService;
import dayan.eve.service.TopicService;
import dayan.eve.util.SchoolIdPlatformIdUtil;
import dayan.eve.web.dto.topic.TopicCreateDTO;
import dayan.eve.web.dto.topic.TopicLikeDTO;
import dayan.eve.web.dto.topic.TopicReadQueryDTO;
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
public class TopicResource {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    TopicService topicService;

    @Autowired
    RequestService requestService;

    @Autowired
    SchoolIdPlatformIdUtil schoolIdPlatformIdUtil;

    @ApiOperation("看帖")
    @RequestMapping(value = "/readTopics", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResultList readTopics(@RequestBody TopicReadQueryDTO topicReadQueryDTO, HttpServletRequest httpServletRequest) {
        try {
            TopicQuery query = buildQuery(topicReadQueryDTO);
            query.setAccountId(requestService.getAccountId(httpServletRequest));
            JsonResultList result = new JsonResultList();
            result.setData(topicService.readTopics(query));
            result.setPager(topicService.count(query));
            return result;
        } catch (Exception other) {
            LOGGER.error(other.getMessage(), other);
            return new JsonResultList(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
    }

    @ApiOperation("查看自己发的贴")
    @RequestMapping(value = "/readMyTopics", method = RequestMethod.POST)
    public JsonResultList readMyTopics(@RequestBody TopicReadQueryDTO topicReadQueryDTO, HttpServletRequest hsrRequest) {
        return requestService.getUserNumber(hsrRequest)
                .map(accountId -> {
                    JsonResultList result = new JsonResultList();
                    try {
                        TopicQuery query = buildQuery(topicReadQueryDTO);
                        query.setIsMyTopic(true);
                        result.setData(topicService.readTopics(query));
                        result.setPager(topicService.count(query));
                    } catch (Exception ex) {
                        LOGGER.error(ex.getMessage(), ex);
                        result.setInfo(ErrorCN.DEFAULT_SERVER_ERROR);
                        result.setSuccess(false);
                    }
                    return result;
                }).orElse(new JsonResultList(ErrorCN.Login.UN_LOGIN, false));
    }

    @ApiOperation("查看自己的回复")
    @RequestMapping(value = "/readMyReplies", method = RequestMethod.POST)
    public JsonResultList readMyReplies(@RequestBody TopicReadQueryDTO topicReadQueryDTO, HttpServletRequest hsrRequest) {
        return requestService.getUserNumber(hsrRequest)
                .map(accountId -> {
                    JsonResultList result = new JsonResultList();
                    try {
                        TopicQuery query = buildQuery(topicReadQueryDTO);
                        query.setIsMyTopic(false);
                        result.setData(topicService.readTopics(query));
                        result.setPager(topicService.count(query));
                    } catch (Exception ex) {
                        LOGGER.error(ex.getMessage(), ex);
                        result.setInfo(ErrorCN.DEFAULT_SERVER_ERROR);
                        result.setSuccess(false);
                    }
                    return result;
                }).orElse(new JsonResultList(ErrorCN.Login.UN_LOGIN, false));
    }

    @ApiOperation("发帖")
    @RequestMapping(value = "/createTopic", method = RequestMethod.POST)
    public JsonResult createTopic(@RequestBody TopicCreateDTO topicCreateDTO, HttpServletRequest hsrRequest, @RequestParam(required
            = false, value = "files") MultipartFile... files) {
        return requestService.getUserNumber(hsrRequest)
                .map(accountId -> {
                    JsonResult result = new JsonResultList();
                    Topic topic = buildTopic(topicCreateDTO);
                    topic.setAccountId(accountId);
                    result.setData(topicService.createTopic(topic, files));
                    return result;
                }).orElse(new JsonResult(ErrorCN.Login.UN_LOGIN, false));
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
    public JsonResult like(@RequestBody TopicLikeDTO topicLikeDTO, HttpServletRequest hsrRequest) {
        return requestService.getUserNumber(hsrRequest)
                .map(accountId -> {
                    JsonResult result = new JsonResult();
                    try {
                        TopicQuery query = new TopicQuery();
                        query.setTopicId(Integer.valueOf(topicLikeDTO.getTopicId()));
                        query.setAccountId(accountId);
                        topicService.like(query);
                    } catch (Exception ex) {
                        LOGGER.error(ex.getMessage(), ex);
                        result.setInfo(ErrorCN.DEFAULT_SERVER_ERROR);
                        result.setSuccess(false);
                    }
                    return result;
                }).orElse(new JsonResult(ErrorCN.Login.UN_LOGIN, false));
    }

    @ApiOperation("踩")
    @RequestMapping(value = "/dislike", method = RequestMethod.POST)
    public JsonResult dislike(@RequestBody TopicLikeDTO topicLikeDTO, HttpServletRequest hsrRequest) {
        return requestService.getUserNumber(hsrRequest)
                .map(accountId -> {
                    JsonResult result = new JsonResult();
                    try {
                        TopicQuery query = new TopicQuery();
                        query.setTopicId(Integer.valueOf(topicLikeDTO.getTopicId()));
                        query.setAccountId(accountId);
                        topicService.dislike(query);
                    } catch (Exception ex) {
                        LOGGER.error(ex.getMessage(), ex);
                        result.setInfo(ErrorCN.DEFAULT_SERVER_ERROR);
                        result.setSuccess(false);
                    }
                    return result;
                }).orElse(new JsonResult(ErrorCN.Login.UN_LOGIN, false));
    }

    @ApiOperation("删帖")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public JsonResult delete(@RequestBody TopicLikeDTO topicLikeDTO, HttpServletRequest hsrRequest) {
        return requestService.getUserNumber(hsrRequest)
                .map(accountId -> {
                    JsonResult result = new JsonResult();
                    try {
                        TopicQuery query = new TopicQuery();
                        query.setTopicId(Integer.valueOf(topicLikeDTO.getTopicId()));
                        query.setAccountId(accountId);
                        topicService.delete(query);
                    } catch (Exception ex) {
                        LOGGER.error(ex.getMessage(), ex);
                        result.setInfo(ErrorCN.DEFAULT_SERVER_ERROR);
                        result.setSuccess(false);
                    }
                    return result;
                }).orElse(new JsonResult(ErrorCN.Login.UN_LOGIN, false));
    }

    @ApiOperation("查看主题")
    @RequestMapping(value = "/readAllThemes", method = RequestMethod.POST)
    public JsonResultList readAllThemes(@RequestBody TopicReadQueryDTO topicReadQueryDTO, HttpServletRequest
            hsrRequest) {
        try {
            TopicQuery query = buildQuery(topicReadQueryDTO);
            PageResult<TopicTheme> pageResult = topicService.readAllThemes(query);
            JsonResultList result = new JsonResultList();
            result.setData(pageResult.getData());
            result.setPager(pageResult.getPager());
            return result;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new JsonResultList(ErrorCN.DEFAULT_SERVER_ERROR, false);
        }
    }

    private TopicQuery buildQuery(TopicReadQueryDTO data) {
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
        if (data.isIsMyTopic() != null) {
            if (data.isIsMyTopic()) {
                query.setTopicId(null);
            } else {
                query.setTopicId(1);
            }
            query.setIsMyTopic(data.isIsMyTopic());
        } else {
            if (!StringUtils.isEmpty(data.getTopicId())) {
                query.setTopicId(Integer.valueOf(data.getTopicId()));
            }
            if (!StringUtils.isEmpty(data.getParentId())) {
                query.setParentId(Integer.valueOf(data.getTopicId()));
            }
        }
        if (data.getIsPopular() != null) {
            query.setIsPopular(data.getIsPopular());
        }
        if (data.getIsLive() != null) {
            query.setIsLive(data.getIsLive());
        }
        query.setPage(data.getPaging().getPage());
        query.setSize(data.getPaging().getSize());
        return query;
    }

}