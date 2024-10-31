package com.wlkg.auth;

import com.wlkg.auth.entity.UserInfo;
import com.wlkg.auth.utils.JwtUtils;
import com.wlkg.auth.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class TestPubPriKey {

    private static final String pubKeyPath = "E:\\rsa\\rsa.pub";

    private static final String priKeyPath = "E:\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    /**
     *
     * @throws Exception
     */
    @Test
    public void testRsa() throws Exception {
        System.out.println("111");
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "233131212");
    }

   @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        //私钥 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU3MzcxNDc3N30.bYzrz-grSbzy0pG6N06CIWmJZz9_tTnTrtySli1k_147cVr9KD86Ym6A3FYEHvfWdMSm8qPyI_uf0ueRhpJggON_NIW7x46dyCqlr1xTbU1ecl7o8zvzhMl33Oo1cgt-RhhFrODjNSiQSFBn0JmitF9O2xpQDhdLydc_RfH9A28";
        //eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU3MzcxNDc3N30.bYzrz-grSbzy0pG6N06CIWmJZz9_tTnTrtySli1k_147cVr9KD86Ym6A3FYEHvfWdMSm8qPyI_uf0ueRhpJggON_NIW7x46dyCqlr1xTbU1ecl7o8zvzhMl33Oo1cgt-RhhFrODjNSiQSFBn0JmitF9O2xpQDhdLydc_RfH9A28
        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}
