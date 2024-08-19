package org.java.mentorship.user.crypt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MD5Test {

    @Test
    void md5ShouldHash() {
        assertEquals(
                "902fbdd2b1df0c4f70b4a5d23525e932".toUpperCase(),
                MD5.getMd5("ABC")
        );
    }

}
