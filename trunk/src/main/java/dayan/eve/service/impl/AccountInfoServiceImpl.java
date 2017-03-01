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
import dayan.eve.exception.ErrorCN;
import dayan.eve.model.Pager;
import dayan.eve.model.account.Account;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.easemob.Easemob;
import dayan.eve.model.query.AccountInfoQuery;
import dayan.eve.model.query.AccountQuery;
import dayan.eve.model.topic.Topic;
import dayan.eve.repository.AccountInfoRepository;
import dayan.eve.repository.AccountRepository;
import dayan.eve.repository.topic.TopicRepository;
import dayan.eve.service.AccountInfoService;
import dayan.eve.service.VisitorService;
import dayan.eve.util.ExpUtil;
import dayan.eve.util.Go4BaseUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AccountInfoServiceImpl implements AccountInfoService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    AccountInfoRepository accountInfoRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    VisitorService visitorService;

    @Autowired
    Go4BaseUtil go4BaseUtil;

    @Autowired
    TopicRepository topicRepository;

    @Override
    public void createInfo(Account account) {
        AccountInfo info = new AccountInfo();
        info.setAccountId(account.getId());
        info.setMobile(account.getMobile());
        info.setPortraitUrl(account.getAvatarURL());
        accountInfoRepository.insertInfo(info);
    }

    @Override
    public AccountInfo readInfo(Integer accountId) {
        AccountQuery query = new AccountQuery();
        query.setId(accountId);
        List<AccountInfo> list = accountInfoRepository.queryInfo(query);
        if (list == null || list.isEmpty()) {
            return new AccountInfo();
        }
        AccountInfo accountInfo = list.get(0);

        // TODO: 1/17/2017 account info easemob
        try {
            accountInfo.setEasemob(go4BaseUtil.getAccountDetailByHashId(accountInfo.getHashId()).getEasemob());
        } catch (Exception ex) {
            LOGGER.info("get go4 detail failed,{}", ex.getMessage());
        }
        setExp(accountInfo);
        return accountInfo;
    }

    @Override
    public AccountInfo readInfo(String accountHashId) {
        AccountQuery query = new AccountQuery();
        query.setAccountHashId(accountHashId);
        List<AccountInfo> list = accountInfoRepository.queryInfo(query);
        AccountInfo accountInfo = list.get(0);
        AccountInfo result = new AccountInfo();
        result.setMobile(accountInfo.getMobile());
        result.setSubjectType(accountInfo.getSubjectType());
        result.setProvince(accountInfo.getProvince());
        result.setScore(accountInfo.getScore());
        result.setNickname(accountInfo.getNickname());
        result.setAccountId(accountInfo.getAccountId());
        result.setHashId(accountHashId);
        result.setPluginKeys(null);
        return result;
    }

    @Override
    public AccountInfo readAccountByEasemob(String easemobUsername) {
        AccountInfo accountInfo = null;
        try {
            accountInfo = go4BaseUtil.getDetailByEasemob(easemobUsername);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        if (accountInfo == null) {
            LOGGER.error("no user in go4,easemob username:{}", easemobUsername);
            throw new RuntimeException(ErrorCN.Login.USER_NOT_FOUND);
        }
        AccountInfo info = readOrCreate(accountInfo);
        info.setEasemob(accountInfo.getEasemob());
        setExp(info);
        return info;
    }

    @Override
    public List<AccountInfo> readAccountListByEasemob(List<String> easemobUserNames) {
        List<AccountInfo> result = new LinkedList<>();
        try {
            go4BaseUtil.getDetailByEasemob(easemobUserNames).forEach(info -> {
                Easemob easemob = info.getEasemob();
                info = readOrCreate(info);
                info.setEasemob(easemob);
                setExp(info);
                result.add(info);
            });
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return result;
    }

    public Pager setPager(AccountQuery query) {
        Pager pager = new Pager();
        Integer count = accountInfoRepository.count(query);
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
    public void updateInfo(AccountInfo accountInfo) throws Exception {

        LOGGER.warn("AccountInfo updateBuyCount. info: {}", JSON.toJSONString(accountInfo, true));
        if (!StringUtils.isEmpty(accountInfo.getMobile())) {
            Boolean isVerified = go4BaseUtil.checkVerificationCode(accountInfo.getMobile(), accountInfo.getCode());
            if (!isVerified) {
                throw new RuntimeException(ErrorCN.Login.AUTH_CODE_ERROR);
            }

        }
        accountInfoRepository.updateInfo(accountInfo);
    }

    @Override
    public void updateShared(Integer accountId) {
        accountInfoRepository.updateShared(accountId);
    }

    private void setExp(AccountInfo accountInfo) {
        List<Integer> expList = ExpUtil.getExpList();
        accountInfo.setLevelExp(expList.get(accountInfo.getLevel() - 1));
        accountInfo.setNextLevelExp(expList.get(accountInfo.getLevel()));
    }

    @Override
    public Integer updateExp(Integer accountId, Integer updateExp) {
        if (accountId == null) {
            return 0;
        }
        List<Integer> expList = ExpUtil.getExpList();
        AccountQuery query = new AccountQuery();
        query.setId(accountId);
        AccountInfo accountInfo = accountInfoRepository.queryInfo(query).get(0);
        accountInfo.setExp(accountInfo.getExp() + updateExp); //发帖加10分

        //加分则比较下一等级的最小经验，超过则加一级；减分则比较该等级的最小经验，小于则减一级
        if (accountInfo.getExp() >= expList.get(accountInfo.getLevel()) && updateExp > 0) {//与下一等级初始分比较
            accountInfo.setLevel(accountInfo.getLevel() + 1);
        } else if (accountInfo.getExp() < expList.get(accountInfo.getLevel() - 1) && updateExp < 0) {
            accountInfo.setLevel(accountInfo.getLevel() - 1);
        }

        accountInfoRepository.updateInfo(accountInfo);
        return updateExp;
    }


    @Override
    public void countExp() {
        List<Topic> topics = topicRepository.queryAllTopics();//取出所有贴
        Map<Integer, Integer> expMap = new HashMap<>();//用户对应积分map

        Integer[] levels = getLevelArray();
        for (Topic topic : topics) {
            Integer accountId = topic.getAccountId();
            if (!expMap.containsKey(accountId)) {
                expMap.put(accountId, 0);
            }
            Integer curExp = expMap.get(accountId);//当前积分
            Integer add;
            if (topic.getTopicId() == null) {
                add = topic.getReplyCount() * 2 + topic.getLikeNum() * 2 + 10;//一个主贴的加分数值=回帖数*2+点赞数*2+10
            } else {
                add = 5;//一个回复贴的加分数值=5
            }
            curExp += add;
            expMap.put(accountId, curExp);
        }
        for (Integer accountId : expMap.keySet()) {
            AccountInfo info = new AccountInfo();
            info.setAccountId(accountId);
            info.setExp(expMap.get(accountId));
            info.setLevel(levels[info.getExp()]);
            LOGGER.info("info" + accountId + "," + info.getExp());
            accountInfoRepository.updateInfo(info);
        }
    }

    private Integer[] getLevelArray() {
        List<Integer> expList = ExpUtil.getExpList();
        Integer maxLevel = 40;
        Integer maxExp = expList.get(maxLevel);
        Integer[] levelArray = new Integer[maxExp];
        for (int i = 1; i < maxLevel; i++) {
            for (int j = expList.get(i - 1); j < expList.get(i); j++) {
                levelArray[j] = i;
            }
        }
        return levelArray;
    }

    @Override
    public AccountInfo readOrCreate(AccountInfo accountInfo) {
        AccountQuery query = new AccountQuery();
        query.setAccountHashId(accountInfo.getHashId());
        List<AccountInfo> list = accountInfoRepository.queryInfo(query);
        if (list == null || list.isEmpty()) {
            accountRepository.insert(accountInfo);
            createInfo(accountInfo);
            accountInfo.setAccountId(accountInfo.getId());
            accountInfo.setPortraitUrl(accountInfo.getAvatarURL());
            accountInfo.setLevel(1);
        } else {
            accountInfo = list.get(0);
        }
        return accountInfo;
    }

    @Override
    public void updateFromGo4() {
        List<Account> accounts = accountRepository.query(new AccountQuery());
        AccountQuery query = new AccountQuery();
        for (Account account : accounts) {
            if (account.getHashId() != null) {
                AccountInfo info;
                try {
                    info = go4BaseUtil.getAccountDetailByHashId(account.getHashId());
                } catch (Exception ex) {
                    LOGGER.error(ex);
                    continue;
                }
                if (info.getExt() == null) {
                    continue;
                }
                System.out.println(info.getAccountId());
                AccountInfo newInfo = new AccountInfo();
                if (info.getExt().getSchoolHashId() != null || info.getExt().getPlatformId() != null) {
                    System.out.println(JSON.toJSONString(info, true));
                }
                query.setAccountHashId(info.getHashId());
                newInfo.setAccountId(accountRepository.query(query).get(0).getId());
                newInfo.setExt(JSON.toJSONString(info.getExt()));
                accountInfoRepository.updateInfo(newInfo);
            }
        }
    }

    @Override
    public List<AccountInfo> read(AccountInfoQuery query) {
        List<AccountInfo> infos = accountInfoRepository.query(query);
        return infos == null ? Collections.EMPTY_LIST : infos;
    }

    @Override
    public Pager count(AccountInfoQuery query) {
        Integer count = accountInfoRepository.count2(query);
        return new Pager(count, query.getPage(), query.getSize());
    }

}
