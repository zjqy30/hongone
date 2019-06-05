package com.hone.applet.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hone.dao.HoDictDao;
import com.hone.dao.HoSnatchOfferDao;
import com.hone.entity.HoDict;
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
public class HoSnatchOfferService {

    @Autowired
    private HoSnatchOfferDao hoSnatchOfferDao;


    public JsonResult initData(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String filePath=params.get("filePath");

        BufferedReader bufferedReader=new BufferedReader(new FileReader(filePath));
        String line="";
        while ((line=bufferedReader.readLine())!=null){

            JSONObject jsonObject= (JSONObject) JSONObject.parse(line);
            if(jsonObject.getString("status").equals("0")){
                continue;
            }
            if(jsonObject.getString("status").equals("1")){
                continue;
            }
            JSONArray jsonArray= jsonObject.getJSONArray("wh");
            if(jsonArray==null||jsonArray.size()==0){
                HoSnatchOffer hoSnatchOffer=new HoSnatchOffer();
                hoSnatchOffer.setOfferId(jsonObject.getString("_id"));
                hoSnatchOffer.setUserId(jsonObject.getString("whid"));
                hoSnatchOffer.setIfSelect("1");
                hoSnatchOffer.preInsert();
                hoSnatchOfferDao.insert(hoSnatchOffer);
                continue;
            }
            for(int i=0;i<jsonArray.size();i++){
                HoSnatchOffer hoSnatchOffer=new HoSnatchOffer();
                hoSnatchOffer.setOfferId(jsonObject.getString("_id"));
                hoSnatchOffer.setUserId(jsonArray.get(i).toString());
                if(StringUtils.isNotEmpty(jsonObject.getString("whid"))){
                    if(jsonObject.getString("whid").equals(hoSnatchOffer.getUserId())){
                        hoSnatchOffer.setIfSelect("1");
                    }else {
                        hoSnatchOffer.setIfSelect("0");
                    }
                }else {
                    hoSnatchOffer.setIfSelect("0");
                }
                hoSnatchOffer.preInsert();
                hoSnatchOfferDao.insert(hoSnatchOffer);

            }

        }


        /**
         *
         * delete from ho_snatch_offer where user_id is null;
         *
         */

        bufferedReader.close();

        jsonResult.globalSuccess();
        return jsonResult;
    }



}
