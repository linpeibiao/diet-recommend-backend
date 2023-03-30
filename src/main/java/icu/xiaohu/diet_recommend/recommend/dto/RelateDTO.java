package icu.xiaohu.diet_recommend.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaohu
 * 关系数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelateDTO {
    /** 用户id */
    private Long useId;
    /** 物品id */
    private Long itemId;
    /** 评分 */
    private Integer grade;


}
