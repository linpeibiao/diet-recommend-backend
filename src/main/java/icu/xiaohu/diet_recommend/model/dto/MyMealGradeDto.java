package icu.xiaohu.diet_recommend.model.dto;

import icu.xiaohu.diet_recommend.model.entity.Meal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaohu
 * @date 2024/04/06/ 15:38
 * @description 用户餐品评分数据
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyMealGradeDto extends Meal {
    /**
     * 分值
     */
    private Integer grade;
}
