package icu.xiaohu.backen_base.constant;

/**
 * @author xiaohu
 * @date 2022/11/09/ 16:48
 * @description redis key常量
 */
public class RedisConstant {
    /**
     * 用户登录验证码 key
     */
    public static final String LOGIN_CODE_KEY = "login:code:";
    /**
     * 用户登录验证码有效时间
     */
    public static final long LOGIN_CODE_TTL = 2L;

    /**
     * 用户信息缓存 key
     */
    public static final String LOGIN_USER_KEY = "login:user:";
    /**
     * 用户信息缓存有效时间
     */
    public static final long LOGIN_USER_TTL = 30L;

}
