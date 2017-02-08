/**
 * *****************************************************
 * Copyright (C) Dayan techology Co.ltd - All Rights Reserved
 * <p>
 * This file is part of Dayan techology Co.ltd property.
 * <p>
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * *****************************************************
 */
package dayan.eve.model.school;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xsg
 */
public class HotSchoolGroup {

    private List<HotSchool> list = new ArrayList<>();
    private Integer upperLimit = 0;
    private Integer lowerLimit = 0;

    public List<HotSchool> getList() {
        return list;
    }

    public void setList(List<HotSchool> list) {
        this.list = list;
    }

    public Integer getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(Integer upperLimit) {
        this.upperLimit = upperLimit;
    }

    public Integer getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(Integer lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

}
