package dayan.eve.model.school;

import lombok.Data;

/**
 * Created by xsg on 6/3/2017.
 */
@Data
public class RecommendFeedback {
    private Integer accountId;
    private Integer provinceId;
    private Integer score;
    private String subjectType;
    private String majorCode;
    private Integer starLevel;
    private String content;
    private Integer rank;
}
