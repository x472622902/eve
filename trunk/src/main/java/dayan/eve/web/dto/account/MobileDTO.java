package dayan.eve.web.dto.account;

import javax.validation.constraints.NotNull;

/**
 * Created by xsg on 1/18/2017.
 */
public class MobileDTO {
    @NotNull
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
