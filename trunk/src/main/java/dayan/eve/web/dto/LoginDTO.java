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
package dayan.eve.web.dto;

import dayan.eve.model.account.Account;
import dayan.eve.model.enumeration.LoginType;

/**
 * @author zhuangyd
 */
public class LoginDTO {


    private LoginType loginType;
    private Account loginData;

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public Account getLoginData() {
        return loginData;
    }

    public void setLoginData(Account loginData) {
        this.loginData = loginData;
    }
}
