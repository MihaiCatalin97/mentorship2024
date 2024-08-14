package org.java.mentorship.user.crypt;

import lombok.NoArgsConstructor;
import org.springframework.util.DigestUtils;

@NoArgsConstructor
public class MD5 {

    public static String getMd5(String plaintext) {
        return DigestUtils.md5DigestAsHex(plaintext.getBytes()).toUpperCase();
    }

}
