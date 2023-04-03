package icu.xiaohu.diet_recommend.service.impl;

import icu.xiaohu.diet_recommend.model.entity.Nutrition;
import icu.xiaohu.diet_recommend.mapper.NutritionMapper;
import icu.xiaohu.diet_recommend.service.INutritionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
