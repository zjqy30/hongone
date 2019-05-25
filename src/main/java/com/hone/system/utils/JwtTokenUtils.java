package com.hone.system.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwtToken 工具类
 *
 **/
public class JwtTokenUtils {

    private static  String jwtTokenSecret = "ans8euf98eu89fjfioehjfo";
    private static  int expireDays=3;


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
                .withExpiresAt(DateUtils.formatStringToDate(DateUtils.getOneDaysDate(expireDays)))
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
    public static Map<String, Claim> verifyToken(String token) {
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

    /**
     * 根据claims获取userID
     *
     * @param claims
     * @return userID
     */
    public static String getUserID(Map<String, Claim> claims) {
        Claim userIDClaim = claims.get("userID");
        if (null == userIDClaim || StringUtils.isEmpty(userIDClaim.asString())) {
            // token 校验失败, 抛出Token验证非法异常
            return null;
        }
        return userIDClaim.asString();
    }


    public static void main(String[] args){
        try {
            String token=createToken("1001");
            System.out.println(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
