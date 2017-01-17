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
import dayan.common.util.MD5Util;
import dayan.easemob.entity.EasemobRequest;
import dayan.eve.exception.ErrorCN;
import dayan.eve.model.PageResult;
import dayan.eve.model.Pager;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.easemob.EasemobType;
import dayan.eve.model.query.TopicQuery;
import dayan.eve.model.topic.Topic;
import dayan.eve.model.topic.TopicImage;
import dayan.eve.model.topic.TopicNotification;
import dayan.eve.model.topic.TopicTheme;
import dayan.eve.repository.AccountInfoRepository;
import dayan.eve.repository.AccountRepository;
import dayan.eve.repository.CodeRepository;
import dayan.eve.repository.topic.TopicRepository;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class TopicServiceImpl implements TopicService {

    private static final Logger LOGGER = LogManager.getLogger(TopicServiceImpl.class);

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    CodeRepository codeRepository;

    @Autowired
    TopicLikeRepository topicLikeRepository;

    @Autowired
    TopicDislikeRepository topicDislikeRepository;

    @Autowired
    TopicImageRepository topicImageRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountInfoRepository accountInfoRepository;

    @Autowired
    AccountInfoService accountInfoService;

    @Autowired
    EasemobService easemobService;

    @Autowired
    TopicThemeRepository topicThemeRepository;

    @Autowired
    Go4BaseUtil go4BaseUtil;

    @Value("${image.topic.create.url.prefix}")
    String topicImageUrlCreatePrefix;

    @Value("${image.topic.read.url.prefix}")
    String topicImageUrlReadPrefix;

    private PassiveExpiringMap topicCache = new PassiveExpiringMap(10000);

    private boolean isCreatedIn10s(Topic topic) {
        String key = MD5Util.StringMD5(topic.getAccountId() + "-" + topic.getContent());
        if (topicCache.containsKey(key)) {
            return true;
        } else {
            topicCache.put(key, topic);
            return false;
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Pager count(TopicQuery query) {
        Pager pager = new Pager();
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
        pager.setCount(count);
        if (query.getPage() == null) {
            pager.setPage(1);
        } else {
            pager.setPage(query.getPage());
        }
        pager.setSize(query.getSize());
        return pager;
    }

    private void setImages(List<Topic> topics) {
        for (Topic topic : topics) {
            List<TopicImage> images = topicImageRepository.query(topic.getId());
            setImage(images, topic);//设置贴子的图片
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Topic> readTopics(TopicQuery query) {
        List<Topic> topics = topicRepository.queryTopics(query);

        //配置直播贴
        setLive(topics);
//        if (query.getTopicId() != null)
//            setComments(topics);
        return topics;
    }

    private static final String LIKE_MSG = "给你点了个赞";

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void like(TopicQuery query) {
        LOGGER.info("topic like ,info {}", JSON.toJSONString(query, true));
        Integer topicId = query.getTopicId();
        List<TopicQuery> likes = topicLikeRepository.query(query);
        if (likes != null && !likes.isEmpty()) {
            return;
        }
        topicLikeRepository.insert(query);
        TopicQuery newQuery = new TopicQuery();
        newQuery.setLikeUpdateNum(1);
        newQuery.setId(query.getTopicId());
        topicRepository.updateTopic(newQuery);

        TopicNotification notification = new TopicNotification();
        Integer accountId = query.getAccountId();//点赞用户id
        newQuery = new TopicQuery();
        newQuery.setTopicId(topicId);
        List<AccountInfo> originAccounts = topicRepository.queryAccountByTopic(newQuery);//主贴用户
        if (originAccounts == null || originAccounts.isEmpty()) {
            return;
        } else {
            //被赞加2分
            accountInfoService.updateExp(originAccounts.get(0).getAccountId(), LIKE_EXP);
            //如果被点赞的帖子是自己的贴，则不通知，返回
            if (Objects.equals(originAccounts.get(0).getAccountId(), accountId)) {
                return;
            }
        }
        List<String> easemobUserIds = getEasemobUserIds(originAccounts);
        if (easemobUserIds == null || easemobUserIds.isEmpty()) {
            return;
        }
        AccountInfo accountInfo = accountInfoRepository.queryOneInfo(accountId);
        query = new TopicQuery();
        query.setId(topicId);
        List<Topic> topics = topicRepository.queryTopics(query);
        if (topics == null || topics.isEmpty()) {
            return;
        }
        Topic topic = topics.get(0);
        List<TopicImage> images = topicImageRepository.query(topicId);
        setImage(images, topic);//设置贴子的图片
        notification.setTopic(topic);
        Topic originTopic;
        if (topic.getTopicId() != null) {
            originTopic = getOriginTopic(topic.getTopicId());
            if (originTopic == null) {
                return;
            }
            setTopicLiked(originTopic, originTopic.getAccountId());
            originTopic.setPosition(getPosition(topic));
        } else {
            originTopic = topic;
            notification.setTopic(null);
        }

        notification.setAccountInfo(accountInfo);
        notification.setOriginTopic(originTopic);
        notification.setAction(TopicNotification.Action.Like);
        notification.setTime(new Date());

        sendMessage(notification, easemobUserIds, accountInfo.getNickname() + LIKE_MSG);
        LOGGER.info("a user likes a topic!");
    }

    @Transactional
    @Override
    public void dislike(TopicQuery query) {
        LOGGER.info("topic dislike ,info {}", JSON.toJSONString(query, true));
        topicDislikeRepository.insert(query);
        TopicQuery newQuery = new TopicQuery();
        newQuery.setDislikeUpdateNum(1);
        newQuery.setId(query.getTopicId());
        topicRepository.updateTopic(newQuery);
    }

    /**
     * 设置贴子的图片（缩略图、中图和大图）
     */
    private void setImage(List<TopicImage> images, Topic topic) {
        if (images == null || images.isEmpty()) {
            topic.setThumbnailUrls(Collections.emptyList());
            topic.setImageUrls(Collections.emptyList());
            topic.setMiddleImageUrls(Collections.emptyList());
            return;
        }
        List<String> imageUrls = new LinkedList<>();
        List<String> thumbnailUrls = new LinkedList<>();
        List<String> middleImageUrls = new LinkedList<>();
        for (TopicImage image : images) {
            imageUrls.add(image.getImageUrl());
            thumbnailUrls.add(image.getThumbnailUrl());
            middleImageUrls.add(image.getMiddleImageUrl());

        }
        topic.setMiddleImageUrls(middleImageUrls);
        topic.setImageUrls(imageUrls);
        topic.setThumbnailUrls(thumbnailUrls);
    }

    /*
     *设置原帖
     */
    private void setOriginTopic(List<Topic> topics) {
        for (Topic topic : topics) {
            if (topic.getTopicId() == null) {
                continue;
            }
            if (topic.getOriginTopic() != null) {
                //判断原帖是否已经删除,是则将原帖设为null
                if (topic.getOriginTopic().getIsDeleted()) {
                    continue;
                }
                List<TopicImage> images = topicImageRepository.query(topic.getOriginTopic().getId());
                setImage(images, topic.getOriginTopic());
            }
        }
    }

    //    public static final String REPLY_MSG = "有用户回复了你的贴";
    private static final Integer REPLY_EXP = 5;//回帖经验
    private static final Integer TOPIC_EXP = 10;//发帖经验
    private static final Integer LIKE_EXP = 2;//被点赞或被回帖经验
    private static final Integer MAX_TEXT_SIZE = 1024;//最大内容字数

    @Override
    @Transactional
    public Topic createTopic(Topic topic, MultipartFile[] files) {
        if (isCreatedIn10s(topic)) {
            LOGGER.warn("topic created in 10s. {}", JSON.toJSONString(topic, true));
            return null;
        }
        codeRepository.setCode();
        if (topic.getContent().length() > MAX_TEXT_SIZE) {
            throw new RuntimeException(ErrorCN.Topic.TEXT_SIZE_LIMIT);
        }
        if (topic.getTheme() == null) {
            topic.setTheme(new TopicTheme());
        }
        if (topic.getTheme().getId() != null) {
            topicThemeRepository.updateTopicCount(topic.getTheme().getId());
        }
        topicRepository.insertTopic(topic);

        //批量插入图片
        List<TopicImage> imageList = new LinkedList<>();
        if (files != null && files.length > 0) {
            List<TopicImage> imageUrlList = ImageBaseUtil.uploadTopicImages(files, topicImageUrlCreatePrefix, topicImageUrlReadPrefix);
            for (TopicImage topicImage : imageUrlList) {
                topicImage.setTopicId(topic.getId());
                imageList.add(topicImage);
            }
            topicImageRepository.insert(imageList);
            setImage(imageList, topic);
        }

        LOGGER.info("a topic create or reply.  {}", JSON.toJSONString(topic, true));

        Integer accountId = topic.getAccountId();//发帖或回帖的用户id

        Integer topicId = topic.getTopicId();//主贴id
        Topic returnTopic = new Topic();
        //发帖加10分，回帖加5分
        if (topicId == null) {
            accountInfoService.updateExp(accountId, TOPIC_EXP);
            return returnTopic;
        }
        //更新回帖数
        TopicQuery replyUpdate = new TopicQuery(topicId);
        replyUpdate.setReplyUpdateNum(1);
        topicRepository.updateTopic(replyUpdate);

        TopicQuery query = new TopicQuery();
        query.setTopicId(topicId);

        List<AccountInfo> originAccountList = topicRepository.queryAccountByTopic(query);//原帖用户

        //回帖用户加5分
        accountInfoService.updateExp(accountId, REPLY_EXP);
        //被回帖，主贴用户加2分
        accountInfoService.updateExp(originAccountList.get(0).getAccountId(), LIKE_EXP);

        //下面部分为消息推送
        TopicNotification notification = new TopicNotification();

        List<AccountInfo> noticeAccounts;//要被消息通知的用户
        Map<Integer, AccountInfo> noticeAccountMap = new HashMap<>();//用户存用户id和信息
        Integer parentId = topic.getParentId();

        //如果是评论回帖（即楼中楼），读取该楼层所有评论的用户，用于消息通知
        if (parentId != null) {
            query.setAccountId(accountId);
            query.setParentId(parentId);
            query.setTopicId(null);
            noticeAccounts = topicRepository.queryAccountByTopic(query);
            if (noticeAccounts == null || noticeAccounts.isEmpty()) {
                noticeAccounts = new LinkedList<>();
            }
            listConvertToMap(noticeAccounts, noticeAccountMap);
            query.setTopicId(parentId);
            query.setParentId(null);

            //获取该回帖的用户,加入被通知用户队列中
            List<AccountInfo> replyAccounts = topicRepository.queryAccountByTopic(query);
            if (replyAccounts != null && !replyAccounts.isEmpty()) {
                noticeAccountMap.put(replyAccounts.get(0).getAccountId(), replyAccounts.get(0));
            }

            //获取该回帖并设置属性
            TopicQuery newQuery = new TopicQuery();
            newQuery.setId(parentId);
            returnTopic = topicRepository.queryTopics(newQuery).get(0);
            //设置贴子的图片
            setImage(topicImageRepository.query(parentId), returnTopic);
            //设置楼中楼帖子
            returnTopic.setComments(topicRepository.queryComments(parentId));
        } else {
            TopicQuery newQuery = new TopicQuery(topic.getId());
            returnTopic = topicRepository.queryTopics(newQuery).get(0);
            setImage(imageList, returnTopic);//设置贴子的图片
            returnTopic.setComments(Collections.emptyList());
        }
        //判断原帖的用户是不是发帖的用户，如果是则不加入通知队列中
        if (!Objects.equals(originAccountList.get(0).getAccountId(), accountId)) {
            noticeAccountMap.put(originAccountList.get(0).getAccountId(), originAccountList.get(0));
        }
        noticeAccounts = new ArrayList<>(noticeAccountMap.values());

        if (noticeAccounts.isEmpty()) {
            return returnTopic;
        }
        List<String> easemobUserIds = getEasemobUserIds(noticeAccounts);
        AccountInfo accountInfo = accountInfoRepository.queryOneInfo(topic.getAccountId());

        //获取原帖
        Topic originTopic = getOriginTopic(topicId);
        if (originTopic == null) {
            throw new RuntimeException(ErrorCN.Topic.TOPIC_NOT_FOUND);

        }

        //设置是否喜欢
        setTopicLiked(originTopic, accountId);
        //获取定位
        originTopic.setPosition(getPosition(topic));

        notification.setAccountInfo(accountInfo);
        notification.setOriginTopic(originTopic);
        notification.setTopic(topic);
        notification.setAction(TopicNotification.Action.Comment);
        notification.setTime(new Date());

        sendMessage(notification, easemobUserIds, accountInfo.getNickname() + ":" + topic.getContent());
        LOGGER.info("a topic create or reply,then send SMS topicLikeRepository users!");
        return returnTopic;
    }

    private Topic getOriginTopic(Integer topicId) {
        TopicQuery query = new TopicQuery();
        query.setId(topicId);

        //获取原帖
        List<Topic> originTopics = topicRepository.queryTopics(query);
        if (originTopics == null || originTopics.isEmpty()) {
            return null;
        }
        Topic originTopic = originTopics.get(0);
        List<TopicImage> images = topicImageRepository.query(topicId);
        setImage(images, originTopic);//设置贴子的图片
        return originTopic;
    }

    private void setTopicLiked(Topic topic, Integer accountId) {
        TopicQuery query = new TopicQuery();
        query.setTopicId(topic.getId());
        query.setAccountId(accountId);
        List<TopicQuery> likes = topicLikeRepository.query(query);
        if (likes != null && !likes.isEmpty()) {
            topic.setIsLiked(true);
        }
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

    //消息推送
    private void sendMessage(TopicNotification notification, List<String> easemobUserIds, String msgContent) {
        EasemobRequest easemobRequest = new EasemobRequest();
        String FROM = "小言高考";
        easemobRequest.setFrom(FROM);
        easemobRequest.setTarget(easemobUserIds);
        easemobRequest.setTarget_type(EasemobRequest.TargetType.users);
        easemobRequest.setMsg(new EasemobRequest.Msg(msgContent));
        Map<String, String> ext = new HashMap<>();
        ext.put("type", EasemobType.Topic.name());
        ext.put("json", JSON.toJSONString(notification));
        easemobRequest.setExt(ext);
        LOGGER.info("notification info ,{}", JSON.toJSONString(notification, true));
        easemobService.sendMessages(easemobRequest);
    }

    private List<String> getEasemobUserIds(List<AccountInfo> accountHashIds) {
        List<String> easemobUserIds = new LinkedList<>();
        for (AccountInfo info : accountHashIds) {
            String easemobUserName;
            try {
                easemobUserName = go4BaseUtil.getAccountDetailByHashId(info.getHashId()).getEasemob().getUsername();
                if (StringUtils.isEmpty(easemobUserName)) {
                    LOGGER.info("get easemob username failed,hashId,{}", info);
                    continue;
                }
            } catch (Exception ex) {
                LOGGER.info("get detail failed,hashId,{}", info);
                LOGGER.info("get detail failed,exception,{}", ex.getMessage());
                continue;
            }
            easemobUserIds.add(easemobUserName);
        }
        return easemobUserIds;
    }

    @Override
    public List<Topic> readTimelines(TopicQuery query) {
        List<Topic> myTopics = topicRepository.queryMyTopic(query);
        setOriginTopic(myTopics);
        setImages(myTopics);
        return myTopics;
    }

    @Override
    public void delete(TopicQuery query) {
        Integer topicId = query.getTopicId();
        Integer accountId = query.getAccountId();
        LOGGER.info("delete topic ,info {}", JSON.toJSONString(query));
        TopicQuery newQuery = new TopicQuery();
        newQuery.setId(topicId);
        Topic topic = topicRepository.queryTopics(newQuery).get(0);
        if (accountId != null && !Objects.equals(accountId, topic.getAccountId())) return;
        int updateExp;
        if (topic.getTopicId() != null) {
            updateExp = REPLY_EXP;
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
            updateExp = TOPIC_EXP + topic.getReplyCount() * LIKE_EXP;
        }
        if (accountId != null) {
            LOGGER.info("exp update", updateExp * -1);
            accountInfoService.updateExp(accountId, updateExp * -1);
        }
        topicRepository.delete(topic.getId());
    }

    @Override
    public void setTop(Integer topicId) {
        TopicQuery query = new TopicQuery();
        query.setId(topicId);
        Topic topic = topicRepository.queryTopics(query).get(0);
        query.setPinTop(!topic.getIsPinTop());
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

    private void listConvertToMap(List<AccountInfo> noticeAccountList, Map<Integer, AccountInfo> noticeAccountMap) {
        if (noticeAccountList.isEmpty()) {
            return;
        }
        for (AccountInfo ai : noticeAccountList) {
            noticeAccountMap.put(ai.getAccountId(), ai);
        }
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
            throw new RuntimeException(ErrorCN.Topic.THEME_EXISTED);
        }
    }

    @Override
    public void createLiveTopic(Topic topic, MultipartFile[] files, String accountHashId) {

        //根据用户hashId去获取用户的id
        AccountInfo info;
        try {
            info = go4BaseUtil.getAccountDetailByHashId(accountHashId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        AccountInfo accountInfo = accountInfoService.readOrCreate(info);
        topic.setAccountId(accountInfo.getAccountId());

        if (isCreatedIn10s(topic)) {
            LOGGER.warn("topic created in 10s. {}", JSON.toJSONString(topic, true));
            return;
        }
        topic.setIsLive(Boolean.TRUE);
        topic.setLiveStatus(Topic.LiveStatus.Living);
        codeRepository.setCode();
        if (topic.getContent().length() > MAX_TEXT_SIZE) {
            throw new RuntimeException(ErrorCN.Topic.TEXT_SIZE_LIMIT);
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
            List<TopicImage> imageUrlList = ImageBaseUtil.uploadTopicImages(files, topicImageUrlCreatePrefix, topicImageUrlReadPrefix);
            for (TopicImage topicImage : imageUrlList) {
                topicImage.setTopicId(newTopicId);
                imageList.add(topicImage);
            }
            topicImageRepository.insert(imageList);
        }

    }

    private void setLive(List<Topic> topics) {
        for (Topic topic : topics) {
            if (!topic.getIsLive()) {
                continue;
            }
            AccountInfo info;
            try {
                info = go4BaseUtil.getAccountDetailByHashId(topic.getAccountHashId());
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage());
                LOGGER.error("getAccountDetailByHashId failed ,hashId : {},accountId:{}" + topic.getAccountHashId(), topic.getAccountId());
                continue;
            }
            topic.setCsDisplayName(info.getNickname());
        }

    }

    @Override
    public void updateLiveStatus(Integer topicId) {
        TopicQuery query = new TopicQuery();
        query.setId(topicId);

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
