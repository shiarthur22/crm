package com.xxxx.crm.task;

import com.xxxx.crm.service.CustomerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author arthur
 * @date 2021/2/6 10:24
 */
@Service
public class TimedTask {
    @Resource
    private CustomerService customerService;

    @Scheduled(cron = "0/2 * * * * ?")
    public void timingMethod(){
        System.out.println("定时任务开始执行--->"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        customerService.updateCustomerState();
    }
}
