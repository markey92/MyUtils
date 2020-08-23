Quartz定时器简易使用

 https://blog.csdn.net/dxx707099957/article/details/93848653 

# 基本概念

- Scheduler：调度容器
- Job：Job接口类，即被调度的任务
- JobDetail ：Job的描述类，job执行时的依据此对象的信息反射实例化出Job的具体执行对象。
- Trigger：触发器，存放Job执行的时间策略。用于定义任务调度时间规则。
- JobStore： 存储作业和调度期间的状态
- Calendar：指定排除的时间点（如排除法定节假日）



## Job

 Job 是一个接口，只有一个方法 void execute(JobExecutionContext context)，开发者实现接口来定义任务。JobExecutionContext 类提供了调度上下文的各种信息。 



## JobDetail

任务详情类，Job接口只是定义任务的内容（execute方法），具体执行任务还需要其他必要参数。

**JobDetail根据Job实现类创建。**



下面是 job 内部的主要属性：

| 属性名        | 说明                                                         |
| ------------- | ------------------------------------------------------------ |
| class         | 必须是job实现类（比如JobImpl），用来绑定一个具体job          |
| name          | job 名称。如果未指定，会自动分配一个唯一名称。所有job都必须拥有一个唯一name，如果两个 job 的name重复，则只有最前面的 job 能被调度 |
| group         | job 所属的组名                                               |
| description   | job描述                                                      |
| durability    | 是否持久化。如果job设置为非持久，当没有活跃的trigger与之关联的时候，job 会自动从scheduler中删除。也就是说，非持久job的生命期是由trigger的存在与否决定的 |
| shouldRecover | 是否可恢复。如果 job 设置为可恢复，一旦 job 执行时scheduler发生hard shutdown（比如进程崩溃或关机），当scheduler重启后，该job会被重新执行 |
| jobDataMap    | 除了上面常规属性外，用户可以把任意kv数据存入jobDataMap，实现 job 属性的无限制扩展，执行 job 时可以使用这些属性数据。此属性的类型是JobDataMap，实现了Serializable接口，可做跨平台的序列化传输 |



## Trigger 

 Trigger 是一个接口，描述触发Job执行的时间触发规则。主要有 SimpleTrigger 和 CronTrigger 这两个子接口，分别有对应的默认实现类。当仅需触发一次或者以固定时间间隔周期执行，SimpleTrigger是最适合的选择；而CronTrigger则可以通过Cron表达式定义出各种复杂时间规则的调度方案：如每早晨9:00执行，周一、周三、周五下午5:00执行等； 



以下是 trigger 的属性：

| 属性名             | 属性类型          | 说明                                                         |
| ------------------ | ----------------- | ------------------------------------------------------------ |
| name               | 所有trigger通用   | trigger名称                                                  |
| group              | 所有trigger通用   | trigger所属的组名                                            |
| description        | 所有trigger通用   | trigger描述                                                  |
| calendarName       | 所有trigger通用   | 日历名称，指定使用哪个Calendar类，经常用来从trigger的调度计划中排除某些时间段 |
| misfireInstruction | 所有trigger通用   | 错过job（未在指定时间执行的job）的处理策略，默认为MISFIRE_INSTRUCTION_SMART_POLICY。详见这篇[blog](https://link.jianshu.com/?t=http%3A%2F%2Fblog.csdn.net%2Fspbdev%2Farticle%2Fdetails%2F41679477)[^Quartz misfire](https://link.jianshu.com/?t=%5BSpbDev%5D(http%3A%2F%2Fmy.csdn.net%2FSpbDev)%EF%BC%9A%5BQuartz%E7%9A%84misfire%5D(http%3A%2F%2Fblog.csdn.net%2Fspbdev%2Farticle%2Fdetails%2F41679477)) |
| priority           | 所有trigger通用   | 优先级，默认为5。当多个trigger同时触发job时，线程池可能不够用，此时根据优先级来决定谁先触发 |
| jobDataMap         | 所有trigger通用   | 同job的jobDataMap。假如job和trigger的jobDataMap有同名key，通过getMergedJobDataMap()获取的jobDataMap，将以trigger的为准 |
| startTime          | 所有trigger通用   | 触发开始时间，默认为当前时间。决定什么时间开始触发job        |
| endTime            | 所有trigger通用   | 触发结束时间。决定什么时间停止触发job                        |
| nextFireTime       | SimpleTrigger私有 | 下一次触发job的时间                                          |
| previousFireTime   | SimpleTrigger私有 | 上一次触发job的时间                                          |
| repeatCount        | SimpleTrigger私有 | 需触发的总次数                                               |
| timesTriggered     | SimpleTrigger私有 | 已经触发过的次数                                             |
| repeatInterval     | SimpleTrigger私有 | 触发间隔时间                                                 |



## Scheduler 

调度器，代表一个Quartz的独立运行容器，好比一个『大管家』，这个大管家应该可以接受 Job， 然后按照各种Trigger去运行，Trigger和JobDetail可以注册到Scheduler中，两者在Scheduler中拥有各自的组及名称，组及名称是Scheduler查找定位容器中某一对象的依据，Trigger的组及名称必须唯一，JobDetail的组和名称也必须唯一（但可以和Trigger的组和名称相同，因为它们是不同类型的）。Scheduler定义了多个接口方法，允许外部通过组及名称访问和控制容器中Trigger和JobDetail。 

**Scheduler 可以将 Trigger 绑定到某一 JobDetail 中，这样当 Trigger 触发时，对应的 Job 就被执行。**

可以通过 SchedulerFactory创建一个 Scheduler 实例。Scheduler 拥有一个 SchedulerContext，它类似于 ServletContext，保存着 Scheduler 上下文信息，Job 和 Trigger 都可以访问 SchedulerContext 内的信息。SchedulerContext 内部通过一个 Map，以键值对的方式维护这些上下文数据，SchedulerContext 为保存和获取数据提供了多个 put() 和 getXxx() 的方法。可以通过Scheduler# getContext()获取对应的SchedulerContext实例； 



## Calendar 

 org.quartz.Calendar和 java.util.Calendar不同，它是一些日历特定时间点的集合（可以简单地将org.quartz.Calendar看作java.util.Calendar的集合——java.util.Calendar代表一个日历时间点，无特殊说明后面的Calendar即指org.quartz.Calendar）。一个Trigger可以和多个Calendar关联，以便排除或包含某些时间点。假设，我们安排每周星期一早上10:00执行任务，但是如果碰到法定的节日，任务则不执行，这时就需要在Trigger触发机制的基础上使用Calendar进行定点排除。 



# 简单使用

## Maven依赖

```
<!-- https://mvnrepository.com/artifact/org.quartz-scheduler/quartz -->
<dependency>
    <groupId>org.quartz-scheduler</groupId>
    <artifactId>quartz</artifactId>
    <version>2.3.2</version>
</dependency>
```

## 定义Job

```
public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("exec Hello Job, now:" +  new Date());
    }
}
```

## 启动Job

```
public class Application {
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

```

## 使用日历

```
public class Application {
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
```

