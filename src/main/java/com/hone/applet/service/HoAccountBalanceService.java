package com.hone.applet.service;

import com.alibaba.fastjson.JSONObject;
import com.hone.dao.HoAccountBalanceDao;
import com.hone.entity.HoAccountBalance;
import com.hone.entity.HoApplyWithdraw;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.ParamsUtil;
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


    /**
     * 账户余额
     * @param params
     * @return
     */
    public JsonResult accoutBalance(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String userId=params.get("userId");
        ParamsUtil.checkParamIfNull(params,new String[]{"userId"});

        HoAccountBalance hoAccountBalance=hoAccountBalanceDao.findUniqueByProperty("user_id",userId);
        if(hoAccountBalance==null){
            hoAccountBalance=new HoAccountBalance();
            hoAccountBalance.setAvaiableBalance("0");
            hoAccountBalance.setUserId(userId);
            hoAccountBalance.preInsert();
            hoAccountBalanceDao.insert(hoAccountBalance);
        }
        jsonResult.getData().put("accountBalance",hoAccountBalance);

        jsonResult.globalSuccess();
        return jsonResult;
    }
}
