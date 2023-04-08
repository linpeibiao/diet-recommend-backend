package icu.xiaohu.diet_recommend.recommend.core;

import icu.xiaohu.diet_recommend.recommend.dto.RelateDTO;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UserCF {

    /**
     * 方法描述: 推荐饮食id列表
     *
     * @param userId 当前用户
     * @param list 用户饮食评分数据
     * @return {@link List<Integer>}
     * @date 2023年02月02日 14:51:42
     */
    public static List<Long> recommend(Long userId, List<RelateDTO> list) {

        //按用户分组
        // TODO 使用缓存
        Map<Long, List<RelateDTO>> userMap = list.stream().collect(Collectors.groupingBy(RelateDTO::getUserId));
        //获取其他用户与当前用户的关系值
        Map<Long,Double>  userDisMap = CoreMath.computeNeighbor(userId, userMap,0);
        //获取关系最近的用户
        double maxValue=Collections.max(userDisMap.values());
        Set<Long> userIds = userDisMap.entrySet().stream().filter(e->e.getValue() == maxValue).map(Map.Entry::getKey).collect(Collectors.toSet());
        //取关系最近的用户
        Long nearestUserId = userIds.stream().findAny().orElse(null);
        if(nearestUserId==null){
            return Collections.emptyList();
        }
        //最近邻用户看过饮食列表
        List<Long> neighborItems = userMap.get(nearestUserId).stream().map(RelateDTO::getMealId).collect(Collectors.toList());

        if (userMap.get(userId) != null && !userMap.get(userId).isEmpty()){
            //指定用户看过饮食列表
            List<Long> userItems = userMap.get(userId).stream().map(RelateDTO::getMealId).collect(Collectors.toList());
            //找到最近邻看过，但是该用户没看过的饮食
            neighborItems.removeAll(userItems);
        }

        return neighborItems;
    }

}
