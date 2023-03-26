package icu.xiaohu.diet_recommend.model.vo;

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
