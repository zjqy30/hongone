package com.hone.applet.service;

import com.hone.dao.HoFrontMessageDao;
import com.hone.entity.HoFrontMessage;
import com.hone.system.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/7/24.
 */

@Service
@Transactional
public class HoFrontMessageService {
    
    @Autowired
    private HoFrontMessageDao hoFrontMessageDao;


    /**
     * 小程序端消息列表
     * @param params
     * @return
     */
    public JsonResult list(Map<String, String> params) {
        JsonResult jsonResult=new JsonResult();

        Example example=new Example(HoFrontMessage.class);
        example.setOrderByClause("create_date desc");
        List<HoFrontMessage> messageList=hoFrontMessageDao.listForApi();

        //计算时间差
        if(!CollectionUtils.isEmpty(messageList)){
            Date now=new Date();
            for(HoFrontMessage message:messageList){
                //相差毫秒数
                long millsDiff=now.getTime()-message.getCreateDate().getTime();
                //相差小时数
                long hoursDiff=(millsDiff/1000/3600);
                if(hoursDiff==0){
                    //相差小时数=0 计算相差分钟数
                    hoursDiff=(millsDiff/1000/60);
                    message.setHoursDiff(hoursDiff+"分钟前");
                    //相差分钟数=0 刚刚
                    if(hoursDiff==0){
                        message.setHoursDiff("刚刚");
                    }
                }else {
                    message.setHoursDiff(hoursDiff+"小时前");
                }
            }
        }

        jsonResult.getData().put("messageList",messageList);
        jsonResult.globalSuccess();
        return jsonResult;
    }
}
