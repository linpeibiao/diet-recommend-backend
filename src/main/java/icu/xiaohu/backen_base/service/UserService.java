package icu.xiaohu.backen_base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import icu.xiaohu.backen_base.model.dto.UserDto;
import icu.xiaohu.backen_base.model.entity.User;
import icu.xiaohu.backen_base.model.vo.LoginUser;

/**
* @author xiaohu
* @description 针对表【t_user】的数据库操作Service
* @createDate 2022-11-09 14:46:36
*/
public interface UserService extends IService<User> {

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
    void sendCode(String phone);

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
}
