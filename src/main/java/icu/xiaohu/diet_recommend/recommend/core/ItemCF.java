package icu.xiaohu.diet_recommend.recommend.core;

import icu.xiaohu.diet_recommend.recommend.dto.RelateDTO;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xiaohu
 */
public class ItemCF {

    /**
     * 方法描述: 推荐饮食id列表
     *
     * @param itemId 当前饮食项id
     * @param list 用户饮食评分数据
     * @return {@link List<Long>}
     * @date 2023年02月02日 14:51:42
     */
    public static List<Long> recommend(Long itemId, List<RelateDTO> list) {
        //按物品分组
        Map<Long, List<RelateDTO>>  itemMap = list.stream().collect(Collectors.groupingBy(RelateDTO::getItemId));
        //获取其他物品与当前物品的关系值
        Map<Long,Double>  itemDisMap = CoreMath.computeNeighbor(itemId, itemMap,1);
        //获取关系最近物品
        double maxValue=Collections.max(itemDisMap.values());
        return itemDisMap.entrySet().stream().filter(e->e.getValue()==maxValue).map(Map.Entry::getKey).collect(Collectors.toList());
    }


}
