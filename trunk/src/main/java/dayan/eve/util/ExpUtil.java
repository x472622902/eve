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
package dayan.eve.util;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xsg
 */
public class ExpUtil {

    private static List<Integer> expList = new LinkedList<>();

    public static List<Integer> getExpList() {
        if (expList.isEmpty()) {
            expList.add(0);
            for (int i = 1; i < 50; i++) {
                Integer exp = (i - 1) * (i - 1) * 10 + 10;
                expList.add(exp);
            }
        }
        return expList;
    }

}
