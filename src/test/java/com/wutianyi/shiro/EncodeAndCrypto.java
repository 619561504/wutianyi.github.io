package com.wutianyi.shiro;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

/**
 * Created by hanjiewu on 2016/5/20.
 */
public class EncodeAndCrypto {
    @Test
    public void testEncode() {
        String str = "hello";
//        String base64Encoded = Base64.decodeToString()
    }

    @Test
    public void testHash() {
        String str = "hello";
        String salt = "123";

        String md5 = new Md5Hash(str, salt).toString();
    }
}
