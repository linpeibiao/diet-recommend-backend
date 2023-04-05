package icu.xiaohu.diet_recommend.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author xiaohu
 * @date 2023/04/05/ 22:07
 * @description 食材dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialDto {
    /**
     * 名称
     */
    private String name;

    /**
     * 参考价格
     */
    @ApiModelProperty(value = "参考价格")
    private BigDecimal price;

    /**
     * 类型
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 营养成分
     */
    private String nutrition;
}
