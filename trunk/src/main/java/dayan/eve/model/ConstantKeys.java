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
 * @author xsg
 */
public final class ConstantKeys {

//    public static final String[] JOBTYPES=["Clerk","Intern","Leafleteer","Promotion","Receptionist","Tutor","Waiter","Others"];

    public final static Integer[] ADVISORY_SCHOOL_IDS = {162, 148, 161, 158};//当前app首页默认四所可以咨询的学校

    public static final String GAOKAODATE = "2016-06-08";

    public static final String UTF8MB4 = "utf8mb4";

    public static final String AVATAR_START = "/open/avatar/";
    public static final String AVATAR_END = "/img";

    public static final String EVE_PLATFORM_CN = "小言高考";

    public static final String[] SUBJECT_TYPES = {"文科", "理科"};

    public static final Integer DEFAULT_PROVINCE_ID = 11;
    public static final String DEFAULT_SUBJECT_TYPE = "理科";
    public static final int SEGMENT_DIFF = 2;//分段上下浮动值

    public static final int PERSONAL_SCHOOL_ICON_NUM = 4;//学校图标默认取四个


    public static final Integer[] TAG_VALUES = {1, 2, 4, 8, 16, 32};
    public static final String[] SCHOOL_TAGS = {"211", "985", "研究生院", "自主招生", "国防生", "卓越计划"};


    public static class Sign {

        public static final String START_TIME = " 04:30:00";
        public static final String END_TIME = " 18:00:00";
    }

    public static class SchoolDetail {

        public final static String[] SCHOOL_TYPES = {"综合类", "理工类", "财经类", "农林类", "林业类",
                "医药类", "师范类", "体育类", "语文类", "政法类", "艺术类", "民族类", "军事类", "商学类", "语言类"};

        //学校概况--详情
        public static final String SchoolBadgeLogoType = ".png";
        public static final String SchoolProfile = "schoolProfile";
        public static final String Address = "address";
        public static final String LogoUrl = "logoUrl";
        public static final String Tags = "tags";
        public static final String SchoolHashId = "schoolHashId";
        public static final String Name = "name";
        public static final String Belong = "belong";
        public static final String CaeNum = "caeNum";
        public static final String DdgNum = "ddgNum";
        public static final String Employment = "employment";
        public static final String FoundDate = "foundDate";
        public static final String GenderRaTio = "genderRaTio";
        public static final String Intro = "intro";
        public static final String KeySubjectNum = "keySubjectNum";
        public static final String MdgNum = "mdgNum";
        public static final String StudentNum = "studentNum";
        public static final String StudentSource = "studentSource";
        public static final String Tuition = "tuition";
        public static final String Type = "type";

        //学校概况--招生
        public static final String SchoolAddmission = "schoolAddmission";
        public static final String AdmissionUrl = "admissionUrl";
        public static final String AdmissonPhone = "admissonPhone";

        //学校概况--历年分数线
        public static final String SchoolScore = "schoolScore";
        public static final String AvgScore = "avgScore";
        public static final String Batch = "batch";
        public static final String ControlLine = "controlLine";
        public static final String MinScore = "minScore";

        //学校概况--开设专业
        public static final String MajorSubjectTypes = "majorSubjectTypes";
        public static final String MajorSubjectType = "majorSubjectType";
        public static final String MajorNum = "majorNum";
        public static final String MajorHashId = "majorHashId";
        public static final String MajorName = "majorName";
        public static final String Majors = "majors";
    }

    public static class SearchSchool {

        public static final String Schools = "schools";
        public static final String PlatformHashId = "platformHashId";
        public static final String Address = "address";
        public static final String LogoUrl = "logoUrl";
        public static final String Tags = "tags";
        public static final String SchoolHashId = "schoolHashId";
        public static final String Name = "name";
        public static final String Pager = "pager";
        public static final String Like = "%";
        public static final String[] SchoolTags = {"211", "985", "研究生院", "自主招生", "国防生", "卓越计划"};

    }

    public static class SearchMajor {

        public static final String MajorHashId = "majorHashId";
        public static final String MajorName = "majorName";
        public static final String DegreeType = "degreeType";
        public static final String SubjectType = "subjectType";
        public static final String MajorType = "majorType";
        public static final String MajorCode = "majorCode";
        public static final String Majors = "majors";
        public static final String Pager = "pager";
        public static final String Like = "%";
        public static final String[] DegreeTypes = {"本科专业", "高职专科专业"};

    }

    public static class Hol {

