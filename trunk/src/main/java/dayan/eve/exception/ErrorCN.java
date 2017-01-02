package dayan.eve.exception;

/**
 * @author dengqg
 */
public final class ErrorCN {

    public final static class Login {
        public final static String MOBILE_OCCUPY = "该手机号已注册，请更换手机号";

        public final static String MOBILE_NO_REGISTER = "手机号未注册";

        public static final String AUTH_CODE_ERROR = "验证码错误";

        public static final String USER_NOT_FOUND = "手机号暂未注册或用户名不存在";

        public static final String ACCOUNT_LENGTH_ERROR = "请输入有效的手机号或学籍号";

        public static final String PWD_ERROR = "密码错误";

        public static final String LOGIN_FAILED = "登陆失败，服务器错误";

        public static final String UN_LOGIN = "用户未登陆";

    }


    public static final String DEFAULT_SERVER_ERROR = "服务器繁忙";

    public final static class Go4 {
        public final static String OLD_PWD_ERROR = "原密码错误";
        public final static String LOGIN_ERROR = "登陆失败";
        public final static String REGISTER_ERROR = "创建账户失败";
        public final static String GET_PWD_ERROR = "获取密码失败";
        public final static String USER_EXISTED = "用户已存在";
    }

    public final static class Mo {
        public final static String REQUEST_ERR = "Mo请求异常";
    }

    public final static class Wechat {
        public final static String UTIL_ERR = "工具调用异常";

        public final static String OPENID_ERR = "openId获取失败";
    }

    public final static class Template {
        public static final String TOKEN_INVALID = "token无效";
        public static final String PAGE_NOT_FOUND = "页面不存在";
    }

    public final static class InvitedCode {
        public static final String TYPE_INVALID = "邀请码类型不匹配";
        public static final String NORMAL_ERROR = "邀请码错误";
        public static final String ERROR_THREE_TIMES = "邀请码有误（已错误%d次）<br/> 输错5次后，需24小时后才能重新输入";
        public static final String ERROR_FIVE_TIMES = "已累计输错5次<br/>请%s后再输入";
    }
}
