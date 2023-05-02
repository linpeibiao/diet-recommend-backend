package icu.xiaohu.diet_recommend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.xiaohu.diet_recommend.model.entity.Log;
import icu.xiaohu.diet_recommend.model.entity.Material;
import icu.xiaohu.diet_recommend.service.LogService;
import icu.xiaohu.diet_recommend.mapper.LogMapper;
import jodd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author xiaohu
* @description 针对表【t_log(接口访问日志表)】的数据库操作Service实现
* @createDate 2023-04-23 22:47:15
*/
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log>
    implements LogService{

    @Override
    public IPage<Log> listQuery(Log log, Date startTime, Date endTime, int pageNum, int pageSize) {

        QueryWrapper<Log> query = new QueryWrapper<>();
        // 用户
        Long userId = log.getUserId();
        // 方法名
        String methodName = log.getMethodName();
        // 类名
        String className = log.getClassName();
        // 包名
        String packageName = log.getPackageName();
        // 请求状态
        String status = log.getStatus();
        if (!StringUtils.isBlank(status)){
            query.eq("status", status);
        }
        // 访问时间区间
        // 都不为空
        if (startTime != null && endTime != null){
            query.between("request_time", startTime, endTime);
        }
        // 其中一个为空
        if (startTime != null || endTime != null){
            startTime = startTime == null ? new Date() : startTime;
            endTime = endTime == null ? new Date() : endTime;
            query.between("request_time", startTime, endTime);
        }
        if (userId != null && userId > 0){
            query.eq("user_id", userId);
        }
        if (!StringUtil.isBlank(methodName)){
            query.like("method_name", "%" + methodName + "%");
        }
        if (!StringUtil.isBlank(className)){
            query.like("class_name", "%" + className + "%");
        }
        if (!StringUtil.isBlank(packageName)){
            query.like("package_name", "%" + packageName + "%");
        }

        query.orderByDesc("request_time");

        return this.page(new Page<Log>(pageNum, pageSize), query);
    }
}




