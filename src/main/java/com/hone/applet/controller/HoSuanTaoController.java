package com.hone.applet.controller;

import com.alibaba.fastjson.JSONObject;
import com.hone.dao.HoDictDao;
import com.hone.dao.HoUserBasicDao;
import com.hone.dao.HoUserStarDao;
import com.hone.entity.HoDict;
import com.hone.entity.HoUserBasic;
import com.hone.entity.HoUserStar;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.ParamsUtil;
import com.hone.system.utils.suantao.SuanTaoUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Lijia on 2019/7/9.
 */
@RestController
@RequestMapping("/applet/suantao")
public class HoSuanTaoController {

    private static Logger logger= LoggerFactory.getLogger(HoSuanTaoController.class);

    @Autowired
    private HoUserStarDao hoUserStarDao;
    @Autowired
    private HoDictDao hoDictDao;

    @RequestMapping("/fanPortraits")
    public JsonResult login(@RequestBody Map<String,String> params){
        logger.info("酸桃粉丝画像");
        JsonResult jsonResult=new JsonResult();

        try {
            ParamsUtil.checkParamIfNull(params,new String[]{"userId"});
            String userId=params.get("userId");
            String platId=params.get("platId");
            String platUserId=params.get("platUserId");
            HoUserStar hoUserStar=hoUserStarDao.findUniqueByProperty("user_id",userId);
            //已经认证
            if(hoUserStar!=null){
                HoDict hoDict=hoDictDao.selectByPrimaryKey(hoUserStar.getPlatformId());;
                String identityCode=hoUserStar.getPlatformUserId();
                String content=SuanTaoUtils.getDataFromSuanTao(Long.valueOf(hoDict.getDictDesc()),identityCode);
                jsonResult.getData().put("content", content);
            }else {
            //未认证
                HoDict hoDict=hoDictDao.selectByPrimaryKey(platId);
                String identityCode=platUserId;
                if(StringUtils.isEmpty(identityCode)){
                    identityCode=platUserId;
                }
                String content=SuanTaoUtils.getDataFromSuanTao(Long.valueOf(hoDict.getDictDesc()),identityCode);
                jsonResult.getData().put("content", content);
            }
        }catch (Exception e){
            logger.error("酸桃粉丝画像",e);
            jsonResult.globalError(e.getMessage());
        }

        jsonResult.globalSuccess();
        return jsonResult;
    }

    public static void main(String[] args){
        String str="baseInfo\":{\"_";
    }

}
