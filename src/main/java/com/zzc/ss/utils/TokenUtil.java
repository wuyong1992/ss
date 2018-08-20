package com.zzc.ss.utils;

import com.zzc.ss.common.Const;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * created by JianGuo
 * on 2018/3/30
 * description:
 * JJWT token util
 * 用来生成和解密token
 */


@Slf4j
public class TokenUtil {

    private static final String SECRET_KEY = "JianGuo2018ZhongShengfxysapiJwtSecretKey";



    /**
     * 加密
     * 创建有时效的token
     *
     * @param id        该token的唯一标识
     * @param subject   json格式的字符串，表明这个token是签发给谁的，保存着接收者的信息
     * @param ttlMillis 有效期 是个时间段 例如2000毫秒，在使用的时候需要转换成过期的时间点例如2018年3月30日12点30分
     * @param claims    创建payload的私有声明，键值对的方式保存信息
     * @return 返回token
     * @throws Exception
     */
    public static String createJWT(String id, String subject, long ttlMillis, Map claims) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        long nowMillis = System.currentTimeMillis();//生成JWT的时间
        Date now = new Date(nowMillis);

        SecretKey key = generalKey();//生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        //下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() //这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims)          //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setId(id)                  //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(now)           //iat: jwt的签发时间
                .setSubject(subject)        //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm, key);//设置签名使用的签名算法和签名使用的秘钥
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);     //设置过期时间
        }
        return builder.compact();           //就开始压缩为xxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx这样的jwt
    }


    /**
     * 加密
     * 创建永久有效的token
     *
     * @param id      该token的唯一标识
     * @param claims  创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
     * @param subject 主体信息，可存放用户简单信息的json数据
     * @return 返回token
     * @throws Exception
     */
    public static String createJWT(String id, Map claims, String subject) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SecretKey key = generalKey();//生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        //下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() //这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims)          //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setId(id)                  //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(new Date())    //iat: jwt的签发时间
                .setSubject(subject)        //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm, key);//设置签名使用的签名算法和签名使用的秘钥
        return builder.compact();           //就开始压缩为xxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx这样的jwt
    }

    /**
     * 加密
     * 创建永久有效的token
     *
     * @param claims  创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
     * @param subject 主体信息，可存放用户简单信息的json数据
     * @return 返回token
     * @throws Exception
     */
    public static String createJWT(Map claims, String subject) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SecretKey key = generalKey();//生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        //下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() //这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims)          //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setIssuedAt(new Date())    //iat: jwt的签发时间
                .setSubject(subject)        //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm, key);//设置签名使用的签名算法和签名使用的秘钥
        return builder.compact();           //就开始压缩为xxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx这样的jwt
    }


    /**
     * 解析token
     *
     * @param jwt
     * @return 加密的主体内容
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey key = generalKey();  //签名秘钥，和生成的签名的秘钥一模一样
        return Jwts.parser()  //得到DefaultJwtParser
                .setSigningKey(key)         //设置签名的秘钥
                .parseClaimsJws(jwt).getBody();
    }


    /**
     * 生成加解密的秘钥
     *
     * @return
     */
    private static SecretKey generalKey() {
        String key = PropertiesUtil.getProperty("JWT_SECRET", SECRET_KEY);
        byte[] encodedKey = Base64.decodeBase64(key);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }


    /**
     * 从请求中获取token中加密的信息
     *
     * @param request 请求
     * @return
     */
    public static Claims getClaimsFromRequest(HttpServletRequest request) {
        return (Claims) request.getAttribute("claims");
    }


    private static Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = parseJWT(token);
        } catch (Exception e) {
            log.error("【token 异常 -- 解析异常】，token：{}",token);
            return null;
        }
        return claims;
    }


    /**
     * 从请求中获取用户昵称
     *
     * @param token
     * @return
     */
    public static String getNickNameFromToken(String token) {
        // 从请求中获取用户的昵称
        Claims claims = TokenUtil.getClaimsFromToken(token);
        return claims.getSubject();
    }

    /**
     * 从请求中获取用户id
     *
     * @param token
     * @return
     */
    public static Integer getUserIdFromToken(String token) {
        // 从请求中获取用户的id
        Claims claims = TokenUtil.getClaimsFromToken(token);
        return (Integer) claims.get(Const.UserAuthJwtTokenClaims.USER_ID);
    }

    /**
     * 从请求中获取企业id
     *
     * @param token
     * @return
     */
    public static Integer getEnterpriseIdFromToken(String token) {
        // 从请求中获取用户的id
        Claims claims = TokenUtil.getClaimsFromToken(token);
        return (Integer) claims.get(Const.UserAuthJwtTokenClaims.ENTERPRISE_ID);
    }

    /**
     * 从请求中获取用户id
     *
     * @param token
     * @return
     */
    public static String getUnionidFromToken(String token) {
        // 从请求中获取用户的unionid
        Claims claims = TokenUtil.getClaimsFromToken(token);
        return (String) claims.get(Const.UserAuthJwtTokenClaims.UNIONID);
    }

    /**
     * 从请求中获取用户 openid
     *
     * @param token 请求
     * @return
     */
    public static String getOpenidFromToken(String token) {
        // 从请求中获取用户的openid
        Claims claims = TokenUtil.getClaimsFromToken(token);
        return (String) claims.get(Const.UserAuthJwtTokenClaims.OPENID);
    }
    /**
     * 从请求中获取用户手机号
     *
     * @param token
     * @return
     */
    public static String getMemberPhone(String token) {
        // 从请求中获取用户的openid
        Claims claims = TokenUtil.getClaimsFromToken(token);
        return (String) claims.get(Const.UserAuthJwtTokenClaims.PHONE);
    }


}
