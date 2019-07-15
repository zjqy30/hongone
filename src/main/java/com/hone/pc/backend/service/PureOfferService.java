package com.hone.pc.backend.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hone.dao.HoBannersDao;
import com.hone.dao.HoPureOfferDao;
import com.hone.entity.HoBanners;
import com.hone.entity.HoPureOffer;
import com.hone.pc.backend.repo.StarUserListRepo;
import com.hone.pc.web.repo.PureOfferListRepo;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.Page;
import com.hone.system.utils.ParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/6/14.
 */

@Service
@Transactional
public class PureOfferService {

    @Autowired
    private HoPureOfferDao hoPureOfferDao;


    /**
     * 纯佣订单列表
     * @param params
     * @return
     */
    public JsonResult list(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String status=params.get("status");
        String pageNumber = params.get("pageNumber");
        String pageSize = params.get("pageSize");
        ParamsUtil.checkParamIfNull(params,new String[]{"status","pageNumber","pageSize"});

        com.github.pagehelper.Page pageInfo=PageHelper.startPage(Integer.parseInt(pageNumber),Integer.parseInt(pageSize),true);
        List<PureOfferListRepo> pureOfferListRepos=hoPureOfferDao.listForBackend(status);

        Page<PureOfferListRepo> page=new Page<>(pageInfo.toPageInfo());

        jsonResult.getData().put("pageData",page);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 纯佣订单结束
     * @param params
     * @return
     */
    public JsonResult finsh(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String id=params.get("id");
        ParamsUtil.checkParamIfNull(params,new String[]{"id"});

        HoPureOffer pureOffer=hoPureOfferDao.selectByPrimaryKey(id);
        if(pureOffer==null||!pureOffer.getStatus().equals("AP")){
            jsonResult.globalError("当前订单状态有误");
            return jsonResult;
        }
        pureOffer.setStatus("FN");
        hoPureOfferDao.updateByPrimaryKeySelective(pureOffer);

        jsonResult.globalSuccess();
        return jsonResult;
    }

    /**
     * 纯佣订单删除
     * @param params
     * @return
     */
    public JsonResult del(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String id=params.get("id");
        ParamsUtil.checkParamIfNull(params,new String[]{"id"});

        HoPureOffer pureOffer=hoPureOfferDao.selectByPrimaryKey(id);
        if(pureOffer==null){
            jsonResult.globalError("订单不存在");
            return jsonResult;
        }
        pureOffer.setEnableFlag("0");
        hoPureOfferDao.updateByPrimaryKeySelective(pureOffer);

        jsonResult.globalSuccess();
        return jsonResult;
    }
}
