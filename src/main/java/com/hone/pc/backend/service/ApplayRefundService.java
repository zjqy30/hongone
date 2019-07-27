package com.hone.pc.backend.service;

import com.github.pagehelper.PageHelper;
import com.hone.applet.service.HoWxPayService;
import com.hone.dao.*;
import com.hone.entity.*;
import com.hone.pc.backend.repo.ApplyWithDrawListRepo;
import com.hone.pc.backend.repo.OfferListRepo;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.Page;
import com.hone.system.utils.ParamsUtil;
import com.hone.system.utils.wxpay.OutTradeNoUtil;
import com.hone.system.utils.wxtemplate.TemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/7/12.
 */

@Transactional
@Service
public class ApplayRefundService {

    @Autowired
    private HoApplyRefundDao hoApplyRefundDao;
    @Autowired
    private HoOffersDao hoOffersDao;
    @Autowired
    private HoWxPayService wxPayService;
    @Autowired
    private HoPayFlowDao hoPayFlowDao;
    @Autowired
    private TemplateUtils templateUtils;
    @Autowired
    private HoAccountChargeDao hoAccountChargeDao;
    @Autowired
    private HoUserBasicDao hoUserBasicDao;
    @Autowired
    private HoBackendMessageDao hoBackendMessageDao;

    /**
     * 申请退款列表
     * @param params
     * @return
     */
    public JsonResult list(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String pageNumber = params.get("pageNumber");
        String pageSize = params.get("pageSize");
        ParamsUtil.checkParamIfNull(params, new String[]{"pageSize", "pageNumber"});


        com.github.pagehelper.Page pageInfo = PageHelper.startPage(Integer.parseInt(pageNumber), Integer.parseInt(pageSize), true);
        List<OfferListRepo> repoList=hoApplyRefundDao.listForBackend();
        Page<OfferListRepo> page = new Page<>(pageInfo.toPageInfo());

        jsonResult.getData().put("pageData",page);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 退款操作
     * @param params
     * @return
     */
    public JsonResult operate(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String offerId=params.get("offerId");
        String ifPass=params.get("ifPass");
        Date date=new Date();
        HashMap<String,String> map=new HashMap<>();

        ParamsUtil.checkParamIfNull(params,new String[]{"offerId","ifPass"});

        HoOffers hoOffers=hoOffersDao.selectByPrimaryKey(offerId);
        if(hoOffers==null||!hoOffers.getStatus().equals("RA")){
            jsonResult.globalError("当前订单信息有误");
            return jsonResult;
        }

        HoApplyRefund applyRefund=hoApplyRefundDao.findUniqueByProperty("offer_id",offerId);
        if(applyRefund==null||!applyRefund.getStatus().equals("CH")){
            jsonResult.globalError("当前订单无法退款");
            return jsonResult;
        }


        HoPayFlow payFlow=hoPayFlowDao.findByOfferIdAndType(offerId,"PY");
        if(payFlow==null||!payFlow.getStatus().equals("1")){
            jsonResult.globalError("当前订单交易流水异常");
            return jsonResult;
        }

        if(ifPass.equals("pass")){
            //执行退款
            String refundNo= OutTradeNoUtil.outTradeNo("2");
            String result=wxPayService.wechatPayRefundForApplet(payFlow.getOutTradeNo(),refundNo,payFlow.getTotalFee().toString(),payFlow.getTotalFee().toString());
            if(result.equalsIgnoreCase("SUCCESS")){
                //更改订单状态
                hoOffers.setStatus("RN");
                hoOffers.setUpdateDate(date);
                hoOffersDao.updateByPrimaryKeySelective(hoOffers);

                //更改申请退款表状态
                applyRefund.setStatus("FN");
                applyRefund.setUpdateDate(date);
                hoApplyRefundDao.updateByPrimaryKeySelective(applyRefund);

                //插入交易流水
                HoPayFlow hoPayFlow2 = hoPayFlowDao.findByOfferIdAndType(offerId, "RN");
                if (hoPayFlow2 == null) {
                    hoPayFlow2 = new HoPayFlow();
                    hoPayFlow2.setStatus("1");
                    hoPayFlow2.setOutTradeNo(refundNo);
                    hoPayFlow2.setOfferId(offerId);
                    hoPayFlow2.setTransType("RN");
                    hoPayFlow2.setTotalFee(payFlow.getTotalFee());
                    hoPayFlow2.setUserId(payFlow.getUserId());
                    hoPayFlow2.preInsert();
                    hoPayFlowDao.insert(hoPayFlow2);
                }

                map.put("result","已同意，请留意微信账户信息通知");
            }

            //删除对应的 object 消息
            hoBackendMessageDao.deleteByObjectId(applyRefund.getId());
        }else {
            //更改订单状态
            hoOffers.setStatus("RF");
            hoOffers.setUpdateDate(date);
            hoOffersDao.updateByPrimaryKeySelective(hoOffers);

            //更改申请退款表状态
            applyRefund.setStatus("RE");
            applyRefund.setUpdateDate(date);
            hoApplyRefundDao.updateByPrimaryKeySelective(applyRefund);

            map.put("result","已拒绝，详情请联系红腕客服人员");
        }

        HoUserBasic hoUserBasic=hoUserBasicDao.selectByPrimaryKey(hoOffers.getUserId());
        map.put("openId",hoUserBasic.getOpenId());
        map.put("title","订单退款申请");
        map.put("type","2");
        templateUtils.sendMessage(map);

        jsonResult.globalSuccess();
        return jsonResult;
    }
}
