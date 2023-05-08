package com.zyl.common.util;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * @author: zyl
 * @date 2023/3/17 20:41
 */
public class SHA256 {

    private static final int SALT_LENGTH = 32; //盐值长度
    private static final int ITERATIONS = 10000; //迭代次数
    private static final int KEY_LENGTH = 256; //密钥长度
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256"; //hash算法

    /**
     * 生成一个盐值和哈希值的组合字符串
     * @param password 需要加密的密码
     * @return 包含盐值、迭代次数和哈希值的字符串
     */
    public static String encrypt(String password) {
        try {
            SecureRandom random = new SecureRandom(); // 使用安全随机数生成器生成随机盐值
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt); // 生成随机盐值

            byte[] hash = pbkdf2(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH); // 使用PBKDF2算法生成哈希值
            return ITERATIONS + ":" + bytesToHex(salt) + ":" + bytesToHex(hash); // 返回盐值、迭代次数和哈希值的组合字符串
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e); // 抛出运行时异常
        }
    }



    /**
     * 验证给定的密码是否与存储的哈希值匹配
     * @param password 需要验证的密码
     * @param storedPassword 包含盐值、迭代次数和哈希值的字符串
     * @return 如果密码与存储的哈希值匹配，则为true；否则为false
     */
    public static boolean verify(String password, String storedPassword) {
        try {
            String[] parts = storedPassword.split(":"); // 将存储的字符串分解为盐值、迭代次数和哈希值
            int iterations = Integer.parseInt(parts[0]);
            byte[] salt = hexToBytes(parts[1]);
            byte[] hash = hexToBytes(parts[2]);

            byte[] testHash = pbkdf2(password.toCharArray(), salt, iterations, hash.length * 8); // 使用相同的盐值、迭代次数和密钥长度计算给定密码的哈希值
            return slowEquals(hash, testHash); // 使用安全比较函数比较存储的哈希值和计算出的哈希值
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e); // 抛出运行时异常
        }
    }

    /**
     * 使用PBKDF2算法生成哈希值
     * @param password 密码
     * @param salt 盐值
     * @param iterations 迭代次数
     * @param keyLength 密钥长度
     * @return 哈希值
     * @throws NoSuchAlgorithmException
     */
    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int keyLength) throws NoSuchAlgorithmException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength); // 创建PBEKeySpec对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM); // 创建SecretKeyFactory对象
        SecretKey key = null; // 生成密钥
        try {
            key = keyFactory.generateSecret(spec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return key.getEncoded(); // 返回密钥的字节数组
    }

    /**
     * 将十六进制字符串转换为字节数组
     * @param hex 十六进制字符串
     * @return 字节数组
     */
    private static byte[] hexToBytes(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16); // 将每两个字符作为一个十六进制数解析为字节
        }
        return bytes;
    }

    /**
     * 将字节数组转换为十六进制字符串
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b)); // 将每个字节转换为两位的十六进制字符串
        }
        return sb.toString();
    }

    /**
     * 使用安全比较函数比较两个字节数组
     * @param a 第一个字节数组
     * @param b 第二个字节数组
     * @return 如果两个字节数组相等，则为true；否则为false
     */
    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length; // 计算两个字节数组的长度差异
        for (int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i]; // 使用按位或和按位异或运算符计算差异
        }
        return diff == 0; // 如果差异为0，则两个字节数组相等
    }
}

