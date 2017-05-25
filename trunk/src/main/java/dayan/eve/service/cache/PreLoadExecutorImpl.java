package dayan.eve.service.cache;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by xsg on 3/6/2017.
 */
@Component
public class PreLoadExecutorImpl implements PreLoadExecutor {


    private Set<PreLoadListener> preLoadListeners = Sets.newHashSet();

    @Override
    public void loadAll() {
        preLoadListeners.forEach(PreLoadListener::load);
    }

    @Override
    public void regist(PreLoadListener preLoadListener) {
        preLoadListeners.add(preLoadListener);
    }
}
