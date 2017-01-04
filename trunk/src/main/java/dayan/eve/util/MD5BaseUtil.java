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

import org.springframework.web.multipart.MultipartFile;

import java.security.MessageDigest;

public class MD5BaseUtil {

    // 返回文件的MD5值 错误则返回null
    public static String md5(byte[] inputByteArray) {

        try {
            MessageDigest messageDigest;
            messageDigest = MessageDigest.getInstance("MD5");

            // inputByteArray是输入字符串转换得到的字节数组
            messageDigest.update(inputByteArray);

            // 转换并返回结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();

            // 字符数组转换成字符串返回
            return byteArrayToHex(resultByteArray);
        } catch (Exception e) {
            return null;
        }
    }

    public static String md5(MultipartFile mpf) {
        try {
            return md5(mpf.getBytes());
        } catch (Exception e) {
            return null;
        }
    }

    public static String StringMD5(String str) {

        try {
            // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
            MessageDigest messageDigest;
            messageDigest = MessageDigest.getInstance("MD5");

            // 输入的文件换成字节数组
            byte[] inputByteArray = str.getBytes();

            // inputByteArray是输入字符串转换得到的字节数组
            messageDigest.update(inputByteArray);

            // 转换并返回结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();

            // 字符数组转换成字符串返回
            return byteArrayToHex(resultByteArray);
        } catch (Exception e) {
            return null;
        }
    }

    private static String byteArrayToHex(byte[] byteArray) {

        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];

        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;

        for (byte b : byteArray) {

            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];

            resultCharArray[index++] = hexDigits[b & 0xf];

        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);

    }
}
