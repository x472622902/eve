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
import dayan.eve.model.Announcement;
import dayan.eve.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    @Autowired
    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public List<Announcement> read() {
        return announcementRepository.query();
    }

    //todo 以下为后台功能
    public void create(Announcement a) {
        String androidParamsStr = a.getAndroidParams() == null ? null : JSON.toJSONString(a.getAndroidParams());
        String iosParamsStr = a.getIosParams() == null ? null : JSON.toJSONString(a.getIosParams());
        announcementRepository.insert(a, androidParamsStr, iosParamsStr);
    }

    public void delete(Integer id) {
        announcementRepository.delete(id);
    }

}
