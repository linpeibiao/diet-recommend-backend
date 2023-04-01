package icu.xiaohu.diet_recommend.service;

import icu.xiaohu.diet_recommend.model.entity.UserMeal;
import com.baomidou.mybatisplus.extension.service.IService;
import icu.xiaohu.diet_recommend.recommend.dto.RelateDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaohu
 * @since 2023-03-29
 */
public interface IUserMealService extends IService<UserMeal> {
    List<RelateDTO> getUserMealRelate();

}
