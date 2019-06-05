package com.hone.applet.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hone.dao.HoMarketerDao;
import com.hone.entity.HoMarketer;
import com.hone.entity.HoSnatchOffer;
import com.hone.system.utils.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;

/**
 * Created by Lijia on 2019/5/30.
 */

@Service
@Transactional
public class HoMarketerService {

    @Autowired
    private HoMarketerDao hoMarketerDao;

    public HoMarketer findOne(HoMarketer hoMarketer) {
        return hoMarketerDao.selectOne(hoMarketer);
    }


    public JsonResult initData(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String filePath=params.get("filePath");

        BufferedReader bufferedReader=new BufferedReader(new FileReader(filePath));
        String line="";
        while ((line=bufferedReader.readLine())!=null){

            JSONObject jsonObject= (JSONObject) JSONObject.parse(line);

            HoMarketer hoMarketer=new HoMarketer();
            hoMarketer.setMarketName(jsonObject.getString("name"));
            hoMarketer.setId(jsonObject.getString("_id"));
            hoMarketer.setOpenId(jsonObject.getString("_openid"));
            hoMarketer.setUserCode(jsonObject.getString("key"));
            hoMarketer.preInsert();
            hoMarketerDao.insert(hoMarketer);


        }


        bufferedReader.close();

        jsonResult.globalSuccess();
        return jsonResult;
    }


}
