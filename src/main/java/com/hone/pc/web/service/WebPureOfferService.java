package com.hone.pc.web.service;

import com.github.pagehelper.PageHelper;
import com.hone.dao.*;
import com.hone.entity.*;
import com.hone.pc.web.repo.PureOfferListRepo;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.Page;
import com.hone.system.utils.ParamsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/7/8.
 */

@Service
@Transactional
public class WebPureOfferService {

    @Autowired
    private HoPureOfferDao hoPureOfferDao;
    @Autowired
    private HoPureOfferTagDao hoPureOfferTagDao;
    @Autowired
    private HoUserBasicDao hoUserBasicDao;
    @Autowired
    private HoBackendMessageDao hoBackendMessageDao;
    @Autowired
    private HoSnatchPureOfferDao hoSnatchPureOfferDao;


    /**
     * 发布纯佣订单
     * @param params
     * @return
     */
    public JsonResult create(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String title=params.get("title");
        String productType=params.get("productType");
        String shopLevel=params.get("shopLevel");
        String salesBefore=params.get("salesBefore");
        String starPlate=params.get("starPlate");
        String fansNums=params.get("fansNums");
        String starTag=params.get("starTag");
        String ifSend=params.get("ifSend");
        String profitRatio=params.get("profitRatio");
        String userId=params.get("loginUserId");

        String[] strings= new String[]{"loginUserId","title","productType","shopLevel","salesBefore","fansNums","starTag","ifSend","profitRatio"};
        ParamsUtil.checkParamIfNull(params,strings);

        //校验用户信息
        HoUserBasic userBasic=hoUserBasicDao.selectByPrimaryKey(userId);
        if(userBasic==null||!userBasic.getUserType().equals("2")){
            jsonResult.globalError("当前用户不可发布订单");
            return jsonResult;
        }

        //插入 HoPureOffer
        HoPureOffer pureOffer=new HoPureOffer();
        pureOffer.setFansNums(fansNums);
        pureOffer.setIfSend(ifSend);
        pureOffer.setProductTypeId(productType);
        pureOffer.setProfitRatio(profitRatio);
        pureOffer.setSalesBefore(salesBefore);
        pureOffer.setShopLevel(shopLevel);
        pureOffer.setStarPlate(starPlate);
        pureOffer.setUserId(userId);
        pureOffer.setTitle(title);
        pureOffer.setStatus("PY");
        pureOffer.preInsert();
        hoPureOfferDao.insert(pureOffer);

        //插入 HoPureOfferTag
        if (StringUtils.isNotEmpty(starTag)){
            for(String each:starTag.split(",")){
                HoPureOfferTag offerTag=new HoPureOfferTag();
                offerTag.setPureOfferId(pureOffer.getId());
                offerTag.setDictId(each);
                offerTag.preInsert();
                hoPureOfferTagDao.insert(offerTag);
            }
        }

        //插入后台消息提醒
        HoBackendMessage backendMessage=new HoBackendMessage();
        backendMessage.setContent("有商家提交纯佣订单快去看看");
        backendMessage.setType("6");
        backendMessage.setObjectId(pureOffer.getId());
        backendMessage.preInsert();
        hoBackendMessageDao.insert(backendMessage);

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 纯佣订单列表
     * @param params
     * @return
     */
    public JsonResult list(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        Integer pageNumber=Integer.parseInt(params.get("pageNumber"));
        Integer pageSize=Integer.parseInt(params.get("pageSize"));
        String  productType=params.get("productType");
        String  orderType=params.get("orderType");
        String  dateType=params.get("dateType");
        ParamsUtil.checkParamIfNull(params,new String[]{"pageNumber","pageSize"});


        com.github.pagehelper.Page pageInfo =PageHelper.startPage(pageNumber,pageSize,true);
        List<PureOfferListRepo> pureOfferList=hoPureOfferDao.listForWeb(productType,dateType);

        Page<PureOfferListRepo> page=new Page<>(pageInfo.toPageInfo());

        jsonResult.getData().put("pageData", page);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 个人纯佣订单列表
     * @param params
     * @return
     */
    public JsonResult selfList(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        Integer pageNumber=Integer.parseInt(params.get("pageNumber"));
        Integer pageSize=Integer.parseInt(params.get("pageSize"));
        String userId=params.get("loginUserId");
        String keys=params.get("keys");
        String fansNumsOrderBy=params.get("fansNumsOrderBy");
        String dateOrderBy=params.get("dateOrderBy");
        ParamsUtil.checkParamIfNull(params,new String[]{"pageNumber","pageSize"});

        String orderBy="";
        if(StringUtils.isNotEmpty(fansNumsOrderBy)){
            orderBy=" a.fans_nums "+fansNumsOrderBy;
        }
        if(StringUtils.isNotEmpty(dateOrderBy)){
            if(StringUtils.isEmpty(orderBy)){
                orderBy=orderBy+" a.create_date "+dateOrderBy;
            }else {
                orderBy=orderBy+", a.create_date "+dateOrderBy;
            }
        }
        if(StringUtils.isEmpty(orderBy)){
            orderBy=" a.create_date desc ";
        }
        com.github.pagehelper.Page pageInfo =PageHelper.startPage(pageNumber,pageSize,true);
        List<PureOfferListRepo> pureOfferList=hoPureOfferDao.selfListForWeb(userId,keys,orderBy);

        Page<PureOfferListRepo> page=new Page<>(pageInfo.toPageInfo());

        jsonResult.getData().put("pageData", page);
        jsonResult.globalSuccess();
        return jsonResult;
    }

    /**
     * 纯佣订单删除
     * @param params
     * @return
     */
    public JsonResult del(Map<String, String> params) {
        JsonResult jsonResult=new JsonResult();

        String id=params.get("id");
        HoPureOffer pureOffer=hoPureOfferDao.selectByPrimaryKey(id);
        pureOffer.setEnableFlag("0");
        hoPureOfferDao.updateByPrimaryKeySelective(pureOffer);

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 纯佣订单抢单
     * @param params
     * @return
     */
    public JsonResult snatch(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String loginUserId=params.get("loginUserId");
        String offerId=params.get("offerId");
        ParamsUtil.checkParamIfNull(params,new String[]{"offerId","loginUserId"});

        HoPureOffer pureOffer=hoPureOfferDao.selectByPrimaryKey(offerId);
        if(pureOffer==null||!pureOffer.getStatus().equals("AP")){
            jsonResult.globalError("当前订单不可抢单");
            return jsonResult;
        }

        //用户信息校验
        HoUserBasic hoUserBasic=hoUserBasicDao.selectByPrimaryKey(loginUserId);
        if(hoUserBasic==null||!hoUserBasic.getIfApproved().equals("1")||!hoUserBasic.getUserType().equals("1")){
            jsonResult.globalError("请前往小程序申请网红认证后重试");
            return jsonResult;
        }

        //判断是否已经存在记录
        HoSnatchPureOffer snatchPureOffer= hoSnatchPureOfferDao.findByOfferIdAndUserId(offerId,loginUserId);
        if(snatchPureOffer==null){
            snatchPureOffer=new HoSnatchPureOffer();
            snatchPureOffer.setPureOfferId(offerId);
            snatchPureOffer.setUserId(loginUserId);
            snatchPureOffer.preInsert();
            hoSnatchPureOfferDao.insert(snatchPureOffer);
        }else {
            jsonResult.globalError("你已经抢过该单啦");
            return jsonResult;
        }

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 网红纯佣订单抢单列表
     * @param params
     * @return
     */
    public JsonResult snatchList(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        Integer pageNumber=Integer.parseInt(params.get("pageNumber"));
        Integer pageSize=Integer.parseInt(params.get("pageSize"));
        String userId=params.get("loginUserId");
        String keys=params.get("keys");
        String fansNumsOrderBy=params.get("fansNumsOrderBy");
        String dateOrderBy=params.get("dateOrderBy");
        ParamsUtil.checkParamIfNull(params,new String[]{"pageNumber","pageSize"});

        String orderBy="";
        if(StringUtils.isNotEmpty(fansNumsOrderBy)){
            orderBy=" a.fans_nums "+fansNumsOrderBy;
        }
        if(StringUtils.isNotEmpty(dateOrderBy)){
            if(StringUtils.isEmpty(orderBy)){
                orderBy=orderBy+" a.create_date "+dateOrderBy;
            }else {
                orderBy=orderBy+", a.create_date "+dateOrderBy;
            }
        }
        if(StringUtils.isEmpty(orderBy)){
            orderBy=" a.create_date desc ";
        }
        com.github.pagehelper.Page pageInfo =PageHelper.startPage(pageNumber,pageSize,true);
        List<PureOfferListRepo> pureOfferList=hoPureOfferDao.snatchListForWeb(userId,keys,orderBy);

        Page<PureOfferListRepo> page=new Page<>(pageInfo.toPageInfo());

        jsonResult.getData().put("pageData", page);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 网红删除纯佣订单抢单记录
     * @param params
     * @return
     */
    public JsonResult delSnatch(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String userId=params.get("loginUserId");
        String offerId=params.get("offerId");
        ParamsUtil.checkParamIfNull(params,new String[]{"loginUserId","offerId"});

        HoSnatchPureOffer snatchPureOffer=hoSnatchPureOfferDao.findByOfferIdAndUserId(offerId,userId);
        if(snatchPureOffer!=null){
            snatchPureOffer.setEnableFlag("0");
            hoSnatchPureOfferDao.updateByPrimaryKeySelective(snatchPureOffer);
        }

        jsonResult.globalSuccess();
        return jsonResult;
    }

    /**
     * 查看网红是否抢纯佣单
     * @param params
     * @return
     */
    public JsonResult ifSnatch(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String userId=params.get("loginUserId");
        String offerId=params.get("offerId");
        ParamsUtil.checkParamIfNull(params,new String[]{"loginUserId","offerId"});

        HoSnatchPureOffer snatchPureOffer=hoSnatchPureOfferDao.findByOfferIdAndUserId(offerId,userId);
        if(snatchPureOffer!=null){
            jsonResult.getData().put("ifSnatch","1");
        }else {
            jsonResult.getData().put("ifSnatch","0");
        }

        jsonResult.globalSuccess();
        return jsonResult;
    }
}
