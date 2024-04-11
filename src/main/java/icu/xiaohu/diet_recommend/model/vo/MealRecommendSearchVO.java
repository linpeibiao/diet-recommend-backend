package icu.xiaohu.diet_recommend.model.vo;

import lombok.Data;

/**
 * @author xiaohu
 * @date 2024/04/11/ 17:33
 * @description 学生饮食搭配筛选请求体
 */
@Data
public class MealRecommendSearchVO {
    private Integer maxAge;
    private Integer minAge;

    /**
     * 阶段：比如小学生 初中生 高中生 大学生
     */
    private String phase;

    private Integer gender;

    /**
     * BMI 指数：偏高 正常 偏低
     */
    private String BMI;

    /**
     * 餐品类型
     */
    private String type;

    /**
     * 餐品标签，例如：减肥、养生、增重、高中生 初中生 大学生等等
     */
    private String tag;



}
