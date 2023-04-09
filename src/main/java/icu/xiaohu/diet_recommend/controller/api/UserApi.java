package icu.xiaohu.diet_recommend.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.dto.UserDto;
import icu.xiaohu.diet_recommend.model.entity.Plan;
import icu.xiaohu.diet_recommend.model.entity.User;
import icu.xiaohu.diet_recommend.model.entity.UserMeal;
import icu.xiaohu.diet_recommend.model.result.Result;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.service.IPlanService;
import icu.xiaohu.diet_recommend.service.IUserMealService;
import icu.xiaohu.diet_recommend.service.UserService;
import icu.xiaohu.diet_recommend.util.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiaohu
 * @date 2023/04/08/ 17:40
 * @description
 */
@Api(tags = "个人用户服务")
@RestController
@RequestMapping("/api/user")
public class UserApi {
    @Resource
    private UserService userService;
    @Resource
    private IUserMealService userMealService;
    @Resource
    private IPlanService planService;

    @ApiOperation("创建个人计划")
    @PostMapping("/create-plan/")
    public Result<Boolean> createPlan(@RequestBody Plan plan){
        if (plan == null) {
            throw new BusinessException(ResultCode.PARAMS_ERROR, "参数为空");
        }
        boolean isSuccess = planService.add(plan);
        return Result.success(isSuccess);
    }

    @ApiOperation("查看个人计划")
    @PostMapping("/query-plan/")
    public Result<List<Plan>> queryPlan(){
        List<Plan> plans = planService.list(new QueryWrapper<Plan>().eq("user_id", UserHolder.get().getId()));
        return Result.success(plans);
    }

    @ApiOperation("用户餐品评分")
    @PostMapping("/meal-grade/")
    public Result<Boolean> mealGrade(@RequestBody List<UserMeal> userMeals){
        if (userMeals == null || userMeals.isEmpty()) {
            throw new BusinessException(ResultCode.PARAMS_ERROR, "参数为空");
        }
        boolean isSuccess = userMealService.add(userMeals);
        return Result.success(isSuccess);
    }

    @ApiOperation("获取用户个人信息")
    @PostMapping("/info/{userId}")
    public Result<UserDto> getInfo(@PathVariable("userId") Long userId){
        if (userId == null || userId < 1){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "参数不合法");
        }
        User user = userService.getById(userId);
        user.setPassword("");
        return Result.success(user);
    }

    @ApiOperation("当前用户信息")
    @PostMapping("/cur-info/")
    public Result<User> getCurInfo(){
        User user = UserHolder.get();
        return Result.success(user);
    }
}
