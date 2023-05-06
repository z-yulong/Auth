package com.zyl.common.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zyl.model.system.SysUser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * @author: zyl
 * @date 2023/4/27 19:11
 */

public class JWTUtil {
    //token有效时长(2小时)
    private static final long EXPIRE = 5 * 60 * 60 * 1000L;
    //token的密钥
    private static final String SECRET = "wph0331admin";

    public static String createToken(SysUser sysUser) {
        //token过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE);

        //jwt的header部分
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        //使用jwt的api生成token
        return JWT.create()
                .withHeader(map)
                .withClaim("username", sysUser.getUsername())//私有声明
                .withClaim("id", String.valueOf(sysUser.getId()))
                .withExpiresAt(date)//过期时间
                .withIssuedAt(new Date())//签发时间
                .sign(Algorithm.HMAC256(SECRET));//签名
    }

    //校验token的有效性，1、token的header和payload是否没改过；2、没有过期
    public static boolean verify(String token) {
        try {
            //解密
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //通过token获取username
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    //通过token获取用户id
    public static String getIdByToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("id").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
