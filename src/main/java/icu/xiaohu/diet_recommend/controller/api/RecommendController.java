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
    public Result<List<Meal>> recommendByUserCf(Long id){
        List<Meal> meals = recommend.userCfRecommend(id);
        return Result.success(meals);
    }

}
