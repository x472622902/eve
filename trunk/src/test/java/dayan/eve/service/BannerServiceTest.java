package dayan.eve.service;

import com.alibaba.fastjson.JSON;
import dayan.eve.EveApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by xsg on 12/30/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EveApplication.class)
@WebAppConfiguration
public class BannerServiceTest {
    @Autowired
    BannerService bannerService;

    @Test
    public void readBanners() throws Exception {
        System.out.println(JSON.toJSONString(bannerService.readBanners(), true));
    }

}