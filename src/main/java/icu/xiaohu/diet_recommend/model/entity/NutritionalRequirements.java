package icu.xiaohu.diet_recommend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 营养需求表
 * @TableName t_nutritional_requirements
 */
@TableName(value ="t_nutritional_requirements")
@Data
public class NutritionalRequirements implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 年龄范围
     */
    private String ageRange;

    /**
     * 性别1-男 0-女
     */
    private Integer gender;

    /**
     * 卡路里
     */
    private Integer calories;

    /**
     * 蛋白质
     */
    private Integer proteins;

    /**
     * 碳水化合物
     */
    private Integer carbohydrates;

    /**
     * 脂肪
     */
    private Integer fats;

    /**
     * 维生素
     */
    private String vitamins;

    /**
     * 矿物质
     */
    private String minerals;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * BMI指数
     */
    private Double bmi;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}