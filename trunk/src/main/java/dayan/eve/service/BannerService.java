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

import dayan.eve.model.Banner;
import dayan.eve.model.Information;
import dayan.eve.model.PageResult;
import dayan.eve.model.School;
import dayan.eve.model.query.BannerQuery;
import dayan.eve.model.query.InformationQuery;
import dayan.eve.model.query.SearchQuery;
import dayan.eve.model.query.TopicQuery;
import dayan.eve.model.topic.Topic;
import dayan.eve.repository.BannerRepository;
import dayan.eve.repository.InformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService {
    private final SchoolSearchService schoolSearchService;
    private final TopicService topicService;
    private final BannerRepository bannerRepository;
    private final InformationRepository informationRepository;

    @Autowired
    public BannerService(SchoolSearchService schoolSearchService, TopicService topicService, BannerRepository bannerRepository, InformationRepository informationRepository) {
        this.schoolSearchService = schoolSearchService;
        this.topicService = topicService;
        this.bannerRepository = bannerRepository;
        this.informationRepository = informationRepository;
    }

    public List<Banner> readBanners() {
        List<Banner> banners = bannerRepository.query(new BannerQuery());
        for (Banner banner : banners) {
            if (banner.getType().equals(Banner.Type.Topic)) {
                setTopic(banner);
            } else if (banner.getType().equals(Banner.Type.News)) {
                setNews(banner);
            } else if (banner.getType().equals(Banner.Type.School)) {
                setSchool(banner);
            }
        }
        return banners;
    }

    private void setTopic(Banner banner) {
        TopicQuery query = new TopicQuery();
        query.setId(banner.getTopic().getId());
        PageResult<Topic> topics = topicService.readTopics(query);
        if (topics.getPager().getCount() > 0) {
            banner.setTopic(topics.getList().get(0));
        }
    }

    private void setNews(Banner banner) {
        InformationQuery query = new InformationQuery();
        query.setId(banner.getNews().getId());
        List<Information> informations = informationRepository.queryNews(query);
        if (informations != null && !informations.isEmpty()) {
            banner.setNews(informations.get(0));
        }
    }

    private void setSchool(Banner banner) {
        SearchQuery query = new SearchQuery();
        query.setSchoolId(banner.getSchool().getId());
        School school = schoolSearchService.readSingleSchool(query);
        if (school != null) {
            banner.setSchool(school);
        }
    }
// TODO: 2/21/2017  后台功能添加轮播图
}
