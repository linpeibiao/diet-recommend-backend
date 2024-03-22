package icu.xiaohu.diet_recommend.service;

import icu.xiaohu.diet_recommend.model.entity.FoodIntakeRecords;
import com.baomidou.mybatisplus.extension.service.IService;
import icu.xiaohu.diet_recommend.service.impl.FoodIntakeRecordsServiceImpl;

import java.util.List;

/**
* @author xiaohu
* @description 针对表【t_food_intake_records(用户饮食记录表)】的数据库操作Service
* @createDate 2024-03-20 18:07:08
*/
public interface FoodIntakeRecordsService extends IService<FoodIntakeRecords> {

    /**
     * 用户新增饮食记录
     * @param foodIntakeRecords
     * @return
     */
    boolean add(FoodIntakeRecords foodIntakeRecords);

    /**
     * 用户编辑饮食记录
     * @param foodIntakeRecords
     * @return
     */
    int update(FoodIntakeRecords foodIntakeRecords);

    /**
     * 获取饮食记录
     * @return
     */
    List<FoodIntakeRecords> getDietRecords();

    /**
     * 通过id删除记录
     * @param recordId
     * @return
     */
    boolean deleteRecord(Long recordId);

    /**
     * 批量删除记录
     * @param recordIds
     * @return
     */
    int deleteRecords(List<Long> recordIds);
}
