package com.example.admin.componet;

import com.example.system.componet.Constant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * JWT的token，区分大小写
 */
@Data
@Component
public class JwtOperator {

    /**
     * 生成token，并设置过期时间
     * @param subject 为主题名 ， expire 为过期时间（秒）
     * @return
     */
    public String createToken (String subject , long expire ){
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);//过期时间

        return Jwts.builder()
//                .setHeaderParam("typ", "JWT")//设置请求头参数
                .setSubject(subject)//设置 载荷 签名的主题
                .setIssuedAt(nowDate)//设置 载荷 生成签名的时间
                .setExpiration(expireDate)//设置 载荷 签名过期的时间
                .signWith(SignatureAlgorithm.HS512, Constant.TOKEN_SALT)//签名 Signature
                .compact();
    }
    /**
     * 获取token中注册信息
     * @param token
     * @return
     */
    public Claims getTokenClaim (String token) {
        try {
            return Jwts.parser().setSigningKey(Constant.TOKEN_SALT).parseClaimsJws(token).getBody();
        }catch (Exception e){
            return null;
        }
    }
    /**
     * 验证token是否过期失效
     * @param expirationTime
     * @return
     */
    public boolean isTokenExpired (Date expirationTime) {
        //过期时间是否早于现在时间
        return expirationTime.before(new Date());
    }

    /**
     * 获取token失效时间
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        return getTokenClaim(token).getExpiration();
    }

    /**
     * 获取用户名从token中
     */
    public String getUsernameFromToken(String token) {
        return getTokenClaim(token).getSubject();
    }

    /**
     * 获取jwt发布时间
     */
    public Date getIssuedAtDateFromToken(String token) {
        return getTokenClaim(token).getIssuedAt();
    }

}