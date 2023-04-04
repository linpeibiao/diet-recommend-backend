package icu.xiaohu.diet_recommend.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.dto.MenuDto;
import icu.xiaohu.diet_recommend.model.entity.Meal;
import icu.xiaohu.diet_recommend.model.entity.Menu;
import icu.xiaohu.diet_recommend.model.result.Result;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaohu
 * @since 2023-03-29
 */
@RestController
@RequestMapping("/menu")
@Api( tags = "菜单管理")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @ApiOperation("添加菜单")
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody List<MenuDto> menuDtos){
        if (menuDtos == null || menuDtos.isEmpty()){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "参数不能为空");
        }
        boolean isSuccess = menuService.add(menuDtos);
        return Result.success(isSuccess);
    }

    @ApiOperation("删除菜单")
    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestBody List<Long> menuIds){
        if (menuIds == null || menuIds.isEmpty()){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "参数不能为空");
        }
        boolean isSuccess = menuService.delete(menuIds);
        return Result.success(isSuccess);
    }

    @ApiOperation("更新菜单")
    @PostMapping("/update/{menuId}")
    public Result<Boolean> update(@PathVariable("menuId") Long menuId, @RequestBody MenuDto menuDto){
        if (menuId == null || menuDto == null){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "参数不能为空");
        }
        boolean isSuccess = menuService.modify(menuId, menuDto);
        return Result.success(isSuccess);
    }

    @ApiOperation("获取用户创建的菜单")
    @PostMapping("/list-by-user/{userId}")
    public Result<List<Menu>> getNotesByNoteBaseId(@PathVariable("userId") Long userId){
        List<Menu> menuList = menuService.getMenusByUserId(userId);
        return Result.success(menuList);
    }

    @ApiOperation("分页获取所有菜单")
    @PostMapping("/page/{pageNum}/{pageSize}")
    public Result<IPage<Menu>> getMenuPage(@PathVariable("pageNum")int pageNum,
                                           @PathVariable("pageSize")int pageSize){
        if (pageNum <= 0 || pageSize <= 0){
            return Result.fail("分页参数错误, pageNum、pageSize 要大于0");
        }
        IPage<Menu> page = menuService.getMenuPage(pageNum, pageSize);
        return Result.success(page);
    }

    @ApiOperation("高级列表查询")
    @PostMapping("/list-query/{pageNum}/{pageSize}")
    public Result<IPage<Meal>> listQuery(@RequestBody MenuDto menuDto,
                                         @PathVariable("pageNum")int pageNum,
                                         @PathVariable("pageSize")int pageSize){
        if (pageNum <= 0 || pageSize <= 0){
            return Result.fail("分页参数错误, pageNum、pageSize 要大于0");
        }
        IPage<Menu> list = menuService.listQuery(menuDto, pageNum, pageSize);
        return Result.success(list);
    }




}

