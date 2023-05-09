package icu.xiaohu.diet_recommend.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.entity.Meal;
import icu.xiaohu.diet_recommend.model.result.Result;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.service.IMealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public Result<Boolean> add(@RequestBody List<Meal> meals, HttpServletRequest request){
        if (meals == null || meals.isEmpty()){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "参数不能为空");
        }
        boolean isSuccess = mealService.add(meals,request);
        return Result.success(isSuccess);
    }

    @ApiOperation("删除餐品数据")
    @PostMapping("/delete}")
    public Result<Boolean> delete(@RequestBody List<Long> mealIds){
        if (mealIds == null || mealIds.isEmpty()){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "参数不能为空");
        }
        boolean isSuccess = mealService.delete(mealIds);
        return Result.success(isSuccess);
    }

    @ApiOperation("更新餐品数据")
    @PostMapping("/update/{mealId}")
    public Result<Boolean> update(@PathVariable("mealId") Long mealId, @RequestBody Meal meal){
        if (meal == null || mealId == null){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "参数不能为空");
        }

        boolean isSuccess = mealService.updateByCurUser(mealId, meal);
        return Result.success(isSuccess);

    }

    @ApiOperation("获取用户创建的餐品")
    @PostMapping("/list-by-user/{userId}")
    public Result<List<Meal>> getNotesByNoteBaseId(@PathVariable("userId") Long userId){
        List<Meal> mealList = mealService.getMealsByUserId(userId);
        return Result.success(mealList);
    }

    @ApiOperation("分页获取所有餐品")
    @PostMapping("/page/{pageNum}/{pageSize}")
    public Result<IPage<Meal>> getMealPageByUserId(@PathVariable("pageNum")int pageNum,
                                                           @PathVariable("pageSize")int pageSize){
        if (pageNum <= 0 || pageSize <= 0){
            return Result.fail("分页参数错误, pageNum、pageSize 要大于0");
        }
        IPage<Meal> page = mealService.getMealPage(pageNum, pageSize);
        return Result.success(page);
    }

    @ApiOperation("高级列表查询")
    @PostMapping("/list-query/{pageNum}/{pageSize}")
    public Result<IPage<Meal>> listQuery(@RequestBody Meal meal,
                                        @PathVariable("pageNum")int pageNum,
                                        @PathVariable("pageSize")int pageSize){
        if (pageNum <= 0 || pageSize <= 0){
            return Result.fail("分页参数错误, pageNum、pageSize 要大于0");
        }
        IPage<Meal> list = mealService.listQuery(meal, pageNum, pageSize);
        return Result.success(list);
    }


}

