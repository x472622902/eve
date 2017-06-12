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
package dayan.eve.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import dayan.easemob.entity.EasemobRequest;
import dayan.eve.config.EveProperties;
import dayan.eve.exception.ErrorCN;
import dayan.eve.exception.EveException;
import dayan.eve.model.ConstantKeys;
import dayan.eve.model.Constants;
import dayan.eve.model.PageResult;
import dayan.eve.model.Pager;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.easemob.EasemobType;
import dayan.eve.model.query.TopicQuery;
import dayan.eve.model.topic.Topic;
import dayan.eve.model.topic.TopicImage;
import dayan.eve.model.topic.TopicNotification;
import dayan.eve.model.topic.TopicTheme;
import dayan.eve.redis.respository.SingleValueRedis;
import dayan.eve.repository.AccountInfoRepository;
import dayan.eve.repository.CodeRepository;
import dayan.eve.repository.topic.*;
import dayan.eve.service.AccountInfoService;
import dayan.eve.service.EasemobService;
import dayan.eve.service.TopicService;
import dayan.eve.util.Go4BaseUtil;
import dayan.eve.util.ImageBaseUtil;
import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {

    private static final Logger LOGGER = LogManager.getLogger(TopicServiceImpl.class);

    private static final Integer TOPIC_CREATE_LIMIT_TIME = 10;
    private final EveProperties.Topic topicProperties;
    private final TopicRepository topicRepository;
    private final CodeRepository codeRepository;
    private final TopicLikeRepository topicLikeRepository;
    private final TopicDislikeRepository topicDislikeRepository;
    private final TopicImageRepository topicImageRepository;
    private final AccountInfoRepository accountInfoRepository;
    private final AccountInfoService accountInfoService;
    private final EasemobService easemobService;
    private final TopicThemeRepository topicThemeRepository;
    private final Go4BaseUtil go4BaseUtil;
    private PassiveExpiringMap topicCache = new PassiveExpiringMap(10000);
    private final SingleValueRedis singleValueRedis;

    @Autowired
    public TopicServiceImpl(EveProperties eveProperties, TopicThemeRepository topicThemeRepository, TopicImageRepository
            topicImageRepository, TopicRepository topicRepository, TopicDislikeRepository topicDislikeRepository, TopicLikeRepository topicLikeRepository, Go4BaseUtil go4BaseUtil, EasemobService easemobService, CodeRepository codeRepository, AccountInfoRepository accountInfoRepository, AccountInfoService accountInfoService, SingleValueRedis singleValueRedis) {
        this.topicProperties = eveProperties.getTopic();
        this.topicThemeRepository = topicThemeRepository;
        this.topicImageRepository = topicImageRepository;
        this.topicRepository = topicRepository;
        this.topicDislikeRepository = topicDislikeRepository;
        this.topicLikeRepository = topicLikeRepository;
        this.go4BaseUtil = go4BaseUtil;
        this.easemobService = easemobService;
        this.codeRepository = codeRepository;
        this.accountInfoRepository = accountInfoRepository;
        this.accountInfoService = accountInfoService;
        this.singleValueRedis = singleValueRedis;
    }

    private boolean isCreatedIn10s(Topic topic) {
        boolean createdInTenSeconds = false;
        try {
            if (singleValueRedis.get(SingleValueRedis.SingleKey.TopicAccount, topic.getAccountId().toString()) != null) {
                createdInTenSeconds = true;
            } else {
                singleValueRedis.putInSeconds(SingleValueRedis.SingleKey.TopicAccount, topic.getAccountId().toString()
                        , TOPIC_CREATE_LIMIT_TIME);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return createdInTenSeconds;
    }

    public Integer count(TopicQuery query) {
        Integer count;
        if (query.getAccountId() == null) {
            if (query.getTopicId() == null) {
                count = topicRepository.countTopic(query);
            } else {
                count = topicRepository.countReply(query.getTopicId());
            }
        } else {
            if (query.getTopicId() == null) {
                count = topicRepository.countMyTopic(query);
            } else {
                count = topicRepository.countMyReply(query);
            }
        }
        return count;
    }

    private void setImages(List<Topic> topics) {
        for (Topic topic : topics) {
            List<TopicImage> images = topicImageRepository.query(topic.getId());
            setImage(images, topic);//设置贴子的图片
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public PageResult<Topic> readTopics(TopicQuery query) {
        Integer count = count(query);
        PageResult<Topic> pageResult = new PageResult<>(count, query.getPage(), query.getSize());
        if (count > 0) {
            List<Topic> topics = topicRepository.queryTopics(query);
            //配置直播贴
            setLive(topics);
            pageResult.setList(topics);
        }
        return pageResult;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void like(Integer accountId, Integer topicId) {
        LOGGER.info("topic like ,accountId: {},topicId: {}", accountId, topicId);
        if (topicLikeRepository.queryExisted(accountId, topicId)) {
            return;
        }
        topicLikeRepository.insert(accountId, topicId);
        topicRepository.updateLikeNum(topicId);

        List<AccountInfo> noticeAccounts = topicRepository.queryAccountByTopic(new TopicQuery(topicId));//主贴用户
        if (noticeAccounts.isEmpty()) {
            return;
        }
        //被赞加2分
        accountInfoService.updateExp(noticeAccounts.get(0).getAccountId(), topicProperties.getBeLikedExp());
        //如果被点赞的帖子是自己的贴，则不通知，返回
        if (Objects.equals(noticeAccounts.get(0).getAccountId(), accountId)) {
            return;
        }

        List<Topic> topics = topicRepository.queryTopics(new TopicQuery(topicId));
        if (topics.isEmpty()) return;
        Topic topic = topics.get(0);
        Topic originTopic = getOriginTopic(topic);
        if (originTopic == null) return;

        sendNotification(topic, noticeAccounts, originTopic, TopicNotification.Action.Like);
    }

    @Transactional
    @Override
    public void dislike(Integer accountId, Integer topicId) {
        if (!topicDislikeRepository.queryExisted(accountId, topicId)) {
            LOGGER.info("topic dislike ,accountId: {},topicId: {}", accountId, topicId);
            topicDislikeRepository.insert(accountId, topicId);
            topicRepository.updateDislikeNum(topicId);
        }
    }

    /**
     * 设置贴子的图片（缩略图、中图和大图）
     */
    private void setImage(List<TopicImage> images, Topic topic) {
        topic.setThumbnailUrls(Collections.emptyList());
        topic.setImageUrls(Collections.emptyList());
        topic.setMiddleImageUrls(Collections.emptyList());
        if (images == null || images.isEmpty()) {
            return;
        }
        for (TopicImage image : images) {
            topic.getImageUrls().add(image.getImageUrl());
            topic.getThumbnailUrls().add(image.getThumbnailUrl());
            topic.getMiddleImageUrls().add(image.getMiddleImageUrl());

        }
    }

    /*
     *设置原帖
     */
    private void setOriginTopic(List<Topic> topics) {

        topics.stream()
                .filter(topic -> topic.getTopicId() != null && topic.getOriginTopic() != null && !topic.getOriginTopic().getIsDeleted())
                .forEach(topic -> {
                    List<TopicImage> images = topicImageRepository.query(topic.getOriginTopic().getId());
                    setImage(images, topic.getOriginTopic());
                });
        topics.stream()
                .filter(topic -> topic.getOriginTopic() != null && topic.getOriginTopic().getIsDeleted())
                .forEach(topic -> topic.setOriginTopic(null));
    }

    /**
     * 发帖流程
     * <p>
     * 1.判断是否十秒内再发（防止恶意刷帖），是则结束
     * 2.内容过长（1024字），是则结束
     * 3.如果帖子带主题，更新该主题的帖子数
     * 4.插入数据
     * 5.插入图片
     * 6.如果是发帖，更新该用户的经验（+10），如果是回复，继续
     * 7.更新该主贴的回复数
     * 8.更新主贴用户经验（+2），该用户（+5）
     * 9.生成被通知列表（初始为主贴用户）
     * 10.如果该回复是楼中楼，则将所有楼中楼的用户加入通知列表，将该回帖作为返回结果
     * 11.设置回帖定位
     * 12.发送通知
     * 13.结束
     * <p>
     * PS:帖子分3个等级，1.主贴（topic） 2.回帖(reply) 3.评论(comment)
     */
    @Override
    @Transactional
    public Topic createTopic(Topic topic, MultipartFile[] files) {
        if (isCreatedIn10s(topic)) {
            LOGGER.error("topic created in 10s. {}", JSON.toJSONString(topic, true));
            return null;
        }
        checkTopicContentLength(topic);
        updateTheme(topic);
        codeRepository.setCode(Constants.EMOJI_CODE);
        topicRepository.insertTopic(topic);

        Topic returnTopic = new Topic();
        if (topic.getTopicId() == null) {
            createMainTopic(topic, files);
        } else if (topic.getParentId() == null) {
            returnTopic = createReply(topic, files);
        } else {
            returnTopic = createComment(topic);
        }
        return returnTopic;
    }

    //回帖中评论
    private Topic createComment(Topic topic) {

        //获取原帖
        Topic originTopic = getOriginTopic(topic);
        if (originTopic == null) {
            throw new EveException(ErrorCN.Topic.TOPIC_NOT_FOUND);
        }
        //更新回帖数
        topicRepository.updateReplyNum(topic.getTopicId());

        //获取原帖用户
        AccountInfo originTopicAccount = topicRepository.queryAccountByTopic(new TopicQuery(topic.getTopicId())).get(0);
        //回帖用户加5分
        accountInfoService.updateExp(topic.getAccountId(), topicProperties.getReplyExp());
        //被回帖，原贴用户加2分
        accountInfoService.updateExp(originTopicAccount.getAccountId(), topicProperties.getBeRepliedExp());

        Map<Integer, AccountInfo> noticeAccountMap = new HashMap<>();

        //判断原帖的用户是不是发帖的用户，如果是则不加入通知队列中
        if (!Objects.equals(originTopicAccount.getAccountId(), topic.getAccountId())) {
            noticeAccountMap.put(originTopicAccount.getAccountId(), originTopicAccount);
        }
        //如果是评论回帖（即楼中楼），读取该楼层所有评论的用户，用于消息通知
        setReplyNoticeMap(topic, noticeAccountMap);

        sendNotification(topic, (List<AccountInfo>) noticeAccountMap.values(), originTopic, TopicNotification.Action.Comment);
        return topicRepository.queryTopics(new TopicQuery(topic.getParentId())).get(0);
    }

    //回帖
    private Topic createReply(Topic topic, MultipartFile[] files) {

        List<TopicImage> imageList = saveImages(files, topic.getTopicId());
        setImage(imageList, topic);
        //获取原帖
        Topic originTopic = getOriginTopic(topic);
        if (originTopic == null) {
            throw new EveException(ErrorCN.Topic.TOPIC_NOT_FOUND);
        }
        //更新回帖数
        topicRepository.updateReplyNum(originTopic.getTopicId());

        //获取原帖用户
        AccountInfo originTopicAccount = topicRepository.queryAccountByTopic(new TopicQuery(topic.getTopicId())).get(0);
        //回帖用户加5分
        accountInfoService.updateExp(topic.getAccountId(), topicProperties.getReplyExp());
        //被回帖，原贴用户加2分
        accountInfoService.updateExp(originTopicAccount.getAccountId(), topicProperties.getBeRepliedExp());

        List<AccountInfo> noticeAccounts = Lists.newArrayList();
        //如果回帖用户不是主贴用户，加入通知队列中
        if (!Objects.equals(originTopicAccount.getAccountId(), topic.getAccountId())) {
            noticeAccounts.add(originTopicAccount);
        }

        sendNotification(topic, noticeAccounts, originTopic, TopicNotification.Action.Comment);
        return topicRepository.queryTopics(new TopicQuery(topic.getId())).get(0);
    }

    //发帖
    private void createMainTopic(Topic topic, MultipartFile[] files) {
        List<TopicImage> imageList = saveImages(files, topic.getTopicId());
        setImage(imageList, topic);
        accountInfoService.updateExp(topic.getAccountId(), topicProperties.getCreateExp());

    }

    @Async
    private void sendNotification(Topic topic, List<AccountInfo> noticeAccounts, Topic originTopic, TopicNotification.Action action) {
        if (noticeAccounts.isEmpty()) return;

        TopicNotification notification = TopicNotification.builder()
                .accountInfo(accountInfoRepository.queryOneInfo(topic.getAccountId()))
                .originTopic(originTopic)
                .topic(topic)
                .action(action)
                .title(topicProperties.getNoticeTitle())
                .isMyTopic(true)
                .build();
        EasemobRequest easemobRequest = new EasemobRequest();
        easemobRequest.setFrom(ConstantKeys.EVE_PLATFORM_CN);
        easemobRequest.setTarget(getEasemobUserIds(noticeAccounts));
        easemobRequest.setTarget_type(EasemobRequest.TargetType.users);
        easemobRequest.setMsg(new EasemobRequest.Msg(buildMsg(action, topic)));
        easemobRequest.setExt(new HashMap<String, String>() {{
            put("type", EasemobType.Topic.name());
            put("json", JSON.toJSONString(notification));
        }});
        easemobService.sendMessages(easemobRequest);
    }

    private String buildMsg(TopicNotification.Action action, Topic topic) {
        String msg;
        if (TopicNotification.Action.Comment.equals(action)) {
            msg = topic.getAccountId() + ":" + topic.getContent();
        } else {
            msg = topic.getAccountId() + topicProperties.getLikeNotificationMsg();
        }
        return msg;
    }

    /**
     * 获取该楼层的所有用户（包括楼主，不包括自己）
     */
    private void setReplyNoticeMap(Topic topic, Map<Integer, AccountInfo> map) {
        //获取该楼层下所有回帖用户
        TopicQuery query = new TopicQuery();
        query.setAccountId(topic.getAccountId());
        query.setParentId(topic.getParentId());
        topicRepository.queryAccountByTopic(query)
                .forEach(accountInfo -> map.put(accountInfo.getAccountId(), accountInfo));
        //获取楼层的层主
        TopicQuery replyQuery = new TopicQuery();
        replyQuery.setTopicId(topic.getParentId());
        replyQuery.setAccountId(topic.getAccountId());
        topicRepository.queryAccountByTopic(replyQuery)
                .forEach(accountInfo -> map.put(accountInfo.getAccountId(), accountInfo));
    }

    private List<TopicImage> saveImages(MultipartFile[] files, Integer topicId) {
        List<TopicImage> imageList = Lists.newArrayList();
        if (files != null && files.length > 0) {
            imageList = ImageBaseUtil.uploadTopicImages(files, topicProperties
                    .getTopicCreateUrlPrefix(), topicProperties.getTopicReadUrlPrefix());

            imageList.forEach(topicImage -> topicImage.setTopicId(topicId));
            topicImageRepository.insert(imageList);
        }
        return imageList;
    }

    private void checkTopicContentLength(Topic topic) {
        if (topic.getContent().length() > topicProperties.getMaxContentSize()) {
            throw new EveException(ErrorCN.Topic.TEXT_SIZE_LIMIT);
        }
    }

    /**
     * 更新主题帖子数
     */
    private void updateTheme(Topic topic) {
        if (topic.getTheme().getId() != null) {
            topicThemeRepository.updateTopicCount(topic.getTheme().getId());
        }
    }

    private Topic getOriginTopic(Topic topic) {
        TopicQuery query = new TopicQuery();
        query.setId(topic.getTopicId());
        query.setAccountId(topic.getAccountId());
        List<Topic> originTopics = topicRepository.queryTopics(query);
        if (originTopics.isEmpty()) {
            return null;
        }
        Topic originTopic = originTopics.get(0);
        //获取回复所在的定位
        originTopic.setPosition(getPosition(topic));
        return originTopic;
    }

    /**
     * 获取贴子的位置
     */
    private Integer getPosition(Topic topic) {
        TopicQuery query = new TopicQuery();
        query.setTopicId(topic.getTopicId());
        query.setAccountId(topic.getAccountId());
        //获取定位
        if (topic.getParentId() == null) {
            query.setId(topic.getId());
        } else {
            query.setId(topic.getParentId());
        }
        return topicRepository.queryPosition(query);
    }

    private List<String> getEasemobUserIds(List<AccountInfo> accountHashIds) {
        return accountHashIds.stream()
                .map(info -> {
                    String easemobUserName = null;
                    try {
                        easemobUserName = go4BaseUtil.getAccountDetailByHashId(info.getHashId()).getEasemob().getUsername();
                    } catch (Exception ex) {
                        LOGGER.error(ex.getMessage(), ex);
                    }
                    return easemobUserName;
                })
                .filter(StringUtils::isEmpty)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<Topic> readTimelines(TopicQuery query) {

        Integer count = count(query);
        PageResult<Topic> pageResult = new PageResult<>(count, query.getPage(), query.getSize());
        if (count > 0) {
            List<Topic> topics = topicRepository.queryMyTopic(query);
            setOriginTopic(topics);
            setImages(topics);
            pageResult.setList(topics);
        }
        return pageResult;
    }

    @Override
    public void delete(TopicQuery query) {
        Integer topicId = query.getTopicId();
        Integer accountId = query.getAccountId();
        LOGGER.info("delete topic ,info {}", JSON.toJSONString(query));
        TopicQuery newQuery = new TopicQuery(topicId);
        Topic topic = topicRepository.queryTopics(newQuery).get(0);
        if (accountId != null && !Objects.equals(accountId, topic.getAccountId())) return;
        int updateExp;
        if (topic.getTopicId() != null) {
            updateExp = topicProperties.getReplyExp();
            newQuery.setId(topic.getId());
            if (topic.getParentId() == null) {
                Integer countDelete = topicRepository.countComment(topic.getId()) + 1;
                newQuery.setReplyUpdateNum(countDelete * (-1));
            } else {
                newQuery.setReplyUpdateNum(-1);
            }
            newQuery.setId(topic.getTopicId());
            topicRepository.updateTopic(newQuery);
        } else {
            updateExp = topicProperties.getCreateExp() + topic.getReplyCount() * topicProperties.getBeLikedExp();
        }
        if (accountId != null) {
            LOGGER.info("exp updateBuyCount", updateExp * -1);
            accountInfoService.updateExp(accountId, updateExp * -1);
        }
        topicRepository.delete(topic.getId());
    }

    // TODO: 2/23/2017 讨论区后台功能
    @Override
    public void setTop(Integer topicId) {
        TopicQuery query = new TopicQuery(topicId);
        Topic topic = topicRepository.queryTopics(query).get(0);
        query.setIsPinTop(!topic.getIsPinTop());
        topicRepository.updateTopic(query);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Topic> search(TopicQuery query) {
        List<Topic> search = topicRepository.queryTopics(query);
        if (search == null || search.isEmpty()) {
            return Collections.emptyList();
        }
        setImages(search);
        return search;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Pager countSearch(TopicQuery query) {
        Pager pager = new Pager();
        Integer count = topicRepository.countSearch(query);
        pager.setCount(count);
        if (query.getPage() == null) {
            pager.setPage(1);
        } else {
            pager.setPage(query.getPage());
        }
        pager.setSize(query.getSize());
        return pager;
    }

    @Override
    public List<AccountInfo> searchAccount(TopicQuery query) {
        query.setSize(20);
        return topicRepository.queryAccountByTopic(query);
    }

    @Override
    public void setStamp(TopicQuery query) {
        topicRepository.updateTopic(query);
    }

    @Override
    public PageResult<TopicTheme> readAllThemes(TopicQuery query) {
        return new PageResult<>(topicThemeRepository.queryAll(query),
                new Pager(topicThemeRepository.count(), query.getPage(), query.getSize()));
    }

    @Override
    public void addTheme(String theme) {
        try {
            topicThemeRepository.insert(theme);
        } catch (Exception e) {
            throw new EveException(ErrorCN.Topic.THEME_EXISTED);
        }
    }

    @Override
    public void createLiveTopic(Topic topic, MultipartFile[] files, String accountHashId) {

        //根据用户hashId去获取用户的id
        AccountInfo info;
        try {
            info = go4BaseUtil.getAccountDetailByHashId(accountHashId);
        } catch (Exception ex) {
            throw new EveException(ex.getMessage());
        }
        AccountInfo accountInfo = accountInfoService.readOrCreate(info);
        topic.setAccountId(accountInfo.getAccountId());

        if (isCreatedIn10s(topic)) {
            LOGGER.warn("topic created in 10s. {}", JSON.toJSONString(topic, true));
            return;
        }
        topic.setIsLive(Boolean.TRUE);
        topic.setLiveStatus(Topic.LiveStatus.Living);
        codeRepository.setCode(Constants.EMOJI_CODE);
        if (topic.getContent().length() > topicProperties.getMaxContentSize()) {
            throw new EveException(ErrorCN.Topic.TEXT_SIZE_LIMIT);
        }
        if (topic.getTheme() == null) {
            topic.setTheme(new TopicTheme());
        }
        if (topic.getTheme().getId() != null) {
            topicThemeRepository.updateTopicCount(topic.getTheme().getId());
        }
        topicRepository.insertTopic(topic);

        List<TopicImage> imageList = new LinkedList<>();
        if (files != null && files.length > 0) {
            Integer newTopicId = topicRepository.queryId(topic);
            List<TopicImage> imageUrlList = ImageBaseUtil.uploadTopicImages(files, topicProperties
                    .getTopicCreateUrlPrefix(), topicProperties.getTopicReadUrlPrefix());
            for (TopicImage topicImage : imageUrlList) {
                topicImage.setTopicId(newTopicId);
                imageList.add(topicImage);
            }
            topicImageRepository.insert(imageList);
        }

    }

    private void setLive(List<Topic> topics) {
        topics.stream()
                .filter(Topic::getIsLive)
                .forEach(topic -> {
                    try {
                        AccountInfo info = go4BaseUtil.getAccountDetailByHashId(topic.getAccountHashId());
                        topic.setCsDisplayName(info.getNickname());
                    } catch (Exception ex) {
                        LOGGER.error(ex.getMessage(), ex);
                    }

                });
    }

    @Override
    public void updateLiveStatus(Integer topicId) {
        TopicQuery query = new TopicQuery(topicId);

        List<Topic> topics = topicRepository.queryTopics(query);
        if (topics == null || topics.isEmpty()) {
            LOGGER.error("topic not exits,topic id :{}", topicId);
            return;
        }
        if (!topics.get(0).getIsLive()) {
            LOGGER.error("topic is not a live topic,topic id :{}", topicId);
            return;
        }
        Topic topic = topics.get(0);

        if (Topic.LiveStatus.Wait.equals(topic.getLiveStatus())) {
            query.setLiveStatus(Topic.LiveStatus.Living);
        } else if (Topic.LiveStatus.Living.equals(topic.getLiveStatus())) {
            query.setLiveStatus(Topic.LiveStatus.End);
        } else {
            return;
        }
        topicRepository.updateTopic(query);
    }

}
