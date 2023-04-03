package icu.xiaohu.diet_recommend.controller;


import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.entity.Meal;
import icu.xiaohu.diet_recommend.model.result.Result;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.service.IMealService;
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
@Api(tags = "餐品管理")
@RequestMapping("/meal")
public class MealController {
    @Autowired
    private IMealService mealService;

    @ApiOperation("添加餐品数据")
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody List<Meal> meals){
        if (meals.isEmpty()){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "参数不能为空");
        }
        boolean isSuccess = mealService.add(meals);
        return Result.success(isSuccess);
    }

}

