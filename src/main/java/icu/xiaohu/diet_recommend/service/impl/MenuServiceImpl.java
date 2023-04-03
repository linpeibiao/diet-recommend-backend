package icu.xiaohu.diet_recommend.service.impl;

import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.dto.MenuDto;
import icu.xiaohu.diet_recommend.model.entity.Meal;
import icu.xiaohu.diet_recommend.model.entity.Menu;
import icu.xiaohu.diet_recommend.mapper.MenuMapper;
import icu.xiaohu.diet_recommend.model.entity.MenuMeal;
import icu.xiaohu.diet_recommend.model.entity.User;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.service.IMenuMealService;
import icu.xiaohu.diet_recommend.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.xiaohu.diet_recommend.util.UserHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private IMenuMealService menuMealService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(List<MenuDto> menuDtos) {
        // 错误提示消息
        StringBuilder errMsg = new StringBuilder();
        // 参数是否合法标识
        boolean isValidate = false;
        // 菜单餐品关系列表
        List<MenuMeal> menuMeals = new ArrayList<>();
        User user = UserHolder.get();
        // 参数判断
        for (int i = 0; i < menuDtos.size(); i ++){
            MenuDto menuDto = menuDtos.get(i);
            // 名称判空
            String name = menuDto.getName();
            // 标签判空
            String tag = menuDto.getTag();
            // 价格判空
            BigDecimal price = menuDto.getPrice();
            // 描述判空
            String desc = menuDto.getDescription();
            // 餐品列表判空
            List<Long> mealIds = menuDto.getMealIds();
            if (StringUtils.isAnyBlank(name, tag, desc)){
                errMsg.append("第" + (i + 1) + "名称为【" + name + "】的菜单数据不能为空\n");
                isValidate = true;
            }
            if (price == null){
                errMsg.append("第" + (i + 1) + "名称为【" + name + "】的菜单价格不能为空\n");
                isValidate = true;
            }
            if (mealIds == null || mealIds.isEmpty()){
                errMsg.append("第" + (i + 1) + "名称为【" + name + "】的菜单要包含至少一个餐品\n");
                isValidate = true;
            }

            menuDto.setCreateUserId(user.getId());
            // 转化为实体
            Menu menu = BeanUtil.copyProperties(menuDto, Menu.class);
            menu.setCreateUserId(user.getId());
            // 保存
            this.save(menu);

            // 保存菜单餐品关系
            for (Long mealId : mealIds){
                MenuMeal menuMeal = new MenuMeal();
                menuMeal.setMenuId(menu.getId());
                menuMeal.setMealId(mealId);
                menuMeals.add(menuMeal);
            }
        }
        if (isValidate){
            throw new BusinessException(ResultCode.PARAMS_ERROR, errMsg.toString());
        }
        return menuMealService.saveBatch(menuMeals);
    }
}
