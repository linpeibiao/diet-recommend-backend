package icu.xiaohu.diet_recommend.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户对象
 *
 * @author TARZAN
 * @version 1.0
 * @company 洛阳图联科技有限公司
 * @copyright (c) 2019 LuoYang TuLian Co'Ltd Inc. All rights reserved.
 * @date 2020/7/31$ 14:55$
 * @since JDK1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
    /** 主键 */
    private Long id;
    /** 年纪 */
    private Integer age;
    /** 性别 */
    private Integer gender;

}
