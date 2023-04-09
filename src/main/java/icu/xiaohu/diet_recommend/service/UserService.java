package icu.xiaohu.diet_recommend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import icu.xiaohu.diet_recommend.model.dto.UserDto;
import icu.xiaohu.diet_recommend.model.entity.User;
import icu.xiaohu.diet_recommend.model.vo.LoginUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author xiaohu
* @description 针对表【t_user】的数据库操作Service
* @createDate 2022-11-09 14:46:36
*/
public interface UserService extends IService<User> {

    /**
     * 当前登录用户
     * @param request
     * @return
     */
    User curUser(HttpServletRequest request);
    /**
     * 退出登录
     * @param request
     */
    void logout(HttpServletRequest request);

    /**
     * 手机登录
     * @param loginUser
     * @return
     */
    String login(LoginUser loginUser);

    /**
     * 发送短信验证码
     * @param phone
     */
    String sendCode(String phone);

    /**
     * 修改用户信息
     * @param userDto
     * @return
     */
    boolean updateInfo(UserDto userDto);

    /**
     * 修改密码
     * @param loginUser
     * @return
     */
    boolean setPassword(LoginUser loginUser);

    /**
     * 通过账号密码登录
     * @param loginUser
     * @return
     */
    String loginByAccount(LoginUser loginUser);

    /**
     * 用户注册
     * @param loginUser
     * @return
     */
    boolean register(LoginUser loginUser);

    /**
     * 解禁禁用用户
     * @param ids
     * @return
     */
    boolean banUserByIds(List<Long> ids);

    /**
     *
     * @param ids
     * @return
     */
    boolean unBanUserByIds(List<Long> ids);
}
