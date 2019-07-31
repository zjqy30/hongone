package com.hone.system.schedule;

import com.hone.dao.HoWxFormidDao;
import com.hone.system.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Calendar;
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
            deleteQrcode();
        }catch (Exception e){
            logger.error("每日凌晨一点定时器发生异常",e);
        }

        logger.info("====每日凌晨一点定时器结束执行====");
    }


    /**
     * 删除 本地缓存的二维码 图片
     */
    public void  deleteQrcode(){
        try {
            //获取项目根路径
            String relativelyPath = System.getProperty("user.dir");
            logger.info("根路径:"+relativelyPath);
            Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.DATE,-1);
            String mkdirs=relativelyPath+"/wxqrcode/"+ DateUtils.YYYYMMDD(calendar.getTime())+"/";
            logger.info("文件路径:"+mkdirs);
            //文件夹
            File files=new File(mkdirs);
            //判断文件夹是否存在
            if(files.isDirectory()){
                //遍历删除每个文件
                for (File eachFile : files.listFiles()) {
                    System.out.println(eachFile.getName());
                    eachFile.delete();
                }
                //删除文件夹
                files.delete();
            }
        }catch (Exception e){
            logger.error("删除本地缓存二维码图片异常",e);
        }
    }

}
