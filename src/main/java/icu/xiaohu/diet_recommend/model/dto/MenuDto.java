package icu.xiaohu.diet_recommend.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xiaohu
 * @date 2023/04/03/ 17:58
 * @description 菜单DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {
    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 标签
     */
    private String tag;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 图片url
     */
    private String url;

    /**
     * 营养价值id
     */
    private String nutrition;

    /**
     * id
     */
    @JsonIgnore
    private Long createUserId;

    /**
     * 餐品ids
     */
    @ApiModelProperty(value = "餐品id列表")
    private List<Long> mealIds;
}
