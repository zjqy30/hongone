package com.hone.pc.backend.service;

import com.github.pagehelper.PageHelper;
import com.hone.applet.service.HoWxPayService;
import com.hone.dao.*;
import com.hone.entity.*;
import com.hone.pc.backend.repo.InviteUserListRepo;
import com.hone.pc.backend.repo.OfferListRepo;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.NumUtils;
import com.hone.system.utils.Page;
import com.hone.system.utils.ParamsUtil;
import com.hone.system.utils.wxpay.OutTradeNoUtil;
import com.hone.system.utils.wxtemplate.TemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/6/15.
 */

@Service
@Transactional
public class OfferService {

    @Autowired
    private HoOffersDao hoOffersDao;
    @Autowired
    private HoPayFlowDao hoPayFlowDao;
    @Autowired
    private HoWxPayService hoWxPayService;
    @Autowired
    private HoSnatchOfferDao hoSnatchOfferDao;
    @Autowired
    private HoUserBasicDao hoUserBasicDao;
    @Autowired
    private TemplateUtils templateUtils;
    @Autowired
    private HoAccountChargeDao hoAccountChargeDao;
    @Autowired
    private HoAccountBalanceDao hoAccountBalanceDao;
    @Autowired
    private HoBackendMessageDao hoBackendMessageDao;
    @Autowired
    private HoFrontMessageDao hoFrontMessageDao;

