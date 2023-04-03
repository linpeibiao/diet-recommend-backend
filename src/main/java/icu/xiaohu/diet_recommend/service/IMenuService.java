package icu.xiaohu.diet_recommend.service;

import icu.xiaohu.diet_recommend.model.dto.MenuDto;
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
}
