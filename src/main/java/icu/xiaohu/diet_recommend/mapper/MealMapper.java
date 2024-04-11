package icu.xiaohu.diet_recommend.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import icu.xiaohu.diet_recommend.model.entity.Meal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import icu.xiaohu.diet_recommend.model.vo.MealRecommendSearchVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xiaohu
 * @since 2023-03-29
 */
public interface MealMapper extends BaseMapper<Meal> {
    /**
     * 学生饮食搭配
     * @param mealRecommendSearchVO ..
     */
    IPage<Meal> recommendSearch(MealRecommendSearchVO mealRecommendSearchVO, IPage<?> page);
}
