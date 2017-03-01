/**
 * *****************************************************
 * Copyright (C) Dayan techology Co.ltd - All Rights Reserved
 *
 * This file is part of Dayan techology Co.ltd property.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * *****************************************************
 */
package dayan.eve.util;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 *
 * @author xsg
 */
public class CDKeyUtil {

    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
        "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
        "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
        "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
        "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
        "W", "X", "Y", "Z"};

    public static String createCDKey() {
        StringBuilder sb = new StringBuilder();
        String uuid = "";
        for (int i = 0; i < 15; i++) {
            int start = i % 8;
            if (start == 0) {
                uuid = UUID.randomUUID().toString().replace("-", "");
            }
            String str = uuid.substring(start * 4, start * 4 + 4);
            int x = Integer.parseInt(str, 16);
            sb.append(chars[x % 0x3E]);
        }

        return StringUtils.upperCase(sb.toString());

    }

    public static void main(String[] args) {
        System.out.println(createCDKey());
        System.out.println(createCDKey().length());
    }
}
