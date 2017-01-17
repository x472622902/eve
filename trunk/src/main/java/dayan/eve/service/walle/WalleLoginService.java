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
package dayan.eve.service.walle;

import dayan.eve.util.WalleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class WalleLoginService {

    @Autowired
    WalleUtil walleUtil;

    public String login(String username, String password) throws Exception {
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        return walleUtil.checkLogin(username, password);
    }

}
