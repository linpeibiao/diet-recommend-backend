package icu.xiaohu.diet_recommend.service.impl;

import icu.xiaohu.diet_recommend.model.entity.UserMeal;
import icu.xiaohu.diet_recommend.service.RedisService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaohu
 * @date 2023/04/20/ 21:19
 * @description
 */

@Service
public class RedisServiceImpl implements RedisService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void saveDataToRedis(String key, Object obj, long ddl) {
        stringRedisTemplate.opsForValue().set(key, obj.toString(), ddl, TimeUnit.MINUTES);
    }

    @Override
    public Object getDataToRedis(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }
}
