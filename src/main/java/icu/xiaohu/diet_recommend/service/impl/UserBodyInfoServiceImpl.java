package icu.xiaohu.diet_recommend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.xiaohu.diet_recommend.model.entity.UserBodyInfo;
import icu.xiaohu.diet_recommend.service.UserBodyInfoService;
import icu.xiaohu.diet_recommend.mapper.UserBodyInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author xiaohu
* @description 针对表【t_user_body_info(用户身体信息表)】的数据库操作Service实现
* @createDate 2023-04-18 17:42:11
*/
@Service
public class UserBodyInfoServiceImpl extends ServiceImpl<UserBodyInfoMapper, UserBodyInfo>
    implements UserBodyInfoService{

}




