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

package dayan.eve.model;

/**
 * @author leiky
 */
public class Constants {

    // Spring profile for development and production, see http://jhipster.github.io/profiles/
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_TEST = "test";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";

    public static final String SPRING_PROFILE_ONLINE_TEST = "onlineTest";
    // Spring profile used when deploying with Spring Cloud (used when deploying to CloudFoundry)
    public static final String SPRING_PROFILE_CLOUD = "cloud";
    // Spring profile used when deploying to Heroku
    public static final String SPRING_PROFILE_HEROKU = "heroku";
    // Spring profile used to disable swagger
    public static final String SPRING_PROFILE_NO_SWAGGER = "no-swagger";
    // Spring profile used to disable running flyway
    public static final String SPRING_PROFILE_NO_FLYWAY = "no-flyway";

    public static final String SPRING_PROFILE_NO_EMBEDDED_REDIS = "no-embedded-redis";

    public static final String SYSTEM_ACCOUNT = "system";

    public static final String EMOJI_CODE = "utf8mb4";

    public static final Integer DAY_MINUTES = 1440;


    public static class Key {
        public static final String UserNumber = "userNumber";
        public static final String UserNumbers = "userNumbers";
        public static final String Answer = "answer";
        public static final String PlatformHashId = "platformHashId";
        public static final String SchoolHashId = "schoolHashId";
        public static final String SchoolName = "schoolName";
        public static final String Message = "message";
        public static final String UserName = "userName";
        public static final String From = "FROM";

    }

    public final static class Alipay {
        public final static String SUCCESS = "TRADE_SUCCESS";
        public final static String FINISHED = "TRADE_FINISHED";
    }

    public static final String UPLOAD_PROGRESS = "UPLOAD_PROGRESS";

    public static class Passport {

        public static final String SESSION_LOGIN_NAME = "SESSION_LOGIN_NAME";
        public static final String SESSION_LOGIN_ID = "SESSION_LOGIN_ID";
        public static final String SESSION_PRIVILEGES = "SESSION_PRIVILEGES";
        public static final String SESSION_USER = "SESSION_USER";
    }

    public static class Channel {

        public static final int MONITOR = 0;
        public static final int WECHAT = 1;
        public static final int WEB_DIALOG = 2;
        public static final int SEARCH_PUBLIC = 3;
        public static final int SEARCH_INNER = 4;
        public static final int APP = 5;
    }

    public static class Role {

        public static final int SysAdmin = 1;
        public static final int StoreAdmin = 2;
        public static final int DeliveryMan = 3;
        public static final int MallAdmin = 3;
    }
}
