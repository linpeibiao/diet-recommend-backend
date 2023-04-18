package icu.xiaohu.diet_recommend.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 用户身体信息表
 * @TableName t_user_body_info
 */
@TableName(value ="t_user_body_info")
@Data
public class UserBodyInfo implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 用户id
     */
    @JsonIgnore
    private Long userId;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 身高/cm
     */
    private Double height;

    /**
     * 体重/kg
     */
    private Double weight;

    /**
     * BMI 指数
     */
    private String bmi;

    /**
     * 睡眠时间/h
     */
    private Double sleepTime;

    /**
     * 运动时间/h
     */
    private Double sportTime;

    /**
     * 血压
     */
    private String bloodPressure;

    /**
     * 血糖
     */
    private String bloodSugar;

    /**
     * 血氧
     */
    private String bloodOxygen;

    /**
     * 体脂
     */
    private String bodyFat;

    /**
     * 备用字段1
     */
    @JsonIgnore
    private String backup1;

    /**
     * 备用字段2
     */
    @JsonIgnore
    private String backup2;

    /**
     * 备注
     */
    private String remark;

    /**
     * 逻辑删除
     */
    @TableLogic
    @JsonIgnore
    private Integer isDeleted;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonIgnore
    private Date updateTime;

    /**
     * 乐观锁
     */
    @JsonIgnore
    private Integer version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}