package dayan.eve.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xsg on 5/19/2017.
 */
@Component
public class MajorPreloadListener implements PreLoadListener {

    private final MajorCache majorCache;

    @Autowired
    public MajorPreloadListener(MajorCache majorCache) {
        this.majorCache = majorCache;
    }


    @Override
    public void load() {
        try {
            majorCache.getAllMajorNames();
        } catch (Exception ignored) {
        }
    }
}
