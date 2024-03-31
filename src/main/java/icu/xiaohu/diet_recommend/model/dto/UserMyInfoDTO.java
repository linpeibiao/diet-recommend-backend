package icu.xiaohu.diet_recommend.model.dto;

import icu.xiaohu.diet_recommend.model.entity.Plan;
import icu.xiaohu.diet_recommend.model.entity.UserBodyInfo;
import icu.xiaohu.diet_recommend.recommend.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author xiaohu
 * @date 2024/03/31/ 11:50
 * @description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserMyInfoDTO {
    /**
     * 用户基本信息
     */
    private UserDto userDto;

    /**
     * 用户身体信息
     */
    private UserBodyInfo userBodyInfo;

    /**
     * 用户计划
     */
    private Plan plan;
}
