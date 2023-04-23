package icu.xiaohu.diet_recommend.aop;

import icu.xiaohu.diet_recommend.model.entity.Log;
import icu.xiaohu.diet_recommend.model.result.Result;
import icu.xiaohu.diet_recommend.service.LogService;
import icu.xiaohu.diet_recommend.util.UserHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author xiaohu
 * @date 2023/04/23/ 14:37
 * @description
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Resource
    private LogService logService;

    @Pointcut("@annotation(icu.xiaohu.diet_recommend.anotation.SaveLog)")
    public void logPointcut() {}

    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法名和参数
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        // 记录日志
        logger.info("访问接口：" + methodName + "，参数：" + Arrays.toString(args));
        // 调用目标方法
        Object result = joinPoint.proceed();
        // 记录返回值
        logger.info("接口返回：" + result);
        return result;
    }

    @Pointcut("execution(* icu.xiaohu.diet_recommend.controller..*.*(..)) && !execution(* icu.xiaohu.diet_recommend.controller.admin.LogController.*(..))")
    public void pointcut() {}

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取方法名、类名和包名
        String methodName = joinPoint.getSignature().getName();
        String className = signature.getDeclaringType().getSimpleName();
        String packageName = signature.getDeclaringType().getPackage().getName();
        // 参数
        Object[] args = joinPoint.getArgs();
        String params = Arrays.toString(args);
        // 访问用户
        Long userId = UserHolder.get().getId();
        // 保存访问日志至数据库
        Log log = new Log();
        log.setUserId(userId);
        log.setMethodName(methodName);
        log.setClassName(className);
        log.setPackageName(packageName);
        log.setRequestTime(new Date());
        log.setRequestParam(params);
        log.setStatus("成功");
        logService.save(log);
    }

    /**
     * 只要抛出异常就会记录日志
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        String params = Arrays.toString(args);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String packageName = signature.getDeclaringType().getPackage().getName();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // 访问用户
        Long userId = UserHolder.get().getId();
        String errorMsg = e.getMessage();
        String logMsg = String.format("[%s] %s.%s(%s) failed: %s", time, className, methodName, params, errorMsg);
        logger.error(logMsg, e);

        // 保存至数据库
        // 保存访问日志至数据库
        Log log = new Log();
        log.setUserId(userId);
        log.setMethodName(methodName);
        log.setClassName(className);
        log.setPackageName(packageName);
        log.setRequestTime(new Date());
        log.setRequestParam(params);
        log.setStatus("失败");
        log.setExceptionText(errorMsg);
        logService.save(log);

    }

    /**
     * 需要判断返回结果的状态码，如果请求接口失败（状态码为20000）才会记录
     * @param joinPoint
     * @param result
     */
    @AfterReturning(pointcut = "pointcut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        if (result instanceof Result && ((Result) result).getCode() != 20000) {
            String methodName = joinPoint.getSignature().getName();
            String className = joinPoint.getTarget().getClass().getSimpleName();
            Object[] args = joinPoint.getArgs();
            String params = Arrays.toString(args);
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String errorMsg = ((Result) result).getMessage();
            String logMsg = String.format("[%s] %s.%s(%s) failed: %s", time, className, methodName, params, errorMsg);
            logger.info(logMsg);
        }
    }
}
