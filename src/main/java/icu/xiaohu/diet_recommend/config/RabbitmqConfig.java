package icu.xiaohu.diet_recommend.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author xiaohu
 * @date 2023/06/01/ 23:14
 * @description 队列配置信息
 */
@Configuration
public class RabbitmqConfig {
    /**
     * 路由
     */
    private static String routingKey = "rabbit.msg";

    // 定义队列
    // 定义交换机
    // 定义队列和交换机的绑定实例
    // 配置超时重传、消息确认、消息持久化等操作


}
