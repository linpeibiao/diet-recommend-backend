package icu.xiaohu.diet_recommend.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import icu.xiaohu.diet_recommend.model.entity.Material;
import icu.xiaohu.diet_recommend.model.entity.Nutrition;
import icu.xiaohu.diet_recommend.model.result.Result;
import icu.xiaohu.diet_recommend.service.INutritionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 营养成分表 前端控制器
 * </p>
 *
 * @author xiaohu
 * @since 2023-04-03
 */
@RestController
@RequestMapping("/nutrition")
@Api(tags = "营养成分信息服务")
public class NutritionController {
    @Autowired
    private INutritionService nutritionService;

    @ApiOperation("获取营养成分列表")
    @PostMapping("list/{pageNum}/{pageSize}")
    public Result<IPage<Nutrition>> getNutritionPage(@PathVariable("pageNum")int pageNum,
                                                     @PathVariable("pageSize")int pageSize){
        if (pageNum <= 0 || pageSize <= 0){
            return Result.fail("分页参数错误, pageNum、pageSize 要大于0");
        }
        IPage<Nutrition> page = nutritionService.page(new Page<>(pageNum, pageSize));
        return Result.success(page);
    }

    @ApiOperation("查询营养成分")
    @PostMapping("list-by-nutrition/{pageNum}/{pageSize}")
    public Result<List<Nutrition>> getNutritionPageByNutrition(@RequestParam String nutrition){
        List<Nutrition> list = nutritionService.listByNutrition(nutrition);
        return Result.success(list);
    }





}

