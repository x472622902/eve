package dayan.eve.config;

import dayan.eve.service.cache.PreLoadExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by xsg on 5/19/2017.
 */
@Component
public class MyInitializer implements ApplicationListener<ApplicationEvent>{
    @Autowired
    private PreLoadExecutor preLoadExecutor;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        preLoadExecutor.loadAll();
    }
}