    /**
     * 需求列表
     *
     * @param params
     * @return
     */
    public JsonResult list(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String pageNumber = params.get("pageNumber");
        String pageSize = params.get("pageSize");
        String type = params.get("type");
        String wxName = params.get("wxName");

        ParamsUtil.checkParamIfNull(params, new String[]{"type", "pageSize", "pageNumber"});

        com.github.pagehelper.Page pageInfo = PageHelper.startPage(Integer.parseInt(pageNumber), Integer.parseInt(pageSize), true);
        List<OfferListRepo> offerListRepos = hoOffersDao.listForBackend(type,wxName);
        Page<OfferListRepo> page = new Page<>(pageInfo.toPageInfo());

        //获取已选抢单人员联系方式
        if(type.equals("LK")||type.equals("FN")){
            offerListRepos =page.getList();
            if(!CollectionUtils.isEmpty(offerListRepos)){
                for(OfferListRepo offer:offerListRepos){
                    HoSnatchOffer hoSnatchOffer= hoSnatchOfferDao.findByOfferIdSelect(offer.getId());
                    HoUserBasic hoUserBasic=hoUserBasicDao.selectByPrimaryKey(hoSnatchOffer.getUserId());
                    offer.setStarName(hoUserBasic.getWxName());
                    offer.setStarPhone(hoUserBasic.getPhoneNo());
                }
            }
        }

        jsonResult.getData().put("pageData", page);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 需求审核操作
     *
     * @param params
     * @return
     */
    public JsonResult approveOperate(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String id = params.get("id");
        String ifPass = params.get("ifPass");

        ParamsUtil.checkParamIfNull(params, new String[]{"ifPass", "id"});

        HoOffers hoOffers = hoOffersDao.selectByPrimaryKey(id);
        if (hoOffers == null) {
            jsonResult.globalError("需求不存在");
            return jsonResult;
        } else if (!hoOffers.getStatus().equals("PY")) {
            jsonResult.globalError("当前需求状态有误");
            return jsonResult;
        } else if (ifPass.equals("pass")) {
            hoOffers.setStatus("AP");
            //删除对应的 object 消息
            hoBackendMessageDao.deleteByObjectId(hoOffers.getId());

            //添加 ho_front_message 记录
            HoFrontMessage hoFrontMessage=new HoFrontMessage();
            hoFrontMessage.setContent("小程序订单大厅有新的订单");
            hoFrontMessage.setObjectId(hoOffers.getId());
            hoFrontMessage.setType("3");
            hoFrontMessage.preInsert();
            hoFrontMessageDao.insert(hoFrontMessage);

        } else if (ifPass.equals("nopass")) {
            hoOffers.setStatus("NAP");
        }

        hoOffers.setApproveDate(new Date());
        hoOffersDao.updateByPrimaryKeySelective(hoOffers);

        //发送模板消息
        HoUserBasic hoUserBasic=hoUserBasicDao.selectByPrimaryKey(hoOffers.getUserId());
        Map<String,String> templateMap=new HashMap<>();
        templateMap.put("type","2");
        templateMap.put("title","订单审核");
        templateMap.put("openId",hoUserBasic.getOpenId());
        templateMap.put("result",ifPass.equals("pass")?"审核通过!开启红腕之旅":"审核驳回，点击查看详情");
        templateUtils.sendMessage(templateMap);

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 派单中删除需求
     *
     * @param params
     * @return
     */
    public JsonResult del(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String id = params.get("id");
        ParamsUtil.checkParamIfNull(params, new String[]{"id"});

        HoOffers hoOffers = hoOffersDao.selectByPrimaryKey(id);
        if(hoOffers==null||!hoOffers.getStatus().equals("AP")){
            jsonResult.globalError("当前订单不可删除");
            return jsonResult;
        }
        hoOffers.setEnableFlag("0");

        //获取交易流水
        HoPayFlow hoPayFlow = hoPayFlowDao.findByOfferIdAndType(id, "PY");
        if (hoPayFlow == null) {
            jsonResult.globalError("当前交易流水不存在");
            return jsonResult;
        }
        //退款流水号
        String outRefundNo = OutTradeNoUtil.outTradeNo("2");
        //执行退款
        String tradeResult = hoWxPayService.wechatPayRefundForApplet(hoPayFlow.getOutTradeNo(), outRefundNo, String.valueOf(hoPayFlow.getTotalFee()), String.valueOf(hoPayFlow.getTotalFee()));
        if (tradeResult.equals("SUCCESS")) {
            hoOffers.setStatus("RN");
            hoOffers.setUpdateDate(new Date());
            hoOffersDao.updateByPrimaryKeySelective(hoOffers);
            HoPayFlow hoPayFlow2 = hoPayFlowDao.findByOfferIdAndType(id, "RN");
            if (hoPayFlow2 == null) {
                hoPayFlow2 = new HoPayFlow();
                //插入交易流水
                hoPayFlow2.setStatus("1");
                hoPayFlow2.setOutTradeNo(outRefundNo);
                hoPayFlow2.setOfferId(id);
                hoPayFlow2.setTransType("RN");
                hoPayFlow2.setTotalFee(hoPayFlow.getTotalFee());
                hoPayFlow2.setUserId(hoPayFlow.getUserId());
                hoPayFlow2.preInsert();
                hoPayFlowDao.insert(hoPayFlow2);
            }

            //发送模板消息提醒用户已退款
            HoUserBasic hoUserBasic=hoUserBasicDao.selectByPrimaryKey(hoOffers.getUserId());
            HashMap<String,String> map=new HashMap<>();
            map.put("type","3");
            map.put("reason","订单已被删除，详情联系客服");
            map.put("orderNo",hoOffers.getOrderNo());
            map.put("totalFee",hoOffers.getPrice()+"");
            map.put("openId",hoUserBasic.getOpenId());
            templateUtils.sendMessage(map);
        } else {
            jsonResult.globalError("退款失败");
            return jsonResult;
        }

        //TODO 发送模板消息提醒抢单用户 抢单失败


        jsonResult.globalSuccess();
        return jsonResult;
    }

    /**
     * 抢单用户列表
     * @param params
     * @return
     */
    public JsonResult snatchUserList(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String pageNumber = params.get("pageNumber");
        String pageSize = params.get("pageSize");
        String id = params.get("id");

        ParamsUtil.checkParamIfNull(params, new String[]{"id", "pageSize", "pageNumber"});

        com.github.pagehelper.Page pageInfo = PageHelper.startPage(Integer.parseInt(pageNumber), Integer.parseInt(pageSize), true);
        List<InviteUserListRepo> userListRepos=hoSnatchOfferDao.snatchListForBackend(id);
        if(!CollectionUtils.isEmpty(pageInfo.toPageInfo().getList())){
            for(InviteUserListRepo repo:(List<InviteUserListRepo>)pageInfo.toPageInfo().getList()){
                repo.setFansNums(NumUtils.formatNum(repo.getFansNums(),false));
            }
        }
        Page<InviteUserListRepo> page = new Page<>(pageInfo.toPageInfo());

        jsonResult.getData().put("pageData", page);
        jsonResult.globalSuccess();
        return jsonResult;
    }

    /**
     * 结束需求
     * @param params
     * @return
     */
    public JsonResult finsh(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String id=params.get("id");
        ParamsUtil.checkParamIfNull(params, new String[]{"id"});

        HoOffers hoOffers=hoOffersDao.selectByPrimaryKey(id);
        if(hoOffers==null){
            jsonResult.globalError("当前需求不存在");
            return jsonResult;
        }
        if(!hoOffers.getStatus().equals("LK")){
            jsonResult.globalError("当前需求状态有误");
            return jsonResult;
        }

        //获取抢单记录
        HoSnatchOffer hoSnatchOffer=hoSnatchOfferDao.findByOfferIdSelect(id);

        if(hoSnatchOffer==null){
            jsonResult.globalError("当前需求抢单信息异常");
            return jsonResult;
        }

        //更新需求状态
        hoOffers.setStatus("FN");
        hoOffers.setFinshDate(new Date());
        hoOffersDao.updateByPrimaryKeySelective(hoOffers);


        //插入网红账户变动记录
        HoAccountCharge hoAccountCharge = new HoAccountCharge();
        hoAccountCharge.setOfferId(id);
        hoAccountCharge.setUserId(hoSnatchOffer.getUserId());
        hoAccountCharge.setTotalFee(new BigDecimal(hoOffers.getPrice()));
        hoAccountCharge.setServiceFee(new BigDecimal(hoOffers.getPrice() * 0.2));
        hoAccountCharge.setChargeType("SR");
        hoAccountCharge.setChargeStatus("1");
        hoAccountChargeDao.insert(hoAccountCharge);

        //插入账户金额信息
        HoAccountBalance hoAccountBalance = hoAccountBalanceDao.findUniqueByProperty("user_id", hoSnatchOffer.getUserId());
        if (hoAccountBalance == null) {
            hoAccountBalance = new HoAccountBalance();
            hoAccountBalance.setAvaiableBalance(hoAccountCharge.getTotalFee().subtract(hoAccountCharge.getServiceFee()).toString());
            hoAccountBalance.setUserId(hoSnatchOffer.getUserId());
            hoAccountBalance.preInsert();
            hoAccountBalanceDao.insert(hoAccountBalance);
        } else {
            String totalMoney = hoAccountBalance.getAvaiableBalance();
            totalMoney = new BigDecimal(totalMoney).add(hoAccountCharge.getTotalFee().subtract(hoAccountCharge.getServiceFee())).toString();
            hoAccountBalance.setAvaiableBalance(totalMoney);
            hoAccountBalanceDao.updateByPrimaryKeySelective(hoAccountBalance);
        }

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 进行中需求恢复
     * @param params
     * @return
     */
    public JsonResult recover(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String id=params.get("id");
        ParamsUtil.checkParamIfNull(params, new String[]{"id"});

        HoOffers hoOffers=hoOffersDao.selectByPrimaryKey(id);
        if(hoOffers==null){
            jsonResult.globalError("当前需求不存在");
            return jsonResult;
        }

        if(!hoOffers.getStatus().equals("LK")){
            jsonResult.globalError("当前需求状态有误");
            return jsonResult;
        }

        //恢复成可抢单
        hoOffers.setStatus("AP");
        hoOffersDao.updateByPrimaryKeySelective(hoOffers);

        //删除抢单记录
        hoSnatchOfferDao.deleteByOfferId(id);

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 订单生成
     * @param params
     * @return
     */
    public JsonResult autoCreate(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String offerId=params.get("offerId");
        String starId=params.get("starId");
        ParamsUtil.checkParamIfNull(params, new String[]{"offerId","starId"});

        HoOffers hoOffers=hoOffersDao.selectByPrimaryKey(offerId);
        if(hoOffers==null){
            jsonResult.globalError("当前需求不存在");
            return jsonResult;
        }

        if(!hoOffers.getStatus().equals("AP")){
            jsonResult.globalError("当前需求状态有误");
            return jsonResult;
        }

        HoSnatchOffer hoSnatchOffer=new HoSnatchOffer();
        hoSnatchOffer.setIfSelect("1");
        hoSnatchOffer.setOfferId(offerId);
        hoSnatchOffer.setUserId(starId);
        hoSnatchOffer.setStarApprove("0");
        hoSnatchOffer.setSellApprove("0");
        hoSnatchOffer.preInsert();
        hoSnatchOfferDao.insert(hoSnatchOffer);

        hoOffers.setStatus("LK");
        hoOffersDao.updateByPrimaryKeySelective(hoOffers);

        jsonResult.globalSuccess();
        return jsonResult;
    }

}
