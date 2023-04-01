package icu.xiaohu.diet_recommend.mapper;

import icu.xiaohu.diet_recommend.model.entity.UserMeal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import icu.xiaohu.diet_recommend.recommend.dto.RelateDTO;

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
}
