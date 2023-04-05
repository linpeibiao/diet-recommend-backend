package icu.xiaohu.diet_recommend.service.impl;
import java.time.LocalDateTime;

import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import icu.xiaohu.diet_recommend.constant.UserRole;
import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.mapper.MenuMealMapper;
import icu.xiaohu.diet_recommend.model.dto.MenuDto;
import icu.xiaohu.diet_recommend.model.entity.*;
import icu.xiaohu.diet_recommend.mapper.MenuMapper;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.service.IMenuMealService;
import icu.xiaohu.diet_recommend.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.xiaohu.diet_recommend.util.UserHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    @Resource
    private MenuMealMapper menuMealMapper;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(List<Long> menuIds) {
        // 非管理员只能删除自己创建的菜单
        // 还要将关系删除
        QueryWrapper<MenuMeal> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("menu_id", menuIds);
        if (UserRole.ADMIN.getRoleName().equals(getUserRole())){
            menuMealMapper.delete(queryWrapper);
            // TODO 管理员删除用户的订单应该将信息推送给用户
            return removeBatchByIds(menuIds);
        }
        // 判断菜单是否属于当前用户
        User user = UserHolder.get();
        for (Long menuId : menuIds) {
            QueryWrapper<Menu> query = new QueryWrapper<>();
            query.eq("menu_id", menuId);
            Menu menu = this.getOne(query);
            // 不是该用户增添，去除
            if (!menu.getCreateUserId().equals(user.getId())){
                menuIds.remove(menuId);
            }
        }
        menuMealMapper.delete(queryWrapper);
        return removeBatchByIds(menuIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean modify(Long menuId, MenuDto menuDto) {
        UpdateWrapper<Menu> update = new UpdateWrapper<>();
        String name = menuDto.getName();
        String desc = menuDto.getDescription();
        String tag = menuDto.getTag();
        BigDecimal price = menuDto.getPrice();
        String url = menuDto.getUrl();
        List<Long> meals = menuDto.getMealIds();

        // 拼接
        update.set("id", menuId);
        if (!StringUtils.isBlank(name)){
            update.set("name", name);
        }
        if (!StringUtils.isBlank(desc)){
            update.set("description", desc);
        }
        if (!StringUtils.isBlank(tag)){
            update.set("tag", tag);
        }
        if (price != null){
            update.set("price", price);
        }
        if (!StringUtils.isBlank(url)){
            update.set("url", url);
        }
        // 查询menuId是否存在
        if (this.getById(menuId) == null){
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        // 餐品更新比较麻烦
        if (meals != null && !meals.isEmpty()){
            // 删除原来的关系
            menuMealMapper.delete(new QueryWrapper<MenuMeal>().in("meal_id", meals));
            // 重新添加
            List<MenuMeal> menuMeals = new ArrayList<>();
            for (Long meal : meals) {
                MenuMeal menuMeal = new MenuMeal();
                menuMeal.setMenuId(menuId);
                menuMeal.setMealId(meal);
                menuMeals.add(menuMeal);
            }
            menuMealService.saveBatch(menuMeals);
        }
        return this.update(update);
    }

    @Override
    public List<Menu> getMenusByUserId(Long userId) {
        if (userId == null || userId < 1){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }
        // 查询
        return list(new QueryWrapper<Menu>().eq("create_user_id", userId));
    }

    @Override
    public IPage<Menu> getMenuPage(int pageNum, int pageSize) {
        return this.page(new Page<>(pageNum, pageSize));
    }

    @Override
    public IPage<Menu> listQuery(MenuDto menuDto, int pageNum, int pageSize) {
        if (menuDto == null){
            return this.page(new Page<>(pageNum, pageSize));
        }
        QueryWrapper<Menu> query = new QueryWrapper<>();
        // 查询条件拼接
        String tag = menuDto.getTag();
        String name = menuDto.getName();
        if (!StringUtils.isBlank(name)){
            query.like("name", "%"+ name +"%");
        }
        if (!StringUtils.isBlank(tag)){
            query.eq("tag", tag);
        }

        return this.page(new Page<Menu>(pageNum, pageSize), query);
    }

    /**
     * 获取当前用户角色
     * @return
     */
    private String getUserRole(){
        return UserHolder.get().getRoleName().getRoleName();
    }
}
