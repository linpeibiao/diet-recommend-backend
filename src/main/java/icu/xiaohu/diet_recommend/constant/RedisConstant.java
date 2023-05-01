package icu.xiaohu.diet_recommend.constant;

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
    public static final long LOGIN_USER_TTL = 24L * 60L;

    /**
     * 饮食评分数据缓存 key
     */
    public static final String DIET_GRADE_KEY = "diet:grade:";
    /**
     * 饮食评分数据缓存 有效时间
     */
    public static final long DIET_GRADE_TTL = 24L * 60L;

    /**
     * 排行榜 zSet KEY
     */
    public static final String DIET_MEAL_TOP = "diet:meal:top:";

}
