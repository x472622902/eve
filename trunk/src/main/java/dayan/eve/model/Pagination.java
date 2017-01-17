package dayan.eve.model;

import dayan.eve.web.dto.PaginationDTO;

/**
 * Created by xsg on 2016/7/25.
 */
public class Pagination {
    public static Integer DEFAULT_SIZE = 20;
    protected Integer page;
    protected Integer size;

    public Pagination() {
    }

    public Pagination(Integer page, Integer size) {
        if (page <= 0) {
            page = 1;
        }
        if (size <= 0) {
            size = DEFAULT_SIZE;
        }
        this.page = page;
        this.size = size;
    }

    public void initPaging(PaginationDTO pagination) {
        if (pagination == null || pagination.getSize() == null) {
            setSize(DEFAULT_SIZE);
        } else {
            setSize(pagination.getSize());
        }
        if (pagination != null){
            setPage(pagination.getPage());
        }
    }

    public Pagination(Pagination p) {
        this(p.getPage(), p.getSize());
    }

    public Integer getCount() {
        return size;
    }

    /**
     * 可用于sql 查询
     *
     * @return
     */
    public Integer getLimit() {
        return size;
    }

    /**
     * 获取当前页
     *
     * @return
     */
    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }

    /**
     * 可用于sql 查询
     *
     * @return
     */
    public Integer getStart() {
        if (page == null && size == null) {
            return null;
        }
        if (page != null && size == null) {
            size = DEFAULT_SIZE;
        }
        if (page == null && size != null) {
            page = 1;
        }
        return (page - 1) * size;
    }

    public void setCount(Integer count) {
        this.size = count;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

}
