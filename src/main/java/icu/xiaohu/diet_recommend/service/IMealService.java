package icu.xiaohu.diet_recommend.service;

import icu.xiaohu.diet_recommend.model.entity.Meal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaohu
 * @since 2023-03-29
 */
public interface IMealService extends IService<Meal> {

    /**
     * 批量添加餐品数据
     * @param meals
     * @return
     */
    boolean add(List<Meal> meals);

}
