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
import com.alibaba.fastjson.JSONObject;
import dayan.eve.exception.ErrorCN;
import dayan.eve.exception.EveException;
import dayan.eve.model.Constants;
import dayan.eve.model.account.Account;
import dayan.eve.model.account.AccountBase;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.enumeration.LoginType;
import dayan.eve.model.query.AccountQuery;
import dayan.eve.repository.AccountInfoRepository;
import dayan.eve.repository.AccountRepository;
import dayan.eve.repository.CodeRepository;
import dayan.eve.service.AccountInfoService;
import dayan.eve.service.AccountService;
import dayan.eve.util.Go4BaseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author xsg
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LogManager.getLogger();
    private final AccountRepository accountRepository;
    private final CodeRepository codeMapper;
    private final AccountInfoService accountInfoService;
    private final AccountInfoRepository accountInfoRepository;
    private final Go4BaseUtil go4BaseUtil;

    @Autowired
    public AccountServiceImpl(CodeRepository codeMapper, AccountInfoService accountInfoService, AccountInfoRepository accountInfoRepository, Go4BaseUtil go4BaseUtil, AccountRepository accountRepository) {
        this.codeMapper = codeMapper;
        this.accountInfoService = accountInfoService;
        this.accountInfoRepository = accountInfoRepository;
        this.go4BaseUtil = go4BaseUtil;
        this.accountRepository = accountRepository;
    }


    @Override
    public Account login(LoginType loginType, AccountBase loginData) throws Exception {
        Account account = null;
        switch (loginType) {
            case qq:
                account = createOrUpdate(loginType, loginData);
                break;
            case baidu:
                account = createOrUpdate(loginType, loginData);
                break;
            case eve:
                Account accountData = (Account) loginData;
                account = checkLogin(accountData.getMobile(), accountData.getPassword());
                break;
            default:

        }
        return account;
    }

    private Account createOrUpdate(LoginType loginType, AccountBase loginData) throws Exception {
        AccountInfo go4Info = go4BaseUtil.checkLogin(loginType.toString(), loginData);
        codeMapper.setCode(Constants.EMOJI_CODE);
        return checkInfoAndUpdate(go4Info);
    }

    private Account checkLogin(String mobile, String password) throws Exception {
        JSONObject loginInfo = new JSONObject();
        loginInfo.put("loginAccount", mobile);
        loginInfo.put("password", password);
        AccountInfo go4Info = go4BaseUtil.checkLogin(Constants.GO4, loginInfo);
        codeMapper.setCode(Constants.EMOJI_CODE);
        return checkInfoAndUpdate(go4Info);
    }

    private Account checkInfoAndUpdate(AccountInfo go4Info) {
        AccountInfo eveInfo = accountInfoService.readOrCreate(go4Info);
        go4Info.setId(eveInfo.getAccountId());
        if (!go4Info.getNickname().equals(eveInfo.getNickname())) {
            accountRepository.update(eveInfo);
        }
        if (go4Info.getAvatarURL() != null && !go4Info.getAvatarURL().equals(eveInfo.getPortraitUrl())) {
            go4Info.setPortraitUrl(go4Info.getAvatarURL());
            accountInfoRepository.updateInfo(go4Info);
        }
        Account account = new Account();
        account.setId(go4Info.getId());
        account.setHashId(go4Info.getHashId());
        account.setNickname(go4Info.getNickname());
        account.setEasemob(go4Info.getEasemob());
        return account;
    }

    @Override
    public Account register(Account account, MultipartFile file) throws Exception {

        LOGGER.info("register,account info: {}", JSON.toJSONString(account, true));
        Boolean isVerified = go4BaseUtil.checkVerificationCode(account.getMobile(), account.getVerifyCode());
        if (!isVerified) {
            throw new EveException(ErrorCN.Login.AUTH_CODE_ERROR);
        }
        String hashId = go4BaseUtil.register(account.getMobile(), account.getPassword(), account.getNickname());
        account.setHashId(hashId);
        if (file != null && !file.isEmpty()) {
            go4BaseUtil.updateAvatar(hashId, file);
            account.setAvatarURL(go4BaseUtil.getAccountDetailByHashId(hashId).getAvatarURL());
        }
        codeMapper.setCode(Constants.EMOJI_CODE);
        accountRepository.insert(account);

        accountInfoService.createInfo(account);

        return account;
    }

    @Override
    public List<Account> read(AccountQuery query) {
        return accountRepository.query(query);
    }

    @Override
    public void updatePassword(Account account) throws Exception {
        Boolean isVerified = go4BaseUtil.checkVerificationCode(account.getMobile(), account.getVerifyCode());
        if (!isVerified) {
            throw new EveException(ErrorCN.Login.AUTH_CODE_ERROR);
        }
        Account result = go4BaseUtil.getAccountDetailByLoginAccount(account.getMobile());
        String hashId = result.getHashId();

        go4BaseUtil.updatePassword(hashId, account.getPassword());
    }

    @Override
    public String updateAvatar(Integer accountId, MultipartFile file) throws Exception {
        String accountHashId = accountRepository.queryHashId(accountId).get(0);
        go4BaseUtil.updateAvatar(accountHashId, file);
        Account account = go4BaseUtil.getAccountDetailByHashId(accountHashId);
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountId(accountId);
        accountInfo.setPortraitUrl(account.getAvatarURL());
        accountInfoService.updateInfo(accountInfo);
        return account.getAvatarURL();
    }

    @Override
    public void updateBlock(Integer blocked, Integer accountId) {
        AccountQuery query = new AccountQuery();
        query.setId(accountId);
        query.setBlocked(blocked);
        accountRepository.updateBlock(query);

    }

}
