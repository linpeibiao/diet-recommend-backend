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
@TableName("t_meal")
public class Meal implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 口味
     */
    private String taste;

    /**
     * 分类
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 主料
     */
    private String mainMaterial;

    /**
     * 辅料
     */
    private String ancillaryMaterial;

    /**
     * 图片url
     */
    private String url;

    /**
     * 状态 0-正常
     * 1-禁用
     */
    private Integer status;

    /**
     * 创建用户id
     */
    private Long createUserId;

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
