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

import dayan.common.util.SchoolPlatformIdEncoder;
import dayan.eve.model.major.Major;
import dayan.eve.model.major.MajorProfile;
import dayan.eve.repository.MajorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author xsg
 */
@Service
@Transactional
public class MajorProfileService {

    @Autowired
    MajorRepository majorRepository;
    private SchoolPlatformIdEncoder idEncoder = new SchoolPlatformIdEncoder();

    //获取本专业信息
    private Major getMajor(Integer majorId) {
        Major major = majorRepository.queryById(majorId);
        major.setHashId(idEncoder.encrypt(majorId.longValue()));
        return major;
    }

    //获取同类专业的名字
    private List<Major> getRelativeMajors(String majorType, Integer majorId) {
        List<Major> list = majorRepository.queryByType(majorType);

        List<Major> newList = new LinkedList<>();
        for (Major major : list) {
            major.setHashId(idEncoder.encrypt(major.getId().longValue()));
            if (!Objects.equals(majorId, major.getId())) {
                newList.add(major);
            }
        }
        return newList;
    }


    private MajorProfile getResult(Major major, List<Major> list) {
        MajorProfile result = new MajorProfile(major, list);
        result.setHashId(idEncoder.encrypt(major.getId().longValue()));
        return result;
    }

    public MajorProfile readMajorProfile(Integer majorId) {
        Major major = getMajor(majorId);
        List<Major> relativeMajors = getRelativeMajors(major.getType(), majorId);
        MajorProfile result = new MajorProfile(major, relativeMajors);
        result.setHashId(idEncoder.encrypt(major.getId().longValue()));
        return result;
    }

}
