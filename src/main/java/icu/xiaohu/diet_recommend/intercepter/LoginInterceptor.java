package icu.xiaohu.diet_recommend.intercepter;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import icu.xiaohu.diet_recommend.model.entity.User;
import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.service.UserService;
import icu.xiaohu.diet_recommend.util.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;



/**
 * @author xiaohu
 * @date 2022/11/19/ 16:55
 * @description 登录拦截器。
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        log.info("登录拦截器被执行");
        String url = request.getRequestURI();
        log.info(url);
        if (url.contains("login") || url.contains("sendCode") || url.contains("register")){
            return true;
        }
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)){
            throw new BusinessException(ResultCode.NOT_LOGIN, "未登录");
        }
        // 1.判断是否需要拦截（是否存在分佈式session）
        // 先从 redis 中拿到登录信息，若数据为空，返回false
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(token);
        // 判断 userMap
        if (userMap.isEmpty()){
            throw new BusinessException(ResultCode.NOT_LOGIN, "未登录");
        }
        // 将 map 转换成 User 实体,枚举值无法转换成整形，要忽略掉错误
        User user = BeanUtil.fillBeanWithMap(userMap, new User(), true);
        log.info(user.toString());
        // 将 USer 保存在 UserHolder
        UserHolder.save(user);
        stopWatch.stop();
        log.info("本次登录拦截消耗时间: {}", stopWatch.getTotalTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("响应结束删除 session 操作");
        UserHolder.remove();
    }

}
