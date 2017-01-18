package dayan.eve.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
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

    public PageResult(List<T> list, Pager pager) {
        this.list = list;
        this.pager = pager;
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
