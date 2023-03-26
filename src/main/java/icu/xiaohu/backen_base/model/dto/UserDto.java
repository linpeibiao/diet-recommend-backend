package icu.xiaohu.backen_base.model.dto;

import icu.xiaohu.backen_base.constant.UserRole;
import lombok.Data;

/**
 * @author xiaohu
 * @date 2022/11/09/ 17:30
 * @description
 */
@Data
public class UserDto {
    /**
     * 主键
     */
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 昵称
     */
    private String nackname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 用户状态 0-正常，1-禁用
     */
    private Integer status;

    /**
     * 用户角色ID
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private UserRole roleName;

    private String remark;
}
