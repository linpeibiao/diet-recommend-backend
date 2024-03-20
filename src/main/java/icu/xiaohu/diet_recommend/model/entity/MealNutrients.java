package icu.xiaohu.diet_recommend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 餐品营养成分表
 * @TableName t_meal_nutrients
 */
@TableName(value ="t_meal_nutrients")
@Data
public class MealNutrients implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 餐品id
     */
    private Long mealId;

    /**
     * 
     */
    private Double calories;

    /**
     * 
     */
    private Double proteins;

    /**
     * 
     */
    private Double carbohydrates;

    /**
     * 
     */
    private Double fats;

    /**
     * 
     */
    private String vitamins;

    /**
     * 
     */
    private String minerals;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}