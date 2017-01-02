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

import java.util.Map;

/**
 * @author leiky
 */
public class EasemobResult {

    public EasemobResult(JsonResult result, EasemobType type) {
        this.result = result;
        this.type = type;
    }

    public EasemobResult() {
    }

    private JsonResult result;
    private EasemobType type;
    private Map<String, String> ext;

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

    public EasemobType getType() {
        return type;
    }

    public void setType(EasemobType type) {
        this.type = type;
    }
}
