package dayan.eve.service;

import com.alibaba.fastjson.JSON;
import dayan.eve.EveApplication;
import dayan.eve.model.query.InformationQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by xsg on 1/17/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EveApplication.class)
@WebAppConfiguration
public class InformationServiceTest {
    @Autowired
    InformationService service;

    @Test
    public void readInformation() throws Exception {
        InformationQuery query = new InformationQuery();
        query.setPage(1);
        query.setSize(10);
        System.out.println(JSON.toJSONString(service.read(query), true));
    }

    @Test
    public void sendInformation() throws Exception {

    }

}