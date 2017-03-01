package dayan.eve.service;

import com.alibaba.fastjson.JSON;
import dayan.eve.EveApplication;
import dayan.eve.model.course.CourseTrade;
import dayan.eve.model.query.CourseQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by xsg on 2/17/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EveApplication.class)
@WebAppConfiguration
public class CourseServiceTest {
    @Autowired
    CourseService courseService;

    @Test
    public void read() throws Exception {
        System.out.println("read");
        CourseQuery query = new CourseQuery();
        query.setPage(1);
        query.setSize(20);
        query.setAccountId(22);
//        query.setReadMy(Boolean.TRUE);
//        query.setCourseId(1);
//        query.setCdkey("EZ2GZWHYSIZZCOC");
        System.out.println(JSON.toJSONString(courseService.read(query), true));
    }

    @Test
    public void readMy() throws Exception {
        CourseQuery query = new CourseQuery();
        query.setAccountId(21);
        query.setPage(1);
        query.setSize(10);
        System.out.println(JSON.toJSONString(courseService.readMy(query), true));
    }

    @Test
    public void count() throws Exception {

    }

    @Test
    public void readPayments() throws Exception {

    }

    @Test
    public void redeem() throws Exception {

    }

    @Test
    public void buy() throws Exception {
        System.out.println("buy");
        CourseTrade query = new CourseTrade();
        query.setAccountId(23);
        query.setCourseId(30);
//        query.setCdkey("PGIU1FFWCLTSAOG");
        courseService.buy(query);
    }

}