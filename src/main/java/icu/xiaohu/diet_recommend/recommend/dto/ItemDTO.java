package icu.xiaohu.diet_recommend.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 食谱推荐项
 * @author xiaohu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO implements Serializable {
    /** 主键 */
    private Long id;
    /** 名称 */
    private String name;
    /** 日期 */
    private String date;
    /** 链接 */
    private String link;

}
