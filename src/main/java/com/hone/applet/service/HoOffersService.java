package com.hone.applet.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hone.applet.repo.HoOfferInfoRepo;
import com.hone.applet.repo.HoOffersListRepo;
import com.hone.applet.repo.HoSellerOfferListRepo;
import com.hone.applet.repo.HoStarSnatchOfferRepo;
import com.hone.dao.*;
import com.hone.entity.*;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.NumUtils;
import com.hone.system.utils.Page;
import com.hone.system.utils.ParamsUtil;
import com.hone.system.utils.wxpay.OutTradeNoUtil;
import org.apache.commons.lang3.StringUtils;
import org.omg.PortableInterceptor.HOLDING;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/5/30.
 */

@Service
@Transactional
public class HoOffersService {

    @Autowired
    private HoOffersDao hoOffersDao;
    @Autowired
    private HoOfferTagDao hoOfferTagDao;
    @Autowired
    private HoSnatchOfferDao hoSnatchOfferDao;
    @Autowired
    private HoUserBasicDao hoUserBasicDao;
    @Autowired
    private HoOfferTemplateDao hoOfferTemplateDao;
    @Autowired
    private HoPayFlowDao hoPayFlowDao;
    @Autowired
    private HoAccountChargeDao hoAccountChargeDao;
    @Autowired
    private HoApplyRefundDao hoApplyRefundDao;


