package icu.xiaohu.diet_recommend.controller;


import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.dto.MenuDto;
import icu.xiaohu.diet_recommend.model.result.Result;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

}

