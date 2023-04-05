package icu.xiaohu.diet_recommend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import icu.xiaohu.diet_recommend.model.entity.Nutrition;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 营养成分表 服务类
 * </p>
 *
 * @author xiaohu
 * @since 2023-04-03
 */
public interface INutritionService extends IService<Nutrition> {

    /**
     * 查询营养成分
     * @param nutrition
     * @return
     */
    List<Nutrition> listByNutrition(String nutrition);
}
