package com.xxxx.crm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @EnableScheduling: 定时任务
 * @author arthur
 * @date 2021/1/22 20:36
 */
@SpringBootApplication
@MapperScan("com.xxxx.crm.dao")
//@EnableScheduling
public class Starter {
    public static void main(String[] args) {
        SpringApplication.run(Starter.class);
    }
}