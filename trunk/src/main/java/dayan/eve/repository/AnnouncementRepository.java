package dayan.eve.repository;

import dayan.eve.model.Announcement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xsg on 6/12/2017.
 */
@Mapper
public interface AnnouncementRepository {
    List<Announcement> query();

    public void insert(@Param("announcement") Announcement announcement, @Param("androidParams") String androidParams, @Param("iosParams") String iosParams);

    void delete(Integer id);
}
