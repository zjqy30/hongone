package com.hone.applet.service;

import com.alibaba.fastjson.JSONObject;
import com.hone.dao.HoOfferTemplateDao;
import com.hone.entity.HoMarketer;
import com.hone.entity.HoOfferTemplate;
import com.hone.system.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/5/31.
 */

@Service
@Transactional
public class HoOfferTemplateService {

    @Autowired
    private HoOfferTemplateDao hoOfferTemplateDao;


    public JsonResult initData(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String filePath=params.get("filePath");

        BufferedReader bufferedReader=new BufferedReader(new FileReader(filePath));
        String line="";
        while ((line=bufferedReader.readLine())!=null){

            JSONObject jsonObject= (JSONObject) JSONObject.parse(line);

            HoOfferTemplate hoOfferTemplate=new HoOfferTemplate();
            hoOfferTemplate.setTitle(jsonObject.getString("title"));
            hoOfferTemplate.setInfos(jsonObject.getString("desc"));
            hoOfferTemplate.setMinPrice(jsonObject.getString("minPrice"));
            hoOfferTemplate.setMaxPrice(jsonObject.getString("maxPrice"));
            hoOfferTemplate.setId(jsonObject.getString("_id"));
            hoOfferTemplate.setCreateDate(new Date(jsonObject.getLong("createtime")));
            hoOfferTemplate.setUpdateDate(hoOfferTemplate.getCreateDate());
            hoOfferTemplate.setEnableFlag("1");
            hoOfferTemplateDao.insert(hoOfferTemplate);



        }


        bufferedReader.close();

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 模板列表
     * @param params
     * @return
     */
    public JsonResult list(Map<String, String> params) {
        JsonResult jsonResult=new JsonResult();
        List<HoOfferTemplate> templateList=hoOfferTemplateDao.listAll();
        jsonResult.getData().put("templateList",templateList);
        jsonResult.globalSuccess();
        return jsonResult;
    }
}
