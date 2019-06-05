package com.hone.applet.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hone.dao.HoDictDao;
import com.hone.dao.HoPayFlowDao;
import com.hone.dao.HoUserBasicDao;
import com.hone.entity.HoDict;
import com.hone.entity.HoPayFlow;
import com.hone.system.utils.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lijia on 2019/5/30.
 */

@Service
@Transactional
public class HoPayFlowService {

    @Autowired
    private HoPayFlowDao hoPayFlowDao;
    @Autowired
    private HoUserBasicDao hoUserBasicDao;
    static  HashMap<String,String> map=new HashMap<>();


    public JsonResult initData(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String filePath=params.get("filePath");

        BufferedReader bufferedReader=new BufferedReader(new FileReader(filePath));
        String line="";

        while (filePath.contains("order")&&(line=bufferedReader.readLine())!=null){
            JSONObject jsonObject= (JSONObject) JSONObject.parse(line);
            map.put(jsonObject.getString("out_trade_no"),jsonObject.getString("offerId"));
        }
        while (!filePath.contains("order")&&(line=bufferedReader.readLine())!=null){

            JSONObject jsonObject= (JSONObject) JSONObject.parse(line);


            String offerId=map.get(jsonObject.getString("out_trade_no"));

            HoPayFlow hoPayFlow=new HoPayFlow();
            hoPayFlow.setOfferId(offerId);
            hoPayFlow.setOutTradeNo(map.get(jsonObject.getString("out_trade_no")));
            hoPayFlow.setStatus("1");
            String type=jsonObject.getString("type");
            if(StringUtils.isNotEmpty(type)){
                if(type.equals("PAY")){
                    hoPayFlow.setTransType("PA");
                }
                if(type.equals("REFUND")){
                    hoPayFlow.setTransType("RE");
                }
            }
            hoPayFlow.setId(jsonObject.getString("_id"));
            hoPayFlow.setTotalFee(new BigDecimal(jsonObject.getString("total_fee")).divide(new BigDecimal(100)));
            hoPayFlow.setUserId(jsonObject.getString("userId"));
            hoPayFlow.setCreateDate(new Date(jsonObject.getLong("createtime")));
            hoPayFlow.setUpdateDate(hoPayFlow.getCreateDate());
            hoPayFlow.setEnableFlag("1");
            if(StringUtils.isEmpty(hoPayFlow.getUserId())){
                continue;
            }
            if(StringUtils.isEmpty(hoPayFlow.getTransType())){
                continue;
            }
            if(StringUtils.isEmpty(hoPayFlow.getOutTradeNo())){
                continue;
            }
            if(hoUserBasicDao.selectByPrimaryKey(hoPayFlow.getUserId())==null){
                continue;
            }
            hoPayFlowDao.insert(hoPayFlow);
        }

        bufferedReader.close();

        jsonResult.globalSuccess();
        return jsonResult;
    }



}
