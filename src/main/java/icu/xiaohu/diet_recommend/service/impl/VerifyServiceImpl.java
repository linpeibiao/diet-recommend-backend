package icu.xiaohu.diet_recommend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.entity.Meal;
import icu.xiaohu.diet_recommend.model.entity.Message;
import icu.xiaohu.diet_recommend.model.entity.User;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.server.WebSocketServer;
import icu.xiaohu.diet_recommend.service.IMealService;
import icu.xiaohu.diet_recommend.service.VerifyService;
import icu.xiaohu.diet_recommend.util.UserHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ClientInfoStatus;
import java.util.List;

/**
 * @author xiaohu
 * @date 2023/06/06/ 9:31
 * @description
 */
@Service
public class VerifyServiceImpl implements VerifyService {
    @Resource
    private IMealService mealService;
    @Override
    public Meal mealVerify(Long mealId, Integer status) {
        // 参数判断
        // TODO 新增状态枚举类
        boolean isValid = (mealId == null || status == null || (status != 0 && status != 1));
        if (isValid){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "参数不合法");
        }
        // 餐品信息判断
        Meal meal = mealService.getById(mealId);
        if (meal == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "未收录该餐品信息");
        }
        // 修改状态
        meal.setStatus(status);
        mealService.updateById(meal);
        String contend = status == 0 ? "通过审核" : "不过审";
        // 审核结果推送
        User user = UserHolder.get();
        Message msg = new Message();
        msg.setBrief("餐品信息审核结果")
                .setContent(meal.getName() + contend)
                .setProducer(user.getId())
                .setConsumer(meal.getCreateUserId())
                .setType("系统通知")
                // 未读
                .setStatus(0);
        WebSocketServer.sendInfo(msg, msg.getConsumer());
        return meal;
    }

    @Override
    public List<Meal> getToVerifyMeal() {
        QueryWrapper<Meal> query = new QueryWrapper<>();
        query.eq("status", 1);
        return mealService.list(query);
    }
}
