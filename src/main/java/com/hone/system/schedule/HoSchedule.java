package com.hone.system.schedule;

import com.hone.dao.HoWxFormidDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务
 */

@Component
public class HoSchedule {

    private static Logger logger= LoggerFactory.getLogger(HoSchedule.class);

    @Autowired
    private HoWxFormidDao hoWxFormidDao;

//    @Scheduled(cron = "0 */1 * * * *")
//    public void test2(){
//        System.out.println("===每分钟执行==="+new Date().toLocaleString());
//    }


    @Scheduled(cron = "0 0 1 * * *")
    public void test2() {
        logger.info("====每日凌晨一点定时器开始执行====");

        try {
            //删除过期的 formId
            hoWxFormidDao.deleteExpireData();
        }catch (Exception e){
            logger.error("每日凌晨一点定时器发生异常",e);
        }

        logger.info("====每日凌晨一点定时器结束执行====");
    }


}
