package dayan.eve.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by xsg on 12/31/2016.
 */
@ConfigurationProperties(prefix = "eve")
public class EveProperties {

    private final Swagger swagger = new Swagger();
    private Mo mo = new Mo();
    private Clock clock = new Clock();
    private Walle walle = new Walle();
    private Image image = new Image();
    private Html html = new Html();
    private Alipay alipay = new Alipay();
    private School school = new School();
    private Course course = new Course();
    private Redis redis = new Redis();
    private Topic topic = new Topic();
    private Async async = new Async();

    public Topic getTopic() {
        return topic;
    }

    public Mo getMo() {
        return mo;
    }


    public Clock getClock() {
        return clock;
    }


    public Walle getWalle() {
        return walle;
    }


    public Image getImage() {
        return image;
    }


    public Html getHtml() {
        return html;
    }


    public Alipay getAlipay() {
        return alipay;
    }


    public School getSchool() {
        return school;
    }


    public Course getCourse() {
        return course;
    }


    public Swagger getSwagger() {
        return swagger;
    }

    public Redis getRedis() {
        return redis;
    }

    public Async getAsync() {
        return async;
    }

    public static class Swagger {

        private String title = "Hal API";

        private String description = "Hal API documentation";

        private String version = "0.0.1";

        private String termsOfServiceUrl;

        private String contactName;

        private String contactUrl;

        private String contactEmail;

        private String license;

        private String licenseUrl;

        private Boolean enabled;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getTermsOfServiceUrl() {
            return termsOfServiceUrl;
        }

        public void setTermsOfServiceUrl(String termsOfServiceUrl) {
            this.termsOfServiceUrl = termsOfServiceUrl;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getContactUrl() {
            return contactUrl;
        }

        public void setContactUrl(String contactUrl) {
            this.contactUrl = contactUrl;
        }

        public String getContactEmail() {
            return contactEmail;
        }

        public void setContactEmail(String contactEmail) {
            this.contactEmail = contactEmail;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getLicenseUrl() {
            return licenseUrl;
        }

        public void setLicenseUrl(String licenseUrl) {
            this.licenseUrl = licenseUrl;
        }

        public Boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }
    }

    public static class Clock {
        private String inStart;
        private String inEnd;
        private String outStart;
        private String outEnd;
        private String clockedContent;
        private String unclockedContent;
        private String morningContent;
        private String eveningContent;
        private String dayStart;

        public String getDayStart() {
            return dayStart;
        }

        public void setDayStart(String dayStart) {
            this.dayStart = dayStart;
        }

        public String getClockedContent() {
            return clockedContent;
        }

        public void setClockedContent(String clockedContent) {
            this.clockedContent = clockedContent;
        }

        public String getUnclockedContent() {
            return unclockedContent;
        }

        public void setUnclockedContent(String unclockedContent) {
            this.unclockedContent = unclockedContent;
        }

        public String getMorningContent() {
            return morningContent;
        }

        public void setMorningContent(String morningContent) {
            this.morningContent = morningContent;
        }

        public String getEveningContent() {
            return eveningContent;
        }

        public void setEveningContent(String eveningContent) {
            this.eveningContent = eveningContent;
        }

        public String getInStart() {
            return inStart;
        }

        public void setInStart(String inStart) {
            this.inStart = inStart;
        }

        public String getInEnd() {
            return inEnd;
        }

        public void setInEnd(String inEnd) {
            this.inEnd = inEnd;
        }

        public String getOutStart() {
            return outStart;
        }

        public void setOutStart(String outStart) {
            this.outStart = outStart;
        }

        public String getOutEnd() {
            return outEnd;
        }

        public void setOutEnd(String outEnd) {
            this.outEnd = outEnd;
        }
    }

    public static class Walle {

        private String username;
        private String password;
        private String clientId;
        private String grantType;
        private String refreshGrantType;
        private String csTopHashId;
        private String qaAnswer;
        private String qaHint;
        private String qaGreet;
        private String qaFreq;
        private String csAsk;
        private String csClose;
        private String csStaff;
        private String csAllOnline;
        private Integer evePlatformId;
        private String platform;
        private String url;
        private String login;
        private String statis;
        private String guestbookSendQuestion;
        private String guestbookGetSchool;
        private String guestbookPut;
        private String guestbookGet;
        private String messageHandlerSend;
        private String answer;
        private String freq;
        private String hot;
        private String visitor;
        private String feedback;
        private String accessToken;
        private Integer csOnlineDisplayNum;
        private Long csOnlineCacheSeconds;
        private Integer hotQuestionNum;
        private Integer freqQuestionNum;

        public Integer getHotQuestionNum() {
            return hotQuestionNum;
        }

        public void setHotQuestionNum(Integer hotQuestionNum) {
            this.hotQuestionNum = hotQuestionNum;
        }

