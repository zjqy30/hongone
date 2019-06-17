package com.hone.pc.backend.controller;

import com.hone.pc.backend.service.SystemUserService;
import com.hone.system.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Lijia on 2019/6/13.
 */

@RestController
@RequestMapping("/backend/sysUser")
public class SystemUserController {

    private static Logger logger= LoggerFactory.getLogger(SystemUserController.class);

    @Autowired
    private SystemUserService systemUserService;



    @RequestMapping("/login")
    public JsonResult initData(@RequestBody Map<String,String> params){
        logger.info("系统管理员登录");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=systemUserService.login(params);
        }catch (Exception e){
            logger.error("系统管理员登录",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }



}
