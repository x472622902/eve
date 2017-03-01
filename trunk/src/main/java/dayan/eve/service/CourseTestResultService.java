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
package dayan.eve.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import dayan.eve.config.EveProperties;
import dayan.eve.exception.ErrorCN;
import dayan.eve.model.ConstantKeys;
import dayan.eve.model.PageResult;
import dayan.eve.model.Pager;
import dayan.eve.model.course.CDKey;
import dayan.eve.model.course.CourseTestResult;
import dayan.eve.model.course.Option;
import dayan.eve.model.query.CourseQuery;
import dayan.eve.model.query.CourseTestResultQuery;
import dayan.eve.repository.course.CourseCDKeyRepository;
import dayan.eve.repository.course.CoursePersonalityRepository;
import dayan.eve.repository.course.CourseTestRepository;
import dayan.eve.repository.course.CourseTradeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CourseTestResultService {

    private final CourseCDKeyRepository courseCDKeyRepository;
    private final CourseTradeRepository courseTradeRepository;
    private final CourseTestRepository courseTestRepository;
    private final CoursePersonalityRepository coursePersonalityRepository;
    private final String TEST_RESULT_URL;

    @Autowired
    public CourseTestResultService(CoursePersonalityRepository coursePersonalityRepository, CourseTradeRepository courseTradeRepository, CourseTestRepository courseTestRepository, CourseCDKeyRepository courseCDKeyRepository,
                                   EveProperties eveProperties) {
        this.coursePersonalityRepository = coursePersonalityRepository;
        this.courseTradeRepository = courseTradeRepository;
        this.courseTestRepository = courseTestRepository;
        this.courseCDKeyRepository = courseCDKeyRepository;
        this.TEST_RESULT_URL = eveProperties.getCourse().getResult();
    }

    public Integer submit(CourseTestResultQuery query) {
        //先判断是否已购买了课程
        CourseQuery courseQuery = new CourseQuery();
        if (!StringUtils.isEmpty(query.getCdkey())) {
            CDKey cdkey = new CDKey();
            cdkey.setCode(query.getCdkey());
            List<CDKey> keys = courseCDKeyRepository.query(cdkey);
            if (keys == null || keys.isEmpty()) {
                throw new RuntimeException(ErrorCN.Course.CD_KEY_NOT_FOUND);
            }
            courseQuery.setCdkeyId(keys.get(0).getId());
        }
        courseQuery.setAccountId(query.getAccountId());
        courseQuery.setCourseId(query.getCourseId());
        if (!courseTradeRepository.queryExisted(courseQuery)) {
            throw new RuntimeException(ErrorCN.Course.COURSE_NOT_BOUGHT);
        }
        CourseTestResult testResult = new CourseTestResult();
        analyse(query.getOptions(), testResult, query.getTestType());
        testResult.setOptionsStr(JSON.toJSONString(query.getOptions()));
        testResult.setAccountId(query.getAccountId());
        testResult.setCourseId(query.getCourseId());
        testResult.setCdkey(query.getCdkey());
        if (!StringUtils.isEmpty(testResult.getPersonalityCode())) {
            testResult.setPersonalityId(coursePersonalityRepository.queryId(testResult.getPersonalityCode()));
        }
        courseTestRepository.insert(testResult);
        return testResult.getId();
    }

    public PageResult<CourseTestResult> readMy(CourseTestResultQuery query) {
        Integer count = courseTestRepository.count(query);
        PageResult<CourseTestResult> pageResult = new PageResult<>(Collections.emptyList(), new Pager(count, query
                .getPage(), query.getSize()));

        if (count != 0) {
            List<CourseTestResult> results = courseTestRepository.query(query);
            results.forEach(result -> result.setUrl(TEST_RESULT_URL));
            pageResult.setList(results);
        }
        return pageResult;
    }

    public CourseTestResult readAnalysis(Integer testId) {
        CourseTestResult detail = courseTestRepository.queryDetail(testId);
        List<Option> options = JSONArray.parseArray(detail.getOptionsStr(), Option.class);
        analyse(options, detail, detail.getTestType());
        detail.setOptionsStr(null);
        return detail;
    }

    private void analyseMBTI(List<Option> options, CourseTestResult testResult) {
        CourseTestResult.MBTI mbti = new CourseTestResult().new MBTI();
        int EI = 0;
        int SN = 0;
        int TF = 0;
        int JP = 0;
        for (Option option : options) {
            switch (option.getType()) {
                case "E":
                    EI++;
                    break;
                case "S":
                    SN++;
                    break;
                case "T":
                    TF++;
                    break;
                case "J":
                    JP++;
                    break;
                case "I":
                    EI--;
                    break;
                case "N":
                    SN--;
                    break;
                case "F":
                    TF--;
                    break;
                case "P":
                    JP--;
                    break;
            }
        }
        mbti.setEi(EI);
        mbti.setJp(JP);
        mbti.setSn(SN);
        mbti.setTf(TF);
        testResult.setMbti(mbti);
        testResult.setPersonalityCode((EI > 0 ? "E" : "I") + (SN > 0 ? "S" : "N") + (TF > 0 ? "T" : "F") + (JP > 0 ? "J" : "P"));
    }

    /*
     *多元智能测试结果分析
     */
    private void analyseMI(List<Option> options, CourseTestResult detail) {
        int vl = 0;//语言
        int lm = 0;//逻辑数学
        int vs = 0;//空间
        int bk = 0;//身体运动
        int mr = 0;//音乐
        int is = 0;//人际交往
        int ii = 0;//自我认识
        int na = 0;//自然观察
        for (Option option : options) {
            switch (option.getType()) {
                case "语言":
                    vl += option.getValue();
                    break;
                case "逻辑数学":
                    lm += option.getValue();
                    break;
                case "视觉空间":
                    vs += option.getValue();
                    break;
                case "身体运动":
                    bk += option.getValue();
                    break;
                case "音乐":
                    mr += option.getValue();
                    break;
                case "人际交往":
                    is += option.getValue();
                    break;
                case "自我认知":
                    ii += option.getValue();
                    break;
                case "自然观察者":
                    na += option.getValue();
                    break;
            }
        }
        CourseTestResult.MI mI = new CourseTestResult().new MI(vl, lm, vs, bk, mr, is, ii, na);
        detail.setMi(mI);
        detail.setPersonality(ConstantKeys.Course.MI_TITLE_PREFIX + mI.topThree());
        detail.setPersonalityCode("MI");
    }

    private void analyse(List<Option> options, CourseTestResult detail, String type) {
        switch (type) {
            case "MBTI":
                analyseMBTI(options, detail);
                break;
            case "MI":
                analyseMI(options, detail);
                break;
            case "HOL":
                analyseHOL(options, detail);
                break;
            case "CA":
                analyseCA(options, detail);
                break;
        }
    }

    private void analyseHOL(List<Option> options, CourseTestResult detail) {
        int r = 0;
        int i = 0;
        int a = 0;
        int s = 0;
        int e = 0;
        int c = 0;
        for (Option option : options) {
            switch (option.getType()) {
                case "R":
                    r += option.getValue();
                    break;
                case "I":
                    i += option.getValue();
                    break;
                case "A":
                    a += option.getValue();
                    break;
                case "S":
                    s += option.getValue();
                    break;
                case "E":
                    e += option.getValue();
                    break;
                case "C":
                    c += option.getValue();
                    break;
            }
        }
        CourseTestResult.HOL hol = new CourseTestResult().new HOL(r, i, s, a, e, c);
        detail.setHol(hol);
        detail.setPersonalityCode(hol.getPersonalityCode());
    }

    private void analyseCA(List<Option> options, CourseTestResult detail) {
        int tf = 0;
        int gm = 0;
        int au = 0;
        int se = 0;
        int ec = 0;
        int sv = 0;
        int ch = 0;
        int ls = 0;
        for (Option option : options) {
            switch (option.getType()) {
                case "TF":
                    tf += option.getValue();
                    break;
                case "GM":
                    gm += option.getValue();
                    break;
                case "AU":
                    au += option.getValue();
                    break;
                case "SE":
                    se += option.getValue();
                    break;
                case "EC":
                    ec += option.getValue();
                    break;
                case "SV":
                    sv += option.getValue();
                    break;
                case "CH":
                    ch += option.getValue();
                    break;
                case "LS":
                    ls += option.getValue();
                    break;
            }
        }
        CourseTestResult.CA ca = detail.new CA(tf, gm, au, se, ec, sv, ch, ls);
        detail.setCa(ca);
        detail.setPersonalityCode(ca.getPersonalityCode());
    }

    //todo 后台功能
    public List<CourseTestResult> readResults(CourseTestResultQuery query) {
        return coursePersonalityRepository.queryDetails(query);
    }

    public void updatePersonality(CourseTestResult result) {
        coursePersonalityRepository.update(result);
    }
}
