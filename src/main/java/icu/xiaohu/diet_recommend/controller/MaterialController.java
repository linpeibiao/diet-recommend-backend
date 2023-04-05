package icu.xiaohu.diet_recommend.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import icu.xiaohu.diet_recommend.anotation.AuthCheck;
import icu.xiaohu.diet_recommend.constant.UserRole;
import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.dto.MaterialDto;
import icu.xiaohu.diet_recommend.model.entity.Material;
import icu.xiaohu.diet_recommend.model.result.Result;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.service.IMaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaohu
 * @since 2023-03-29
 */
@RestController
@RequestMapping("/material")
@Api(tags = "食材管理")
public class MaterialController {
    @Autowired
    private IMaterialService materialService;

    @ApiOperation("添加食材数据")
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody List<MaterialDto> materials){
        if (materials == null || materials.isEmpty()){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "参数不能为空");
        }
        boolean isSuccess = materialService.add(materials);
        return Result.success(isSuccess);
    }

    @ApiOperation("删除食材数据")
    @PostMapping("/delete}")
    @AuthCheck(mustRole = UserRole.ADMIN)
    public Result<Boolean> delete(@RequestBody List<Long> materialIds){
        if (materialIds == null || materialIds.isEmpty()){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "参数不能为空");
        }
        boolean isSuccess = materialService.delete(materialIds);
        return Result.success(isSuccess);
    }

    @ApiOperation("更新食材数据")
    @PostMapping("/update/{materialId}")
    @AuthCheck(mustRole = UserRole.ADMIN)
    public Result<Boolean> update(@PathVariable("materialId") Long materialId, @RequestBody MaterialDto materialDto){
        if (materialDto == null || materialId == null){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "参数不能为空");
        }

        boolean isSuccess = materialService.modify(materialId, materialDto);
        return Result.success(isSuccess);

    }

    @ApiOperation("分页获取所有食材")
    @PostMapping("/page/{pageNum}/{pageSize}")
    public Result<IPage<Material>> getMaterialPageByUserId(@PathVariable("pageNum")int pageNum,
                                                   @PathVariable("pageSize")int pageSize){
        if (pageNum <= 0 || pageSize <= 0){
            return Result.fail("分页参数错误, pageNum、pageSize 要大于0");
        }
        IPage<Material> page = materialService.getMaterialPage(pageNum, pageSize);
        return Result.success(page);
    }

    @ApiOperation("高级列表查询")
    @PostMapping("/list-query/{pageNum}/{pageSize}")
    public Result<IPage<Material>> listQuery(@RequestBody MaterialDto materialDto,
                                         @PathVariable("pageNum")int pageNum,
                                         @PathVariable("pageSize")int pageSize){
        if (pageNum <= 0 || pageSize <= 0){
            return Result.fail("分页参数错误, pageNum、pageSize 要大于0");
        }
        IPage<Material> list = materialService.listQuery(materialDto, pageNum, pageSize);
        return Result.success(list);
    }
}

