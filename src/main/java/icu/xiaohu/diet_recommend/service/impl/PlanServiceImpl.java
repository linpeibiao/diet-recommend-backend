package icu.xiaohu.diet_recommend.service.impl;

import icu.xiaohu.diet_recommend.model.entity.Plan;
import icu.xiaohu.diet_recommend.mapper.PlanMapper;
import icu.xiaohu.diet_recommend.service.IPlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户计划表 服务实现类
 * </p>
 *
 * @author xiaohu
 * @since 2023-04-03
 */
@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper, Plan> implements IPlanService {

}
