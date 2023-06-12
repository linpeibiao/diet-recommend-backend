package icu.xiaohu.diet_recommend.service;

import icu.xiaohu.diet_recommend.model.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author xiaohu
* @description 针对表【t_message】的数据库操作Service
* @createDate 2023-05-23 23:30:43
*/
public interface MessageService extends IService<Message> {

    /**
     * 获取未审核消息
     * @return
     */
    List<Message> getNotCheckMessage();

}
