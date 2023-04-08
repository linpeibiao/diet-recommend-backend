package icu.xiaohu.diet_recommend.service;

import icu.xiaohu.diet_recommend.model.entity.Meal;
import icu.xiaohu.diet_recommend.model.entity.MenuMeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaohu
 * @since 2023-03-29
 */
public interface IMenuMealService extends IService<MenuMeal> {

    /**
     * 获取菜单餐品
     * @param menuId
     * @return
     */
    List<Meal> getMenuMeals(Long menuId);
}
