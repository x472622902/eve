package dayan.eve.model;

import dayan.eve.model.major.MoMajor;

import java.util.List;

/**
 * @author dengqg
 */
public class MoPageResult {
    private List<MoMajor> list;
    private Pager pager;

    public MoPageResult() {
    }

    public List<MoMajor> getList() {
        return list;
    }

    public void setList(List<MoMajor> list) {
        this.list = list;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }
}
