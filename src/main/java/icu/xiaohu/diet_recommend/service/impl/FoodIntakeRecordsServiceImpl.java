package icu.xiaohu.diet_recommend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.entity.FoodIntakeRecords;
import icu.xiaohu.diet_recommend.model.entity.User;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.service.FoodIntakeRecordsService;
import icu.xiaohu.diet_recommend.mapper.FoodIntakeRecordsMapper;
import icu.xiaohu.diet_recommend.util.UserHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author xiaohu
* @description 针对表【t_food_intake_records(用户饮食记录表)】的数据库操作Service实现
* @createDate 2024-03-20 18:07:08
*/
@Service
public class FoodIntakeRecordsServiceImpl extends ServiceImpl<FoodIntakeRecordsMapper, FoodIntakeRecords>
    implements FoodIntakeRecordsService{


    @Override
    public boolean add(FoodIntakeRecords foodIntakeRecords) {
        // 获取当前用户
        User user = UserHolder.get();
        if (foodIntakeRecords == null){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }
        if (StringUtils.isBlank(foodIntakeRecords.getMealIds())){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "请选择餐品");
        }
        foodIntakeRecords.setUserId(user.getId());
        foodIntakeRecords.setDate(new Date());
        return this.save(foodIntakeRecords);
    }

    @Override
    public int update(FoodIntakeRecords foodIntakeRecords) {
        // 获取当前用户
        User user = UserHolder.get();
        // 获取数据库数据
        if (foodIntakeRecords.getId() == null){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "请求参数错误");
        }
        FoodIntakeRecords foodIntakeRecord = this.getById(foodIntakeRecords.getId());
        if (foodIntakeRecord == null){
            throw new BusinessException(ResultCode.NOT_FOUND, "请求餐品数据异常，请检查网络状态");
        }
        if (!user.getId().equals(foodIntakeRecord.getUserId())){
            throw new BusinessException(ResultCode.NO_AUTH, "您无权限更新该记录");
        }
        return this.update(foodIntakeRecords);
    }

    @Override
    public List<FoodIntakeRecords> getDietRecords() {
        User user = UserHolder.get();
        LambdaQueryWrapper<FoodIntakeRecords> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(FoodIntakeRecords::getUserId, user.getId());
        queryWrapper.orderBy(true, false, FoodIntakeRecords::getDate);
        return this.list(queryWrapper);
    }

    @Override
    public boolean deleteRecord(Long recordId) {
        // 获取当前用户
        User user = UserHolder.get();
        // 获取数据库数据
        if (recordId == null || recordId <= 0){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "请求参数错误");
        }
        FoodIntakeRecords foodIntakeRecord = this.getById(recordId);
        if (foodIntakeRecord == null){
            throw new BusinessException(ResultCode.NOT_FOUND, "请求餐品数据异常，请检查网络状态");
        }
        if (!user.getId().equals(foodIntakeRecord.getUserId())){
            throw new BusinessException(ResultCode.NO_AUTH, "您无权限删除该记录");
        }
        return this.deleteRecord(recordId);
    }

    @Override
    public int deleteRecords(List<Long> recordIds) {
        // TODO
        return 0;
    }
}




