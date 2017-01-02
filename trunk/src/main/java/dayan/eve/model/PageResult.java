package dayan.eve.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * @author dengqg
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageResult<T> implements Serializable {
    private List<T> data;
    private Pager pager;

    public PageResult() {
    }

    public PageResult(List<T> data, Pager pager) {
        this.data = data;
        this.pager = pager;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }
}
