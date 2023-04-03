package icu.xiaohu.diet_recommend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;
import java.time.LocalDateTime;
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
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 计划类型
     */
    private String planType;

    /**
     * 计划目标
     */
    private String planGoal;

    /**
     * 计划进度
     */
    private String planProgress;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 计划截止日期
     */
    private LocalDateTime ddl;

    /**
     * 备用字段1
     */
    private String backup1;

    /**
     * 备用字段2
     */
    private String backup2;

    /**
     * 备注
     */
    private String remark;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Boolean isDeleted;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;


}
