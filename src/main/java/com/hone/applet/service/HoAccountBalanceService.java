package com.hone.applet.service;

import com.alibaba.fastjson.JSONObject;
import com.hone.dao.HoAccountBalanceDao;
import com.hone.entity.HoAccountBalance;
import com.hone.entity.HoApplyWithdraw;
import com.hone.system.utils.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Created by Lijia on 2019/5/30.
 */

@Service
@Transactional
public class HoAccountBalanceService {

    @Autowired
    private HoAccountBalanceDao hoAccountBalanceDao;


    public JsonResult initData(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String filePath=params.get("filePath");

        BufferedReader bufferedReader=new BufferedReader(new FileReader(filePath));
        String line="";
        while ((line=bufferedReader.readLine())!=null){

            JSONObject jsonObject= (JSONObject) JSONObject.parse(line);

            HoAccountBalance hoAccountBalance=new HoAccountBalance();



        }

        bufferedReader.close();

        jsonResult.globalSuccess();
        return jsonResult;
    }



}
