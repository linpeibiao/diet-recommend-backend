package icu.xiaohu.diet_recommend.model.vo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiaohu
 * @date 2024/05/05/ 21:39
 * @description 餐品采用详情报表
 */
@Data
@Builder
@Accessors(chain = true)
public class MealAdoptReportVO {
    private String mealName;
    private String mealType;
    /**
     * 餐品平均分
     */
    private Double averageGrade;
    private Integer adoptCount;
    /**
     * 原料
     */
    private String material;
    private String picture;
    private String description;

}
