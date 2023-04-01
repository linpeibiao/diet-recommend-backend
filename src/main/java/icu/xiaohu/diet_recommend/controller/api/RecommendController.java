package icu.xiaohu.diet_recommend.controller.api;

import icu.xiaohu.diet_recommend.model.entity.Meal;
import icu.xiaohu.diet_recommend.model.result.Result;
import icu.xiaohu.diet_recommend.recommend.Recommend;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xiaohu
 * @date 2023/04/01/ 18:42
 * @description
 */

@Api(tags = "饮食推荐api")
@RestController
@RequestMapping("/recommend")
public class RecommendController {
    @Autowired
    private Recommend recommend;

    @ApiOperation("基于用户行为推荐")
    @PostMapping("/user-cf")
    public Result<List<Meal>> recommendByUserCf(Long userid){
        List<Meal> meals = recommend.userCfRecommend(userid);
        return Result.success(meals);
    }


    @ApiOperation("特别推荐")
    @PostMapping("/meal-cf")
    /**
     * 基于饮食相似度推荐
     */
    public Result<List<Meal>> recommendByMealCf(Long mealId){
        List<Meal> meals = recommend.itemCfRecommend(mealId);
        return Result.success(meals);
    }

}
