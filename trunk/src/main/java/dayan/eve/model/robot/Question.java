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

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author zhuangyd
 */
public class Question extends BaseMessage implements Serializable {

    protected String question;
    protected Date askTime;
    protected String robotAnswer;
    protected String province = "未知用户";

    public Question() {
    }

    public Question(Integer platformId, String question, String customSession, String csSessionId, Date askTime) {
        this.platformId = platformId;
        this.question = question;
        this.customSessionId = customSession;
        this.csSessionId = csSessionId;
        this.askTime = askTime;
    }
    public Question(Integer platformId, String question, String customSession, String csSessionId, Date askTime,String robotAnswer) {
        this.platformId = platformId;
        this.question = question;
        this.customSessionId = customSession;
        this.csSessionId = csSessionId;
        this.askTime = askTime;
        this.robotAnswer = robotAnswer;
    }
    public Question(Question question) {
        this.platformId = question.getPlatformId();
        this.question = question.getQuestion();
        this.customSessionId = question.getCustomSessionId();
        this.csSessionId = question.getCsSessionId();
        this.askTime = question.getAskTime();
    }

    public Boolean isInService() {
        return this.csSessionId != null;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Date getAskTime() {
        return askTime;
    }

    public void setAskTime(Date askTime) {
        this.askTime = askTime;
    }


    public String getRobotAnswer() {
        return robotAnswer;
    }

    public void setRobotAnswer(String robotAnswer) {
        this.robotAnswer = robotAnswer;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.platformId);
        hash = 83 * hash + Objects.hashCode(this.question);
        hash = 83 * hash + Objects.hashCode(this.customSessionId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Question other = (Question) obj;
        if (!Objects.equals(this.platformId, other.platformId)) {
            return false;
        }
        if (!Objects.equals(this.question, other.question)) {
            return false;
        }
        return Objects.equals(this.customSessionId, other.customSessionId);
    }

    @Override
    public String toString() {
        return "Question{" + "platformId=" + platformId + ", question=" + question + ", customSessionId=" + customSessionId + ", csSessionId=" + csSessionId + ", askTime=" + askTime +
                ",robotAnswer" + robotAnswer + ",province" + province + '}';
    }

}
