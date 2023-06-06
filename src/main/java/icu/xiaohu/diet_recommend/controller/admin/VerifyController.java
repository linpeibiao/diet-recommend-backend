package icu.xiaohu.diet_recommend.controller.admin;

import icu.xiaohu.diet_recommend.anotation.AuthCheck;
import icu.xiaohu.diet_recommend.constant.UserRole;
import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.entity.Meal;
import icu.xiaohu.diet_recommend.model.result.Result;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.service.IMealService;
import icu.xiaohu.diet_recommend.service.VerifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiaohu
 * @date 2023/06/06/ 1:04
 * @description 管理员信息资源审核接口
 */
@RestController
@RequestMapping("/verify")
@Api(tags = "管理员审核")
public class VerifyController {
    @Resource
    private VerifyService verifyService;

    /**
     *
     * @param mealId
     * @param status
     * @return
     */

    @PostMapping("/meal")
    @ApiOperation("餐品信息审核")
    @AuthCheck(mustRole = UserRole.ADMIN)
    public Result<Object> mealVerify(Long mealId, Integer status){

        Meal meal = verifyService.mealVerify(mealId, status);
        if (meal == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        return Result.success(meal.getName() + "已审核");
    }

    @GetMapping("/meal")
    @ApiOperation("获取待审核餐品")
    @AuthCheck(mustRole = UserRole.ADMIN)
    public Result<List<Meal>> getToVerifyMeal(){
        List<Meal> meals = verifyService.getToVerifyMeal();
        return Result.success(meals);
    }


}
