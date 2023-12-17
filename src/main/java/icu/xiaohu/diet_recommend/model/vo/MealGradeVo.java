package icu.xiaohu.diet_recommend.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author xiaohu
 * @date 2023/06/26/ 13:05
 * @description
 */
@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealGradeVo implements Serializable {
    private String mealName;
    private String tag;
    private Integer grade;
}
