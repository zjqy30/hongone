package com.hone.applet.service;


import com.alibaba.fastjson.JSONObject;
import com.hone.dao.HoApplyWithdrawDao;
import com.hone.entity.HoApplyWithdraw;
import com.hone.entity.HoUserBasic;
import com.hone.system.utils.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Created by Lijia on 2019/5/30.
 */

@Service
@Transactional
public class HoApplyWithdrawService {

    @Autowired
    private HoApplyWithdrawDao hoApplyWithdrawDao;

    public JsonResult initData(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String filePath=params.get("filePath");

        BufferedReader bufferedReader=new BufferedReader(new FileReader(filePath));
        String line="";
        while ((line=bufferedReader.readLine())!=null){

            JSONObject jsonObject= (JSONObject) JSONObject.parse(line);

            HoApplyWithdraw hoApplyWithdraw=new HoApplyWithdraw();
            hoApplyWithdraw.setCradBank(jsonObject.getString("cardBank"));
            hoApplyWithdraw.setCradNumber(jsonObject.getString("cardNumber"));
            if(StringUtils.isEmpty(jsonObject.getString("amount"))){
                continue;
            }
            hoApplyWithdraw.setDrawAmount(new BigDecimal(jsonObject.getString("amount")==null?"0":jsonObject.getString("amount")));
            hoApplyWithdraw.setDrawFee(new BigDecimal(jsonObject.getString("fee")==null?"0":jsonObject.getString("fee")));
            hoApplyWithdraw.setStatus(jsonObject.getString("status"));
            hoApplyWithdraw.setTransferImgs(jsonObject.getString("paynotice"));
            hoApplyWithdraw.setUserName(jsonObject.getString("cardName"));
            hoApplyWithdraw.setId(jsonObject.getString("_id"));
            hoApplyWithdraw.setCreateDate(new Date(jsonObject.getLong("createtime")));
            hoApplyWithdraw.setUpdateDate(hoApplyWithdraw.getCreateDate());
            hoApplyWithdraw.setEnableFlag("1");
            hoApplyWithdrawDao.insert(hoApplyWithdraw);


        }

        bufferedReader.close();

        jsonResult.globalSuccess();
        return jsonResult;
    }
}
