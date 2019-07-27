package com.hone.pc.backend.service;

import com.hone.dao.HoAccountChargeDao;
import com.hone.dao.HoApplyWithdrawDao;
import com.hone.dao.HoBackendMessageDao;
import com.hone.dao.HoPayFlowDao;
import com.hone.entity.HoAccountCharge;
import com.hone.entity.HoApplyWithdraw;
import com.hone.entity.HoPayFlow;
import com.hone.pc.backend.repo.ApplyWithDrawListRepo;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.ParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/7/12.
 */

@Transactional
@Service
public class ApplayWithDrawService {

    @Autowired
    private HoApplyWithdrawDao hoApplyWithdrawDao;
    @Autowired
    private HoAccountChargeDao hoAccountChargeDao;
    @Autowired
    private HoPayFlowDao hoPayFlowDao;
    @Autowired
    private HoBackendMessageDao hoBackendMessageDao;


    /**
     * 申请提现列表
     * @param params
     * @return
     */
    public JsonResult list(Map<String, String> params) {
        JsonResult jsonResult=new JsonResult();

        List<ApplyWithDrawListRepo> repoList=hoApplyWithdrawDao.listForBackend();

        jsonResult.getData().put("pageData",repoList);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 提现操作
     * @param params
     * @return
     */
    public JsonResult operate(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String id=params.get("id");
        String ifPass=params.get("ifPass");
        Date date=new Date();
        ParamsUtil.checkParamIfNull(params,new String[]{"id","ifPass"});

        HoApplyWithdraw hoApplyWithdraw=hoApplyWithdrawDao.selectByPrimaryKey(id);
        if(hoApplyWithdraw==null||!hoApplyWithdraw.getStatus().equals("CH")){
            jsonResult.globalError("当前提现信息有误");
            return jsonResult;
        }

        //提现操作
        if (ifPass.equals("pass")){
            hoApplyWithdraw.setStatus("FN");
            hoApplyWithdraw.setUpdateDate(date);
            hoApplyWithdrawDao.updateByPrimaryKeySelective(hoApplyWithdraw);

            HoAccountCharge accountCharge=hoAccountChargeDao.selectByPrimaryKey(hoApplyWithdraw.getAccountChargeId());
            if(accountCharge!=null){
                accountCharge.setChargeStatus("1");
                hoAccountChargeDao.updateByPrimaryKeySelective(accountCharge);
            }

            //插入 平台流水
            HoPayFlow payFlow=new HoPayFlow();
            payFlow.setUserId(hoApplyWithdraw.getUserId());
            payFlow.setTotalFee(hoApplyWithdraw.getDrawAmount());
            payFlow.setTransType("DR");
            payFlow.setOutTradeNo("手动打款");
            payFlow.preInsert();
            hoPayFlowDao.insert(payFlow);


            //删除对应的 object 消息
            hoBackendMessageDao.deleteByObjectId(hoApplyWithdraw.getId());
        }else {
            hoApplyWithdraw.setStatus("RE");
            hoApplyWithdraw.setUpdateDate(date);
            hoApplyWithdrawDao.updateByPrimaryKeySelective(hoApplyWithdraw);

            HoAccountCharge accountCharge=hoAccountChargeDao.selectByPrimaryKey(hoApplyWithdraw.getAccountChargeId());
            if(accountCharge!=null){
                accountCharge.setChargeStatus("3");
                hoAccountChargeDao.updateByPrimaryKeySelective(accountCharge);
            }
        }

        jsonResult.globalSuccess();
        return jsonResult;
    }
}
