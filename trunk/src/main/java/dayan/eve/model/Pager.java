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
package dayan.eve.model;

/**
 *
 * @author zhuangyd
 */
public class Pager {

    protected Integer count; //记录条数
    protected Integer page = 1; //当前页
    protected Integer size = 20; //每页数据条数

    public Pager() {
    }

    public Pager(Integer count, Integer page, Integer size) {
        this.count = count;
        this.page = page;
        this.size = size;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotalPages() {
        if (count % size == 0) {
            return count / size;
        } else {
            return count / size + 1;
        }
    }

    public Integer getStart() {
        return (page - 1) * size + 1;
    }

    public Integer getEnd() {
        return page * size;
    }

}
