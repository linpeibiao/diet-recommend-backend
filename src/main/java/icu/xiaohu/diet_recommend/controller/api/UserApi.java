package icu.xiaohu.diet_recommend.controller.api;

import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.dto.UserDto;
import icu.xiaohu.diet_recommend.model.entity.User;
import icu.xiaohu.diet_recommend.model.result.Result;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xiaohu
 * @date 2023/04/08/ 17:40
 * @description
 */
@Api(tags = "用户信息服务")
@RestController
@RequestMapping("/api/user")
public class UserApi {
    @Resource
    private UserService userService;

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
}
