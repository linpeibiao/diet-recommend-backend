package icu.xiaohu.backen_base.anotation;

import icu.xiaohu.backen_base.constant.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiaohu
 * @date 2022/12/10/ 21:02
 * @description 权限校验自定义注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

    /**
     * 存在任何一个角色
     * @return
     */
    UserRole[] anyRole() default UserRole.VISITOR;

    /**
     * 必须有某个角色
     * @return
     */
    UserRole mustRole() default UserRole.VISITOR;
}
