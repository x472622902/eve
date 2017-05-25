package dayan.eve.service.cache;

/**
 * Created by xsg on 3/6/2017.
 */
public interface PreLoadExecutor {
    void loadAll();

    void regist(PreLoadListener preLoadListener);
}
