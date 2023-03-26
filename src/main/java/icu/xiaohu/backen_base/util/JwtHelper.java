package icu.xiaohu.backen_base.util;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author xiaohu
 * @date 2022/09/24/ 22:37
 * @description
 */

public class JwtHelper {
    /**
     * 过期时间 一天
     */
    private static long tokenExpiration = 24*60*60*1000;
    /**
     * 签名秘钥
     */
    private static String tokenSignKey = "xiaohu";

    /**
     * 根据参数生成token
     * @param userId
     * @param account
     * @return
     */
    public static String createToken(Long userId, String account) {
        String token = Jwts.builder()
                .setSubject("cloud-notebook")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("user_id", userId)
                .claim("account", account)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    /**
     * 根据token字符串得到用户id
     * @param token
     * @return
     */
    public static Long getUserId(String token) {
        if(StringUtils.isEmpty(token)) {
            return null;
        }

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Integer userId = (Integer)claims.get("userId");
        return userId.longValue();
    }

    /**
     * 根据token字符串得到用户名称
     * @param token
     * @return
     */
    public static String getAccount(String token) {
        if(StringUtils.isEmpty(token)) {
            return "";
        }

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("account");
    }

}
