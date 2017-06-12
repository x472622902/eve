package dayan.eve.service.school;

import com.alibaba.fastjson.JSON;
import dayan.eve.model.PageResult;
import dayan.eve.model.School;
import dayan.eve.model.WeiboUser;
import dayan.eve.model.query.SearchQuery;
import dayan.eve.model.query.WeiboQuery;
import dayan.eve.model.school.WeiboTimeline;
import dayan.eve.repository.SchoolRepository;
import dayan.eve.repository.WeiboRepository;
import dayan.eve.repository.WeiboUserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xsg on 6/3/2017.
 */
@Service
public class WeiboService {
    private final WeiboRepository weiboRepository;
    private final SchoolRepository schoolRepository;
    private final WeiboUserRepository weiboUserRepository;

    private static String IMAGE_MIDDLE = "bmiddle";
    private static String IMAGE_THUMBNAIL = "thumbnail";
    private static String IMAGE_LARGE = "large";

    @Autowired
    public WeiboService(WeiboUserRepository weiboUserRepository, WeiboRepository weiboRepository, SchoolRepository schoolRepository) {
        this.weiboUserRepository = weiboUserRepository;
        this.weiboRepository = weiboRepository;
        this.schoolRepository = schoolRepository;
    }

    public PageResult<WeiboTimeline> readWeiboTimelines(WeiboQuery query) {
        Integer count = weiboRepository.count(query);
        PageResult<WeiboTimeline> pageResult = new PageResult<>(count, query.getPage(), query.getSize());
        if (count > 0) {
            pageResult.setList(weiboRepository.query(query));
            pageResult.getList().forEach(wt -> {
                WeiboTimeline rwt = wt.getRetweetedTimeline();
                wt.setThumbnailPicUrls(getImageList(wt.getImageUrlsStr()));
                wt.setOriginalPicUrls(getImageList(wt.getThumbnailPicUrls(), IMAGE_LARGE));
                wt.setMiddlePicUrls(getImageList(wt.getThumbnailPicUrls(), IMAGE_MIDDLE));
                if (rwt != null) {
                    rwt.setThumbnailPicUrls(getImageList(rwt.getImageUrlsStr()));
                    rwt.setOriginalPicUrls(getImageList(rwt.getThumbnailPicUrls(), IMAGE_LARGE));
                    rwt.setMiddlePicUrls(getImageList(rwt.getThumbnailPicUrls(), IMAGE_MIDDLE));
                }
            });
        }

        return pageResult;
    }


    private List<String> getImageList(String imageUrlsStr) {
        if (StringUtils.isEmpty(imageUrlsStr)) {
            return Collections.emptyList();
        }
        return JSON.parseArray(imageUrlsStr).stream().map(Object::toString).collect(Collectors.toList());
    }

    private List<String> getImageList(List<String> thumbList, String imageType) {
        if (thumbList == null || thumbList.isEmpty()) {
            return Collections.emptyList();
        }
        thumbList.forEach(thumb -> thumb.replace(IMAGE_THUMBNAIL, imageType));
        return thumbList;
    }


    public PageResult<School> search(SearchQuery query) {
        if (!StringUtils.isEmpty(query.getQueryStr())) {
            query.setQueryStr(query.getQueryStr().replace(" ", ""));
        } else {
            query.setQueryStr(null);
        }

        Integer count = schoolRepository.count(query);
        PageResult<School> pageResult = new PageResult<>(count, query.getPage(), query.getSize());
        if (count > 0) {
            pageResult.setList(schoolRepository.search(query));
            pageResult.getList().forEach(school -> {
                school.setWeiboUsers(weiboUserRepository.queryBySchool(school.getId()));
            });
        }
        return pageResult;
    }

    public void createWeiboUser(WeiboUser weiboUser) {
        weiboUserRepository.insert(weiboUser);
    }
}
