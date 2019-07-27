package com.hone.applet.controller;

import com.hone.applet.service.HoAccountBalanceService;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.ParamsUtil;
import com.hone.system.utils.SimpleUploadFileCos;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by Lijia on 2019/5/29.
 */
@RestController
@RequestMapping("/applet/cos")
public class HoCosController {

    private static Logger logger= LoggerFactory.getLogger(HoCosController.class);


    @Autowired
    private SimpleUploadFileCos simpleUploadFileCos;


    @RequestMapping("/uploadFile")
    public JsonResult uploadFile(@RequestParam(name = "file") MultipartFile multipartFile){
        logger.info("上传图片");
        JsonResult jsonResult=new JsonResult();

        try {
            if(multipartFile==null){
                jsonResult.globalError("上传对象为空");
                return jsonResult;
            }
            //上传图片大小 < 10m
            if(multipartFile.getSize()/1024>10*1024*1024){
                jsonResult.globalError("上传对象超过10M");
                return jsonResult;
            }
            String fileName = multipartFile.getOriginalFilename();
            InputStream inputStream=multipartFile.getInputStream();
            fileName= simpleUploadFileCos.SimpleUploadFileFromStream(inputStream,fileName,multipartFile.getSize());
            //判断是否上传成功
            if(StringUtils.isEmpty(fileName)){
                jsonResult.globalError("上传图片失败,请稍后再试");
                return  jsonResult;
            }
            jsonResult.globalSuccess();
            jsonResult.getData().put("fileName",fileName);
        }catch (Exception e){
            logger.error("上传图片",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/batchUploadFile")
    public JsonResult batchUploadFile(@RequestParam(name = "files") MultipartFile[] multipartFiles){
        logger.info("批量上传图片");
        JsonResult jsonResult=new JsonResult();

        try {

            if(multipartFiles.length==0){
                jsonResult.globalError("上传对象为空");
                return jsonResult;
            }

            //上传图片大小 < 10m
            for(MultipartFile multipartFile:multipartFiles){
                if(multipartFile.getSize()/1024>10*1024*1024){
                    jsonResult.globalError("上传对象超过10M");
                    return jsonResult;
                }
            }
            //逐个上传文件
            StringBuffer sb=new StringBuffer();
            for(MultipartFile multipartFile:multipartFiles){
                String fileName = multipartFile.getOriginalFilename();
                InputStream inputStream=multipartFile.getInputStream();
                fileName= simpleUploadFileCos.SimpleUploadFileFromStream(inputStream,fileName,multipartFile.getSize());
                //判断是否上传成功
                if(StringUtils.isEmpty(fileName)){
                    jsonResult.globalError("上传图片失败,请稍后再试");
                    return  jsonResult;
                }
                sb.append(fileName).append(",");
            }
           String fileNames= sb.substring(0,sb.lastIndexOf(","));
           jsonResult.globalSuccess();
           jsonResult.getData().put("fileNames",fileNames);
        }catch (Exception e){
            logger.error("批量上传图片",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/delFile")
    public JsonResult delFile(@RequestBody Map<String,String> params){
        logger.info("删除图片");
        JsonResult jsonResult=new JsonResult();

        try {
            String fileName=params.get("fileName");
            ParamsUtil.checkParamIfNull(params,new String[]{"fileName"});
            simpleUploadFileCos.DelSingleFile(fileName.substring(fileName.indexOf("images")));
            jsonResult.globalSuccess();
        }catch (Exception e){
            logger.error("删除图片",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

}
