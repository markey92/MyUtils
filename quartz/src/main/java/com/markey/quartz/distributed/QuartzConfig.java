package com.markey.quartz.distributed;

import com.markey.quartz.simple.HelloJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

/**
 * @ProjectName: MyUtils
 * @Package: com.markey.quartz.distributed
 * @ClassName: QuartzConfig
 * @Author: markey
 * @Description:
 * @Date: 2020/8/30 17:44
 * @Version: 1.0
 */
@Configuration
public class QuartzConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean jobDetail = new JobDetailFactoryBean();
        jobDetail.setJobClass(HelloJob.class);
        return jobDetail;
    }

    @Bean
    public CronTriggerFactoryBean cronTrigger() {
        CronTriggerFactoryBean cronTrigger = new CronTriggerFactoryBean();
        cronTrigger.setCronExpression("0/2 * * * * ?");
        cronTrigger.setJobDetail(jobDetail().getObject());
        return cronTrigger;
    }

    @Bean
    public SchedulerFactoryBean jobFactory(ApplicationContext applicationContext) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setApplicationContext(applicationContext);
        schedulerFactoryBean.setTriggers(this.cronTrigger().getObject());
        return schedulerFactoryBean;
    }
}
