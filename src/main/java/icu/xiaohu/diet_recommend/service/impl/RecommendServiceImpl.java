package icu.xiaohu.diet_recommend.service.impl;

import icu.xiaohu.diet_recommend.model.entity.UserMeal;
import icu.xiaohu.diet_recommend.recommend.dto.RelateDTO;
import icu.xiaohu.diet_recommend.service.IUserMealService;
import icu.xiaohu.diet_recommend.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaohu
 * @date 2023/04/01/ 10:04
 * @description
 */

@Service
public class RecommendServiceImpl implements RecommendService {
    @Autowired
    private IUserMealService userMealService;
    @Override
    public List<RelateDTO> getUserMealData() {
        List<RelateDTO> userMeals = userMealService.getUserMealRelate();
        return userMeals;
    }
}
