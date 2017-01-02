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
import dayan.eve.model.query.BannerQuery;
import dayan.eve.model.query.InformationQuery;
import dayan.eve.repository.BannerRepository;
import dayan.eve.repository.InformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService {
//
//    @Autowired
//    SchoolSearchV20Service schoolSearchV20Service;

    @Autowired
    BannerRepository bannerRepository;

    @Autowired
    InformationRepository informationRepository;

    public List<Banner> readBanners() {
        List<Banner> banners = bannerRepository.query(new BannerQuery());
        for (Banner banner : banners) {
            if (banner.getType().equals(Banner.Type.Topic)) {
//                setTopic(banner);
//                if (banner.getTopic() == null) {
//                    continue;
//                }
            } else if (banner.getType().equals(Banner.Type.News)) {
                setNews(banner);
                if (banner.getNews() == null) {
                    continue;
                }
            } else if (banner.getType().equals(Banner.Type.School)) {
//                setSchool(banner);
//                if (banner.getSchool() == null) {
//                    continue;
//                }
            }
        }
        return banners;
    }

//    @Autowired
//    TopicV20Service topicV20Service;
//
//    private void setTopic(Banner banner) {
//        TopicQuery query = new TopicQuery();
//        query.setId(banner.getTopic().getId());
//        List<Topic> topics = topicV20Service.readTopics(query);
//        if (topics != null && !topics.isEmpty()) {
//            banner.setTopic(topics.get(0));
//        }
//    }

    private void setNews(Banner banner) {
        InformationQuery query = new InformationQuery();
        query.setId(banner.getNews().getId());
        List<Information> informations = informationRepository.queryNews(query);
        if (informations != null && !informations.isEmpty()) {
            banner.setNews(informations.get(0));
        }
    }

//    private void setSchool(Banner banner) {
//        SearchQuery query = new SearchQuery();
//        query.setSchoolId(banner.getSchool().getId());
//        School school = schoolSearchV20Service.readSingleSchool(query);
//        if (school != null) {
//            banner.setSchool(school);
//        }
//    }

//    public void createBanner(Banner banner) {
//        String androidParamsStr = banner.getAndroidParams() == null ? null : JSON.toJSONString(banner.getAndroidParams());
//        String iosParamsStr = banner.getIosParams() == null ? null : JSON.toJSONString(banner.getIosParams());
//        bannerRepository.insert(banner, androidParamsStr, iosParamsStr);
//    }
//
//    @Value("${image.banner.create.url.prefix}")
//    String BANNER_IMAGE_CREATE_PRIFIX;
//
//    @Value("${image.banner.read.url.prefix}")
//    String BANNER_IMAGE_READ_PREFIX;
//
//    public String uploadImage(MultipartFile file) {
//        String imageUrl = ImageBaseUtil.uploadSingleImage(file, BANNER_IMAGE_CREATE_PRIFIX, BANNER_IMAGE_READ_PREFIX);
//        return imageUrl;
//    }
//
//    public void deleteBanner(Integer id) {
//        bannerRepository.delete(id);
//    }
//
//    public void updateBanner(Banner banner) {
//        bannerRepository.update(banner);
//    }

}
