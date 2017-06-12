package dayan.eve.model.school;

import lombok.Data;

/**
 * Created by xsg on 6/3/2017.
 */
@Data
public class SchoolRecommendLog {
    private Integer id;
    private Integer accountId;
    private Integer score;
    private Integer rank;
    private String majorCode;
    private Integer provinceId;
    private String subjectType;
    private String requestIp;
    private String filter;
}