    public JsonResult initData(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String filePath = params.get("filePath");

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {

            JSONObject jsonObject = (JSONObject) JSONObject.parse(line);

            HoOffers hoOffers = new HoOffers();
//            hoOffers.setFansNum(0);
            hoOffers.setId(jsonObject.getString("_id"));
            hoOffers.setGender(jsonObject.getString("gender"));
            hoOffers.setImgs(jsonObject.getString("images"));
            hoOffers.setOfferTemplateId(jsonObject.getString("template"));
            System.out.println(jsonObject.getString("_id")+"   "+jsonObject.getString("price"));
            if(StringUtils.isEmpty(jsonObject.getString("price"))){
                continue;
            }
            hoOffers.setPrice(jsonObject.getInteger("price")/100);
            hoOffers.setRemarks(jsonObject.getString("remarks"));
            hoOffers.setShopPlate(jsonObject.getString("mall"));
            hoOffers.setStatus(jsonObject.getIntValue("status")+"");
            if(hoOffers.getStatus().equals("0")||hoOffers.getStatus().equals("1")){
                continue;
            }
            hoOffers.setTitle(jsonObject.getString("name"));
            hoOffers.setUserId(jsonObject.getString("userId"));
            hoOffers.setUserPlate(jsonObject.getString("platform_title"));
            hoOffers.setCreateDate(new Date(jsonObject.getLong("createtime")));
            hoOffers.setUpdateDate(hoOffers.getCreateDate());
            hoOffers.setEnableFlag(jsonObject.getString("fans_nb"));
            hoOffersDao.insert(hoOffers);


        }

        bufferedReader.close();

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 查看订单列表
     * @param params
     * @return
     */
    public JsonResult list(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        Integer pageNumber=Integer.parseInt(params.get("pageNumber"));
        Integer pageSize=Integer.parseInt(params.get("pageSize"));
        String platIds=params.get("platIds");
        String fansNumsOrderBy=params.get("fansNumsOrderBy");
        String priceOrderBy=params.get("priceOrderBy");
        ParamsUtil.checkParamIfNull(params,new String[]{"pageNumber","pageSize"});

        //标签
        List<String> platIdsList= new ArrayList<>();
        if(StringUtils.isNotEmpty(platIds)){
            for(String each:platIds.split(",")){
                platIdsList.add(each);
            }
        }

        //排序
        String orderBy="";
        if(StringUtils.isEmpty(fansNumsOrderBy)&&StringUtils.isEmpty(priceOrderBy)){
            orderBy=" a.create_date desc";
        }else {
            if(!StringUtils.isEmpty(fansNumsOrderBy)){
                orderBy=" a.fans_num "+fansNumsOrderBy;
            }
            if(!StringUtils.isEmpty(priceOrderBy)){
                if(orderBy.contains("fans_num")){
                    orderBy=orderBy+", a.price " + priceOrderBy;
                }else {
                    orderBy=" a.price " + priceOrderBy;
                }
            }
        }

        PageHelper.startPage(pageNumber,pageSize,false);
        List<HoOffersListRepo> hoOffersRepoList= hoOffersDao.listForApiTag(platIdsList,orderBy);
        if(!CollectionUtils.isEmpty(hoOffersRepoList)){
            for(HoOffersListRepo repo:hoOffersRepoList){
                repo.setFanNums(NumUtils.formatNum(repo.getFanNums(),false));
            }
        }
        Page<HoOffersListRepo> page=new Page<>(pageNumber,pageSize,hoOffersRepoList);
        jsonResult.getData().put("pageData",page);

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 查看需求详情
     * @param params
     * @return
     */
    public JsonResult offerInfo(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String id=params.get("id");
        String userId=params.get("userId");
        String status=params.get("status");
        ParamsUtil.checkParamIfNull(params,new String[]{"id","userId"});

        //获取需求详情
        HoOfferInfoRepo hoOfferInfoRepo=hoOffersDao.detailsById(id);
        if(hoOfferInfoRepo==null){
            jsonResult.globalError("需求不存在");
            return jsonResult;
        }
        if(StringUtils.isNotEmpty(status)&&!status.equals(hoOfferInfoRepo.getStatus())){
            jsonResult.globalError("需求状态不一致");
            return jsonResult;
        }

        //获取标签
        String offerTag=hoOfferTagDao.findStringByOfferId(id);
        hoOfferInfoRepo.setTag(offerTag);

        //是否抢单
        HoSnatchOffer hoSnatchOffer=hoSnatchOfferDao.findByOfferIdAndUserId(id,userId);
        if(hoSnatchOffer!=null&&StringUtils.isNotEmpty(hoSnatchOffer.getId())){
            hoOfferInfoRepo.setIfSnatch("1");
        }else {
            hoOfferInfoRepo.setIfSnatch("0");
        }

        jsonResult.getData().put("offerInfo",hoOfferInfoRepo);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 抢单
     * @param params
     * @return
     */
    public JsonResult snatchOffer(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String id=params.get("id");
        String userId=params.get("userId");
        ParamsUtil.checkParamIfNull(params,new String[]{"id","userId"});

        //判断是否已经抢单
        HoSnatchOffer hoSnatchOffer=hoSnatchOfferDao.findByOfferIdAndUserId(id,userId);
        if(hoSnatchOffer!=null&&StringUtils.isNotEmpty(hoSnatchOffer.getId())){
            jsonResult.globalError("不可重复抢单");
            return jsonResult;
        }

        //校验需求状态
        HoOffers hoOffers=hoOffersDao.selectByPrimaryKey(id);
        if(!hoOffers.getStatus().equals("RA")){
            jsonResult.globalError("当前状态不可抢单");
            return jsonResult;
        }

        HoUserBasic hoUserBasic=hoUserBasicDao.selectByPrimaryKey(userId);
        if(hoUserBasic==null||hoUserBasic.getUserType().equals("2")){
            jsonResult.globalError("当前用户不可抢单");
            return jsonResult;
        }

        if(!hoUserBasic.getIfApproved().equals("1")){
            jsonResult.globalError("请先申请认证");
            return jsonResult;
        }

        //插入数据
        hoSnatchOffer=new HoSnatchOffer();
        hoSnatchOffer.setUserId(userId);
        hoSnatchOffer.setOfferId(id);
        hoSnatchOffer.setIfSelect("0");
        hoSnatchOffer.preInsert();
        hoSnatchOfferDao.insert(hoSnatchOffer);

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 查看是否抢单
     * @param params
     * @return
     */
    public JsonResult ifSnatch(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String id=params.get("offerId");
        String userId=params.get("userId");
        ParamsUtil.checkParamIfNull(params,new String[]{"offerId","userId"});
        String ifSnatch="0";

        //判断是否已经抢单
        HoSnatchOffer hoSnatchOffer=hoSnatchOfferDao.findByOfferIdAndUserId(id,userId);
        if(hoSnatchOffer!=null&&StringUtils.isNotEmpty(hoSnatchOffer.getId())){
            ifSnatch="1";
        }

        jsonResult.getData().put("ifSnatch",ifSnatch);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 网红抢单记录
     * @param params
     * @return
     */
    public JsonResult starSnatchList(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        Integer pageNumber=Integer.parseInt(params.get("pageNumber"));
        Integer pageSize=Integer.parseInt(params.get("pageSize"));
        String userId=params.get("userId");
        String type=params.get("type");
        ParamsUtil.checkParamIfNull(params,new String[]{"pageSize","pageNumber","type","userId"});

        PageHelper.startPage(pageNumber,pageSize,false);
        List<HoStarSnatchOfferRepo> offerList=hoOffersDao.starSnatchList(userId,type);
        Page<HoStarSnatchOfferRepo> page=new Page<>(pageNumber,pageSize,offerList);

        jsonResult.getData().put("pageData",page);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 发布需求
     * @param params
     * @return
     */
    public JsonResult relase(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String plateFormId=params.get("plateFormId");
        String fansNum=params.get("fansNum");
        String shopPlateForm=params.get("shopPlateForm");
        String templateId=params.get("templateId");
        String price=params.get("price");
        String remarks=params.get("remarks");
        String tags=params.get("tags");
        String pics=params.get("pics");
        String userId=params.get("userId");

        ParamsUtil.checkParamIfNull(params,new String[]{});

        HoUserBasic hoUserBasic=hoUserBasicDao.selectByPrimaryKey(userId);
        if(hoUserBasic==null||!hoUserBasic.getUserType().equals("2")){
            jsonResult.globalError("当前用户不可发布需求");
            return jsonResult;
        }
        if(!hoUserBasic.getIfApproved().equals("1")){
            jsonResult.globalError("请先申请认证");
            return jsonResult;
        }

        HoOfferTemplate hoOfferTemplate=hoOfferTemplateDao.selectByPrimaryKey(templateId);

        //插入 HoOffers
        HoOffers hoOffers=new HoOffers();
        hoOffers.setUserPlate(plateFormId);
        hoOffers.setUserId(userId);
        hoOffers.setTitle(hoOfferTemplate.getTitle());
        hoOffers.setStatus("CR");
        hoOffers.setShopPlate(shopPlateForm);
        hoOffers.setRemarks(remarks);
        hoOffers.setPrice(Integer.parseInt(price));
        hoOffers.setOfferTemplateId(templateId);
        hoOffers.setImgs(pics);
        hoOffers.setFansNum(Integer.parseInt(fansNum));
        hoOffers.preInsert();
        hoOffersDao.insert(hoOffers);

        //插入 HoOfferTag
        if(StringUtils.isNotEmpty(tags)){
            for(String tag:tags.split(",")){
                HoOfferTag hoOfferTag=new HoOfferTag();
                hoOfferTag.setDictId(tag);
                hoOfferTag.setOfferId(hoOffers.getId());
                hoOfferTag.preInsert();
                hoOfferTagDao.insert(hoOfferTag);
            }
        }

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 商家查看自己需求列表
     * @param params
     * @return
     */
    public JsonResult sellerOfferList(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        Integer pageNumber=Integer.parseInt(params.get("pageNumber"));
        Integer pageSize=Integer.parseInt(params.get("pageSize"));
        String userId=params.get("userId");
        String type=params.get("type");
        ParamsUtil.checkParamIfNull(params,new String[]{"pageSize","pageNumber","type","userId"});


        PageHelper.startPage(pageNumber,pageSize,false);
        List<HoSellerOfferListRepo> offerListRepos=hoOffersDao.sellerOfferList(userId,type);
        //抢单人数和抢单网红ID
        if(type.equals("AP")&&!CollectionUtils.isEmpty(offerListRepos)){
            for(HoSellerOfferListRepo hoSellerOfferListRepo:offerListRepos){
                List<HoSnatchOffer> snatchOfferList=hoSnatchOfferDao.findByOfferId(hoSellerOfferListRepo.getId());
                if(CollectionUtils.isEmpty(snatchOfferList)){
                    hoSellerOfferListRepo.setSnatchNums("0");
                }else {
                    hoSellerOfferListRepo.setSnatchNums(snatchOfferList.size()+"");
                    for(HoSnatchOffer snatchOffer:snatchOfferList){
                        hoSellerOfferListRepo.getStarIds().add(snatchOffer.getUserId());
                    }
                }
            }
        }
        //锁单网红基本信息
        if(type.equals("LK")&&!CollectionUtils.isEmpty(offerListRepos)){
            for(HoSellerOfferListRepo hoSellerOfferListRepo:offerListRepos){
                List<HoSnatchOffer> snatchOfferList=hoSnatchOfferDao.findByOfferId(hoSellerOfferListRepo.getId());
                if(!CollectionUtils.isEmpty(snatchOfferList)){
                    hoSellerOfferListRepo.setSnatchNums(snatchOfferList.size()+"");
                    for(HoSnatchOffer snatchOffer:snatchOfferList){
                        if(snatchOffer.getIfSelect().equals("1")){
                            hoSellerOfferListRepo.getStarIds().add(snatchOffer.getUserId());
                        }
                    }
                }
            }
        }

        Page<HoSellerOfferListRepo> page=new Page<>(pageNumber,pageSize,offerListRepos);
        jsonResult.getData().put("pageData",page);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 申请退款
     * @param params
     * @return
     */
    public JsonResult applyRefund(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String offerId=params.get("offerId");
        String userId=params.get("userId");
        String openid=params.get("openid");

        ParamsUtil.checkParamIfNull(params,new String[]{"openid","userId","offerId"});

        //校验当前订单信息是否可以退款
        HoOffers hoOffers=hoOffersDao.selectByPrimaryKey(offerId);
        if(hoOffers==null||!hoOffers.getUserId().equals(userId)){
            jsonResult.globalError("当前订单信息有误");
            return jsonResult;
        }
        HoUserBasic hoUserBasic=hoUserBasicDao.selectByPrimaryKey(userId);
        if(hoUserBasic==null||!hoUserBasic.getOpenId().equals(openid)){
            jsonResult.globalError("当前用户信息有误");
            return jsonResult;
        }
        if(!hoOffers.getStatus().equals("PY")){
            jsonResult.globalError("当前订单不能申请退款");
            return jsonResult;
        }

        //获取交易流水
        HoPayFlow hoPayFlow=hoPayFlowDao.findByOfferIdAndType(offerId,"PY");
        if(hoPayFlow==null){
            jsonResult.globalError("当前交易流水不存在");
            return jsonResult;
        }

        hoOffers.setStatus("RA");
        hoOffers.setUpdateDate(new Date());
        hoOffersDao.updateByPrimaryKeySelective(hoOffers);

        HoApplyRefund applyRefund=new HoApplyRefund();
        applyRefund.setOfferId(offerId);
        applyRefund.setUserId(userId);
        applyRefund.setReason(null);

 //       String outRefundNo= OutTradeNoUtil.outTradeNo("2");

        //直接退款
//        String tradeResult=hoWxPayService.wechatPayRefundForApplet(hoPayFlow.getOutTradeNo(),outRefundNo,String.valueOf(hoPayFlow.getTotalFee()),String.valueOf(hoPayFlow.getTotalFee()));
//        if(tradeResult.equals("SUCCESS")){
//            hoOffers.setStatus("RA");
//            hoOffers.setUpdateDate(new Date());
//            hoOffersDao.updateByPrimaryKeySelective(hoOffers);
//            HoPayFlow hoPayFlow2=hoPayFlowDao.findByOfferIdAndType(offerId,"RA");
//            if(hoPayFlow2==null){
//                hoPayFlow2=new HoPayFlow();
//                //插入交易流水
//                hoPayFlow2.setStatus("1");
//                hoPayFlow2.setOutTradeNo(outRefundNo);
//                hoPayFlow2.setOfferId(offerId);
//                hoPayFlow2.setTransType("RA");
//                hoPayFlow2.setTotalFee(hoPayFlow.getTotalFee());
//                hoPayFlow2.setUserId(hoPayFlow.getUserId());
//                hoPayFlow2.preInsert();
//                hoPayFlowDao.insert(hoPayFlow2);
//            }
//        }else {
//            jsonResult.globalError("退款失败");
//            return jsonResult;
//        }

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 商家确认订单完成
     * @param params
     * @return
     */
    public JsonResult confirmFN(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String offerId=params.get("offerId");
        String userId=params.get("userId");
        String openid=params.get("openid");

        ParamsUtil.checkParamIfNull(params,new String[]{"openid","userId","offerId"});
        HoOffers hoOffers=hoOffersDao.selectByPrimaryKey(offerId);
        if(hoOffers==null||!hoOffers.getUserId().equals(userId)){
            jsonResult.globalError("当前订单信息有误");
            return jsonResult;
        }
        HoUserBasic hoUserBasic=hoUserBasicDao.selectByPrimaryKey(userId);
        if(hoUserBasic==null||!hoUserBasic.getOpenId().equals(openid)){
            jsonResult.globalError("当前用户信息有误");
            return jsonResult;
        }
        if(!hoOffers.getStatus().equals("LK")){
            jsonResult.globalError("当前订单状态有误");
            return jsonResult;
        }

        //获取网红ID
        HoSnatchOffer hoSnatchOffer=hoSnatchOfferDao.findByOfferIdSelect(offerId);
        if(hoSnatchOffer==null){
            jsonResult.globalError("当前订单未锁定网红");
            return jsonResult;
        }

        //更新需求状态
        hoOffers.setStatus("FN");
        hoOffers.setUpdateDate(new Date());
        hoOffersDao.updateByPrimaryKeySelective(hoOffers);

        //插入网红账户变动记录
        HoAccountCharge hoAccountCharge=new HoAccountCharge();
        hoAccountCharge.setOfferId(offerId);
        hoAccountCharge.setUserId(hoSnatchOffer.getUserId());
        hoAccountCharge.setTotalFee(new BigDecimal(hoOffers.getPrice()));
        hoAccountCharge.setChargeType("SR");
        hoAccountCharge.setChargeStatus("1");
        hoAccountChargeDao.insert(hoAccountCharge);

        jsonResult.globalSuccess();
        return jsonResult;
    }

    /**
     * 商家锁单
     * @param params
     * @return
     */
    public JsonResult lock(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String offerId=params.get("offerId");
        String userId=params.get("userId");

        ParamsUtil.checkParamIfNull(params,new String[]{"userId","offerId"});

        HoOffers hoOffers=hoOffersDao.selectByPrimaryKey(offerId);
        if(hoOffers==null||!hoOffers.getStatus().equals("AP")){
            jsonResult.globalError("当前订单信息有误");
            return jsonResult;
        }

        boolean ifExist=false;
        //遍历抢单用户
        List<HoSnatchOffer> snatchOfferList= hoSnatchOfferDao.findByOfferId(offerId);
        if(!CollectionUtils.isEmpty(snatchOfferList)){
            for(HoSnatchOffer snatchOffer:snatchOfferList){
                if(snatchOffer.getUserId().equals(userId)){
                    snatchOffer.setIfSelect("1");
                    snatchOffer.setUpdateDate(new Date());
                    hoSnatchOfferDao.updateByPrimaryKeySelective(snatchOffer);
                    ifExist=true;
                }
            }
        }else {
            jsonResult.globalError("当前订单无人抢单");
            return jsonResult;
        }
        //确认抢单用户是否存在
        if(ifExist==false){
            jsonResult.globalError("锁单用户没有参与抢单");
            return jsonResult;
        }

        jsonResult.globalSuccess();
        return jsonResult;
    }
}
