package icu.xiaohu.diet_recommend.recommend;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.entity.Meal;
import icu.xiaohu.diet_recommend.model.entity.User;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.recommend.core.ItemCF;
import icu.xiaohu.diet_recommend.recommend.core.UserCF;
import icu.xiaohu.diet_recommend.recommend.dto.ItemDTO;
import icu.xiaohu.diet_recommend.recommend.dto.RelateDTO;
import icu.xiaohu.diet_recommend.service.IMealService;
import icu.xiaohu.diet_recommend.service.IUserMealService;
import icu.xiaohu.diet_recommend.service.RecommendService;
import icu.xiaohu.diet_recommend.service.UserService;
import icu.xiaohu.diet_recommend.util.UserHolder;
import org.apache.commons.lang3.StringUtils;
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
    @Autowired
    private UserService userService;


    /**
     * 冷启动
     * @param userId
     * @return
     */
    public List<Meal> coolRecommend(long userId){
        // TODO 在缓存中随机获取热点餐品
        User user = userService.getById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "无该用户对用数据");
        }
        String taste = user.getTasteHobby();
        QueryWrapper<Meal> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id").last("RAND()").last("limit 10");

        if (!StringUtils.isBlank(taste)) {
            queryWrapper.like("taste", taste);
        }
        return mealService.list(queryWrapper);
    }

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
