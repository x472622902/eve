/**
 * *****************************************************
 * Copyright (C) Dayan techology Co.ltd - All Rights Reserved
 * <p/>
 * This file is part of Dayan techology Co.ltd property.
 * <p/>
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * *****************************************************
 */
package dayan.eve.web.rest;

import dayan.eve.exception.ErrorCN;
import dayan.eve.model.*;
import dayan.eve.model.course.Course;
import dayan.eve.model.course.CourseTrade;
import dayan.eve.model.query.CourseQuery;
import dayan.eve.model.query.CourseTestResultQuery;
import dayan.eve.service.CourseService;
import dayan.eve.service.CourseTestResultService;
import dayan.eve.service.RequestService;
import dayan.eve.web.dto.course.CourseQueryDTO;
import dayan.eve.web.dto.course.CourseRedeemDTO;
import dayan.eve.web.dto.course.CourseTestDTO;
import dayan.eve.web.dto.course.CourseTestResultQueryDTO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author xsg
 */
@RestController
@RequestMapping(value = "/api/v20/mobile/course")
public class CourseResource {

    private static final Logger LOGGER = LogManager.getLogger();
    private final CourseService courseService;
    private final CourseTestResultService courseTestResultService;
    private final RequestService requestService;

    @Autowired
    public CourseResource(RequestService requestService, CourseService courseService, CourseTestResultService courseTestResultService) {
        this.requestService = requestService;
        this.courseService = courseService;
        this.courseTestResultService = courseTestResultService;
    }

    @ApiOperation("读取列表")
    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public JsonResultList read(@RequestBody CourseQueryDTO queryDTO, HttpServletRequest request) {
        CourseQuery query = buildQuery(queryDTO);
        query.setAccountId(requestService.getAccountIdValue(request));
        PageResult<Course> pageResult;
        if (query.getReadMy()) {
            pageResult = courseService.readMy(query);
        } else {
            pageResult = courseService.read(query);
        }
        return new JsonResultList(pageResult);
    }

    @ApiOperation("读取自己的测试结果")
    @RequestMapping(value = "/readMyTestResults", method = RequestMethod.POST)
    public JsonResultList readMyTestResults(@RequestBody CourseQueryDTO queryDTO, HttpServletRequest request) {
        Integer accountId = requestService.getAccountId(request);
        JsonResultList result = new JsonResultList();
        CourseTestResultQuery query = new CourseTestResultQuery();
        query.setAccountId(accountId);
        query.initPaging(queryDTO.getPaging());
        result.setData(courseTestResultService.readMy(query));
        return result;
    }

    @ApiOperation("兑换")
    @RequestMapping(value = "/redeem", method = RequestMethod.POST)
    public JsonResult buyCourse(@RequestBody CourseRedeemDTO queryDTO, HttpServletRequest request) {
        CourseQuery query = buildRedeemQuery(queryDTO);
        query.setAccountId(requestService.getAccountIdValue(request));
        if (StringUtils.isEmpty(query.getCdkey())) {
            throw new RuntimeException(ErrorCN.Course.NO_CD_KEY);
        }
        return new JsonResult(courseService.redeem(query));
    }

    @ApiOperation("购买")
    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public JsonResult buy(HttpServletRequest request) {
        CourseTrade courseTrade = buildCourseTrade(request);
        courseService.buy(courseTrade);
        return new JsonResult();
    }

    @ApiOperation("提交测试")
    @RequestMapping(value = "/submitTest", method = RequestMethod.POST)
    public JsonResult submitTest(@RequestBody CourseTestDTO testDTO, HttpServletRequest request) {
        CourseTestResultQuery query = buildTestQuery(testDTO);
        query.setAccountId(requestService.getAccountIdValue(request));
        if (query.getAccountId() == null && query.getCdkey() == null) {
            throw new RuntimeException(ErrorCN.Course.LOGIN_OR_INVITED_CODE);
        }
        return new JsonResult(courseTestResultService.submit(query));
    }

    @ApiOperation("读取测试结果")
    @RequestMapping(value = "/readTestResult", method = RequestMethod.POST)
    public JsonResult readTestResult(@RequestBody CourseTestResultQueryDTO queryDTO, HttpServletRequest request) {
        return new JsonResult(courseTestResultService.readAnalysis(Integer.valueOf(queryDTO.getTestId())));
    }

