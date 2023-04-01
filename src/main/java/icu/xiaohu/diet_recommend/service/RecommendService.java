package icu.xiaohu.diet_recommend.service;

import icu.xiaohu.diet_recommend.recommend.dto.RelateDTO;

import java.util.List;

/**
 * @author xiaohu
 * @date 2023/04/01/ 10:04
 * @description
 */
public interface RecommendService {
    /**
     * 获取用户评分数据
     * @return
     */
    List<RelateDTO> getUserMealData();
}
