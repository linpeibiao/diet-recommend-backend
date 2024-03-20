package icu.xiaohu.diet_recommend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 每日菜品推荐表
 * @TableName t_daily_recommendation
 */
@TableName(value ="t_daily_recommendation")
@Data
public class DailyRecommendation implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 搭配、推荐日期
     */
    private Date date;

    /**
     * 菜品类型：如早餐、午餐、晚餐、下午茶、宵夜等
     */
    private String mealType;

    /**
     * 搭配的菜品id
     */
    private String mealIds;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private Date createTime;

    /**
     * 推荐对象
     */
    private String recommendationObject;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}