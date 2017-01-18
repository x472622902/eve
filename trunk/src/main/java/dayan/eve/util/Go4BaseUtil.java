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
package dayan.eve.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dayan.common.util.Go4Util;
import dayan.eve.exception.ErrorCN;
import dayan.eve.exception.EveException;
import dayan.eve.model.account.Account;
import dayan.eve.model.account.AccountInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xsg
 */
@Component
public class Go4BaseUtil {

    private static String ACCESS_TOKEN;

    public String getAccessToken() throws Exception {
        if (StringUtils.isEmpty(ACCESS_TOKEN)) {
            updateAccessToken();
        }
        return ACCESS_TOKEN;
    }

    public void updateAccessToken() throws Exception {
        String resultJSON = Go4Util.accessToken();
        ACCESS_TOKEN = JSONObject.parseObject(resultJSON).getString("access_token");
    }

    /**
     * 检查用户是否可以注册
     *
     * @param loginAccount 用户类的json字符串
     * @return 可注册则返回true
     * @throws Exception 异常错误
     */
    public boolean checkLoginAccount(String loginAccount) throws Exception {
        String accessToken = getAccessToken();
        String resultJSON = Go4Util.checkLoginAccount(accessToken, loginAccount);
        JSONObject result = JSONObject.parseObject(resultJSON);
        boolean success = result.getBoolean("success");
        if (!success) {
            String errorCN = result.getString("errorCN");
            throw new EveException(errorCN);
        }
        JSONObject data = result.getJSONObject("data");
        boolean available = data.getBoolean("available");
        if (!available) {
            throw new EveException(ErrorCN.Login.USER_NOT_FOUND);
        }
        return true;
    }

    /**
     * 注册
     *
     * @param mobile   手机号码
     * @param password 密码
     * @param nickname 昵称
     * @return 用户hashId
     * @throws Exception 异常错误
     */
    public String register(String mobile, String password, String nickname) throws Exception {
        String accessToken = getAccessToken();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", mobile);
        jsonObject.put("password", password);
        jsonObject.put("nickname", nickname);
        String resultJSON = Go4Util.register(accessToken, jsonObject.toString());
        JSONObject result = JSONObject.parseObject(resultJSON);
        boolean success = result.getBoolean("success");
        if (!success) {
            String errorCN = result.getString("errorCN");
            throw new EveException(errorCN);
        }
        JSONObject data = result.getJSONObject("data");
        return data.getString("hashId");
    }

    //注册密码已经md5加密过的用户
    public String registerMd5(String mobile, String password, String md5password, String nickname) throws Exception {
        String accessToken = getAccessToken();
        JSONObject jsonobj = new JSONObject();
        jsonobj.put("mobile", mobile);
        jsonobj.put("password", password);
        jsonobj.put("md5password", md5password);
        jsonobj.put("nickname", nickname);
        String resultJSON = Go4Util.register(accessToken, jsonobj.toString());
        JSONObject result = JSONObject.parseObject(resultJSON);
        boolean success = result.getBoolean("success");
        if (!success) {
            String errorCN = result.getString("errorCN");
            throw new EveException(errorCN);
        }
        JSONObject data = result.getJSONObject("data");
        return data.getString("hashId");
    }

    /**
     * 更具用户的username，email，mobile获取用户的基本信息
     *
     * @param loginAccount 登录用户的json字符串
     * @return 用户基本信息
     * @throws Exception 异常错误
     */
    public Account getAccountDetailByLoginAccount(String loginAccount) throws Exception {
        String accessToken = getAccessToken();
        String resultJSON = Go4Util.getDetailByLoginAccount(accessToken, loginAccount);
        JSONObject result = JSONObject.parseObject(resultJSON);
        boolean success = result.getBoolean("success");
        if (!success) {
            String errorCN = result.getString("errorCN");
            throw new EveException(errorCN);
        }
        String data = result.getString("data");
        return JSONObject.parseObject(data, Account.class);
    }

    /**
     * 根据用户hashid 获取用户的基本信息
     *
     * @param accountHashId 用户hashid
     * @return 用户基本信息
     * @throws Exception 异常错误
     */
    public AccountInfo getAccountDetailByHashId(String accountHashId) throws Exception {
        String accessToken = getAccessToken();
        String resultJSON = Go4Util.getDetailByAccountHashId(accessToken, accountHashId);
        JSONObject result = JSONObject.parseObject(resultJSON);
        boolean success = result.getBoolean("success");
        if (!success) {
            String errorCN = result.getString("errorCN");
            throw new EveException(errorCN);
        }
        String data = result.getString("data");
        return JSONObject.parseObject(data, AccountInfo.class);
    }


