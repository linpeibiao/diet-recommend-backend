package icu.xiaohu.diet_recommend.model.vo;

import icu.xiaohu.diet_recommend.model.entity.Material;
import icu.xiaohu.diet_recommend.model.entity.Meal;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiaohu
 * @date 2023/05/02/ 14:40
 * @description
 */
@Data
public class SearchVO implements Serializable {
    private List<Meal> meals;
    private List<Material> materials;
    private List<?> dataList;
}
