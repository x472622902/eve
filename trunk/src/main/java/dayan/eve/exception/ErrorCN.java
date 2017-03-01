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

    public final static class Course {

        public final static String CD_KEY_NOT_FOUND = "邀请码不存在";
        public final static String CD_KEY_NOT_SUIT = "邀请码不匹配";
        public final static String COURSE_BOUGHT = "课程已购买";
        public final static String NO_CD_KEY = "请输入邀请码";
        public final static String COURSE_NOT_BOUGHT = "课程未购买";
        public final static String LOGIN_OR_INVITED_CODE = "请先登陆或输入邀请码";
        public final static String TEST_NOT_SELECTED = "请选择测试";
        public final static String COURSE_NOT_SELECTED = "请选择课程";
        public final static String ALIPAY_FAILED = "支付失败，请重新支付";
    }

    public final static class Walle {
        public final static String NO_PLATFORM_ID = "无平台id";
        public final static String NO_QUESTION_DATA = "暂无问答数据";
        public final static String PLATFORM_NOT_FOUND = "资讯平台不存在";
    }

    public final static class Topic {

        public final static String IMAGE_ERROR = "图片有问题，请换一张试试";
        public final static String TOPIC_NOT_SELECTED = "请选择帖子";
        public final static String NO_CONTENT = "请输入内容";
        public final static String TEXT_SIZE_LIMIT = "内容不能超过1000字";
        public final static String TOPIC_NOT_FOUND = "帖子不存在";
        public final static String THEME_EXISTED = "主题已存在";

    }

    public final static class Clock {
        public final static String CLOCK_DONE = "今天已签到";
        public final static String TIME_LIMIT = "签到时间已经过啦";
    }

    public final static class SMS {
        public final static String NO_MOBILE = "请输入手机号";

    }

    public final static class School {

        public final static String SCHOOL_NOT_SELECTED = "请选择学校";
    }

    public final static class Account {
        public final static String NOT_LOGIN = "未登录";
        public final static String ACCOUNT_EXIST = "用户已存在";
        public final static String ACCOUNT_NOT_FOUND = "用户不存在";
        public final static String VERIFY_CODE_ERROR = "验证码错误";
        public final static String ACCOUNT_DUPLICATE = "账号重复";
    }


}
