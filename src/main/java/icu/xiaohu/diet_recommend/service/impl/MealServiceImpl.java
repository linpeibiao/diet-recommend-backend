package icu.xiaohu.diet_recommend.service.impl;

import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.entity.Meal;
import icu.xiaohu.diet_recommend.mapper.MealMapper;
import icu.xiaohu.diet_recommend.model.entity.User;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.service.IMealService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.xiaohu.diet_recommend.util.UserHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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

    @Override
    public boolean add(List<Meal> meals) {
        // 错误提示消息
        StringBuilder errMsg = new StringBuilder();
        // 参数是否合法标识
        boolean isValidate = false;
        User user = UserHolder.get();
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
}
