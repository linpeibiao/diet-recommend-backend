package icu.xiaohu.diet_recommend.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaohu
 * @date 2023/05/02/ 11:49
 * @description
 */

@Getter
@AllArgsConstructor
public enum SearchType {
    MEAL("meal"),
    MATERIAL("material");

    /**
     * 搜索类型
     */
    private String type;
}