        public Integer getFreqQuestionNum() {
            return freqQuestionNum;
        }

        public void setFreqQuestionNum(Integer freqQuestionNum) {
            this.freqQuestionNum = freqQuestionNum;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getGrantType() {
            return grantType;
        }

        public void setGrantType(String grantType) {
            this.grantType = grantType;
        }

        public String getRefreshGrantType() {
            return refreshGrantType;
        }

        public void setRefreshGrantType(String refreshGrantType) {
            this.refreshGrantType = refreshGrantType;
        }

        public String getCsTopHashId() {
            return csTopHashId;
        }

        public void setCsTopHashId(String csTopHashId) {
            this.csTopHashId = csTopHashId;
        }

        public String getQaAnswer() {
            return qaAnswer;
        }

        public void setQaAnswer(String qaAnswer) {
            this.qaAnswer = qaAnswer;
        }

        public String getQaHint() {
            return qaHint;
        }

        public void setQaHint(String qaHint) {
            this.qaHint = qaHint;
        }

        public String getQaGreet() {
            return qaGreet;
        }

        public void setQaGreet(String qaGreet) {
            this.qaGreet = qaGreet;
        }

        public String getQaFreq() {
            return qaFreq;
        }

        public void setQaFreq(String qaFreq) {
            this.qaFreq = qaFreq;
        }

        public String getCsAsk() {
            return csAsk;
        }

        public void setCsAsk(String csAsk) {
            this.csAsk = csAsk;
        }

        public String getCsClose() {
            return csClose;
        }

        public void setCsClose(String csClose) {
            this.csClose = csClose;
        }

        public String getCsStaff() {
            return csStaff;
        }

        public void setCsStaff(String csStaff) {
            this.csStaff = csStaff;
        }

        public String getCsAllOnline() {
            return csAllOnline;
        }

        public void setCsAllOnline(String csAllOnline) {
            this.csAllOnline = csAllOnline;
        }

        public Integer getEvePlatformId() {
            return evePlatformId;
        }

        public void setEvePlatformId(Integer evePlatformId) {
            this.evePlatformId = evePlatformId;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getStatis() {
            return statis;
        }

        public void setStatis(String statis) {
            this.statis = statis;
        }

        public String getGuestbookSendQuestion() {
            return guestbookSendQuestion;
        }

        public void setGuestbookSendQuestion(String guestbookSendQuestion) {
            this.guestbookSendQuestion = guestbookSendQuestion;
        }

        public String getGuestbookGetSchool() {
            return guestbookGetSchool;
        }

        public void setGuestbookGetSchool(String guestbookGetSchool) {
            this.guestbookGetSchool = guestbookGetSchool;
        }

        public String getGuestbookPut() {
            return guestbookPut;
        }

        public void setGuestbookPut(String guestbookPut) {
            this.guestbookPut = guestbookPut;
        }

        public String getGuestbookGet() {
            return guestbookGet;
        }

        public void setGuestbookGet(String guestbookGet) {
            this.guestbookGet = guestbookGet;
        }

        public String getMessageHandlerSend() {
            return messageHandlerSend;
        }

        public void setMessageHandlerSend(String messageHandlerSend) {
            this.messageHandlerSend = messageHandlerSend;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getFreq() {
            return freq;
        }

        public void setFreq(String freq) {
            this.freq = freq;
        }

        public String getHot() {
            return hot;
        }

        public void setHot(String hot) {
            this.hot = hot;
        }

        public String getVisitor() {
            return visitor;
        }

        public void setVisitor(String visitor) {
            this.visitor = visitor;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public Integer getCsOnlineDisplayNum() {
            return csOnlineDisplayNum;
        }

        public void setCsOnlineDisplayNum(Integer csOnlineDisplayNum) {
            this.csOnlineDisplayNum = csOnlineDisplayNum;
        }

        public Long getCsOnlineCacheSeconds() {
            return csOnlineCacheSeconds;
        }

        public void setCsOnlineCacheSeconds(Long csOnlineCacheSeconds) {
            this.csOnlineCacheSeconds = csOnlineCacheSeconds;
        }
    }

    public static class Image {
        private String accountPortraitReadUrlPrefix;
        private String accountPortraitCreateUrlPrefix;
        private String informationReadUrlPrefix;
        private String informationCreateUrlPrefix;
        private String bannerReadUrlPrefix;
        private String bannerCreateUrlPrefix;
        private String adsReadUrlPrefix;
        private String adsCreateUrlPrefix;


        public String getAccountPortraitReadUrlPrefix() {
            return accountPortraitReadUrlPrefix;
        }

        public void setAccountPortraitReadUrlPrefix(String accountPortraitReadUrlPrefix) {
            this.accountPortraitReadUrlPrefix = accountPortraitReadUrlPrefix;
        }

        public String getAccountPortraitCreateUrlPrefix() {
            return accountPortraitCreateUrlPrefix;
        }

        public void setAccountPortraitCreateUrlPrefix(String accountPortraitCreateUrlPrefix) {
            this.accountPortraitCreateUrlPrefix = accountPortraitCreateUrlPrefix;
        }

        public String getInformationReadUrlPrefix() {
            return informationReadUrlPrefix;
        }

        public void setInformationReadUrlPrefix(String informationReadUrlPrefix) {
            this.informationReadUrlPrefix = informationReadUrlPrefix;
        }

        public String getInformationCreateUrlPrefix() {
            return informationCreateUrlPrefix;
        }

        public void setInformationCreateUrlPrefix(String informationCreateUrlPrefix) {
            this.informationCreateUrlPrefix = informationCreateUrlPrefix;
        }

        public String getBannerReadUrlPrefix() {
            return bannerReadUrlPrefix;
        }

        public void setBannerReadUrlPrefix(String bannerReadUrlPrefix) {
            this.bannerReadUrlPrefix = bannerReadUrlPrefix;
        }

        public String getBannerCreateUrlPrefix() {
            return bannerCreateUrlPrefix;
        }

        public void setBannerCreateUrlPrefix(String bannerCreateUrlPrefix) {
            this.bannerCreateUrlPrefix = bannerCreateUrlPrefix;
        }

        public String getAdsReadUrlPrefix() {
            return adsReadUrlPrefix;
        }

        public void setAdsReadUrlPrefix(String adsReadUrlPrefix) {
            this.adsReadUrlPrefix = adsReadUrlPrefix;
        }

        public String getAdsCreateUrlPrefix() {
            return adsCreateUrlPrefix;
        }

        public void setAdsCreateUrlPrefix(String adsCreateUrlPrefix) {
            this.adsCreateUrlPrefix = adsCreateUrlPrefix;
        }
    }

    public static class Html {
        private String informationReadUrlPrefix;
        private String informationCreateUrlPrefix;

        public String getInformationReadUrlPrefix() {
            return informationReadUrlPrefix;
        }

        public void setInformationReadUrlPrefix(String informationReadUrlPrefix) {
            this.informationReadUrlPrefix = informationReadUrlPrefix;
        }

        public String getInformationCreateUrlPrefix() {
            return informationCreateUrlPrefix;
        }

        public void setInformationCreateUrlPrefix(String informationCreateUrlPrefix) {
            this.informationCreateUrlPrefix = informationCreateUrlPrefix;
        }
    }

    public static class Alipay {
        private String notifyUrlPrefix;

        public String getNotifyUrlPrefix() {
            return notifyUrlPrefix;
        }

        public void setNotifyUrlPrefix(String notifyUrlPrefix) {
            this.notifyUrlPrefix = notifyUrlPrefix;
        }
    }

    public static class School {
        private String logoPrefix;
        private String imageUrlPrefix;
        private Integer searchPromptNum;

        public String getLogoPrefix() {
            return logoPrefix;
        }

        public void setLogoPrefix(String logoPrefix) {
            this.logoPrefix = logoPrefix;
        }

        public String getImageUrlPrefix() {
            return imageUrlPrefix;
        }

        public void setImageUrlPrefix(String imageUrlPrefix) {
            this.imageUrlPrefix = imageUrlPrefix;
        }

        public Integer getSearchPromptNum() {
            return searchPromptNum;
        }

        public void setSearchPromptNum(Integer searchPromptNum) {
            this.searchPromptNum = searchPromptNum;
        }
    }

    public static class Course {
        private String detail;
        private String result;

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }

    public static class Mo {
        private String username;
        private String password;
        private String tokenUrl;
        private String schoolMajor;
        private String schoolList;
        private String schoolPlan;
        private String schoolSumPlan;
        private String schoolDetail;
        private String schoolNames;
        private String schoolScore;
        private String majorDetail;
        private String majorList;
        private String holQuestion;
        private String holPersonality;
        private String holJobClass;
        private String holMajorType;
        private String holMajors;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getTokenUrl() {
            return tokenUrl;
        }

        public void setTokenUrl(String tokenUrl) {
            this.tokenUrl = tokenUrl;
        }

        public String getSchoolMajor() {
            return schoolMajor;
        }

        public void setSchoolMajor(String schoolMajor) {
            this.schoolMajor = schoolMajor;
        }

        public String getSchoolList() {
            return schoolList;
        }

        public void setSchoolList(String schoolList) {
            this.schoolList = schoolList;
        }

        public String getSchoolPlan() {
            return schoolPlan;
        }

        public void setSchoolPlan(String schoolPlan) {
            this.schoolPlan = schoolPlan;
        }

        public String getSchoolSumPlan() {
            return schoolSumPlan;
        }

        public void setSchoolSumPlan(String schoolSumPlan) {
            this.schoolSumPlan = schoolSumPlan;
        }

        public String getSchoolDetail() {
            return schoolDetail;
        }

        public void setSchoolDetail(String schoolDetail) {
            this.schoolDetail = schoolDetail;
        }

        public String getSchoolNames() {
            return schoolNames;
        }

        public void setSchoolNames(String schoolNames) {
            this.schoolNames = schoolNames;
        }

        public String getSchoolScore() {
            return schoolScore;
        }

        public void setSchoolScore(String schoolScore) {
            this.schoolScore = schoolScore;
        }

        public String getMajorDetail() {
            return majorDetail;
        }

        public void setMajorDetail(String majorDetail) {
            this.majorDetail = majorDetail;
        }

        public String getMajorList() {
            return majorList;
        }

        public void setMajorList(String majorList) {
            this.majorList = majorList;
        }

        public String getHolQuestion() {
            return holQuestion;
        }

        public void setHolQuestion(String holQuestion) {
            this.holQuestion = holQuestion;
        }

        public String getHolPersonality() {
            return holPersonality;
        }

        public void setHolPersonality(String holPersonality) {
            this.holPersonality = holPersonality;
        }

        public String getHolJobClass() {
            return holJobClass;
        }

        public void setHolJobClass(String holJobClass) {
            this.holJobClass = holJobClass;
        }

        public String getHolMajorType() {
            return holMajorType;
        }

        public void setHolMajorType(String holMajorType) {
            this.holMajorType = holMajorType;
        }

        public String getHolMajors() {
            return holMajors;
        }

        public void setHolMajors(String holMajors) {
            this.holMajors = holMajors;
        }
    }

    public static class Redis {
        private String host;
        private int port;
        private int timeout;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }
    }

    public static class Topic {
        private Integer replyExp;//回帖经验
        private Integer createExp;//发帖经验
        private Integer beLikedExp;//被点赞经验
        private Integer beRepliedExp;//主贴被回复经验
        private Integer maxContentSize;
        private String likeNotificationMsg;
        private String topicReadUrlPrefix;
        private String topicCreateUrlPrefix;
        private String noticeTitle;
        private String messageTemplate;

        public Integer getBeRepliedExp() {
            return beRepliedExp;
        }

        public void setBeRepliedExp(Integer beRepliedExp) {
            this.beRepliedExp = beRepliedExp;
        }

        public String getNoticeTitle() {
            return noticeTitle;
        }

        public void setNoticeTitle(String noticeTitle) {
            this.noticeTitle = noticeTitle;
        }

        public String getMessageTemplate() {
            return messageTemplate;
        }

        public void setMessageTemplate(String messageTemplate) {
            this.messageTemplate = messageTemplate;
        }

        public Integer getReplyExp() {
            return replyExp;
        }

        public void setReplyExp(Integer replyExp) {
            this.replyExp = replyExp;
        }

        public Integer getCreateExp() {
            return createExp;
        }

        public void setCreateExp(Integer createExp) {
            this.createExp = createExp;
        }

        public Integer getBeLikedExp() {
            return beLikedExp;
        }

        public void setBeLikedExp(Integer beLikedExp) {
            this.beLikedExp = beLikedExp;
        }

        public Integer getMaxContentSize() {
            return maxContentSize;
        }

        public void setMaxContentSize(Integer maxContentSize) {
            this.maxContentSize = maxContentSize;
        }

        public String getLikeNotificationMsg() {
            return likeNotificationMsg;
        }

        public void setLikeNotificationMsg(String likeNotificationMsg) {
            this.likeNotificationMsg = likeNotificationMsg;
        }

        public String getTopicReadUrlPrefix() {
            return topicReadUrlPrefix;
        }

        public void setTopicReadUrlPrefix(String topicReadUrlPrefix) {
            this.topicReadUrlPrefix = topicReadUrlPrefix;
        }

        public String getTopicCreateUrlPrefix() {
            return topicCreateUrlPrefix;
        }

        public void setTopicCreateUrlPrefix(String topicCreateUrlPrefix) {
            this.topicCreateUrlPrefix = topicCreateUrlPrefix;
        }
    }

    public static class Async {
        private Integer corePoolSize;
        private Integer maxPoolSize;
        private Integer queueCapacity;

        public Integer getCorePoolSize() {
            return corePoolSize;
        }

        public void setCorePoolSize(Integer corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public Integer getMaxPoolSize() {
            return maxPoolSize;
        }

        public void setMaxPoolSize(Integer maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

        public Integer getQueueCapacity() {
            return queueCapacity;
        }

        public void setQueueCapacity(Integer queueCapacity) {
            this.queueCapacity = queueCapacity;
        }
    }
}
