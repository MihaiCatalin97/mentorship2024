package org.java.mentorship.user.crypt;

import lombok.experimental.UtilityClass;
import org.springframework.util.DigestUtils;

@UtilityClass
public class MD5 {

    public static String getMd5(String plaintext) {
        return DigestUtils.md5DigestAsHex(plaintext.getBytes()).toUpperCase();
    }

}
