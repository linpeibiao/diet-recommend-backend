package icu.xiaohu.diet_recommend.controller.admin;

import icu.xiaohu.diet_recommend.anotation.AuthCheck;
import icu.xiaohu.diet_recommend.constant.UserRole;
import icu.xiaohu.diet_recommend.model.result.Result;
import icu.xiaohu.diet_recommend.service.IMealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    private IMealService mealService;

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
        // 参数判断
        // 餐品信息判断
        // 修改状态
        // 审核结果推送
        return Result.success("");
    }

}
