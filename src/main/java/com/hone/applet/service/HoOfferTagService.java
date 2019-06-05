package com.hone.applet.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hone.dao.HoDictDao;
import com.hone.dao.HoOfferTagDao;
import com.hone.entity.HoDict;
import com.hone.entity.HoOfferTag;
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
public class HoOfferTagService {

    @Autowired
    private HoOfferTagDao hoOfferTagDao;
    @Autowired
    private HoDictDao hoDictDao;


    public JsonResult initData(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String filePath=params.get("filePath");

        BufferedReader bufferedReader=new BufferedReader(new FileReader(filePath));
        String line="";
        while ((line=bufferedReader.readLine())!=null){

            JSONObject jsonObject= (JSONObject) JSONObject.parse(line);


            JSONArray jsonArray=jsonObject.getJSONArray("whtag");
            if(jsonArray==null||jsonArray.size()==0){
                continue;
            }
            for(int i=0;i<jsonArray.size();i++){


//                HoDict hoDict=hoDictDao.selectByTypeAndValue("label",jsonArray.getString(i));
                HoDict hoDict=hoDictDao.findUniqueByProperty("dict_value",jsonArray.getString(i));
                if(hoDict!=null){
                    HoOfferTag hoOfferTag=new HoOfferTag();
                    hoOfferTag.setOfferId(jsonObject.getString("_id"));
                    hoOfferTag.setDictId(hoDict.getId());
                    hoOfferTag.preInsert();
                    hoOfferTagDao.insert(hoOfferTag);
                }
            }





        }

        bufferedReader.close();

        jsonResult.globalSuccess();
        return jsonResult;
    }



}
