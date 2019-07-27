package com.hone.pc.backend.service;

import com.github.pagehelper.PageHelper;
import com.hone.dao.HoMarketerDao;
import com.hone.dao.HoUserBasicDao;
import com.hone.dao.HoUserSellerDao;
import com.hone.dao.HoUserStarDao;
import com.hone.entity.HoMarketer;
import com.hone.pc.backend.repo.InviteUserListRepo;
import com.hone.pc.backend.repo.SellerUserListRepo;
import com.hone.system.utils.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/6/14.
 */

@Service
@Transactional
public class MarketerService {

    @Autowired
    private HoMarketerDao hoMarketerDao;

    @Autowired
    private AccessTokenUtils accessTokenUtils;
    @Autowired
    private SimpleUploadFileCos simpleUploadFileCos;

    @Autowired
    private HoUserBasicDao hoUserBasicDao;
    @Autowired
    private HoUserStarDao hoUserStarDao;
    @Autowired
    private HoUserSellerDao hoUserSellerDao;

    /**
     * 销售人员列表
     * @param params
     * @return
     */
    public JsonResult list(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String pageNumber=params.get("pageNumber");
        String pageSize=params.get("pageSize");

        ParamsUtil.checkParamIfNull(params,new String[]{"pageSize","pageNumber"});

        com.github.pagehelper.Page pageInfo=PageHelper.startPage(Integer.parseInt(pageNumber),Integer.parseInt(pageSize),true);
        List<HoMarketer> marketerList=hoMarketerDao.findListBackend();
        marketerList=pageInfo.toPageInfo().getList();
        if(!CollectionUtils.isEmpty(marketerList)){
            for (HoMarketer marketer:marketerList){
                marketer.setInviteNums(hoMarketerDao.countInviteNums(marketer.getId()));
            }
        }
        Page<HoMarketer> page=new Page<>(pageInfo.toPageInfo());
        jsonResult.getData().put("pageData",page);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 删除销售人员
     * @param params
     * @return
     */
    public JsonResult del(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String marketerId=params.get("marketerId");
        ParamsUtil.checkParamIfNull(params,new String[]{"marketerId"});

        HoMarketer hoMarketer=hoMarketerDao.selectByPrimaryKey(marketerId);
        if(hoMarketer==null||hoMarketer.getEnableFlag().equals("0")){
            jsonResult.globalError("销售人员不存在");
            return jsonResult;
        }
        hoMarketer.setEnableFlag("0");
        hoMarketerDao.updateByPrimaryKeySelective(hoMarketer);

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 创建销售人员
     * @param params
     * @param request
     * @return
     */
    public JsonResult create(Map<String, String> params, HttpServletRequest request) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String name=params.get("name");
        ParamsUtil.checkParamIfNull(params,new String[]{"name"});

//        //数字验证码4位
//        String verifyCode = RandomStringUtils.random(4, "1234567890");
//        while (hoMarketerDao.findUniqueByProperty("user_code",verifyCode)!=null){
//            verifyCode = RandomStringUtils.random(4, "1234567890");
//        }
//
//        //插入 hoMarketer
//        HoMarketer hoMarketer=new HoMarketer();
//        hoMarketer.setMarketName(name);
//        hoMarketer.setUserCode(verifyCode);
//        hoMarketer.setInviteNums("0");
//        hoMarketer.preInsert();
//        hoMarketerDao.insert(hoMarketer);

        //获取 accessToken
        String accessToken=accessTokenUtils.getAccessToken();
        //生成二维码图片
        String fileName=WxAppletQrCode.createQR(accessToken,name);

        //上传二维码图片到腾讯云COS
        File file=new File(fileName);
        String imgUrl= simpleUploadFileCos.SimpleUploadFileFromStream(new FileInputStream(file), file.getName(),file.length());
//        //更新 hoMarketer
//        hoMarketer.setQrcodeUrl(imgUrl);
//        hoMarketerDao.updateByPrimaryKeySelective(hoMarketer);
//
//        jsonResult.getData().put("hoMarketer",hoMarketer);
        jsonResult.getData().put("imgUrl",imgUrl);
        jsonResult.globalSuccess();
        return jsonResult;
    }

    /**
     * 销售人员邀请列表
     * @param params
     * @param request
     * @return
     */
    public JsonResult inviteList(Map<String, String> params, HttpServletRequest request) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String marketerId=params.get("marketerId");
        String pageNumber=params.get("pageNumber");
        String pageSize=params.get("pageSize");

        ParamsUtil.checkParamIfNull(params,new String[]{"pageNumber","pageSize","marketerId"});

        com.github.pagehelper.Page pageInfo=PageHelper.startPage(Integer.parseInt(pageNumber),Integer.parseInt(pageSize),true);
        List<InviteUserListRepo> inviteUserList=hoUserBasicDao.inviteUserListBackend(marketerId);
        Page<InviteUserListRepo> page=new Page<>(pageInfo.toPageInfo());
        //遍历结果集赋值 平台或行业类型
        inviteUserList=page.getList();
        if(!CollectionUtils.isEmpty(inviteUserList)){
            for(InviteUserListRepo inviteUser:inviteUserList){
                if(inviteUser.getType().equals("1")){
                    String platName=hoUserStarDao.findPlatName(inviteUser.getId());
                    inviteUser.setPlatName(platName);
                }else if(inviteUser.getType().equals("2")){
                    String industry=hoUserSellerDao.findIndustry(inviteUser.getId());
                    inviteUser.setIndustry(industry);
                }
            }
        }

        jsonResult.getData().put("pageData",page);
        jsonResult.globalSuccess();
        return jsonResult;
    }
}
