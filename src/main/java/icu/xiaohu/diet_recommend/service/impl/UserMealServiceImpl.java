package icu.xiaohu.diet_recommend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import org.springframework.stereotype.Service;

import java.util.List;

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
            // TODO 返回热门餐品
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
}
