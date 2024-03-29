package icu.xiaohu.diet_recommend.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.dto.UserDto;
import icu.xiaohu.diet_recommend.model.entity.*;
import icu.xiaohu.diet_recommend.model.result.Result;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.model.vo.MealGradeVo;
import icu.xiaohu.diet_recommend.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
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
    @Resource
    private UserBodyInfoService userBodyInfoService;
    @Resource
    private FoodIntakeRecordsService foodIntakeRecordsService;

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
    public Result<List<Plan>> queryPlan(HttpServletRequest request){
        List<Plan> plans = planService.list(new QueryWrapper<Plan>().eq("user_id", userService.curUser(request).getId()));
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

    @ApiOperation("我的评价")
    @PostMapping("/judge/")
    public Result<MealGradeVo> judge(){
        List<MealGradeVo> myJudge = userMealService.getMyJudge();
        return Result.success(myJudge);
    }



    @ApiOperation("获取用户个人信息")
    @PostMapping("/info/{userId}")
    public Result<UserDto> getInfo(@PathVariable("userId") Long userId){
        if (userId == null || userId < 1){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "参数不合法");
        }
        User user = userService.getById(userId);
        if (user == null){
            throw new BusinessException(ResultCode.NULL_ERROR, "无该用户信息");
        }
        user.setPassword("");
        return Result.success(user);
    }

    @ApiOperation("当前用户信息")
    @PostMapping("/cur-info/")
    public Result<User> getCurInfo(HttpServletRequest request){
        User user = userService.curUser(request);
        return Result.success(user);
    }

    @ApiOperation("获取当前用户身体信息")
    @PostMapping("/cur-body-info/")
    public Result<UserBodyInfo> getCurBodyInfo(HttpServletRequest request){
        User user = userService.curUser(request);
        UserBodyInfo userBodyInfo = userBodyInfoService.getOne(new QueryWrapper<UserBodyInfo>().eq("user_id", user.getId()));
        return Result.success(userBodyInfo);
    }

    @ApiOperation("编辑当前用户身体信息")
    @PostMapping("/set-cur-body-info/")
    public Result<UserBodyInfo> setCurBodyInfo(HttpServletRequest request, @RequestBody UserBodyInfo userBodyInfo){
        User user = userService.curUser(request);
        UserBodyInfo temp = userBodyInfoService.getOne(new QueryWrapper<UserBodyInfo>().eq("user_id", user.getId()));
        if (temp != null){
            userBodyInfoService.update(userBodyInfo, new UpdateWrapper<UserBodyInfo>().eq("user_id", user.getId()));
        }else{
            userBodyInfo.setUserId(user.getId());
            userBodyInfoService.save(userBodyInfo);
        }

        return Result.success(userBodyInfoService.getOne(new QueryWrapper<UserBodyInfo>().eq("user_id", user.getId())));

    }

    @ApiOperation("用户新增饮食记录")
    @PostMapping("/add-food-intake-record")
    public Result<Boolean> addFoodIntakeRecord(HttpServletRequest request, @RequestBody FoodIntakeRecords foodIntakeRecords){

        return Result.success(foodIntakeRecordsService.add(foodIntakeRecords));
    }

    @ApiOperation("用户新增饮食记录")
    @PutMapping("/edit-food-intake-record")
    public Result<Integer> editFoodIntakeRecord(HttpServletRequest request, @RequestBody FoodIntakeRecords foodIntakeRecords){

        return Result.success(foodIntakeRecordsService.update(foodIntakeRecords));
    }

    @ApiOperation("用户获取饮食记录")
    @GetMapping("/get-food-intake-record")
    public Result<List<FoodIntakeRecords>> getFoodIntakeRecord(HttpServletRequest request){

        return Result.success(foodIntakeRecordsService.getDietRecords());
    }

    @ApiOperation("用户删除饮食记录")
    @DeleteMapping("/delete-food-intake-record/{recordId}")
    public Result<Boolean> deleteFoodIntakeRecord(HttpServletRequest request, @PathParam("recordId") Long recordId){

        return Result.success(foodIntakeRecordsService.deleteRecord(recordId));
    }




}
