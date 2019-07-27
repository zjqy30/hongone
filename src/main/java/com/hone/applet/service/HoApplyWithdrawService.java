package com.hone.applet.service;


import com.alibaba.fastjson.JSONObject;
import com.hone.applet.repo.HoReceiverListRepo;
import com.hone.dao.HoAccountBalanceDao;
import com.hone.dao.HoAccountChargeDao;
import com.hone.dao.HoApplyWithdrawDao;
import com.hone.dao.HoBackendMessageDao;
import com.hone.entity.*;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.ParamsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
public class HoApplyWithdrawService {

    @Autowired
    private HoApplyWithdrawDao hoApplyWithdrawDao;
    @Autowired
    private HoAccountBalanceDao hoAccountBalanceDao;
    @Autowired
    private HoAccountChargeDao hoAccountChargeDao;
    @Autowired
    private HoBackendMessageDao hoBackendMessageDao;

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

    /**
     * 申请提现
     * @param params
     * @return
     */
    public JsonResult apply(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String receiver=params.get("receiver");
        String cardNo=params.get("cardNo");
        String bankName=params.get("bankName");
        String drawAmount=params.get("drawAmount");
        String userId=params.get("userId");

        ParamsUtil.checkParamIfNull(params,new String[]{"userId","drawAmount","bankName","cardNo","receiver"});

        if(Double.valueOf(drawAmount)<100){
            jsonResult.globalError("提现余额至少100");
            return jsonResult;
        }


        HoAccountBalance hoAccountBalance=hoAccountBalanceDao.findUniqueByProperty("user_id",userId);
        if(hoAccountBalance==null){
            hoAccountBalance=new HoAccountBalance();
            hoAccountBalance.setAvaiableBalance("0");
            hoAccountBalance.setUserId(userId);
            hoAccountBalance.preInsert();
            hoAccountBalanceDao.insert(hoAccountBalance);
        }
        if(Double.valueOf(drawAmount)>Double.valueOf(hoAccountBalance.getAvaiableBalance())){
            jsonResult.globalError("提现余额有误");
            return jsonResult;
        }

        //插入账户交易记录
        HoAccountCharge hoAccountCharge=new HoAccountCharge();
        hoAccountCharge.setUserId(userId);
        hoAccountCharge.setTotalFee(new BigDecimal(drawAmount));
        hoAccountCharge.setChargeType("DR");
        hoAccountCharge.setChargeStatus("2");//待审核
        hoAccountCharge.setServiceFee(new BigDecimal(Double.valueOf(drawAmount)*0.01));
        hoAccountCharge.preInsert();
        hoAccountChargeDao.insert(hoAccountCharge);

        //插入提现记录
        HoApplyWithdraw hoApplyWithdraw=new HoApplyWithdraw();
        hoApplyWithdraw.setUserName(receiver);
        hoApplyWithdraw.setCradNumber(cardNo);
        hoApplyWithdraw.setCradBank(bankName);
        hoApplyWithdraw.setStatus("CH");
        hoApplyWithdraw.setUserId(userId);
        hoApplyWithdraw.setDrawFee(new BigDecimal(Double.valueOf(drawAmount)*0.01));
        hoApplyWithdraw.setDrawAmount(new BigDecimal(drawAmount));
        hoApplyWithdraw.setAccountChargeId(hoAccountCharge.getId());
        hoApplyWithdraw.preInsert();
        hoApplyWithdrawDao.insert(hoApplyWithdraw);

        //更新账户余额
        Double balance= Double.valueOf(hoAccountBalance.getAvaiableBalance())-Double.valueOf(drawAmount);
        hoAccountBalance.setAvaiableBalance(balance.toString());
        hoAccountBalanceDao.updateByPrimaryKeySelective(hoAccountBalance);

        //插入后台消息提醒
        HoBackendMessage backendMessage=new HoBackendMessage();
        backendMessage.setContent("有网红申请提现快去看看");
        backendMessage.setType("4");
        backendMessage.setObjectId(hoApplyWithdraw.getId());
        backendMessage.preInsert();
        hoBackendMessageDao.insert(backendMessage);

        jsonResult.globalSuccess();
        return jsonResult;
    }

    /**
     * 最近收款人列表
     * @param params
     * @return
     */
    public JsonResult receiverList(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String userId=params.get("userId");
        ParamsUtil.checkParamIfNull(params,new String[]{"userId"});

        List<HoReceiverListRepo> receiverListRepos=hoApplyWithdrawDao.receiverList(userId);

        jsonResult.getData().put("receiverListRepos",receiverListRepos);
        jsonResult.globalSuccess();
        return jsonResult;
    }


}
