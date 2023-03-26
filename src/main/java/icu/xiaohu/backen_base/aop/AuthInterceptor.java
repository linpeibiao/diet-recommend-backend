package icu.xiaohu.backen_base.aop;


import icu.xiaohu.backen_base.anotation.AuthCheck;
import icu.xiaohu.backen_base.constant.UserRole;
import icu.xiaohu.backen_base.model.entity.User;
import icu.xiaohu.backen_base.exception.BusinessException;
import icu.xiaohu.backen_base.model.result.ResultCode;
import icu.xiaohu.backen_base.util.UserHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaohu
 * @date 2022/12/10/ 21:50
 * @description 权限校验 aop
 */
@Aspect
@Component
public class AuthInterceptor {
    /**
     * 执行拦截
     *
     * @param joinPoint
     * @param authCheck
     * @return
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        List<UserRole> anyRole = Arrays.stream(authCheck.anyRole()).collect(Collectors.toList());
        String mustRole = authCheck.mustRole().getRoleName();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 当前登录用户
        User user = UserHolder.get();
        if (user == null){
            throw new BusinessException(ResultCode.NOT_LOGIN, "未登录");
        }
        // 拥有任意权限即通过
//        if (CollectionUtils.isNotEmpty(anyRole)) {
//            Integer userRole = user.getRole();
//            if (!anyRole.contains(userRole)) {
//                throw new BusinessException(ResultCode.NO_AUTH, "无权限");
//            }
//        }
        // 必须有指定权限才通过
        String userRole = user.getRoleName().getRoleName();
        if (!mustRole.equals(userRole)) {
            throw new BusinessException(ResultCode.NO_AUTH, "无权限");
        }
        // 通过权限校验，放行
        return joinPoint.proceed();
    }
}
