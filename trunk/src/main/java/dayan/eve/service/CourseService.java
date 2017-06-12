/**
 * *****************************************************
 * Copyright (C) Dayan techology Co.ltd - All Rights Reserved
 * <p/>
 * This file is part of Dayan techology Co.ltd property.
 * <p/>
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * *****************************************************
 */
package dayan.eve.service;

import com.alibaba.fastjson.JSONArray;
import dayan.eve.model.PageResult;
import dayan.eve.model.Pager;
import dayan.eve.model.course.*;
import dayan.eve.model.enumeration.CourseType;
import dayan.eve.model.query.CourseQuery;
import dayan.eve.repository.course.CourseCDKeyRepository;
import dayan.eve.repository.course.CourseRepository;
import dayan.eve.repository.course.CourseTestRepository;
import dayan.eve.repository.course.CourseTradeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static dayan.eve.exception.ErrorCN.Course.*;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseTradeRepository courseTradeRepository;
    private final CourseTestRepository courseTestRepository;
    private final CourseCDKeyRepository courseCDKeyRepository;
    private final CDKeyService cDKeyService;

    @Autowired
    public CourseService(CourseTestRepository courseTestRepository, CourseCDKeyRepository courseCDKeyRepository, CourseTradeRepository courseTradeRepository, CourseRepository courseRepository, CDKeyService cDKeyService) {
        this.courseTestRepository = courseTestRepository;
        this.courseCDKeyRepository = courseCDKeyRepository;
        this.courseTradeRepository = courseTradeRepository;
        this.courseRepository = courseRepository;
        this.cDKeyService = cDKeyService;
    }

    public PageResult<Course> read(CourseQuery query) {
        CDKey cDKey = new CDKey();

        if (!StringUtils.isEmpty(query.getCdkey())) {
            cDKey.setCode(query.getCdkey());
            List<CDKey> keys = courseCDKeyRepository.query(cDKey);
            if (keys == null || keys.isEmpty()) {
                throw new RuntimeException(NO_CD_KEY);
            }
            cDKey = keys.get(0);
            if (!cDKey.getIsRedeemed()) {
                throw new RuntimeException(COURSE_NOT_BOUGHT);
            }
            if (query.getCourseId() == null) {
                query.setCourseId(cDKey.getCourseId());
            }
        }
        Integer count = courseRepository.count(query);
        PageResult<Course> pageResult = new PageResult<>(count, query.getPage(), query.getSize());
        if (count > 0) {
            List<Course> list = courseRepository.query(query);
            if (!StringUtils.isEmpty(query.getCdkey())) {
                list.get(0).setIsBought(Boolean.TRUE);
            }
            pageResult.setList(list);
        }
        return pageResult;
    }

    public PageResult<Course> readMy(CourseQuery query) {
        Integer count = 0;
        if (query.getAccountId() != null) {
            count = courseRepository.count(query);
        }
        PageResult<Course> pageResult = new PageResult<>(count, query.getPage(), query.getSize());
        if (count > 0) {
            pageResult.setList(courseRepository.queryMy(query));
        }
        return pageResult;
    }

    public Pager count(CourseQuery query) {
        Pager pager = new Pager();
        Integer count;
        if (query.getReadMy()) {
            count = courseRepository.countMy(query);
        } else {
            count = courseRepository.count(query);
        }
        pager.setCount(count);
        pager.setPage(query.getPage());
        pager.setSize(query.getSize());
        return pager;
    }

    private void checkBought(List<Course> list, List<Integer> courseIds) {
        if (courseIds == null || courseIds.isEmpty()) {
            return;
        }
        list.stream().filter(c -> courseIds.contains(c.getId())).forEachOrdered(c -> {
            c.setIsBought(Boolean.TRUE);
        });
    }

    private void checkTested(List<Course> list, List<CourseTestResult> courseIds) {
        if (courseIds == null || courseIds.isEmpty()) {
            return;
        }
        //创建课程id与测试结果id对应的map
        Map<Integer, Integer> courseIdResultIdMap = new HashMap<>();
        for (CourseTestResult tr : courseIds) {
            courseIdResultIdMap.put(tr.getCourseId(), tr.getId());
        }
        list.stream()
                .filter(c -> courseIdResultIdMap.containsKey(c.getId()))
                .forEach(c -> {
                    c.setIsTested(Boolean.TRUE);
                    c.setTestResultId(courseIdResultIdMap.get(c.getId()));
                });
    }

    public List<Payment> readPayments(Integer courseId) {
        List<Payment> list = new LinkedList<>();
        CourseQuery query = new CourseQuery();
        query.setCourseId(courseId);
        Course course = courseRepository.query(query).get(0);
        list.add(new Payment("单独购买", course.getPrice(), course.getName()));
        return list;
    }


    public Integer redeem(CourseQuery query) {
        CDKey cDKey = new CDKey();
        cDKey.setCode(query.getCdkey());
        List<CDKey> codes = courseCDKeyRepository.query(cDKey);
        if (codes == null || codes.isEmpty()) {
            throw new RuntimeException(CD_KEY_NOT_FOUND);
        }
        CDKey code = codes.get(0);
        if (query.getCourseId() != null && !Objects.equals(code.getCourseId(), query.getCourseId())) {
            throw new RuntimeException(CD_KEY_NOT_SUIT);
        }
        if (query.getCourseId() == null) {
            query.setCourseId(code.getCourseId());
        }
        query.setCdkeyId(code.getId());
        CourseQuery newQuery = new CourseQuery();
        newQuery.setCdkeyId(code.getId());
        List<CourseQuery> trades = courseTradeRepository.queryTrades(newQuery);

        //判断是不是bundle类型的
        boolean isBundle = checkIsBundle(cDKey.getCode());
        if (trades == null || trades.isEmpty()) {
            if (isBundle) {
                insertBundle(query.getAccountId(), code);
            } else {
                courseTradeRepository.insert(query);
            }
        } else {
            if (trades.get(0).getAccountId() != null) {
                throw new RuntimeException(COURSE_BOUGHT);
            } else if (trades.get(0).getAccountId() == null && query.getAccountId() != null) {
                CourseQuery newQuery2 = new CourseQuery();
                newQuery2.setAccountId(query.getAccountId());
                newQuery2.setCdkeyId(code.getId());
                courseTradeRepository.update(newQuery2);
            }
        }
        if (!code.getIsRedeemed()) {
            cDKey.setId(code.getId());
            cDKey.setIsRedeemed(Boolean.TRUE);
            //兑换码更新为已兑换
            courseCDKeyRepository.update(cDKey);
        }

        courseRepository.updateBuyCount(query.getCourseId());
        return code.getCourseId();
    }

    private void insertBundle(Integer accountId, CDKey code) {
        CourseQuery query = new CourseQuery();
        query.setCourseId(code.getCourseId());
        List<Integer> list = JSONArray.parseArray(courseRepository.query(query).get(0).getBundleIdsStr(), Integer
                .class);
        list.add(code.getCourseId());
        CourseQuery newQuery = new CourseQuery();
        newQuery.setCdkeyId(code.getId());
        newQuery.setAccountId(accountId);
        for (Integer courseId : list) {
            newQuery.setCourseId(courseId);
            courseTradeRepository.insert(newQuery);
        }
    }

    public void buy(CourseTrade courseTrade) {
        if (courseTradeRepository.queryExisted(new CourseQuery(courseTrade.getAccountId(), courseTrade.getCourseId()))) {
            throw new RuntimeException(COURSE_BOUGHT);
        }
        buySingle(courseTrade);
        if (courseRepository.queryType(courseTrade.getCourseId()).equals(CourseType.Bundle.name())) {
            buyBundle(courseTrade, courseRepository.queryBundleIds(courseTrade.getCourseId()));
        }
    }

    private void buySingle(CourseTrade courseTrade) {
        String cdkeyCode = cDKeyService.getCDKey(courseTrade.getCourseId(), 1).get(0);
        CDKey cdkey = new CDKey();
        cdkey.setCode(cdkeyCode);
        Integer codeId = courseCDKeyRepository.query(cdkey).get(0).getId();
        cdkey.setId(codeId);
        cdkey.setIsRedeemed(Boolean.TRUE);
        courseCDKeyRepository.update(cdkey);
        courseTrade.setCdkeyId(codeId);
        courseTradeRepository.insertAlipayTrade(courseTrade);
        courseRepository.updateBuyCount(courseTrade.getCourseId());
    }

    private void buyBundle(CourseTrade courseTrade, String containIdsStr) {

        List<Integer> list = JSONArray.parseArray(containIdsStr, Integer.class);
        CourseQuery newQuery = new CourseQuery();
        newQuery.setAccountId(courseTrade.getAccountId());
        for (Integer courseId : list) {
            newQuery.setCourseId(courseId);
            if (courseTradeRepository.queryExisted(newQuery)) {
                continue;
            }
            courseTrade.setCourseId(courseId);
            buySingle(courseTrade);
        }
    }

    private boolean checkIsBundle(String code) {
        CDKey key = new CDKey();
        key.setCode(code);
        key.setCourseType(CourseType.Bundle);
        Integer id = courseCDKeyRepository.queryId(key);
        return id != null;
    }

    // TODO: 2/8/2017 后台上传（课程、图片等） 看eve源代码

}
