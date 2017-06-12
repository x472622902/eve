/**
 * *****************************************************
 * Copyright (C) Dayan techology Co.ltd - All Rights Reserved
 *
 * This file is part of Dayan techology Co.ltd property.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * *****************************************************
 */
package dayan.eve.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import weibo4j.examples.oauth2.OAuth4Code;
import weibo4j.http.AccessToken;
import weibo4j.model.WeiboException;

/**
 *
 * @author xsg
 */
public class WeiboUtil {

    private static final Logger LOGGER = LogManager.getLogger(WeiboUtil.class);
    private static AccessToken ACCESS_TOKEN;

    public static void updateAccessToken(String code) {
        try {
            ACCESS_TOKEN = OAuth4Code.getAccessToken(code);
        } catch (WeiboException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    public static String getAuthorizeUrl() {
        String authorizeUrl = null;
        try {
            authorizeUrl = OAuth4Code.authorize();
        } catch (WeiboException ex) {
            LOGGER.error(ex.getMessage());
        }
        return authorizeUrl;
    }

    public static AccessToken getAccessToken() {
        return ACCESS_TOKEN;
    }
}
