package icu.xiaohu.diet_recommend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.entity.Meal;
import icu.xiaohu.diet_recommend.model.entity.MenuMeal;
import icu.xiaohu.diet_recommend.mapper.MenuMealMapper;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.service.IMealService;
import icu.xiaohu.diet_recommend.service.IMenuMealService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaohu
 * @since 2023-03-29
 */
@Service
public class MenuMealServiceImpl extends ServiceImpl<MenuMealMapper, MenuMeal> implements IMenuMealService {

    @Autowired
    private IMealService mealService;
    @Override
    public List<Meal> getMenuMeals(Long menuId) {

        QueryWrapper<MenuMeal> query = new QueryWrapper<>();
        query.eq("menu_id", menuId);
        query.select("meal_id");
        List<MenuMeal> list = list(query);
        List<Long> mealIds = list.stream().map(MenuMeal::getMealId).collect(Collectors.toList());
        if (mealIds.isEmpty()){
            throw new BusinessException(ResultCode.NOT_FOUND, "该菜单餐品不可见");
        }
        // 查询餐品
        return mealService.listByIds(mealIds);

    }
}
