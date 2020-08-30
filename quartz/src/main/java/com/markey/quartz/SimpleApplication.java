package com.markey.quartz;

import com.markey.quartz.simple.HelloJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @ProjectName: MyUtils
 * @Package: com.markey.quartz.job
 * @ClassName: Application
 * @Author: markey
 * @Description: 简单的定时任务示例
 * @Date: 2020/8/23 20:56
 * @Version: 1.0
 */
public class SimpleApplication {
    public static void main(String[] args) throws SchedulerException {
        // 1、调度器工厂
        SchedulerFactory factory = new StdSchedulerFactory();

        // 2、获取调度器
        Scheduler scheduler = factory.getScheduler();

        // 3、通过Job定义来创建JobDetail
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withDescription("this is a hello job")
                .withIdentity("helloJob")
                .build();

        // 4、创建Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withDescription("this is a cron tigger")
                .withIdentity("cornTrigger")
                // 2s执行一次
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))
                .build();

        // 5、注册任务和定时器
        scheduler.scheduleJob(jobDetail, trigger);

        // 6、启动定时器
        scheduler.start();
        System.out.println("start job, now:" + new Date());
    }
}
