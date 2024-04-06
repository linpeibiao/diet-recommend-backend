package icu.xiaohu.diet_recommend.mapper;

import icu.xiaohu.diet_recommend.model.dto.MyMealGradeDto;
import icu.xiaohu.diet_recommend.model.entity.UserMeal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import icu.xiaohu.diet_recommend.model.vo.MealGradeVo;
import icu.xiaohu.diet_recommend.recommend.dto.RelateDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xiaohu
 * @since 2023-03-29
 */
public interface UserMealMapper extends BaseMapper<UserMeal> {
    /**
     * 获取用户评分数据
     * @return
     */
    List<RelateDTO> getUserMealRelate();

    /**
     * 获取用户评价
     * @param userId
     * @return
     */
    List<MealGradeVo> getMyJudge(@Param("userId") Long userId);

    /**
     * 获取用户餐品评价
     * @param userId
     * @return
     */
    List<MyMealGradeDto> getMyMealGrades(@Param("userId") Long userId);
}
