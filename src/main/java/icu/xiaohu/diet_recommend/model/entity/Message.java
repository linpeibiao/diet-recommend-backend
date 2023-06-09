package icu.xiaohu.diet_recommend.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @TableName t_message
 * @author xiaohu
 */
@TableName(value ="t_message")
@Data
@Accessors(chain = true)
public class Message implements Serializable {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 简介
     */
    private String brief;

    /**
     * 消息主体
     */
    private String content;

    /**
     * 类型
     */
    private String type;

    /**
     * 创建用户
     */
    private Long producer;

    /**
     * 消费用户
     */
    private Long consumer;

    /**
     * 状态
     */
    private Integer status;

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
    @JsonIgnore
    private Integer isDeleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 乐观锁
     */
    @JsonIgnore
    private Integer version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}