/**
 * *****************************************************
 * Copyright (C) Dayan techology Co.ltd - All Rights Reserved
 * <p>
 * This file is part of Dayan techology Co.ltd property.
 * <p>
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * *****************************************************
 */
package dayan.eve.model.query;

import dayan.eve.model.Pagination;
import dayan.eve.model.course.Option;

import java.util.List;

/**
 *
 * @author xsg
 */
public class CourseTestResultQuery extends Pagination {

    private Integer testId;
    private Integer courseId;
    private List<Option> options;
    private Integer accountId;
    private String cdkey;
    private String questionType;
    private String testType;

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public String getCdkey() {
        return cdkey;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public void setCdkey(String cdkey) {
        this.cdkey = cdkey;
    }

    public List<Option> getOptions() {
        return options;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

}
