package icu.xiaohu.diet_recommend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import icu.xiaohu.diet_recommend.model.entity.Meal;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
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
     * @param request
     * @return
     */
    boolean add(List<Meal> meals, HttpServletRequest request);

    /**
     *
     * @param mealIds
     * @return
     */
    boolean delete(List<Long> mealIds);

    /**
     * 当前用户更新餐品数据
     * @param mealId
     * @param meal
     * @return
     */
    boolean updateByCurUser(Long mealId, Meal meal);

    /**
     * 获取用户创建的餐品
     * @param userId
     * @return
     */
    List<Meal> getMealsByUserId(Long userId);

    /**
     * 分页获取餐品数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    IPage<Meal> getMealPage(int pageNum, int pageSize);

    /**
     * 高级列表查询
     * @param meal
     * @param pageNum
     * @param pageSize
     * @return
     */
    IPage<Meal> listQuery(Meal meal, int pageNum, int pageSize);
}
