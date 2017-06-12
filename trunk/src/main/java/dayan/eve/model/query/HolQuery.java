package dayan.eve.model.query;

import dayan.eve.model.Pagination;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by xsg on 6/12/2017.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HolQuery extends Pagination {
    private String personalityCode;
    private String jobClassCode;
    private String majorTypeCode;
}

