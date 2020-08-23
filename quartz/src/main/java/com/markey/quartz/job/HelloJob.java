package com.markey.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Calendar;
import java.util.Date;

/**
 * @ProjectName: MyUtils
 * @Package: com.markey.quartz.job
 * @ClassName: HelloJob
 * @Author: markey
 * @Description:
 * @Date: 2020/8/23 20:51
 * @Version: 1.0
 */
public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("exec Hello Job, now:" +  new Date());
    }
}
