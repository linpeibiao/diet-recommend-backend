package icu.xiaohu.diet_recommend.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 接口访问日志表
 * @TableName t_log
 */
@TableName(value ="t_log")
@Data
public class Log implements Serializable {
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
     * 访问方法名称
     */
    private String methodName;

    /**
     * 方法所在类
     */
    private String className;

    /**
     * 方法所在包
     */
    private String packageName;

    /**
     * 访问时间
     */
    private Date requestTime;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 请求状态
     */
    private String status;

    /**
     * 异常信息
     */
    private String exceptionText;

    /**
     * 备注
     */
    private String remark;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}