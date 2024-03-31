package icu.xiaohu.diet_recommend.controller.api;

import icu.xiaohu.diet_recommend.model.entity.Meal;
import icu.xiaohu.diet_recommend.model.entity.User;
import icu.xiaohu.diet_recommend.model.result.Result;
import icu.xiaohu.diet_recommend.recommend.Recommend;
import icu.xiaohu.diet_recommend.service.IUserMealService;
import icu.xiaohu.diet_recommend.service.UserService;
import icu.xiaohu.diet_recommend.util.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xiaohu
 * @date 2023/04/01/ 18:42
 * @description
 */

@Api(tags = "饮食推荐api")
@RestController
@RequestMapping("/api/recommend")
public class RecommendController {
    @Autowired
    private Recommend recommend;
    @Autowired
    private IUserMealService userMealService;
    @Autowired
    private UserService userService;

    @ApiOperation("基于用户行为推荐")
    @PostMapping("/user-cf")
    public Result<List<Meal>> recommendByUserCf(HttpServletRequest request){
        User user = userService.curUser(request);
        List<Meal> meals = recommend.userCfRecommend(user.getId());
        // meals 为空 说明用户没有评分数据 或者说明是新用户
        // 通过口味选择随机推荐
        // 通过热门餐品推荐
        if (meals == null || meals.isEmpty()){
            meals = recommend.coolRecommend(user.getId());
        }
        // 再从结果中按照用户口味进行推荐
//        if (StringUtils.isNoneEmpty(user.getTasteHobby())){
//            meals = meals.stream().filter(meal -> user.getTasteHobby().equals(meal.getTaste())
//                    || user.getTasteHobby().equals(meal.getType()))
//                    .collect(Collectors.toList());
//        }
        return Result.success(random(meals));
    }


    @ApiOperation("特别推荐")
    @PostMapping("/meal-cf")
    /**
     * 基于饮食相似度推荐
     */
    public Result<List<Meal>> recommendByMealCf(HttpServletRequest request){
        // 查询用户最喜欢的餐品
        // 评分最高的一项

        // 获取当前用户id
        Long userId = userService.curUser(request).getId();
        Long mealId = userMealService.getUserMostLike(userId).getId();
        List<Meal> meals = recommend.itemCfRecommend(mealId);
        return Result.success(random(meals));
    }

    /**
     * 避免重复推荐，随机推荐一定比例的数据
     * @param meals
     * @return
     */
    private List<Meal> random(List<Meal> meals) {
        // 如果不足三个，返回全部
        if (meals.size() < 3) {
            return new ArrayList<>(meals);
        }

        Set<Integer> selectedIndices = new HashSet<>();
        List<Meal> res = new ArrayList<>();
        // 确定返回的数量，向上取整
        int count = (int) Math.ceil(meals.size() / 3.0);

        while (res.size() < count) {
            int index = (int) (Math.random() * meals.size());
            // 仅当该索引未被选中时才添加到结果列表
            if (selectedIndices.add(index)) {
                res.add(meals.get(index));
            }
        }

        return res;
    }

}
