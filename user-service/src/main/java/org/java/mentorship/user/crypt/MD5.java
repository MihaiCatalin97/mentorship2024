package org.java.mentorship.user.crypt;

import org.springframework.util.DigestUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MD5 {

    public static String getMd5(String plaintext) {
//        MessageDigest m = null;
//        try {
//            m = MessageDigest.getInstance("MD5");
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//        m.reset();
//        m.update(plaintext.getBytes());
//        byte[] digest = m.digest();
//        BigInteger bigInt = new BigInteger(1, digest);
//        String hashtext = bigInt.toString(16);
//
//        hashtext = "0".repeat(32 - hashtext.length()) + hashtext;
//        return hashtext;
        return Arrays.toString(DigestUtils.md5Digest(plaintext.getBytes()));
    }

}
