package icu.xiaohu.diet_recommend.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import icu.xiaohu.diet_recommend.anotation.AuthCheck;
import icu.xiaohu.diet_recommend.constant.UserRole;
import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.entity.Log;
import icu.xiaohu.diet_recommend.model.result.Result;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author xiaohu
 * @date 2023/04/23/ 23:15
 * @description
 */
@RestController
@RequestMapping("/log")
@Api(tags = "系统日志服务")
public class LogController {
    @Resource
    private LogService logService;


    @ApiOperation("日志高级查询")
    @PostMapping("/list-query/{pageNum}/{pageSize}")
    @AuthCheck(mustRole = UserRole.ADMIN)
    public Result<IPage<Log>> listQuery(@RequestBody Log log,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
                                       @PathVariable("pageNum")int pageNum,
                                       @PathVariable("pageSize")int pageSize){
        if (pageNum <= 0 || pageSize <= 0){
            return Result.fail("分页参数错误, pageNum、pageSize 要大于0");
        }
        IPage<Log> page = logService.listQuery(log, startTime, endTime, pageNum, pageSize);
        return Result.success(page);
    }

    @ApiOperation("删除日志（可批量）")
    @PostMapping("/list-delete")
    @AuthCheck(mustRole = UserRole.ADMIN)
    public Result<Boolean> listDelete(@RequestBody List<Long> ids){
        if (ids == null || ids.isEmpty()){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "参数不能为空");
        }
        boolean isSuccess = logService.removeBatchByIds(ids);
        return Result.success(isSuccess);
    }


}
