package com.hone.pc.web.service;

import com.hone.dao.HoSmsRecordsDao;
import com.hone.dao.HoUserBasicDao;
import com.hone.entity.HoUserBasic;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.JwtTokenUtils;
import com.hone.system.utils.ParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by Lijia on 2019/7/8.
 */

@Service
@Transactional
public class WebUserBasicService {

    @Autowired
    private HoUserBasicDao hoUserBasicDao;
    @Autowired
    private HoSmsRecordsDao hoSmsRecordsDao;

    public HoUserBasic findByOpenId(String openId) {
        return hoUserBasicDao.findUniqueByProperty("open_id",openId);
    }


    /**
     * 手机验证码登录
     * @param params
     * @return
     */
    public JsonResult loginByPhone(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String phoneNo=params.get("phoneNo");
        String code=params.get("code");

        ParamsUtil.checkParamIfNull(params,new String[]{"phoneNo","code"});

        int result=hoSmsRecordsDao.verifyCode(phoneNo,code,"3");
        if(result<=0){
            jsonResult.globalError("验证码失效,请重试");
            return jsonResult;
        }

        HoUserBasic hoUserBasic=hoUserBasicDao.findUniqueByProperty("phone_no",phoneNo);
        if(hoUserBasic==null){
            jsonResult.globalError("请前往小程序注册后再试");
            return jsonResult;
        }

        //创建 token
        String token=JwtTokenUtils.createToken(hoUserBasic.getId());

        jsonResult.getData().put("token", token);
        jsonResult.getData().put("userId", hoUserBasic.getId());
        jsonResult.getData().put("openid", hoUserBasic.getOpenId());
        jsonResult.getData().put("userType", hoUserBasic.getUserType());
        jsonResult.getData().put("ifApproved", hoUserBasic.getIfApproved());
        jsonResult.getData().put("phoneNo", hoUserBasic.getPhoneNo());
        jsonResult.getData().put("headPic",hoUserBasic.getAvatarUrl());
        jsonResult.getData().put("wxName",hoUserBasic.getWxName());
        jsonResult.globalSuccess();
        return jsonResult;
    }
}
