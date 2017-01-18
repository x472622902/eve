package dayan.eve.web.dto.major;

import javax.validation.constraints.NotNull;

/**
 * Created by xsg on 1/18/2017.
 */
public class MajorProfileQueryDTO {
    @NotNull
    private String majorHashId;

    public String getMajorHashId() {
        return majorHashId;
    }

    public void setMajorHashId(String majorHashId) {
        this.majorHashId = majorHashId;
    }
}
