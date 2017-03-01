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
package dayan.eve.model.course;

import dayan.eve.model.ConstantKeys;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author xsg
 */
public class CourseTestResult {

    private Integer id;
    private Integer accountId;
    private Integer courseId;
    private String courseName;
    private String cdkey;
    private String testType;
    private String personalityCode;//人格代码
    private String personality;//人格介绍
    private Integer personalityId;
    private String description;//人格分析
    private Date testDate;
    private MBTI mbti;
    private MI mi;
    private HOL hol;
    private CA ca;
    private String url;

    public CA getCa() {
        return ca;
    }

    public void setCa(CA ca) {
        this.ca = ca;
    }

    public HOL getHol() {
        return hol;
    }

    public void setHol(HOL hol) {
        this.hol = hol;
    }

    public MI getMi() {
        return mi;
    }

    public void setMi(MI mi) {
        this.mi = mi;
    }

    private String questionType;//问题类型

    public Integer getPersonalityId() {
        return personalityId;
    }

    public void setPersonalityId(Integer personalityId) {
        this.personalityId = personalityId;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String optionsStr;

    public String getOptionsStr() {
        return optionsStr;
    }

    public void setOptionsStr(String optionsStr) {
        this.optionsStr = optionsStr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCdkey() {
        return cdkey;
    }

    public void setCdkey(String cdkey) {
        this.cdkey = cdkey;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getPersonalityCode() {
        return personalityCode;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public void setPersonalityCode(String personalityCode) {
        this.personalityCode = personalityCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public class MBTI {

        int ei = 0;
        int sn = 0;
        int tf = 0;
        int jp = 0;

        public int getEi() {
            return ei;
        }

        public void setEi(int ei) {
            this.ei = ei;
        }

        public int getSn() {
            return sn;
        }

        public void setSn(int sn) {
            this.sn = sn;
        }

        public int getTf() {
            return tf;
        }

        public void setTf(int tf) {
            this.tf = tf;
        }

        public int getJp() {
            return jp;
        }

        public void setJp(int jp) {
            this.jp = jp;
        }

    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public MBTI getMbti() {
        return mbti;
    }

    public void setMbti(MBTI mbti) {
        this.mbti = mbti;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public class MI {

        public MI() {
        }

        public MI(int vl, int lm, int vs, int bk, int mr, int is, int ii, int na) {
            this.vl = vl;
            this.lm = lm;
            this.vs = vs;
            this.bk = bk;
            this.mr = mr;
            this.is = is;
            this.ii = ii;
            this.na = na;
        }

        private int vl;//语言
        private int lm;//逻辑数学
        private int vs;//空间
        private int bk;//身体运动
        private int mr;//音乐
        private int is;//人际
        private int ii;//自我认知
        private int na;//自然观察

        public int getVl() {
            return vl;
        }

        public void setVl(int vl) {
            this.vl = vl;
        }

        public int getLm() {
            return lm;
        }

        public void setLm(int lm) {
            this.lm = lm;
        }

        public int getVs() {
            return vs;
        }

        public void setVs(int vs) {
            this.vs = vs;
        }

        public int getBk() {
            return bk;
        }

        public void setBk(int bk) {
            this.bk = bk;
        }

        public int getMr() {
            return mr;
        }

        public void setMr(int mr) {
            this.mr = mr;
        }

        public int getIs() {
            return is;
        }

        public void setIs(int is) {
            this.is = is;
        }

        public int getIi() {
            return ii;
        }

        public void setIi(int ii) {
            this.ii = ii;
        }

        public int getNa() {
            return na;
        }

        public void setNa(int na) {
            this.na = na;
        }

        @Override
        public String toString() {
            return "语言：" + vl + "  逻辑数学：" + lm + "  视觉空间：" + vs + "  身体运动：" + bk + "  音乐：" + mr + "  人际交往：" + is + "  自我认知：" + ii + "  自然观察者：" + na;
        }

        public String topThree() {
            List<Option> list = new LinkedList<>();
            int[] valueArray = getValueList();
            for (int i = 0; i < 8; i++) {
                Option o = new Option();
                o.setValue(valueArray[i]);
                o.setContent(ConstantKeys.Course.MI_OPTION_TYPES[i]);
                list.add(o);
            }
            Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
            String result = "";
            for (int i = 0; i < 3; i++) {
                result += list.get(i).getContent() + ":" + list.get(i).getValue() * 4 + "  ";
            }

            return result;
        }

        public int[] getValueList() {
            int[] array = new int[8];
            array[0] = vl;
            array[1] = lm;
            array[2] = vs;
            array[3] = bk;
            array[4] = mr;
            array[5] = is;
            array[6] = ii;
            array[7] = na;
            return array;
        }
    }

    public class HOL {

        int r;
        int i;
        int a;
        int s;
        int e;
        int c;
        String personality;

        public HOL(int r, int i, int a, int s, int e, int c) {
            this.r = r;//现实型
            this.i = i;//研究型
            this.a = a;//艺术型
            this.s = s;//社会型
            this.e = e;//企业型
            this.c = c;//传统型
        }

        public HOL() {
        }

        public int getR() {
            return r;
        }

        public void setR(int r) {
            this.r = r;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public int getS() {
            return s;
        }

        public void setS(int s) {
            this.s = s;
        }

        public int getE() {
            return e;
        }

        public void setE(int e) {
            this.e = e;
        }

        public int getC() {
            return c;
        }

        public void setC(int c) {
            this.c = c;
        }

        public String getPersonalityCode() {
            List<Option> list = new LinkedList<>();
            int[] array = new int[6];
            array[0] = r;
            array[1] = i;
            array[2] = a;
            array[3] = s;
            array[4] = e;
            array[5] = c;
            for (int j = 0; j < 6; j++) {
                Option o = new Option();
                o.setType(ConstantKeys.Course.HOL_OPTION_TYPES_EN[j]);
                o.setValue(array[j]);
                list.add(o);
            }
            Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
            return list.get(0).getType();
        }
    }

    public class CA {

        int tf;
        int gm;
        int au;
        int se;
        int ec;
        int sv;
        int ch;
        int ls;

        public CA(int tf, int gm, int au, int se, int ec, int sv, int ch, int ls) {
            this.tf = tf;
            this.gm = gm;
            this.au = au;
            this.se = se;
            this.ec = ec;
            this.sv = sv;
            this.ch = ch;
            this.ls = ls;
        }

        public CA() {
        }

        public String getPersonalityCode() {
            List<Option> list = new LinkedList<>();
            int[] array = new int[8];
            array[0] = tf;
            array[1] = gm;
            array[2] = au;
            array[3] = se;
            array[4] = ec;
            array[5] = sv;
            array[6] = ch;
            array[7] = ls;
            for (int j = 0; j < array.length; j++) {
                Option o = new Option();
                o.setType(ConstantKeys.Course.CA_OPTION_TYPES_EN[j]);
                o.setValue(array[j]);
                list.add(o);
            }
            Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
            return list.get(0).getType();
        }

        public int getTf() {
            return tf;
        }

        public void setTf(int tf) {
            this.tf = tf;
        }

        public int getGm() {
            return gm;
        }

        public void setGm(int gm) {
            this.gm = gm;
        }

        public int getAu() {
            return au;
        }

        public void setAu(int au) {
            this.au = au;
        }

        public int getSe() {
            return se;
        }

        public void setSe(int se) {
            this.se = se;
        }

        public int getEc() {
            return ec;
        }

        public void setEc(int ec) {
            this.ec = ec;
        }

        public int getSv() {
            return sv;
        }

        public void setSv(int sv) {
            this.sv = sv;
        }

        public int getCh() {
            return ch;
        }

        public void setCh(int ch) {
            this.ch = ch;
        }

        public int getLs() {
            return ls;
        }

        public void setLs(int ls) {
            this.ls = ls;
        }

    }
}
