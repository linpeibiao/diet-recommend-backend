package icu.xiaohu.diet_recommend.service.impl;

import icu.xiaohu.diet_recommend.model.entity.UserMeal;
import icu.xiaohu.diet_recommend.mapper.UserMealMapper;
import icu.xiaohu.diet_recommend.recommend.dto.RelateDTO;
import icu.xiaohu.diet_recommend.service.IUserMealService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaohu
 * @since 2023-03-29
 */
@Service
public class UserMealServiceImpl extends ServiceImpl<UserMealMapper, UserMeal> implements IUserMealService {
    @Autowired
    private UserMealMapper userMealMapper;
    @Override
    public List<RelateDTO> getUserMealRelate() {
        List<RelateDTO> userMealRelate = userMealMapper.getUserMealRelate();
        return userMealRelate;
    }
}
