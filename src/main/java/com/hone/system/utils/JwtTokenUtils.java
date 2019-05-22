package com.hone.system.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwtToken 工具类
 *
 **/
public class JwtTokenUtils {

    private static  String jwtTokenSecret = "ans8euf98eu89fjfioehjfo";
    private static  String expireDays="1";

    /**
     * JWT生成Token.
     * <p>
     * JWT构成: header, payload, signature
     *
     * @param userID 用户ID
     */
    public static String createToken(String userID) throws Exception {
        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // build token
        String token = JWT.create().withHeader(map)
                .withClaim("userID", userID)
                .withIssuedAt(new Date())
                .withExpiresAt(DateUtils.formatStringToDate("2019-05-20 10:32:00"))
                .sign(Algorithm.HMAC256(jwtTokenSecret));

        return token;
    }

    /**
     * 解密Token
     *
     * @param token
     * @return
     * @throws Exception
     */
    private static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt;
        try {
            jwt = JWT.require(Algorithm.HMAC256(jwtTokenSecret)).build().verify(token);
        } catch (Exception e) {
            // token 校验失败, 抛出Token验证非法异常
            return null;
        }
        return jwt.getClaims();
    }

    /**
     * 根据Token获取userID
     *
     * @param token
     * @return userID
     */
    public static String getUserID(String token) {
        Map<String, Claim> claims = verifyToken(token);
        Claim userIDClaim = claims.get("userID");
        if (null == userIDClaim || StringUtils.isEmpty(userIDClaim.asString())) {
            // token 校验失败, 抛出Token验证非法异常
            return null;
        }
        return userIDClaim.asString();
    }


    public static void main(String[] args){


//        try {
//            String token=createToken("123456");
//            System.out.println(token);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTgzMTk1MjAsInVzZXJJRCI6IjEyMzQ1NiIsImlhdCI6MTU1ODMxOTQ4M30.wjCFc7_vIiTbDHCLiS0IYuZnzIe7YlFOcXSSQrwngCY";

        Map<String, Claim> claims = verifyToken(token);

        if (null == claims) {
            // token 校验失败, 抛出Token验证非法异常
            System.out.println("token已经过期");
            return;
        }


        Claim userIDClaim = claims.get("userID");

        System.out.println(userIDClaim.asString());

    }

}
