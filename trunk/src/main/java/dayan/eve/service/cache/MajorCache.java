package dayan.eve.service.cache;

import com.alibaba.fastjson.JSON;
import dayan.eve.model.query.SearchQuery;
import dayan.eve.util.MoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Created by xsg on 5/19/2017.
 */
@Component
public class MajorCache {
    private final MoUtil moUtil;

    @Autowired
    public MajorCache(MoUtil moUtil) {
        this.moUtil = moUtil;
    }

    @Autowired

    @Cacheable(value = "allMajorNames", cacheManager = "guavaCacheManager")
    public String getAllMajorNames() throws Exception {
        SearchQuery query = new SearchQuery();
        query.setPage(1);
        query.setSize(2000);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("|");
        moUtil.getMajorList(JSON.toJSONString(query)).getList()
                .forEach(moMajor -> stringBuilder.append(moMajor.getMajorName()).append("|"));
        return stringBuilder.toString();
    }

}
