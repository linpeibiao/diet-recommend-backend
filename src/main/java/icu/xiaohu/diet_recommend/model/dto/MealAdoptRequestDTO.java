package icu.xiaohu.diet_recommend.model.dto;

import lombok.Data;

/**
 * @author xiaohu
 * @date 2024/05/05/ 21:47
 * @description
 */
@Data
public class MealAdoptRequestDTO {
    private String startTime;
    private String endTime;
    private String mealType;
    private String mealName;
}
