package icu.xiaohu.diet_recommend.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xiaohu
 * @date 2022/11/09/ 15:46
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements Serializable {
    private String account;
    private String phone;
    private String password;
    private String code;
}
