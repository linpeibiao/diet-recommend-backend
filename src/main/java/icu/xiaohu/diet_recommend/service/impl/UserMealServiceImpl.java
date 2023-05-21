package icu.xiaohu.diet_recommend.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import icu.xiaohu.diet_recommend.constant.RedisConstant;
import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.entity.Meal;
import icu.xiaohu.diet_recommend.model.entity.User;
import icu.xiaohu.diet_recommend.model.entity.UserMeal;
import icu.xiaohu.diet_recommend.mapper.UserMealMapper;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.recommend.Recommend;
import icu.xiaohu.diet_recommend.recommend.dto.RelateDTO;
import icu.xiaohu.diet_recommend.service.IMealService;
import icu.xiaohu.diet_recommend.service.IUserMealService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.xiaohu.diet_recommend.util.UserHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaohu
 * @since 2023-03-29
 */
@Service
public class UserMealServiceImpl extends ServiceImpl<UserMealMapper, UserMeal> implements IUserMealService {
    @Autowired
    private IMealService mealService;
    @Autowired
    private UserMealMapper userMealMapper;
    @Autowired
    private Recommend recommend;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<RelateDTO> getUserMealRelate() {
        List<RelateDTO> userMealRelate = userMealMapper.getUserMealRelate();
        return userMealRelate;
    }

    @Override
    public Meal getUserMostLike(Long userId) {
        QueryWrapper<UserMeal> query = new QueryWrapper<>();
        query.eq("user_id", userId)
                .orderByDesc("grade")
                .last("limit 1");
        UserMeal userMeal = this.getOne(query);
        Meal meal = null;
        if (userMeal == null){
            meal = recommend.coolRecommend(UserHolder.get().getId()).get(0);
        }else{
            meal = mealService.getById(userMeal.getMealId());
        }
        return meal;
    }

    @Override
    public boolean add(List<UserMeal> userMeals) {
        // 错误提示消息
        StringBuilder errMsg = new StringBuilder();
        // 参数是否合法标识
        boolean isValidate = false;
        User user = UserHolder.get();
        // 参数判断
        for (int i = 0; i < userMeals.size(); i ++){
            UserMeal userMeal = userMeals.get(i);
            // 用户id
            Long userId = userMeal.getUserId();
            if (userId == null || userId < 0){
                userId = user.getId();
                userMeal.setUserId(userId);
            }
            // 餐品id
            Long mealId = userMeal.getMealId();
            if (mealId == null || mealId < 0){
                errMsg.append("第" + (i + 1) + "条评分数据无选择餐品\n");
                isValidate = true;
            }
        }

        if (isValidate){
            throw new BusinessException(ResultCode.PARAMS_ERROR, errMsg.toString());
        }

        return this.saveBatch(userMeals);

    }

    @Override
    public List<Meal> getTopMeal(Integer top) {
        String key = RedisConstant.DIET_MEAL_TOP;
        // 使用ZREVRANGE命令获取排名前n的饮食及其点击量
        Set<ZSetOperations.TypedTuple<String>> range = stringRedisTemplate.opsForZSet().reverseRangeWithScores(key, 0, top);

        // 将每个饮食及其点击量作为一个整体反序列化为Meal对象，并添加到结果集合中
        List<Meal> topMeals = new ArrayList<>();
        for (ZSetOperations.TypedTuple<String> food : range) {
            Meal meal = JSON.parseObject(food.getValue(), Meal.class);
            topMeals.add(meal);
        }

        return topMeals;
    }

    @Override
    public boolean clickMeal(Long mealId) {
        String key = RedisConstant.DIET_MEAL_TOP;
        double increment = 1d;
        // 查询餐品实体
        Meal meal = mealService.getById(mealId);
        if (meal == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "未找到相关饮食餐品数据");
        }
        stringRedisTemplate.opsForZSet().incrementScore(key, JSON.toJSONString(meal), increment);
        return true;
    }
}
