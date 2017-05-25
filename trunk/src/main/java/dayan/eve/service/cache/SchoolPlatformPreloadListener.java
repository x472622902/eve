package dayan.eve.service.cache;

import dayan.eve.util.SchoolIdPlatformIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xsg on 5/19/2017.
 */
@Component
public class SchoolPlatformPreloadListener implements PreLoadListener {

    private final SchoolIdPlatformIdUtil schoolIdPlatformIdUtil;

    @Autowired
    public SchoolPlatformPreloadListener(PreLoadExecutor preLoadExecutor, SchoolCache schoolCache, SchoolIdPlatformIdUtil schoolIdPlatformIdUtil) {
        this.schoolIdPlatformIdUtil = schoolIdPlatformIdUtil;
        preLoadExecutor.regist(this);
    }

    @Override
    public void load() {
        schoolIdPlatformIdUtil.getSchoolIdAndPlatformIdMap();
        schoolIdPlatformIdUtil.getAllPlatformSchoolMap();
        schoolIdPlatformIdUtil.getAllSchoolPlatformMap();
        schoolIdPlatformIdUtil.getPlatformIdAndSchoolIdMap();
        schoolIdPlatformIdUtil.getSchoolCSMap();
    }
}
