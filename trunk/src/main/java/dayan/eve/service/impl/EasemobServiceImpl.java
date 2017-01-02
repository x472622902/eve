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
package dayan.eve.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dayan.common.util.MD5Util;
import dayan.easemob.entity.EasemobRequest;
import dayan.easemob.jersey.api.EasemobIMUsers;
import dayan.easemob.jersey.api.EasemobMessages;
import dayan.eve.model.ConstantKeys;
import dayan.eve.model.account.Account;
import dayan.eve.model.easemob.EasemobResult;
import dayan.eve.service.EasemobService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leiky
 */
@Service
public class EasemobServiceImpl implements EasemobService {

    private static final Logger LOGGER = LogManager.getLogger(EasemobServiceImpl.class);

    private static final JsonNodeFactory FACTORY = new JsonNodeFactory(false);

    private final static String DEFAULT_FROM = "EveService";

    @Override
    public String token() {
        return EasemobIMUsers.getToken().toString();
    }

    @Override
    public ObjectNode registUsers(Account account) {
        List<Account> accounts = new ArrayList<>(1);
        accounts.add(account);

        return registUsers(accounts);
    }

    @Override
    public ObjectNode registUsers(List<Account> accounts) {
        ObjectNode objectNode = FACTORY.objectNode();

        int start = 0;
        int size = 30;

        // 批量分割注册
        while (accounts.size() > start) {
            ArrayNode dataArrayNode = FACTORY.arrayNode();

            for (int i = start; i < accounts.size() && i < start + size; i++) {
                Account account = accounts.get(i);
                ObjectNode userNode = FACTORY.objectNode();
                // username为id, password为 md5(hzdykj{id})
                userNode.put("username", account.getId());
                userNode.put("password", MD5Util.StringMD5("hzdykj" + account.getId()));
                userNode.put("nickname", account.getNickname());

                dataArrayNode.add(userNode);
            }

            objectNode = EasemobIMUsers.createNewIMUserBatch(dataArrayNode);

            start += size;
        }

        return handleResult(objectNode);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public ObjectNode sendMessage(String to, String message) {
        final String targetType = "users";
        final ArrayNode target = FACTORY.arrayNode();
        target.add(to);
        final ObjectNode msg = FACTORY.objectNode();
        msg.put("type", "txt");
        msg.put("msg", message);
        final String from = DEFAULT_FROM;
        final ObjectNode ext = FACTORY.objectNode();

        return handleResult(EasemobMessages.sendMessages(targetType, target, msg, from, ext));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public ObjectNode sendMessage(String userNumber, EasemobResult result) {
        final String targetType = "users";
        final ArrayNode target = FACTORY.arrayNode();
        target.add(userNumber);
        final ObjectNode msg = FACTORY.objectNode();
        msg.put("type", "txt");
        msg.put("msg", JSON.toJSONString(result));
        String from = result.getExt().get(ConstantKeys.Key.SchoolName);
        if (result.getExt().get(ConstantKeys.Key.UserName) != null) {
            from = result.getExt().get(ConstantKeys.Key.UserName);
        }
        final ObjectNode ext = FACTORY.objectNode();

        return handleResult(EasemobMessages.sendMessages(targetType, target, msg, from, ext));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public ObjectNode sendMessages(List<Integer> users, EasemobResult result) {
        final String targetType = "users";
        ArrayNode target = FACTORY.arrayNode();
        final ObjectNode msg = FACTORY.objectNode();
        msg.put("type", "txt");
        msg.put("msg", JSON.toJSONString(result));
        final String from = result.getExt().get(ConstantKeys.Key.SchoolName);
        final ObjectNode ext = FACTORY.objectNode();
        int count = 0;
        int size = users.size();
        while (true) {
            while (count < size) {
                target.add(users.get(count));
                count++;
                /*
                 每一百人推送一次，遍历循环所有用户后退出
                 */
                if (count % 100 == 0) {
                    break;
                }
            }
            /*
             每秒推送如果超过20次，停1秒
             */
            if (count % 2000 == 0) {
                try {
                    long start = System.currentTimeMillis();
                    Thread.sleep(1000);
                    LOGGER.info("sleep time ,info: {}", (System.currentTimeMillis() - start));
                } catch (InterruptedException ex) {
                    LOGGER.error(ex);
                }
            }
            EasemobMessages.sendMessages(targetType, target, msg, from, ext);
            target.removeAll();
            LOGGER.info("count of information send,info: {}", count);
            if (count == size) {
                LOGGER.info("information send is over");
                break;
            }
        }

        return handleResult(new ObjectNode(FACTORY));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public ObjectNode sendMessages(EasemobRequest request) {
        final String targetType = request.getTarget_type().name();

        final ObjectNode msg = FACTORY.objectNode();
        msg.put("type", request.getMsg().getType());
        msg.put("msg", request.getMsg().getMsg());
        final String from = request.getFrom();

        final ObjectNode ext = FACTORY.objectNode();
        if (request.getExt() != null && request.getExt().size() > 0) {
            for (String key : request.getExt().keySet()) {
                ext.put(key, request.getExt().get(key));
            }
        }

        ArrayNode target = FACTORY.arrayNode();
        int count = 0;
        int size = request.getTarget().size();
        while (true) {
            while (count < size) {
                target.add(request.getTarget().get(count));
                count++;
                /*
                 每一百人推送一次，遍历循环所有用户后退出
                 */
                if (count % 100 == 0) {
                    break;
                }
            }
            /*
             每秒推送如果超过20次，停1秒
             */
            if (count % 2000 == 0) {
                try {
                    long start = System.currentTimeMillis();
                    Thread.sleep(1000);
                    LOGGER.info("sleep time ,info: {}", (System.currentTimeMillis() - start));
                } catch (InterruptedException ex) {
                    LOGGER.error(ex);
                }
            }
            EasemobMessages.sendMessages(targetType, target, msg, from, ext);
            target.removeAll();
            LOGGER.info("count of information send,info: {}", count);
            if (count == size) {
                LOGGER.info("information send is over");
                break;
            }
        }

        return handleResult(new ObjectNode(FACTORY));
    }

    public ObjectNode handleResult(ObjectNode result) {
        if (result.get("error") != null) {
            getLogger().error(result);
        }

        return result;
    }

    public Logger getLogger() {
        return LOGGER;
    }
}
