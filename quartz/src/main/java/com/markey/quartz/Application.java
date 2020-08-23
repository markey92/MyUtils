package com.markey.quartz;

import com.markey.quartz.job.HelloJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.CronCalendar;
import org.quartz.impl.calendar.DailyCalendar;
import org.quartz.impl.triggers.SimpleTriggerImpl;

import java.util.Date;

/**
 * @ProjectName: MyUtils
 * @Package: com.markey.quartz.job
 * @ClassName: Application
 * @Author: markey
 * @Description:
 * @Date: 2020/8/23 20:56
 * @Version: 1.0
 */
public class Application {
    public static void main1(String[] args) throws SchedulerException {
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

        // 定义每日日历
        DailyCalendar calendar = new DailyCalendar("21:48:00", "21:48:30");
        calendar.setInvertTimeRange(true);
        scheduler.addCalendar("myCalendar", calendar, false, false);

        // 4、创建Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withDescription("this is a cron tigger")
                .withIdentity("cornTrigger")
                // 2s执行一次
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))
                // 执行日历
                .modifiedByCalendar("myCalendar")
                .build();

        // 5、注册任务和定时器

        scheduler.scheduleJob(jobDetail, trigger);

        // 6、启动定时器
        scheduler.start();
        System.out.println("start job, now:" + new Date());
    }
}
