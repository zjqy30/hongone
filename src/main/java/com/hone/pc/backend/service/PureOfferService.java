package com.hone.pc.backend.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hone.dao.*;
import com.hone.entity.HoBanners;
import com.hone.entity.HoFrontMessage;
import com.hone.entity.HoPureOffer;
import com.hone.entity.HoUserBasic;
import com.hone.pc.backend.repo.SnatchPureOfferListRepo;
import com.hone.pc.backend.repo.StarUserListRepo;
import com.hone.pc.web.repo.PureOfferListRepo;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.Page;
import com.hone.system.utils.ParamsUtil;
import com.hone.system.utils.wxtemplate.TemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
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
    @Autowired
    private HoUserBasicDao hoUserBasicDao;
    @Autowired
    private TemplateUtils templateUtils;
    @Autowired
    private HoBackendMessageDao hoBackendMessageDao;
    @Autowired
    private HoFrontMessageDao  hoFrontMessageDao;
    @Autowired
    private HoSnatchPureOfferDao hoSnatchPureOfferDao;

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
        String title=params.get("title");
        ParamsUtil.checkParamIfNull(params,new String[]{"status","pageNumber","pageSize"});

        com.github.pagehelper.Page pageInfo=PageHelper.startPage(Integer.parseInt(pageNumber),Integer.parseInt(pageSize),true);
        List<PureOfferListRepo> pureOfferListRepos=hoPureOfferDao.listForBackend(title,status);

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


    /**
     * 纯佣订单审核
     * @param params
     * @return
     */
    public JsonResult approveOperate(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String id=params.get("id");
        String ifPass=params.get("ifPass");
        ParamsUtil.checkParamIfNull(params,new String[]{"ifPass","id"});
        HoPureOffer pureOffer=hoPureOfferDao.selectByPrimaryKey(id);

        if(pureOffer==null||!pureOffer.getStatus().equals("PY")){
            jsonResult.globalError("订单信息异常");
            return jsonResult;
        }

        if(ifPass.equals("pass")){
            pureOffer.setStatus("AP");
            //删除对应的 object 消息
            hoBackendMessageDao.deleteByObjectId(pureOffer.getId());

            //添加 ho_front_message 记录
            HoFrontMessage hoFrontMessage=new HoFrontMessage();
            hoFrontMessage.setContent("网页端订单大厅有新的订单");
            hoFrontMessage.setObjectId(pureOffer.getId());
            hoFrontMessage.setType("4");
            hoFrontMessage.preInsert();
            hoFrontMessageDao.insert(hoFrontMessage);
        }else if(ifPass.equals("nopas")){
            pureOffer.setStatus("NAP");
        }

        pureOffer.setUpdateDate(new Date());
        hoPureOfferDao.updateByPrimaryKeySelective(pureOffer);

        //发送模板消息
        HoUserBasic hoUserBasic=hoUserBasicDao.selectByPrimaryKey(pureOffer.getUserId());
        Map<String,String> templateMap=new HashMap<>();
        templateMap.put("type","2");
        templateMap.put("title","纯佣订单审核");
        templateMap.put("openId",hoUserBasic.getOpenId());
        templateMap.put("result",ifPass.equals("pass")?"审核通过!开启红腕之旅":"审核驳回,详情联系客服");
        templateUtils.sendMessage(templateMap);

        jsonResult.globalSuccess();
        return jsonResult;
    }

    /**
     * 已抢单网红列表
     * @param params
     * @return
     */
    public JsonResult snatchList(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String pageNumber = params.get("pageNumber");
        String pageSize = params.get("pageSize");

        ParamsUtil.checkParamIfNull(params,new String[]{"pageSize","pageNumber"});

        com.github.pagehelper.Page pageInfo=PageHelper.startPage(Integer.parseInt(pageNumber),Integer.parseInt(pageSize),true);
        List<SnatchPureOfferListRepo> pureOfferListRepos=hoSnatchPureOfferDao.listForBackend();

        Page<SnatchPureOfferListRepo> page=new Page<>(pageInfo.toPageInfo());

        jsonResult.getData().put("pageData",page);
        jsonResult.globalSuccess();
        return jsonResult;
    }
}
