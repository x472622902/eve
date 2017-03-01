package dayan.eve.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author dengqg
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageResult<T> implements Serializable {
    private List<T> list;
    private Pager pager;

    public PageResult() {
    }

    public PageResult(Pager pager) {
        this.pager = pager;
        this.list = Collections.emptyList();
    }

    public PageResult(List<T> list, Pager pager) {
        this.list = list;
        this.pager = pager;
    }

    public PageResult(int count, int page, int size) {
        this.list = Collections.emptyList();
        this.pager = new Pager(count, page, size);
    }


    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }
}
