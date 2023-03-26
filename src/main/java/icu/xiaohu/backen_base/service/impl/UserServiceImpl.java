package icu.xiaohu.backen_base.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.xiaohu.backen_base.constant.UserRole;
import icu.xiaohu.backen_base.exception.BusinessException;
import icu.xiaohu.backen_base.mapper.UserMapper;
import icu.xiaohu.backen_base.model.dto.UserDto;
import icu.xiaohu.backen_base.model.entity.User;
import icu.xiaohu.backen_base.model.result.ResultCode;
import icu.xiaohu.backen_base.model.vo.LoginUser;
import icu.xiaohu.backen_base.service.UserService;
import icu.xiaohu.backen_base.util.CodeGenerator;
import icu.xiaohu.backen_base.util.JwtHelper;
import icu.xiaohu.backen_base.util.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static icu.xiaohu.backen_base.constant.RedisConstant.*;

/**
* @author xiaohu
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2022-11-09 14:46:36
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {
    /**
     * 用户随机昵称前缀
     */
    private static final String USER_NICKNAME_PREFIX = "note";
    /**
     * 用户随机账号前缀
     */
    private static final String USER_ACCOUNT_PREFIX = "notebook";
    /**
     * 密码加密盐值
     */
    private static final String SALT = "xiaohu";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String login(LoginUser loginUser) {
        // 1校验用户post请求中的手机号验证码信息是否为空
        String phone = loginUser.getPhone();
        String code = loginUser.getCode();
        if (StringUtils.isAnyEmpty(phone, code)){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "手机号或验证码为空");
        }

        // 2校验用户输入的验证码和缓存中的是否一致
        String key = LOGIN_CODE_KEY + phone;
        String redisCode = stringRedisTemplate.opsForValue().get(key);
        if (!code.equals(redisCode)){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "验证码不正确");
        }
        // 3通过手机号获取用户
        User user = getOne(new QueryWrapper<User>()
                .eq("phone", phone));

        // 4判断用户是否存在，不存在则新建一个
        if (user == null){
            user = createUserByPhone(phone);
        }
        // 5判断用户账户状态
        if (user.getStatus() == 1){
            throw new BusinessException(ResultCode.FAIL, "账户异常，无法登录");
        }
        return loginCommonWork(user, user.getPhone());
    }

    @Override
    public void sendCode(String phone) {
        // 判断手机号合法
        if (StringUtils.isAnyEmpty(phone)){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }
        // 从缓存中拿到短信
        String codeKey = LOGIN_CODE_KEY + phone;
        String code = stringRedisTemplate.opsForValue().get(codeKey);
        if (code != null){
            return;
        }
        // 若缓存中没有，创建一个验证码保存至redis
        code = CodeGenerator.getSixBitRandom();
        stringRedisTemplate.opsForValue().set(codeKey, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);
        // 将验证码发送至用户手机
        log.info("向{}发送短信验证码{}", phone, code);
    }

    @Override
    public boolean updateInfo(UserDto userDto) {
        // 判空
        if (userDto == null){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }
        String phone = userDto.getPhone();
        if (StringUtils.isEmpty(phone)){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "手机号不存在");
        }
        // 判断账号是否唯一
        String account = userDto.getAccount();
        if (accountIsExist(account)){
            throw new BusinessException(ResultCode.FAIL,"账号已经存在");
        }
        // 将dto转换成user对象
        User user = BeanUtil.copyProperties(userDto, User.class);
        // 保存更新
        return update(user, new QueryWrapper<User>().eq("phone", userDto.getPhone()));
    }

    @Override
    public boolean setPassword(LoginUser loginUser) {
        // 判空
        if (loginUser == null){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }
        String account = loginUser.getAccount();
        String password = loginUser.getPassword();
        if (StringUtils.isAnyEmpty(account, password)){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "账号密码不能为空");
        }
        // 判断当前用户 判断用户是否存在
        User curUser = UserHolder.get();
        if (curUser == null || !account.equals(curUser.getAccount())){
            throw new BusinessException(ResultCode.NO_AUTH);
        }
        // TODO 判断密码长度
        // 密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        // 保存密码
        return update().set("password", encryptPassword).eq("account",account).update();
    }

    @Override
    public String loginByAccount(LoginUser loginUser) {
        // 1 判空
        if (loginUser == null){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }
        // 2. 判空
        String account = loginUser.getAccount();
        String password = loginUser.getPassword();
        if (StringUtils.isAnyEmpty(account, password)){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }
        // TODO 应该先判断是否已经登录

        // 3. 查询账户
        User user = getOne(new QueryWrapper<User>().eq("account", account));
        if (user == null){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "该账号不存在");
        }
        // 4. 密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        // 5. 判断密码账号是否匹配
        // 5.1 判断是否已经设置密码
        if (StringUtils.isEmpty(user.getPassword())){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "账户未设置密码,请使用手机号登陆后设置");
        }
        // 判断是否匹配
        if (!encryptPassword.equals(user.getPassword())){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "密码输入错误");
        }
        // 6. 生成token,保存在redis,并设置有效期
        return loginCommonWork(user, user.getAccount());
    }

    @Override
    public boolean register(LoginUser loginUser) {
        // 判空
        if (null == loginUser){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "账号密码不能为空");
        }
        String account = loginUser.getAccount();
        String password = loginUser.getPassword();
        if (StringUtils.isAnyEmpty(account, password)){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "有必要参数为空,请补全信息");
        }
        // 长度限制判断
        if (account.length() > 16 || account.length() < 4){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "账号应为4-16个字符");
        }
        if (password.length() > 16 || password.length() < 8){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "密码应为8-16个字符");
        }
        // 账号应该唯一
        if (accountIsExist(account)){
            // 存在就不能添加
            throw new BusinessException(ResultCode.PARAMS_ERROR, "该账号已存在");
        }
        // 密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        // 新建用户对象保存在数据库
        User user = new User();
        user.setAccount(account);
        user.setPassword(encryptPassword);
        return save(user);
    }

    private String loginCommonWork(User user, String keyword){
        // 6生成token令牌，将用户信息和token一并保存在redis, 并设置有效期。
        String token = JwtHelper.createToken(user.getId(), keyword);
        // 6.1将User转换成Map
        UserDto userDto = BeanUtil.copyProperties(user, UserDto.class);
        /**
         * 注意空指针异常
         * 如果属性中有空属性，则会抛NPE,在设置ignoreNUll 依然会抛
         * 是因为setFieldValueEditor（）优先级更高
         * 解决方案：在加入之前进行判空
         */
        Map<String, Object> userMap = BeanUtil.beanToMap(userDto, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> {
                            if (fieldValue == null){
                                fieldValue = "0";
                            }else{
                                fieldValue = fieldValue.toString();
                            }
                            return fieldValue;
                        }));
        // 6.2以hash的类型保存在redis
        String tokenKey = LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        // 6.3设置有效期
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);
        // 返回token
        return token;
    }

    /**
     * 判断用户账号是否存在
     * @return
     */
    private boolean accountIsExist(String account){
        // 判空
        if (StringUtils.isEmpty(account)){
            return false;
        }
        // 查
        User user = getOne(new QueryWrapper<User>().eq("account", account));
        return user != null;
    }

    /**
     * 创建新用户
     * @param phone
     * @return
     */
    private User createUserByPhone(String phone) {
        // 1.创建用户
        User user = new User();
        user.setPhone(phone);
        user.setNackname(USER_NICKNAME_PREFIX + RandomUtil.randomString(4));
        user.setAccount(USER_ACCOUNT_PREFIX + RandomUtil.randomString(8));
        user.setStatus(0);
        user.setRoleName(UserRole.COMMON_USER);
        // 2.保存用户
        save(user);
        return user;
    }
}




