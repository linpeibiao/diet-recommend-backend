package icu.xiaohu.diet_recommend.controller;


import icu.xiaohu.diet_recommend.model.entity.Taste;
import icu.xiaohu.diet_recommend.model.result.Result;
import icu.xiaohu.diet_recommend.service.ITasteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 口味 前端控制器
 * </p>
 *
 * @author xiaohu
 * @since 2023-04-08
 */
@RestController
@RequestMapping("/taste")
@Api(tags = "口味信息服务")
public class TasteController {
    @Autowired
    private ITasteService tasteService;

    @ApiOperation("获取口味")
    @PostMapping
    public Result<List<Taste>> getTastes(){
        return Result.success(tasteService.list());
    }

}

