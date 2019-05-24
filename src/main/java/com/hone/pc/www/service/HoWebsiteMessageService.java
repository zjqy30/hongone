package com.hone.pc.www.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hone.dao.HoSmsRecordsDao;
import com.hone.dao.HoWebsiteMessageDao;
import com.hone.entity.HoSmsRecords;
import com.hone.entity.HoWebsiteMessage;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/5/20.
 */

@Service
public class HoWebsiteMessageService {

    @Autowired
    private HoWebsiteMessageDao hoWebsiteMessageDao;
    @Autowired
    private HoSmsRecordsDao smsRecordsDao;

    public Page list() {

       PageHelper.startPage(1,2,true);
       HoWebsiteMessage hoWebsiteMessage=new HoWebsiteMessage();
       hoWebsiteMessage.setEnableFlag("1");
       List<HoWebsiteMessage> websiteMessageList= hoWebsiteMessageDao.select(hoWebsiteMessage);
       PageInfo<HoWebsiteMessage> pageInfo=new PageInfo<>(websiteMessageList);
       Page<HoWebsiteMessage> page=new Page<>(pageInfo);

       return page;
    }

    public JsonResult save(Map<String, String> params) {

        JsonResult jsonResult=new JsonResult();

        String phoneNo=params.get("phoneNo");
        String code=params.get("code");

        //校验手机格式
        if(StringUtils.isEmpty(phoneNo)||phoneNo.length()!=11||!StringUtils.isNumeric(phoneNo)){
            jsonResult.globalError("手机号格式不正确");
            return jsonResult;
        }

        //验证验证码有效性
        int nums=smsRecordsDao.verifyCode(phoneNo,code);
        if(nums==0){
            jsonResult.globalError("验证码失效");
            return jsonResult;
        }

        //插入数据
        HoWebsiteMessage websiteMessage=new HoWebsiteMessage();
        websiteMessage.setPhoneNo(phoneNo);
        websiteMessage.preInsert();
        hoWebsiteMessageDao.insert(websiteMessage);

        jsonResult.globalSuccess();
        return jsonResult;
    }
}
