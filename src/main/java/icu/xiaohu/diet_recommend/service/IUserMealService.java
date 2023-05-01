package icu.xiaohu.diet_recommend.service;

import icu.xiaohu.diet_recommend.model.entity.Meal;
import icu.xiaohu.diet_recommend.model.entity.UserMeal;
import com.baomidou.mybatisplus.extension.service.IService;
import icu.xiaohu.diet_recommend.recommend.dto.RelateDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaohu
 * @since 2023-03-29
 */
public interface IUserMealService extends IService<UserMeal> {
    /**
     * 获取用户评分数据
     * @return
     */
    List<RelateDTO> getUserMealRelate();

    /**
     * 获取用户最喜欢的餐品
     * @param userId
     * @return
     */
    Meal getUserMostLike(Long userId);

    /**
     * 用户评分
     * @param userMeals
     * @return
     */
    boolean add(List<UserMeal> userMeals);

    /**
     * 获取饮食排行榜
     * @param top
     * @return
     */
    List<Meal> getTopMeal(Integer top);

    /**
     * 点击某推荐饮食，点击量自增
     * @param mealId
     * @return
     */
    boolean clickMeal(Long mealId);
}
