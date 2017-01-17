package dayan.eve.service;

import com.alibaba.fastjson.JSON;
import dayan.eve.EveApplication;
import dayan.eve.model.query.TopicQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by xsg on 1/4/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EveApplication.class)
@WebAppConfiguration
public class TopicServiceTest {
    @Autowired
    TopicService topicService;

    @Test
    public void readTopics() throws Exception {
        TopicQuery query = new TopicQuery();
        query.setPage(1);
        query.setSize(1);
        query.setAccountId(1);
//        query.setIsMyTopic(false);
        query.setTopicId(1);
        System.out.println(JSON.toJSONString(topicService.readTopics(query), true));
    }

    @Test
    public void count() throws Exception {

    }

    @Test
    public void createTopic() throws Exception {

    }

    @Test
    public void like() throws Exception {

    }

    @Test
    public void dislike() throws Exception {

    }

    @Test
    public void readTimelines() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void setTop() throws Exception {

    }

    @Test
    public void search() throws Exception {

    }

    @Test
    public void searchAccount() throws Exception {

    }

    @Test
    public void countSearch() throws Exception {

    }

    @Test
    public void setStamp() throws Exception {

    }

    @Test
    public void readAllThemes() throws Exception {

    }

    @Test
    public void addTheme() throws Exception {

    }

    @Test
    public void countThemes() throws Exception {

    }

    @Test
    public void createLiveTopic() throws Exception {

    }

    @Test
    public void updateLiveStatus() throws Exception {

    }

}