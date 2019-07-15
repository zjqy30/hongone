package com.hone.pc.web.service;

import com.github.pagehelper.PageHelper;
import com.hone.dao.HoPureOfferDao;
import com.hone.dao.HoPureOfferTagDao;
import com.hone.dao.HoUserBasicDao;
import com.hone.entity.HoPureOffer;
import com.hone.entity.HoPureOfferTag;
import com.hone.entity.HoUserBasic;
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
        String userId=params.get("userId");

        String[] strings= new String[]{"userId","title","productType","shopLevel","salesBefore","fansNums","starTag","ifSend","profitRatio"};
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
        ParamsUtil.checkParamIfNull(params,new String[]{"pageNumber","pageSize"});

        com.github.pagehelper.Page pageInfo =PageHelper.startPage(pageNumber,pageSize,true);
        List<PureOfferListRepo> pureOfferList=hoPureOfferDao.selfListForWeb(userId,keys);

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
}
