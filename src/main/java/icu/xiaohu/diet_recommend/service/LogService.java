package icu.xiaohu.diet_recommend.service;

import icu.xiaohu.diet_recommend.model.entity.Log;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
* @author xiaohu
* @description 针对表【t_log(接口访问日志表)】的数据库操作Service
* @createDate 2023-04-23 22:47:15
*/
public interface LogService extends IService<Log> {

    /**
     * 系统日志查询
     * @param log
     * @param startTime
     * @param endTime
     * @return
     */
    List<Log> listQuery(Log log, Date startTime, Date endTime);
}
