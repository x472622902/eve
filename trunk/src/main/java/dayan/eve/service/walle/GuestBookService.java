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
package dayan.eve.service.walle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import dayan.common.util.HttpClientUtil;
import dayan.eve.config.EveProperties;
import dayan.eve.exception.ErrorCN;
import dayan.eve.model.GuestBook;
import dayan.eve.model.JsonResult;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.query.GuestBookQuery;
import dayan.eve.repository.AccountInfoRepository;
import dayan.eve.repository.SchoolFollowRepository;
import dayan.eve.util.SchoolIdPlatformIdUtil;
import dayan.eve.util.WalleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Service
public class GuestBookService {

    private static final Logger LOGGER = LogManager.getLogger(GuestBookService.class);

    private String guestbookPutUrl;
    private String guestbookGetUrl;

    @Autowired
    SchoolFollowRepository schoolFollowRepository;

    @Autowired
    AccountInfoRepository accountInfoRepository;

    @Autowired
    SchoolIdPlatformIdUtil schoolIdPlatformIdUtil;

    @Autowired
    WalleUtil walleUtil;

    @Autowired
    VisitorService visitorService;

    @Autowired
    public GuestBookService(EveProperties eveProperties) {
        this.guestbookGetUrl = eveProperties.getWalle().getGuestbookGet();
        this.guestbookPutUrl = eveProperties.getWalle().getGuestbookPut();
    }

    public Boolean sendGuestbook(Integer accountId, GuestBookQuery query) {
        AccountInfo accountInfo = accountInfoRepository.queryOneInfo(accountId);
        if (accountInfo == null) {
            accountInfo = new AccountInfo();
        }
        GuestBook guestBook = new GuestBook(accountInfo);
        Integer platformId = schoolIdPlatformIdUtil.getAllSchoolPlatformMap().get(query.getSchoolId());
        if (platformId == null) {
            throw new RuntimeException(ErrorCN.Walle.PLATFORM_NOT_FOUND);
        }
        guestBook.setPlatformId(platformId);
        guestBook.setQuestion(query.getQuestion());
        guestBook.setUserNumber(accountId.toString());
        LOGGER.info("sendQuestion guestBook info {}", JSON.toJSONString(guestBook, false));
        LOGGER.info("a user send question in guestbook,account info:", JSON.toJSONString(accountInfo, false));
        if (sendGuestbook(guestBook)) {
            visitorService.createVisitor(accountInfo, platformId);
        }
        return true;
    }

    public String readGuestbook(GuestBookQuery query) throws Exception {
        LOGGER.info("guestbook read query info,{}", JSON.toJSONString(query, true));
        Map<String, String> params = new HashMap<>();
        params.put("schoolHashId", query.getSchoolHashId());
        params.put("page", query.getPage().toString());
        params.put("size", query.getSize().toString());
        params.put("access_token", walleUtil.getAccessToken());
        return HttpClientUtil.get(guestbookGetUrl, params);
    }

    private Boolean sendGuestbook(GuestBook guestBook) {
        boolean result = false;
        try {
            String url = guestbookPutUrl + "?access_token=" + walleUtil.getAccessToken();
            String resp = HttpClientUtil.put(url, JSON.toJSONString(guestBook));
            Boolean success = JSONObject.parseObject(resp, JsonResult.class).isSuccess();
            result = success != null ? success : false;
        } catch (URISyntaxException | IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return result;
    }

}
