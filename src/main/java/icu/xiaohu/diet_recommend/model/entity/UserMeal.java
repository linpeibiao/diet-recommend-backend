package icu.xiaohu.diet_recommend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiaohu
 * @since 2023-03-29
 */
@Getter
@Setter
@TableName("t_user_meal")
public class UserMeal implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 餐品实体id
     */
    private Long mealId;

    /**
     * 评分
     */
    private Integer grade;

    /**
     * 标签
     */
    private String tag;

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
    @JsonIgnore
    @TableLogic
    private Boolean isDeleted;

    /**
     * 创建时间
     */
    @JsonIgnore
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonIgnore
    private LocalDateTime updateTime;

    /**
     * 乐观锁
     */
    @Version
    @JsonIgnore
    private Integer version;


}
