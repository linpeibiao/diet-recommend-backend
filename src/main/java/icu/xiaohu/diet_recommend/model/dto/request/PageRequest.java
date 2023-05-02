package icu.xiaohu.diet_recommend.model.dto.request;

import lombok.Data;

/**
 * @author xiaohu
 * @date 2023/05/02/ 14:48
 * @description
 */
@Data
public class PageRequest {
    /**
     * 当前页号
     */
    private int current = 1;

    /**
     * 页面大小
     */
    private int pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

}
