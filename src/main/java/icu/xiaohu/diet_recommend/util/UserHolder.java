package icu.xiaohu.diet_recommend.util;

import icu.xiaohu.diet_recommend.model.entity.User;
import icu.xiaohu.diet_recommend.service.UserService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaohu
 * @date 2022/11/09/ 16:10
 * @description 保存用户登录
 */
@Slf4j
public class UserHolder<T> {
    /**
     * 保存用户信息
     */
    private static final ThreadLocal<User> tl = new ThreadLocal<>();

    /**
     * 保存用户登陆状态
     */
    private static final ConcurrentHashMap<String, String> userMap = new ConcurrentHashMap<>();

    public static String getToken(String account){
        return userMap.get(account);
    }
    public static void saveToken(String account, String token){
        userMap.put(account, token);
    }
    public static void removeToken(String account){
        userMap.remove(account);
    }
    public static void save(User user){
        log.info(Thread.currentThread().getName() + "保存登陆用户信息");
        tl.set(user);
    }
    public static User get(){
        log.info(Thread.currentThread().getName() + "获取登陆用户信息");
        return tl.get();
    }
    public static void remove(){
        tl.remove();
    }
}
