package icu.xiaohu.backen_base.util;

import icu.xiaohu.backen_base.model.entity.User;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaohu
 * @date 2022/11/09/ 16:10
 * @description 保存用户登录
 */
@Slf4j
public class UserHolder<T> {
    private static final ThreadLocal<User> tl = new ThreadLocal<>();
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
