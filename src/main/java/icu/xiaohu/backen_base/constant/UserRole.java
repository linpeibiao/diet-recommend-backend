package icu.xiaohu.backen_base.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaohu
 * @date 2022/12/10/ 22:35
 * @description 用户角色枚举类
 */
@Getter
@AllArgsConstructor
public enum UserRole {
    // 未登录游客
    VISITOR(-1, "游客"),
    COMMON_USER(0, "普通用户"),
    ADMIN(1, "管理员"),
    VIP(2, "会员"),
    SVIP(3, "超级会员");


    private int role;
    @EnumValue
    @JsonValue
    private String roleName;

}
