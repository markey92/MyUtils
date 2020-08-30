package com.markey.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

/**
 * @ProjectName: MyUtils
 * @Package: com.markey.quartz
 * @ClassName: SpringBootApplication
 * @Author: markey
 * @Description:
 * @Date: 2020/8/23 23:26
 * @Version: 1.0
 */
@SpringBootApplication
public class QuartzSpringBootApplication {
    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(QuartzSpringBootApplication.class, args);
    }
}
