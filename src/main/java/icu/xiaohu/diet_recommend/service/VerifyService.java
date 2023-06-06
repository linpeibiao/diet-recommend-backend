package icu.xiaohu.diet_recommend.service;

import icu.xiaohu.diet_recommend.model.entity.Meal;

import java.util.List;

/**
 * @author xiaohu
 * @date 2023/06/06/ 9:27
 * @description 管理员审核业务逻辑层
 */
public interface VerifyService {
    /**
     * 餐品信息审核
     * @param mealId
     * @param status
     * @return
     */
    Meal mealVerify(Long mealId, Integer status);

    List<Meal> getToVerifyMeal();

}
