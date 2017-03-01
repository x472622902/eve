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
package dayan.eve.model;

/**
 * @author xsg
 */
public class JsonResultList extends JsonResult {

    private Pager pager;

    public JsonResultList() {
    }

    public <T> JsonResultList(PageResult<T> pageResult) {
        super.setData(pageResult.getList());
        this.pager = pageResult.getPager();
    }


    public JsonResultList(String info, Boolean success) {
        super(info, success);
    }

//    public JsonResultList(Object data) {
//        super(data);
//    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

}
