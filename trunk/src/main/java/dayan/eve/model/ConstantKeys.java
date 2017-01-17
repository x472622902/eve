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
package dayan.eve.model;

/**
 *
 * @author xsg
 */
public final class ConstantKeys {
    
//    public static final String[] JOBTYPES=["Clerk","Intern","Leafleteer","Promotion","Receptionist","Tutor","Waiter","Others"];
    
    public final static Integer[] ADVISORYSCHOOLIDS = {162,148,161,158};//当前app首页默认四所可以咨询的学校
    
    public static final String GAOKAODATE = "2016-06-08";

    public static final String UTF8MB4 = "utf8mb4";
    
    public static final String AVATAR_START = "/open/avatar/";
    public static final String AVATAR_END = "/img";

    public static final String EVE_PLATFORM = "小言高考";

    public static class Sign {

        public static final String StartTime = " 04:30:00";
        public static final String EndTime = " 18:00:00";
    }

    public static class SchoolDetail {

        public final static String[] SchoolTypes = {"综合类", "理工类", "财经类", "农林类", "林业类",
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
