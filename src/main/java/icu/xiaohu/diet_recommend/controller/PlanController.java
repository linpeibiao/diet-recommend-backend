package icu.xiaohu.diet_recommend.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import icu.xiaohu.diet_recommend.anotation.AuthCheck;
import icu.xiaohu.diet_recommend.constant.UserRole;
import icu.xiaohu.diet_recommend.model.dto.MenuDto;
import icu.xiaohu.diet_recommend.model.entity.Meal;
import icu.xiaohu.diet_recommend.model.entity.Menu;
import icu.xiaohu.diet_recommend.model.entity.Plan;
import icu.xiaohu.diet_recommend.model.result.Result;
import icu.xiaohu.diet_recommend.service.IPlanService;
import icu.xiaohu.diet_recommend.util.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户计划表 前端控制器
 * </p>
 *
 * @author xiaohu
 * @since 2023-04-03
 */
@RestController
@RequestMapping("/plan")
@Api(tags = "用户计划信息服务")
public class PlanController {
    @Autowired
    private IPlanService planService;

    @ApiOperation("用户计划列表")
    @PostMapping("/list/{pageNum}/{pageSize}")
//    @AuthCheck(mustRole = UserRole.ADMIN)
    public Result<IPage<Plan>> listQuery(@PathVariable("pageNum")int pageNum,
                                         @PathVariable("pageSize")int pageSize){
        if (pageNum <= 0 || pageSize <= 0){
            return Result.fail("分页参数错误, pageNum、pageSize 要大于0");
        }

        Long userId = UserHolder.get().getId();
        IPage<Plan> list = planService.page(new Page<Plan>(pageNum, pageSize), new LambdaQueryWrapper<Plan>().eq(Plan::getUserId, userId));
        return Result.success(list);
    }
}

