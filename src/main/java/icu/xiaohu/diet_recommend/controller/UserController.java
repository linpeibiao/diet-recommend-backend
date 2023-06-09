package icu.xiaohu.diet_recommend.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import icu.xiaohu.diet_recommend.anotation.AuthCheck;
import icu.xiaohu.diet_recommend.constant.UserRole;
import icu.xiaohu.diet_recommend.model.dto.UserDto;
import icu.xiaohu.diet_recommend.model.entity.Meal;
import icu.xiaohu.diet_recommend.model.entity.User;
import icu.xiaohu.diet_recommend.model.result.Result;

import icu.xiaohu.diet_recommend.model.vo.LoginUser;
import icu.xiaohu.diet_recommend.service.UserService;
import icu.xiaohu.diet_recommend.util.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xiaohu
 * @date 2022/11/09/ 0:12
 * @description
 */
@Api(tags = "用户信息服务")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation("通过账号设置密码")
    @PutMapping("/set-password")
    public Result<String> setPassword(@RequestBody LoginUser loginUser){
        boolean isSuccess = userService.setPassword(loginUser);
        return isSuccess ? Result.success("修改成功") : Result.fail("");
    }

    @ApiOperation("用户更改手机号")
    @PutMapping("/change-phone/{phone}")
    public Result<String> changePhone(@PathVariable("phone") String phone){
        // TODO
        return null;
    }

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result<User> register(@RequestBody LoginUser loginUser){
        boolean isSuccess = userService.register(loginUser);
        if (!isSuccess){
            return Result.fail();
        }
        return Result.success(loginUser.getAccount());
    }

    /**
     *
     * @param userDto
     * @return
     */
    @ApiOperation("用户更新信息")
    @PutMapping("/update")
    public Result<String> update(@RequestBody UserDto userDto, HttpServletRequest request){
        boolean isSuccess = userService.updateInfo(userDto, request);
        return isSuccess ? Result.success("修改成功") : Result.fail("");
    }

    @ApiOperation("用户账号登陆")
    @PostMapping("/login-by-account")
    public Result<String> loginByAccount(@RequestBody LoginUser loginUser){
        String token = userService.loginByAccount(loginUser);
        return Result.success(token);
    }

    /**
     * 用户登录注册
     * @return
     */
    @ApiOperation("用户手机登录")
    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginUser loginUser){
        String token = userService.login(loginUser);
        return Result.success(token);
    }

    @ApiOperation("发送手机验证码")
    @GetMapping("/sendCode/{phone}")
    public Result<String> sendCode(@PathVariable("phone") String phone){
        String code = userService.sendCode(phone);
        return Result.success(code);
    }

    @ApiOperation("获取用户列表")
    @PostMapping("/list-by-ids")
    @AuthCheck(mustRole = UserRole.ADMIN)
    public Result<List<User>> getUserList(@RequestBody List<Long> userIdList){
        if (userIdList.isEmpty()){
            return null;
        }
        List<User> userList = userService.listByIds(userIdList);
        userList.forEach(user -> user.setPassword(""));
        return Result.success(userList);
    }

    @ApiOperation("用户状态禁用")
    @PostMapping("/ban")
    @AuthCheck(mustRole = UserRole.ADMIN)
    public Result<List<User>> banUserByIds(@RequestBody List<Long> userIdList){
        if (userIdList.isEmpty()){
            return null;
        }
        boolean isSuccess = userService.banUserByIds(userIdList);
        return Result.success(isSuccess);
    }

    @ApiOperation("用户状态解除禁用")
    @PostMapping("/unBan")
    @AuthCheck(mustRole = UserRole.ADMIN)
    public Result<List<User>> unBanUserByIds(@RequestBody List<Long> userIdList){
        if (userIdList.isEmpty()){
            return null;
        }
        boolean isSuccess = userService.unBanUserByIds(userIdList);
        return Result.success(isSuccess);
    }

    @ApiOperation("分页获取所有用户")
    @PostMapping("/page/{pageNum}/{pageSize}")
    @AuthCheck(mustRole = UserRole.ADMIN)
    public Result<IPage<User>> getUserPage(@PathVariable("pageNum")int pageNum,
                                                       @PathVariable("pageSize")int pageSize){
        if (pageNum <= 0 || pageSize <= 0){
            return Result.fail("分页参数错误, pageNum、pageSize 要大于0");
        }
        IPage<User> page = userService.page(new Page<User>(pageNum, pageSize));
        List<User> userList = page.getRecords();
        userList.forEach(user -> user.setPassword(""));
        return Result.success(page);
    }

    @ApiOperation("用户退出登陆")
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request){

        userService.logout(request);

        return Result.success("退出登陆成功");
    }


}
