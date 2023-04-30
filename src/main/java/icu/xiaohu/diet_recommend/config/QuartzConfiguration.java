package icu.xiaohu.diet_recommend.config;

import icu.xiaohu.diet_recommend.job.MyCacheJob;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

/**
 * @author xiaohu
 * @date 2023/05/01/ 3:26
 * @description
 */
@Configuration
public class QuartzConfiguration {
    @Bean
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(MyCacheJob.class);
        factory.setName("MyCacheJob");
        factory.setDescription("缓存用户饮食历史评分数据，用于饮食数据推荐");
        factory.setDurability(true);
        return factory;
    }

    @Bean
    public CronTriggerFactoryBean trigger(JobDetailFactoryBean jobDetail) {
        CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
        factory.setJobDetail(jobDetail.getObject());
        factory.setName("myTrigger");
        factory.setDescription("Trigger to run myJob at 4am every day");
        factory.setCronExpression("0 42 3 * * ?"); // 每天凌晨4点执行
        return factory;
    }
}
