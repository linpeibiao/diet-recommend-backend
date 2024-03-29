package icu.xiaohu.diet_recommend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户饮食记录表
 * @TableName t_food_intake_records
 */
@TableName(value ="t_food_intake_records")
@Data
public class FoodIntakeRecords implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 
     */
    private Long userId;

    /**
     * 
     */
    private Date date;

    /**
     * 餐饮类型：如早餐、午餐、晚餐、宵夜等
     */
    private String mealType;

    /**
     * 
     */
    private String mealIds;

    /**
     * 备注
     */
    private String remarks;

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