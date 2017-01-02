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
 *
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
}
