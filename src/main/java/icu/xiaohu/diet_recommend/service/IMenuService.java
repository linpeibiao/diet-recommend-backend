package icu.xiaohu.diet_recommend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import icu.xiaohu.diet_recommend.model.dto.MenuDto;
import icu.xiaohu.diet_recommend.model.entity.Meal;
import icu.xiaohu.diet_recommend.model.entity.Menu;
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
public interface IMenuService extends IService<Menu> {

    /**
     * 添加菜单
     * @param menuDtos
     * @return
     */
    boolean add(List<MenuDto> menuDtos);

    /**
     * 删除
     * @param menuIds
     * @return
     */
    boolean delete(List<Long> menuIds);

    /**
     * 更新菜单数据
     * @param menuDto
     * @param menuId
     * @return
     */
    boolean modify(Long menuId, MenuDto menuDto);

    /**
     * 获取用户创建的菜单
     * @param userId
     * @return
     */
    List<Menu> getMenusByUserId(Long userId);

    /**
     * 分页获取所有菜单
     * @param pageNum
     * @param pageSize
     * @return
     */
    IPage<Menu> getMenuPage(int pageNum, int pageSize);

    /**
     * 高级列表查询
     * @param menuDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    IPage<Menu> listQuery(MenuDto menuDto, int pageNum, int pageSize);
}
