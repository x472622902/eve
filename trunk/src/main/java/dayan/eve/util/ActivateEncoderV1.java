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

import dayan.common.util.Hashids;
import dayan.common.util.MurmurHash;
import org.springframework.util.StringUtils;

/**
 *
 * @author zhuangyd
 */
public class ActivateEncoderV1 implements ActivateEncoder {

    private static final String SALT = "GuoZuQianWo100Kuai";

    @Override
    public String encrypt(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        if (str.matches("\\d+")) {
            return new Hashids(SALT).encodeHex(str);
        }
        long hash64 = Math.abs(MurmurHash.hash32(str));
        return new Hashids(SALT).encode(hash64);

    }

    @Override
    public boolean match(String encrypt, String str) {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(encrypt)) {
            return false;
        }
        if (str.matches("\\d+")) {
            return new Hashids(SALT).decodeHex(encrypt).equals(str);
        }
        long hash64 = MurmurHash.hash32(str);
        return new Hashids(SALT).decode(encrypt)[0] == hash64;
    }

    @Override
    public String encrypt(Long imei) {
        return new Hashids(SALT).encode(imei);
    }

    @Override
    public boolean match(String encrypt, Long imei) {
        return new Hashids(SALT).decode(encrypt)[0] == imei;
    }

    @Override
    public Long decode(String hash) {
        return new Hashids(SALT).decode(hash)[0];
    }

}
