package com.hone.pc.backend.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hone.dao.*;
import com.hone.entity.HoUserBasic;
import com.hone.entity.HoUserSeller;
import com.hone.entity.HoUserStar;
import com.hone.entity.HoUserTag;
import com.hone.pc.backend.repo.SellerUserInfoRepo;
import com.hone.pc.backend.repo.SellerUserListRepo;
import com.hone.pc.backend.repo.StarUserInfoRepo;
import com.hone.pc.backend.repo.StarUserListRepo;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.Page;
import com.hone.system.utils.ParamsUtil;
import com.hone.system.utils.wxtemplate.TemplateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/6/13.
 */

@Service
@Transactional
public class UserBasicService {

    @Autowired
    private HoUserBasicDao hoUserBasicDao;
    @Autowired
    private HoUserStarDao hoUserStarDao;
    @Autowired
    private HoUserSellerDao hoUserSellerDao;
    @Autowired
    private HoUserTagDao hoUserTagDao;
    @Autowired
    private HoDictDao hoDictDao;
    @Autowired
    private TemplateUtils templateUtils;

    /**
     * 网红列表
     * @param params
     * @return
     */
    public JsonResult starList(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String pageNumber=params.get("pageNumber");
        String pageSize=params.get("pageSize");
        String platName=params.get("platName");
        String sex=params.get("sex");
        String wxName=params.get("wxName");
        String orderBy=params.get("orderBy");

        ParamsUtil.checkParamIfNull(params,new String[]{"pageSize","pageNumber"});

        if(StringUtils.isEmpty(orderBy)){
            orderBy="createDate desc";
        }

        com.github.pagehelper.Page pageInfo= PageHelper.startPage(Integer.parseInt(pageNumber),Integer.parseInt(pageSize),true);
        List<StarUserListRepo> starUserList=hoUserStarDao.listByStarBackend(platName,sex,wxName,orderBy);
        Page<StarUserListRepo> page=new Page<>(pageInfo.toPageInfo());

        jsonResult.getData().put("pageData",page);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 删除用户
     * @param params
     * @return
     */
    public JsonResult delUser(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String appletUserId=params.get("appletUserId");
        ParamsUtil.checkParamIfNull(params,new String[]{"appletUserId"});

        HoUserBasic hoUserBasic=hoUserBasicDao.selectByPrimaryKey(appletUserId);

        if(hoUserBasic==null||hoUserBasic.getEnableFlag().equals("0")){
            jsonResult.globalError("用户不存在或已删除");
            return jsonResult;
        }

        hoUserBasic.setEnableFlag("0");
        hoUserBasicDao.updateByPrimaryKeySelective(hoUserBasic);

        if(hoUserBasic.getUserType().equals("1")){
            HoUserStar hoUserStar=hoUserStarDao.findUniqueByProperty("user_id",appletUserId);
            hoUserStar.setEnableFlag("0");
            hoUserStarDao.updateByPrimaryKeySelective(hoUserStar);
        }else if(hoUserBasic.getUserType().equals("2")){
            HoUserSeller hoUserSeller=hoUserSellerDao.findUniqueByProperty("user_id",appletUserId);
            hoUserSeller.setEnableFlag("0");
            hoUserSellerDao.updateByPrimaryKeySelective(hoUserSeller);
        }


        jsonResult.globalSuccess();
        return jsonResult;
    }

    /**
     * 网红详情
     * @param params
     * @return
     */
    public JsonResult starInfo(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String appletUserId=params.get("appletUserId");
        ParamsUtil.checkParamIfNull(params,new String[]{"appletUserId"});

        StarUserInfoRepo starUserInfoRepo=hoUserStarDao.starInfoBackend(appletUserId);

        if(starUserInfoRepo==null){
            jsonResult.globalError("用户不存在或已删除");
            return jsonResult;
        }

        jsonResult.getData().put("userInfo",starUserInfoRepo);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 网红待审核列表
     * @param params
     * @return
     */
    public JsonResult starApproveList(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String pageNumber=params.get("pageNumber");
        String pageSize=params.get("pageSize");

        ParamsUtil.checkParamIfNull(params,new String[]{"pageSize","pageNumber"});

        com.github.pagehelper.Page pageInfo= PageHelper.startPage(Integer.parseInt(pageNumber),Integer.parseInt(pageSize),true);
        List<StarUserInfoRepo> userInfoRepoList= hoUserStarDao.starApproveList();
        Page<StarUserListRepo> page=new Page<>(pageInfo.toPageInfo());

        jsonResult.getData().put("pageData",page);
        jsonResult.globalSuccess();
        return jsonResult;
    }

    /**
     * 网红审核操作
     * @param params
     * @return
     */
    public JsonResult starApproveOperate(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String appletUserId=params.get("appletUserId");
        String ifPass=params.get("ifPass");
        ParamsUtil.checkParamIfNull(params,new String[]{"ifPass","appletUserId"});

        HoUserBasic hoUserBasic=hoUserBasicDao.selectByPrimaryKey(appletUserId);

        if(hoUserBasic==null||hoUserBasic.getEnableFlag().equals("0")){
            jsonResult.globalError("用户不存在或已删除");
            return jsonResult;
        }

        if(ifPass.equals("pass")){
            hoUserBasic.setIfApproved("1");
            hoUserBasicDao.updateByPrimaryKeySelective(hoUserBasic);
        }else if(ifPass.equals("nopass")){
            //删除扩展信息
            HoUserStar hoUserStar=hoUserStarDao.findUniqueByProperty("user_id",appletUserId);
            hoUserStarDao.delete(hoUserStar);

            //删除标签
            HoUserTag hoUserTag=new HoUserTag();
            hoUserTag.setUserId(appletUserId);
            hoUserTagDao.deleteByExample(hoUserTag);

            //还原hoUserBasic基本信息
            hoUserBasic.setUserType("0");
            hoUserBasic.setIfApproved("-1");
            hoUserBasicDao.updateByPrimaryKeySelective(hoUserBasic);

        }

        //发送模板消息
        Map<String,String> templateMap=new HashMap<>();
        templateMap.put("type","2");
        templateMap.put("title","网红认证");
        templateMap.put("openId",hoUserBasic.getOpenId());
        templateMap.put("result",ifPass.equals("pass")?"审核通过!开启红腕之旅":"审核驳回，点击查看详情");
        templateUtils.sendMessage(templateMap);

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 商家待审核列表
     * @param params
     * @return
     */
    public JsonResult sellerApproveList(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String pageNumber=params.get("pageNumber");
        String pageSize=params.get("pageSize");

        ParamsUtil.checkParamIfNull(params,new String[]{"pageSize","pageNumber"});

        com.github.pagehelper.Page pageInfo= PageHelper.startPage(Integer.parseInt(pageNumber),Integer.parseInt(pageSize),true);
        List<SellerUserInfoRepo> userInfoRepoList=hoUserSellerDao.sellerApproveList();
        userInfoRepoList=pageInfo.toPageInfo().getList();
        if(!CollectionUtils.isEmpty(userInfoRepoList)){
            String str="";
            for(SellerUserInfoRepo userInfo:userInfoRepoList){
                str=hoDictDao.findIndustryStr(userInfo.getIndustryId());
                userInfo.setIndustry(str);
            }
        }

        Page<StarUserListRepo> page=new Page<>(pageInfo.toPageInfo());
        jsonResult.getData().put("pageData",page);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 商家审核操作
     * @param params
     * @return
     */
    public JsonResult sellerApproveOperate(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String appletUserId=params.get("appletUserId");
        String ifPass=params.get("ifPass");
        ParamsUtil.checkParamIfNull(params,new String[]{"ifPass","appletUserId"});

        HoUserBasic hoUserBasic=hoUserBasicDao.selectByPrimaryKey(appletUserId);

        if(hoUserBasic==null||hoUserBasic.getEnableFlag().equals("0")){
            jsonResult.globalError("用户不存在或已删除");
            return jsonResult;
        }

        if(ifPass.equals("pass")){
            hoUserBasic.setIfApproved("1");
            hoUserBasicDao.updateByPrimaryKeySelective(hoUserBasic);
        }else if(ifPass.equals("nopass")){
            //还原hoUserBasic基本信息
            hoUserBasic.setUserType("0");
            hoUserBasic.setIfApproved("-1");
            hoUserBasicDao.updateByPrimaryKeySelective(hoUserBasic);
        }


        //发送模板消息
        Map<String,String> templateMap=new HashMap<>();
        templateMap.put("type","2");
        templateMap.put("title","商户认证");
        templateMap.put("openId",hoUserBasic.getOpenId());
        templateMap.put("result",ifPass.equals("pass")?"审核通过!开启红腕之旅":"审核驳回，点击查看详情");
        templateUtils.sendMessage(templateMap);


        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 商家列表
     * @param params
     * @return
     * @throws Exception
     */
    public JsonResult sellerList(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String pageNumber=params.get("pageNumber");
        String pageSize=params.get("pageSize");
        String industry=params.get("industry");
        String sex=params.get("sex");
        String wxName=params.get("wxName");
        String orderBy=params.get("orderBy");

        ParamsUtil.checkParamIfNull(params,new String[]{"pageSize","pageNumber"});

        if(StringUtils.isEmpty(orderBy)){
            orderBy="createDate desc";
        }

        com.github.pagehelper.Page pageInfo= PageHelper.startPage(Integer.parseInt(pageNumber),Integer.parseInt(pageSize),true);
        List<SellerUserListRepo> userListRepos=hoUserSellerDao.listBySellerBackend(industry,sex,wxName,orderBy);
        Page<SellerUserListRepo> page=new Page<>(pageInfo.toPageInfo());

        jsonResult.getData().put("pageData",page);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 商家详情
     * @param params
     * @return
     */
    public JsonResult sellerInfo(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String appletUserId=params.get("appletUserId");
        ParamsUtil.checkParamIfNull(params,new String[]{"appletUserId"});

       SellerUserInfoRepo userInfoRepo=hoUserSellerDao.sellerInfoBackend(appletUserId);

        if(userInfoRepo==null){
            jsonResult.globalError("用户不存在或已删除");
            return jsonResult;
        }

        jsonResult.getData().put("userInfo",userInfoRepo);
        jsonResult.globalSuccess();
        return jsonResult;
    }
}
