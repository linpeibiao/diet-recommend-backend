package icu.xiaohu.diet_recommend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.xiaohu.diet_recommend.constant.MessageStatus;
import icu.xiaohu.diet_recommend.constant.MessageType;
import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.entity.Message;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.service.MessageService;
import icu.xiaohu.diet_recommend.mapper.MessageMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author xiaohu
* @description 针对表【t_message】的数据库操作Service实现
* @createDate 2023-05-23 23:30:43
*/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
    implements MessageService{

    @Override
    public List<Message> getNotCheckMessage() {
        QueryWrapper<Message> query = new QueryWrapper<>();
        query.eq("type", MessageType.ADD_CHECK.getType());
        query.eq("status", MessageStatus.NOT_READ.getStatus());
        query.orderBy(true, false,"update_time");
        return list(query);
    }

    @Override
    public List<Message> getNotReadMessage(Long userId) {
        QueryWrapper<Message> query = new QueryWrapper<>();
        query.eq("consumer", userId);
        query.eq("status", MessageStatus.NOT_READ.getStatus());
        query.orderBy(true, false,"update_time");
        return list(query);
    }
}




