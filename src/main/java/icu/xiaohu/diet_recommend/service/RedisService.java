package icu.xiaohu.diet_recommend.service;

/**
 * @author xiaohu
 * @date 2023/04/20/ 21:18
 * @description redis 业务层，用于饮食数据缓存
 */
public interface RedisService {
    /**
     * 缓存数据到redis
     * @param key
     * @param obj
     * @param ddl
     */
    void saveDataToRedis(String key, Object obj, long ddl);

    /**
     * 从 redis 获取数据
     * @param key
     * @return
     */
    Object getDataToRedis(String key);
}
