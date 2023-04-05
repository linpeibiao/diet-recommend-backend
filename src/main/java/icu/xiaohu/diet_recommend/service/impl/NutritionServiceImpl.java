package icu.xiaohu.diet_recommend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import icu.xiaohu.diet_recommend.model.entity.Nutrition;
import icu.xiaohu.diet_recommend.mapper.NutritionMapper;
import icu.xiaohu.diet_recommend.service.INutritionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 营养成分表 服务实现类
 * </p>
 *
 * @author xiaohu
 * @since 2023-04-03
 */
@Service
public class NutritionServiceImpl extends ServiceImpl<NutritionMapper, Nutrition> implements INutritionService {

    @Override
    public List<Nutrition> listByNutrition(String nutrition) {
        if (StringUtils.isBlank(nutrition)){
            return this.list();
        }
        // 去空格
        nutrition = nutrition.trim();
        String[] nutritions = nutrition.split(",");
        List<Long> nutritionIds = Arrays.stream(nutritions)
                .map(Long :: valueOf)
                .collect(Collectors.toList());
        return this.list(new QueryWrapper<Nutrition>().in("id", nutritionIds));
    }
}