    // TODO: 2/17/2017  后台功能：生成邀请码
//
//    @RequestMapping(value = "/getCDKey", method = RequestMethod.POST)
//    public JsonResult getCDKey(HttpServletRequest request) {
//        try {
//            CourseRequest mobileRequest = MobileRequestBaseUtil.getInstance().createMobileRequest(request, CourseRequest.class);
//            CourseQuery query = buildQuery(mobileRequest);
//            if (query.getCourseId() == null) {
//                throw new RuntimeException(COURSE_NOT_SELECTED);
//            }
//            if (query.getNumber() == null) {
//                query.setNumber(1);
//            }
//            JsonResult result = new JsonResult();
//            result.setData(cDKeyService.getCDKey());
//            result.setJsessionid(request.getSession().getId());
//            return result;
//        } catch (Exception ex) {
//            LOGGER.error(ex.getMessage(), ex);
//            return new JsonResult(ex.getMessage(), false);
//        }
//    }

    private CourseTestResultQuery buildTestQuery(CourseTestDTO testDTO) {
        CourseTestResultQuery query = new CourseTestResultQuery();
        if (!StringUtils.isEmpty(testDTO.getCdkey())) {
            query.setCdkey(testDTO.getCdkey());
        }
        query.setCourseId(Integer.valueOf(testDTO.getCourseId()));
        query.setTestId(Integer.valueOf(testDTO.getTestId()));
        query.setTestType(testDTO.getTestType());
        query.setOptions(testDTO.getOptions());
        return query;
    }

    private CourseQuery buildQuery(CourseQueryDTO queryDTO) {
        CourseQuery query = new CourseQuery();
        if (!StringUtils.isEmpty(queryDTO.getCdkey())) {
            query.setCdkey(queryDTO.getCdkey());
        }
        query.setReadMy(queryDTO.getReadMyCourse());
        query.initPaging(queryDTO.getPaging());
        return query;
    }

    private CourseQuery buildRedeemQuery(CourseRedeemDTO courseRedeemDTO) {
        CourseQuery query = new CourseQuery();
        query.setNumber(Integer.valueOf(courseRedeemDTO.getNumber()));
        query.setCourseId(Integer.valueOf(courseRedeemDTO.getCourseId()));
        query.setCdkey(courseRedeemDTO.getCdkey());
        return query;
    }

    private CourseTrade buildCourseTrade(HttpServletRequest request) {
        String tradeStatus = request.getParameter("trade_status");

        if (!Constants.Alipay.SUCCESS.equals(tradeStatus) && !Constants.Alipay.FINISHED.equals(tradeStatus)) {
            throw new RuntimeException(ErrorCN.Course.ALIPAY_FAILED);
        }

        String courseId = request.getParameter("courseId");
        String accountId = request.getParameter("accountId");

        if (StringUtils.isEmpty(courseId)) {
            throw new RuntimeException(ErrorCN.Course.COURSE_NOT_SELECTED);
        }
        if (StringUtils.isEmpty(accountId)) {
            throw new RuntimeException(ErrorCN.Account.NOT_LOGIN);
        }
        CourseTrade courseTrade = new CourseTrade();
        AlipayTrade trade = new AlipayTrade();

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        courseTrade.setAccountId(Integer.valueOf(accountId));
        courseTrade.setCourseId(Integer.valueOf(courseId));
        trade.setTradeStatus(tradeStatus);
        trade.setBuyerEmail(request.getParameter("buyer_email"));
        try {
            trade.setGmtCreate(format.parse(request.getParameter("gmt_create")));
            trade.setGmtPayment(format.parse(request.getParameter("gmt_payment")));
            trade.setNotifyTime(format.parse(request.getParameter("notify_time")));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException();
        }
        trade.setBuyerId(request.getParameter("buyer_id"));
        trade.setSellerId(request.getParameter("seller_id"));
        trade.setNotifyId(request.getParameter("notify_id"));
        trade.setNotifyType(request.getParameter("notify_type"));
        trade.setOutTradeNo(request.getParameter("out_trade_no"));
        trade.setPaymentType(request.getParameter("payment_type"));
        trade.setPrice(Double.parseDouble(request.getParameter("price")));
        trade.setQuantity(Integer.valueOf(request.getParameter("quantity")));
//        trade.setSellerEmail(request.getParameter("seller_email"));
//        trade.setSign(request.getParameter("sign"));
        trade.setSignType(request.getParameter("sign_type"));
        trade.setSubject(request.getParameter("subject"));
        trade.setTradeNo(request.getParameter("trade_no"));
        trade.setTradeStatus(request.getParameter("trade_status"));

        courseTrade.setAlipayTrade(trade);
        return courseTrade;
    }

}