        //霍兰德--性格推荐职业大类
        public static final String JobClass = "jobClass";
        public static final String JobClassName = "jobClassName";
        public static final String JobClassCode = "jobClassCode";

        //霍兰德--职业大类推荐专业大类
        public static final String MajorTypes = "majorTypes";
        public static final String MajorType = "majorType";
        public static final String MajorTypeCode = "majorTypeCode";
        public static final String MajorTypeIntro = "majorTypeIntro";

        //霍兰德--专业大类推荐专业
        public static final String Majors = "majors";
        public static final String MajorHashId = "majorHashId";
        public static final String MajorName = "majorName";
        public static final String MajorCode = "majorCode";

        public static final String Pager = "pager";

    }

    public static class Course {
        //MBTI

        public static final String MI_TITLE_PREFIX = "多元智能测试——";
        public static final String[] MBTI_OPTIONS = {"A", "B"};
        public static final int[] MBTI_TYPE_A_E = {4, 8, 14, 19, 23, 34, 62, 67, 77};
        public static final int[] MBTI_TYPE_A_I = {12, 18, 22, 26, 27, 35, 42, 48, 54, 60, 66, 72};
        public static final int[] MBTI_TYPE_A_S = {3, 13, 32, 40, 47, 53, 58, 61, 73, 82, 86, 90, 93};
        public static final int[] MBTI_TYPE_A_N = {5, 15, 24, 29, 37, 44, 50, 55, 63, 74, 79, 83, 87};
        public static final int[] MBTI_TYPE_A_T = {31, 39, 46, 52, 57, 69, 78, 81, 85, 89, 92};
        public static final int[] MBTI_TYPE_A_F = {6, 16, 30, 38, 45, 51, 56, 64, 75, 80, 84, 88, 91};
        public static final int[] MBTI_TYPE_A_J = {1, 9, 10, 20, 28, 36, 43, 49, 59, 68, 70};
        public static final int[] MBTI_TYPE_A_P = {2, 7, 11, 17, 21, 25, 33, 41, 65, 71, 76};
        public static final int[] MBTI_TYPE_B_E = {12, 18, 22, 26, 27, 35, 42, 48, 54, 60, 66, 72};
        public static final int[] MBTI_TYPE_B_I = {4, 8, 14, 19, 23, 34, 62, 67, 77};
        public static final int[] MBTI_TYPE_B_S = {5, 15, 24, 29, 37, 44, 50, 55, 63, 74, 79, 83, 87};
        public static final int[] MBTI_TYPE_B_N = {3, 13, 32, 40, 47, 53, 58, 61, 73, 82, 86, 90, 93};
        public static final int[] MBTI_TYPE_B_T = {6, 16, 30, 38, 45, 51, 56, 64, 75, 80, 84, 88, 91};
        public static final int[] MBTI_TYPE_B_F = {31, 39, 46, 52, 57, 69, 78, 81, 85, 89, 92};
        public static final int[] MBTI_TYPE_B_J = {2, 7, 11, 17, 21, 25, 33, 41, 65, 71, 76};
        public static final int[] MBTI_TYPE_B_P = {1, 9, 10, 20, 28, 36, 43, 49, 59, 68, 70};

        //多元智能
        public static final String[] MI_OPTION_TYPES = {"语言", "逻辑数学", "视觉空间", "身体运动", "音乐", "人际交往", "自我认知", "自然观察者"};
        public static final String[] MI_OPTIONS = {"A. 非常符合", "B. 比较符合", "C. 说不清", "D. 不太符合", "E. 非常不符合"};

        //霍兰德
        public static final String[] HOL_OPTION_TYPES = {"常规型", "现实型", "研究型", "艺术型", "社会型", "企业型"};
        public static final String[] HOL_OPTION_TYPES_EN = {"C", "R", "I", "A", "S", "E"};
        public static final String[] HOL_OPTIONS = {"A.是", "B.不是"};

        //职业锚
        public static final String[] CA_OPTION_TYPES = {"TF 技术/职能型", "GM 管理型", "AU 自主/独立型", "SE 安全/定型",
                "EC 创造/创业型", "SV 服务/奉献型", "CH 挑战型", "LS 生活型"};
        public static final String[] CA_OPTION_TYPES_EN = {"TF", "GM", "AU", "SE", "EC", "SV", "CH", "LS"};
        public static final String[] CA_OPTIONS = {"A. 从不", "B. 偶尔", "C. 有时", "D. 经常", "E. 频繁", "F. 总是"};
    }

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
