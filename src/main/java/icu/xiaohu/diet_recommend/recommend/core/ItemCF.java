package icu.xiaohu.diet_recommend.recommend.core;

import icu.xiaohu.diet_recommend.recommend.dto.RelateDTO;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        // 按物品分组
        Map<Long, List<RelateDTO>> itemMap = list.stream()
                .filter(Objects::nonNull) // 过滤掉null元素
                .filter(dto -> dto.getMealId() != null) // 过滤掉mealId为空的元素
                .filter(dto -> dto.getUserId() != null) // 过滤掉userId为空的元素
                .collect(Collectors.groupingBy(RelateDTO::getMealId));
        // 获取其他物品与当前物品的关系值
        Map<Long,Double> itemDisMap = CoreMath.computeNeighbor(itemId, itemMap,1);
        // 获取关系最近物品
        double maxValue = Collections.max(itemDisMap.values());
        if (maxValue == 0.0D){
            return itemDisMap.entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByKey())) // 按照key值降序排列
                    .limit(5) // 取出前5条数据
                    .map(Map.Entry::getKey) // 取出每个Entry的key值
                    .collect(Collectors.toList()); // 将key值收集为List
        }
        return itemDisMap.entrySet().stream().filter(e->e.getValue() == maxValue).map(Map.Entry::getKey).collect(Collectors.toList());
    }
}
