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
package dayan.eve.model.easemob;


import dayan.eve.model.JsonResult;

import java.util.List;
import java.util.Map;

/**
 * @author leiky
 */
public class EasemobRequest {

    public EasemobRequest(JsonResult result, EasemobType type) {
        this.result = result;
        this.type = type;
    }

    public EasemobRequest() {
    }

    private JsonResult result;
    private EasemobType type;
    private Map<String, String> ext;
    private List<String> target;
    private Msg msg;
    private String from;


    public JsonResult getResult() {
        return result;
    }

    public void setResult(JsonResult result) {
        this.result = result;
    }


    public Map<String, String> getExt() {
        return ext;
    }

    public void setExt(Map<String, String> ext) {
        this.ext = ext;
    }


    public List<String> getTarget() {
        return target;
    }

    public void setTarget(List<String> target) {
        this.target = target;
    }

    public Msg getMsg() {
        return msg;
    }

    public void setMsg(Msg msg) {
        this.msg = msg;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public EasemobType getType() {
        return type;
    }

    public void setType(EasemobType type) {
        this.type = type;
    }

    public static class Msg {

        private String type = "txt";
        private String msg = "";

        public Msg() {
        }

        public Msg(String msg) {
            this.msg = msg;
        }

        public Msg(String msg, String type) {
            this.msg = msg;
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

    }
}
