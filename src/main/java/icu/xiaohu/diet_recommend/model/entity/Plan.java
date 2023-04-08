package icu.xiaohu.diet_recommend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户计划表
 * </p>
 *
 * @author xiaohu
 * @since 2023-04-03
 */
@Getter
@Setter
@TableName("t_plan")
public class Plan implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "计划名称")
    private String name;

    /**
     * 计划类型
     */
    @ApiModelProperty(value = "计划类型")
    private String planType;

    /**
     * 计划目标
     */
    @ApiModelProperty(value = "计划目标")
    private String planGoal;

    /**
     * 计划进度
     */
    @ApiModelProperty(value = "计划进度")
    private String planProgress;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 计划截止日期
     */
    @ApiModelProperty(value = "计划截止日期")
    private LocalDateTime ddl;

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
    @JsonIgnore
    private String remark;

    /**
     * 逻辑删除
     */
    @TableLogic
    @JsonIgnore
    private Boolean isDeleted;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "计划创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "进度更新时间")
    private LocalDateTime updateTime;

    /**
     * 乐观锁
     */
    @Version
    @JsonIgnore
    private Integer version;


}
