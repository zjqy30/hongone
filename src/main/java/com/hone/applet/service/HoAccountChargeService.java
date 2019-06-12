package com.hone.applet.service;

import com.alibaba.fastjson.JSONObject;
import com.hone.dao.HoAccountBalanceDao;
import com.hone.dao.HoAccountChargeDao;
import com.hone.entity.HoAccountBalance;
import com.hone.entity.HoAccountCharge;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.Page;
import com.hone.system.utils.ParamsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/5/30.
 */

@Service
@Transactional
public class HoAccountChargeService {

    @Autowired
    private HoAccountChargeDao hoAccountChargeDao;


    public JsonResult initData(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String filePath=params.get("filePath");

        BufferedReader bufferedReader=new BufferedReader(new FileReader(filePath));
        String line="";
        while ((line=bufferedReader.readLine())!=null){

            JSONObject jsonObject= (JSONObject) JSONObject.parse(line);

            HoAccountCharge hoAccountCharge=new HoAccountCharge();

            hoAccountCharge.setOfferId(jsonObject.getString("offerId"));
            hoAccountCharge.setChargeStatus(jsonObject.getString("status"));
            hoAccountCharge.setChargeType(jsonObject.getString("type"));
            hoAccountCharge.setOutTradeNo(jsonObject.getString("out_trade_no"));
            if(StringUtils.isEmpty(jsonObject.getString("total_fee"))){
                continue;
            }
            hoAccountCharge.setTotalFee(new BigDecimal(jsonObject.getString("total_fee")).divide(new BigDecimal(100)));
            hoAccountCharge.setUserId(jsonObject.getString("userId"));
            hoAccountCharge.setId(jsonObject.getString("_id"));
            hoAccountCharge.setCreateDate(new Date(jsonObject.getLong("createtime")));
            hoAccountCharge.setUpdateDate(hoAccountCharge.getCreateDate());
            hoAccountCharge.setEnableFlag("1");
            hoAccountChargeDao.insert(hoAccountCharge);

            /**
             *
             *
             * select sum(a.total_fee),b.wx_name,a.user_id
             from ho_account_charge a left join ho_user_basic b on a.user_id=b.id
             where a.charge_type='SERVICE' group by a.user_id
             *
             *
             */

        }

        bufferedReader.close();

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 账户变动记录
     * @param params
     * @return
     */
    public JsonResult list(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        Integer pageNumber=Integer.parseInt(params.get("pageNumber"));
        Integer pageSize=Integer.parseInt(params.get("pageSize"));
        String userId=params.get("userId");

        ParamsUtil.checkParamIfNull(params,new String[]{"pageSize","pageNumber","userId"});

        List<HoAccountCharge> chargeList=hoAccountChargeDao.listForApi(userId);
        Page<HoAccountCharge> page=new Page<>(pageNumber,pageSize,chargeList);

        jsonResult.getData().put("pageData",page);
        jsonResult.globalSuccess();
        return jsonResult;
    }
}
