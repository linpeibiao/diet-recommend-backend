package icu.xiaohu.diet_recommend.job;

import com.alibaba.fastjson.JSON;
import icu.xiaohu.diet_recommend.constant.RedisConstant;
import icu.xiaohu.diet_recommend.recommend.dto.RelateDTO;
import icu.xiaohu.diet_recommend.service.IUserMealService;
import icu.xiaohu.diet_recommend.service.RecommendService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiaohu
 * @date 2023/05/01/ 3:15
 * @description
 */
@Slf4j
@Component
public class MyCacheJob extends QuartzJobBean {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private IUserMealService userMealService;

    @Override
    public void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("开始执行缓存定时任务");
        String redisKey = RedisConstant.DIET_GRADE_KEY;
        List<RelateDTO> data = userMealService.getUserMealRelate();
        // 进行缓存
        stringRedisTemplate.opsForValue().set(redisKey, JSON.toJSONString(data), RedisConstant.DIET_GRADE_TTL);
        log.info("执行缓存定时任务结束");
    }
}
