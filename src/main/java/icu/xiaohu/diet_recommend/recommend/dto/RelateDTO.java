package icu.xiaohu.diet_recommend.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xiaohu
 * 关系数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelateDTO implements Serializable {
    /** 用户id */
    private Long userId;
    /** 物品id */
    private Long mealId;
    /** 评分 */
    private Integer grade;
}
