package icu.xiaohu.backen_base.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaohu
 * @date 2022/11/09/ 15:46
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {
    private String account;
    private String phone;
    private String password;
    private String code;
}
