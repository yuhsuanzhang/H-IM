package com.yuhsuanzhang.him.imserver.util;

import org.springframework.beans.factory.annotation.Value;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @Author yuxuan.zhang
 * @Description
 */
public class HashUtil {

    private static final String ALGORITHM = "SHA-256";

    private static final String SALT = "mtriJHQqgvJJYYsCFb0BDu8FnQQtcXYbsk9";

    /**
     * 生成随机盐值
     * @return 盐值
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return bytesToHex(salt);
    }

    public static String hash(String password) {
        return hash(password,SALT);

    }

    /**
     * 哈希加盐算法
     * @param password 密码
     * @param salt 盐值
     * @return 哈希值
     */
    public static String hash(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            digest.reset();
            digest.update(salt.getBytes());
            byte[] hash = digest.digest(password.getBytes());
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字节数组转换为十六进制字符串
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexBuilder = new StringBuilder();
        for (byte b : bytes) {
            hexBuilder.append(String.format("%02x", b));
        }
        return hexBuilder.toString();
    }
}

