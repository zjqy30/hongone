package com.hone.applet.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hone.applet.repo.HoOfferInfoRepo;
import com.hone.applet.repo.HoOffersListRepo;
import com.hone.dao.HoOfferTagDao;
import com.hone.dao.HoOffersDao;
import com.hone.dao.HoSnatchOfferDao;
import com.hone.entity.HoOffers;
import com.hone.entity.HoSnatchOffer;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.Page;
import com.hone.system.utils.ParamsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
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
        String tag=params.get("tag");
        ParamsUtil.checkParamIfNull(params,new String[]{"pageNumber","pageSize"});

        List<String> tagList= new ArrayList<>();
        if(StringUtils.isNotEmpty(tag)){
            for(String each:tag.split(",")){
                tagList.add(each);
            }
            PageHelper.startPage(pageNumber,pageSize,false);
            List<HoOffersListRepo> hoOffersRepoList= hoOffersDao.listForApiTag(tagList);
            Page<HoOffersListRepo> page=new Page<>(pageNumber,pageSize,hoOffersRepoList);
            jsonResult.getData().put("pageData",page);

        }else {
            PageHelper.startPage(pageNumber,pageSize,false);
            List<HoOffersListRepo> hoOffersRepoList= hoOffersDao.listForApiNoTag();
            Page<HoOffersListRepo> page=new Page<>(pageNumber,pageSize,hoOffersRepoList);
            jsonResult.getData().put("pageData",page);

        }

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



}
