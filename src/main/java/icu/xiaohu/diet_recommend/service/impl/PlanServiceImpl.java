package icu.xiaohu.diet_recommend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.dto.MaterialDto;
import icu.xiaohu.diet_recommend.model.entity.Material;
import icu.xiaohu.diet_recommend.model.entity.Plan;
import icu.xiaohu.diet_recommend.mapper.PlanMapper;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.service.IPlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.xiaohu.diet_recommend.util.UserHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public boolean add(Plan plan) {
        // 错误提示消息
        StringBuilder errMsg = new StringBuilder();
        // 参数是否合法标识
        boolean isValidate = false;

        // 参数判断
        // 名称判空
        String name = plan.getName();
        // 类型判空
        String type = plan.getPlanType();
        // 目标判空
        String goal = plan.getPlanGoal();
        // 计划进度默认为未开始
        String planProgress = plan.getPlanProgress();
        // 计划截止日期判空
        Date ddl = plan.getDdl();
        if (StringUtils.isAnyBlank(name, type, goal)){
            errMsg.append("计划的名称、类型、目标不能为空");
        }
        if (StringUtils.isBlank(planProgress)){
            plan.setPlanProgress("未开始");
        }
        if (ddl == null){
            errMsg.append("ddl 不能为空");
        }

        if (isValidate){
            throw new BusinessException(ResultCode.PARAMS_ERROR, errMsg.toString());
        }
        // 设置计划创建用户
        plan.setUserId(UserHolder.get().getId());

        return this.save(plan);
    }
}
