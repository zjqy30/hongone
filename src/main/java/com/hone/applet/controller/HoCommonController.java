package com.hone.applet.controller;

import com.hone.applet.service.HoCollectService;
import com.hone.applet.service.HoCommonService;
import com.hone.system.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Lijia on 2019/7/9.
 */

@RestController
@RequestMapping("/applet/common")
public class HoCommonController {

    private static Logger logger= LoggerFactory.getLogger(HoCommonController.class);

    @Autowired
    private HoCommonService hoCommonService;

    @RequestMapping("/serviceTemplate/list")
    public JsonResult serviceList(@RequestBody Map<String,String> params){
        logger.info("服务类型列表");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoCommonService.serviceList(params);
        }catch (Exception e){
            logger.error("服务类型列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

}
