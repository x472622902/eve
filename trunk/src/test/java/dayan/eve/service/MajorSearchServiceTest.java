package dayan.eve.service;

import com.alibaba.fastjson.JSON;
import dayan.eve.EveApplication;
import dayan.eve.model.query.SearchQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by xsg on 1/18/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EveApplication.class)
@WebAppConfiguration
public class MajorSearchServiceTest {
    @Autowired
    MajorSearchService majorSearchService;


    @Test
    public void convertToItems() throws Exception {

    }

    @Test
    public void getAllNameString() throws Exception {

    }

    @Test
    public void getPrompts() throws Exception {
        System.out.println(JSON.toJSONString(majorSearchService.getPrompts("语"), true));
    }

    @Test
    public void searchMajors() throws Exception {
        SearchQuery query = new SearchQuery();
        query.setPage(1);
        query.setSize(10);
        System.out.println(JSON.toJSONString(majorSearchService.searchMajors(query)));
    }

}