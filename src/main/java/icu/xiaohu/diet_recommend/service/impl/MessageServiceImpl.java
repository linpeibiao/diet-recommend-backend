package icu.xiaohu.diet_recommend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.xiaohu.diet_recommend.model.entity.Message;
import icu.xiaohu.diet_recommend.service.MessageService;
import icu.xiaohu.diet_recommend.mapper.MessageMapper;
import org.springframework.stereotype.Service;

/**
* @author xiaohu
* @description 针对表【t_message】的数据库操作Service实现
* @createDate 2023-05-23 23:30:43
*/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
    implements MessageService{

}