    /**
     * 登录
     *
     * @param loginType 登录类型
     * @param loginData 登录参数
     * @return 用户基本信息
     * @throws Exception 异常错误
     */
    public AccountInfo checkLogin(String loginType, Object loginData) throws Exception {
        String accessToken = getAccessToken();
        JSONObject jsonobj = new JSONObject();
        jsonobj.put("loginType", loginType);
        jsonobj.put("loginClient", "eve");
        jsonobj.put("loginData", loginData);
        String resultJSON = Go4Util.login(accessToken, jsonobj.toString());
        JSONObject result = JSONObject.parseObject(resultJSON);
        boolean success = result.getBoolean("success");
        if (!success) {
            String errorCN = result.getString("errorCN");
            throw new EveException(errorCN);
        }
        return result.getObject("data", AccountInfo.class);
    }

    /**
     * 更新密码
     *
     * @param accountHashId 用户hashId
     * @param newPassword   新密码
     * @throws Exception 异常错误
     */
    public void updatePassword(String accountHashId, String newPassword) throws Exception {
        String accessToken = getAccessToken();
        String resultJSON = Go4Util.updatePassword(accessToken, accountHashId, newPassword);
        JSONObject result = JSONObject.parseObject(resultJSON);
        boolean success = result.getBoolean("success");
        if (!success) {
            String errorCN = result.getString("errorCN");
            throw new EveException(errorCN);
        }
    }

    /**
     * 更新头像
     *
     * @param accountHashId 用户hashID
     * @param file          头像图片
     * @throws Exception 异常错误
     */
    public void updateAvatar(String accountHashId, MultipartFile file) throws Exception {
        String accessToken = getAccessToken();
        String resultJSON = Go4Util.updateAvatar(accessToken, accountHashId, file.getBytes());
        JSONObject result = JSONObject.parseObject(resultJSON);
        boolean success = result.getBoolean("success");
        if (!success) {
            String errorCN = result.getString("errorCN");
            throw new EveException(errorCN);
        }
    }

    public void getVerificationCode(String mobile) throws Exception {
        String accessToken = getAccessToken();
        String toString = Go4Util.getVerificationCode(accessToken, mobile);
        JSONObject result = JSONObject.parseObject(toString);
        Boolean success = result.getBoolean("success");
        if (!success) {
            JSONObject info = JSONObject.parseObject(result.getString("info"));
            String message = info.getString("message");
            throw new EveException(message);
        }
    }

    public Boolean checkVerificationCode(String mobile, String code) throws Exception {
        String accessToken = getAccessToken();
        String resultJSON = Go4Util.checkVerificationCode(accessToken, mobile, code);
        JSONObject result = JSONObject.parseObject(resultJSON);
        boolean success = result.getBoolean("success");
        if (!success) {
            String errorCN = result.getString("errorCN");
            throw new EveException(errorCN);
        }
        JSONObject data = result.getJSONObject("data");
        return data.getBoolean("validated");
    }

    public AccountInfo getDetailByEasemob(String username) throws Exception {
        String accessToken = getAccessToken();
        String resultJSON = Go4Util.getEasemobDetail(accessToken, username);
        JSONObject result = JSONObject.parseObject(resultJSON);
        boolean success = result.getBoolean("success");
        if (!success) {
            String errorCN = result.getString("errorCN");
            throw new EveException(errorCN);
        }

        JSONArray array = result.getJSONArray("data");
        return array.getObject(0, AccountInfo.class);
    }

    public List<AccountInfo> getDetailByEasemob(List<String> easemobusernames) throws Exception {
        String accessToken = getAccessToken();
        String params = "";
        for (String easemobusername : easemobusernames) {
            if (!"".equals(params)) {
                params += ",";
            }
            params += easemobusername;
        }
        String resultJSON = Go4Util.getEasemobDetail(accessToken, params);
        JSONObject result = JSONObject.parseObject(resultJSON);
        boolean success = result.getBoolean("success");
        if (!success) {
            String errorCN = result.getString("errorCN");
            throw new EveException(errorCN);
        }

        JSONArray array = result.getJSONArray("data");
        List<AccountInfo> accountInfos = new LinkedList<>();
        for (Object obj : array) {
            AccountInfo info = JSONObject.parseObject(obj.toString(), AccountInfo.class);
            accountInfos.add(info);
        }
        return accountInfos;
    }

}
