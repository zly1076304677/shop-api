package com.fh.shop.api.util;

import java.security.MessageDigest;
import java.util.Random;

import org.apache.commons.codec.binary.Hex;

public class MD5Util {
    public static final String SECRET="wefjqeejfneflqe3eqywy";
	
	/**
     * 生成含有随机盐的密码
     */
    public static String generate(String password) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        String salt = sb.toString();
        password = md5Hex(password + salt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }
    
    /**
     * 校验密码是否正确
     */
    public static boolean verify(String password, String md5) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5.charAt(i);
            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
            cs2[i / 3] = md5.charAt(i + 1);
        }
        String salt = new String(cs2);
        return md5Hex(password + salt).equals(new String(cs1));
    }
    
    /**
     * 获取十六进制字符串形式的MD5摘要
     */
    public static String md5Hex(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(src.getBytes());
            return new String(new Hex().encode(bs));
        } catch (Exception e) {
            return null;
        }
    }

    public static String sign(String info,String secret){
        return md5Hex(info+".."+secret);

    }
    
    public static void main(String[] a){
    	String str = generate("123");
    	String str1 = md5Hex("123");
    	String str2 = generate("5947f6b291d6b24d9bb92eff4cf2eace");
    	System.out.println(str2);
    	System.out.println(str);
    	System.out.println(str1);
    	System.out.println(verify("5947f6b291d6b24d9bb92eff4cf2eace","d2542640cc8cf4553c218e9ce6519ee6d16ba54f15a71c7a"));
    }

}
