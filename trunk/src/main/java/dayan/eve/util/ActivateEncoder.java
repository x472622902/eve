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

import dayan.common.util.NumberEncoder;

/**
 *
 * @author zhuangyd
 */
public interface ActivateEncoder extends NumberEncoder {

    public boolean match(String encrypt, String raw);

    public boolean match(String encrypt, Long imei);

    String encrypt(String digitStr);
}
