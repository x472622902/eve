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

import com.alibaba.fastjson.JSONArray;
import dayan.eve.model.course.CDKey;
import dayan.eve.model.course.Course;
import dayan.eve.model.query.CourseQuery;
import dayan.eve.repository.course.CourseCDKeyRepository;
import dayan.eve.repository.course.CourseRepository;
import dayan.eve.util.CDKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CDKeyService {

    private final CourseCDKeyRepository courseCDKeyRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public CDKeyService(CourseRepository courseRepository, CourseCDKeyRepository courseCDKeyRepository) {
        this.courseRepository = courseRepository;
        this.courseCDKeyRepository = courseCDKeyRepository;
    }

    public List<String> getCDKey(Integer courseId, Integer number) {
        List<String> keys = new LinkedList<>();
        List<CDKey> list = new LinkedList<>();
        for (int i = 0; i < number; i++) {
            CDKey key = new CDKey();
            key.setCourseId(courseId);
            key.setCode(CDKeyUtil.createCDKey());
            list.add(key);
            keys.add(key.getCode());
        }
        try {
            courseCDKeyRepository.multiInsert(list);
        } catch (Exception e) {
            throw new RuntimeException("兑换码重复,请重试！");
        }
        return keys;
    }


    public void updateBundle(CDKey cDKey) {
        CourseQuery query = new CourseQuery();
        query.setCourseId(cDKey.getCourseId());
        List<Course> courses = courseRepository.query(query);
        Course c = courses.get(0);
        List<Integer> list = JSONArray.parseArray(c.getBundleIdsStr(), Integer.class);
        List<CDKey> keys = new LinkedList<>();
        for (Integer courseId : list) {
            CDKey key = new CDKey();
            key.setCode(cDKey.getCode());
            key.setCourseId(courseId);
            key.setAccountId(cDKey.getAccountId());
            keys.add(key);
        }
        courseCDKeyRepository.multiInsert(keys);
        cDKey.setIsRedeemed(Boolean.TRUE);
        courseCDKeyRepository.update(cDKey);
    }
}
