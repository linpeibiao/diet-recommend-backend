package icu.xiaohu.diet_recommend.recommend;

import icu.xiaohu.diet_recommend.model.entity.Meal;
import icu.xiaohu.diet_recommend.recommend.core.ItemCF;
import icu.xiaohu.diet_recommend.recommend.core.UserCF;
import icu.xiaohu.diet_recommend.recommend.dto.ItemDTO;
import icu.xiaohu.diet_recommend.recommend.dto.RelateDTO;
import icu.xiaohu.diet_recommend.service.IMealService;
import icu.xiaohu.diet_recommend.service.IUserMealService;
import icu.xiaohu.diet_recommend.service.RecommendService;
import icu.xiaohu.diet_recommend.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaohu
 * @date 2023/03/31/ 23:21
 * @description
 */
@Service
public class Recommend {
    @Autowired
    private RecommendService recommendService;
    @Autowired
    private IMealService mealService;


    /**
     * 方法描述: 猜你喜欢
     *
     * @param userId 用户id
     * @Return {@link List <ItemDTO>}
     * @author tarzan
     * @date 2020年07月31日 17:28:06
     */
    public List<Meal>  userCfRecommend(long userId){
        // TODO 必须使用缓存
        List<RelateDTO> data = recommendService.getUserMealData();
        List<Long> recommendations = UserCF.recommend(userId, data);
        // 返回推荐数据
        return mealService.list().stream().filter(meal -> recommendations.contains(meal.getId())).collect(Collectors.toList());
    }


    /**
     * 方法描述: 猜你喜欢
     * @Return {@link List<ItemDTO>}
     * @author tarzan
     * @date 2020年07月31日 17:28:06
     */
    public List<Meal> itemCfRecommend(long mealId){
        List<RelateDTO> data = recommendService.getUserMealData();
        List<Long> recommendations = ItemCF.recommend(mealId, data);
        // 需缓存餐品数据
        return mealService.list().stream().filter(meal -> recommendations.contains(meal.getId())).collect(Collectors.toList());
    }
}
