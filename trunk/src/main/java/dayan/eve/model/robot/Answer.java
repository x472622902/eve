/**
 * *****************************************************
 * Copyright (C) Dayan techology Co.ltd - All Rights Reserved
 *
 * This file is part of Dayan techology Co.ltd property.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * *****************************************************
 */
package dayan.eve.model.robot;


import java.util.Date;

/**
 * @author zhuangyd
 */
public class Answer extends Question {

    String answer;
    Date answerTime;
    AnswerType answerType = AnswerType.cs;

    public Answer() {
    }

    public Answer(String answer, Integer platformId, String question, String customSessionId, String csSessionId) {
        this.answer = answer;
        this.platformId = platformId;
        this.question = question;
        this.customSessionId = customSessionId;
        this.csSessionId = csSessionId;
        this.answerTime = new Date();
    }

    public Answer(Question question, String answer) {
        super(question);
        this.answer = answer;
        this.answerTime = new Date();
    }

    public Answer(Question question) {
        super(question);
        this.answerTime = new Date();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }

    public AnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }

}
