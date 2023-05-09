package icu.xiaohu.diet_recommend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import icu.xiaohu.diet_recommend.constant.UserRole;
import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.entity.Meal;
import icu.xiaohu.diet_recommend.mapper.MealMapper;
import icu.xiaohu.diet_recommend.model.entity.User;
import icu.xiaohu.diet_recommend.model.entity.UserMeal;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.service.IMealService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.xiaohu.diet_recommend.service.IUserMealService;
import icu.xiaohu.diet_recommend.service.UserService;
import icu.xiaohu.diet_recommend.util.UserHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaohu
 * @since 2023-03-29
 */
@Service
public class MealServiceImpl extends ServiceImpl<MealMapper, Meal> implements IMealService {
    @Autowired
    private IUserMealService userMealService;
    @Autowired
    private UserService userService;

    @Override
    public boolean add(List<Meal> meals, HttpServletRequest request) {
        // 错误提示消息
        StringBuilder errMsg = new StringBuilder();
        // 参数是否合法标识
        boolean isValidate = false;
        User user = userService.curUser(request);
        // 参数判断
        for (int i = 0; i < meals.size(); i ++){
            Meal meal = meals.get(i);
            // 名称判空
            String name = meal.getName();
            // 口味判空
            String taste = meal.getTaste();
            // 类型判空
            String type = meal.getType();
            // 描述判空
            String desc = meal.getDescription();
            // 主料判空
            String mainMaterial = meal.getMainMaterial();
            if (StringUtils.isAnyBlank(name, taste, type, desc, mainMaterial)){
                errMsg.append("第" + (i + 1) + "名称为【" + name + "】的餐品数据不能为空\n");
                isValidate = true;
            }
            meal.setCreateUserId(user.getId());
        }

        if (isValidate){
            throw new BusinessException(ResultCode.PARAMS_ERROR, errMsg.toString());
        }

        return this.saveBatch(meals);
    }

    @Override
    public boolean delete(List<Long> mealIds) {
        // 判端用户身份
        User user = UserHolder.get();
        if (UserRole.ADMIN.getRoleName().equals(user.getRoleName().getRoleName())){
            return this.removeByIds(mealIds);
        }
        // 非管理员就判断该餐品是否为用户增加
        for (Long mealId : mealIds){
            QueryWrapper<UserMeal> query = new QueryWrapper<>();
            query.eq("meal_id", mealId);
            UserMeal userMeal = userMealService.getOne(query);
            // 不是该用户增添，去除
            if (!userMeal.getUserId().equals(user.getId())){
                mealIds.remove(mealId);
            }
        }
        // 前端页面当然只能在个人页面那可以选择删除

        return this.removeByIds(mealIds);
    }

    @Override
    public boolean updateByCurUser(Long mealId, Meal meal) {
        User user = UserHolder.get();
        meal.setId(mealId);
        if (UserRole.ADMIN.getRoleName().equals(user.getRoleName().getRoleName())){
            return this.updateById(meal);
        }

        QueryWrapper<UserMeal> query = new QueryWrapper<>();
        query.eq("mealId", mealId);
        UserMeal userMeal = userMealService.getOne(query);
        // 不是该用户增添，去除
        if (!userMeal.getUserId().equals(user.getId())){
            throw new BusinessException(ResultCode.NO_AUTH, "仅餐品创建者可以更新");
        }
        return this.updateById(meal);
    }

    @Override
    public List<Meal> getMealsByUserId(Long userId) {
        if (userId == null || userId < 1){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }
        // 查询
        return list(new QueryWrapper<Meal>().eq("create_user_id", userId));
    }

    @Override
    public IPage<Meal> getMealPage(int pageNum, int pageSize) {
        return this.page(new Page<>(pageNum, pageSize));
    }

    @Override
    public IPage<Meal> listQuery(Meal meal, int pageNum, int pageSize) {
        if (meal == null){
            return this.page(new Page<>(pageNum, pageSize));
        }
        QueryWrapper<Meal> query = new QueryWrapper<>();
        // 查询条件拼接
        String taste = meal.getTaste();
        String type = meal.getType();
        String name = meal.getName();

        if (!StringUtils.isBlank(taste)){
            query.like("taste", "%"+ taste +"%");
        }
        if (!StringUtils.isBlank(name)){
            query.like("name", "%"+ name +"%");
        }
        if (!StringUtils.isBlank(type)){
            query.eq("type", type);
        }

        return this.page(new Page<Meal>(pageNum, pageSize), query);
    }
}
