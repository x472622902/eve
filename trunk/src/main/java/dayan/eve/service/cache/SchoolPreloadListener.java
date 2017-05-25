package dayan.eve.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xsg on 5/19/2017.
 */
@Component
public class SchoolPreloadListener implements PreLoadListener {

    private final SchoolCache schoolCache;

    @Autowired
    public SchoolPreloadListener(PreLoadExecutor preLoadExecutor, SchoolCache schoolCache) {
        this.schoolCache = schoolCache;
        preLoadExecutor.regist(this);
    }

    @Override
    public void load() {
        schoolCache.getAllSchoolNames();
    }
}
