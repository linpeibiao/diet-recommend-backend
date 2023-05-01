package icu.xiaohu.diet_recommend.controller.api;

import icu.xiaohu.diet_recommend.model.entity.Meal;
import icu.xiaohu.diet_recommend.model.result.Result;
import icu.xiaohu.diet_recommend.service.IUserMealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiaohu
 * @date 2023/05/01/ 20:50
 * @description 饮食推荐喜好度排行榜
 */
@Api(tags = "饮食排行榜")
@RestController
@RequestMapping("/top")
public class TopListController {
    @Resource
    private IUserMealService userMealService;


    @ApiOperation("获取排行榜")
    @GetMapping("/get/{top}")
    public Result<List<Meal>> getTopMeal(@PathVariable ("top") Integer top){
        List<Meal> list = userMealService.getTopMeal(top);
        return Result.success(list);
    }

    @ApiOperation("饮食推荐点击")
    @GetMapping("/onClick/{mealId}")
    public Result<Boolean> onClick(@PathVariable ("mealId") Long mealId){
        boolean isSuccess = userMealService.clickMeal(mealId);
        return Result.success(isSuccess);
    }
}
