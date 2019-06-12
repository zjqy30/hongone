package com.hone.applet.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hone.applet.repo.HoUserStarListRepo;
import com.hone.applet.repo.HoUserStarSelfInfo;
import com.hone.dao.*;
import com.hone.entity.*;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.Page;
import com.hone.system.utils.ParamsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/5/29.
 */

@Service
@Transactional
public class HoUserBasicService {

    @Autowired
    private HoUserBasicDao hoUserBasicDao;

    @Autowired
    private HoMarketerDao hoMarketerDao;
    @Autowired
    private HoUserStarDao hoUserStarDao;
    @Autowired
    private HoUserTagDao hoUserTagDao;
    @Autowired
    private HoUserSellerDao hoUserSellerDao;
    @Autowired
    private HoDictDao hoDictDao;
    @Autowired
    private HoSmsRecordsDao hoSmsRecordsDao;



    public HoUserBasic findByOpenId(String openid) {
        HoUserBasic hoUserBasic = new HoUserBasic();
        hoUserBasic.setOpenId(openid);
        return hoUserBasicDao.selectOne(hoUserBasic);
    }


    /**
     * 用户登录
     *
     * @param params
     * @throws Exception
     */
    public JsonResult login(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();
        String openid = params.get("openid");
        String wxname = params.get("wxname");
        String gender = params.get("gender");
        String avatarUrl = params.get("avatarUrl");
        String country = params.get("country");
        String inviteCode = params.get("inviteCode");
        ParamsUtil.checkParamIfNull(params, new String[]{"openid", "wxname", "avatarUrl"});


        HoUserBasic hoUserBasic = findByOpenId(openid);
        if (hoUserBasic != null) {
            //更新用户信息
            hoUserBasic.setWxName(wxname);
            hoUserBasic.setAvatarUrl(avatarUrl);
            hoUserBasic.setGender(gender);
            hoUserBasic.setCountry(country);
            hoUserBasic.setUpdateDate(new Date());
            hoUserBasicDao.updateLogin(hoUserBasic);
        } else {
            //插入用户信息
            hoUserBasic = new HoUserBasic();
            hoUserBasic.setWxName(wxname);
            hoUserBasic.setAvatarUrl(avatarUrl);
            hoUserBasic.setGender(gender);
            hoUserBasic.setCountry(country);
            hoUserBasic.setOpenId(openid);
            hoUserBasic.setHasShop("0");
            hoUserBasic.setIfApproved("-1");
            hoUserBasic.setUserType("0");
            //判断是否通过扫描二维码邀请码进入
            if (StringUtils.isNotEmpty(inviteCode)) {
                HoMarketer hoMarketer = new HoMarketer();
                hoMarketer.setUserCode(inviteCode);
                hoMarketer = hoMarketerDao.selectOne(hoMarketer);
                if (hoMarketer.getId() != null) {
                    hoUserBasic.setMarketerId(hoMarketer.getId());
                }
            }
            hoUserBasic.preInsert();
            hoUserBasicDao.insert(hoUserBasic);
        }
        jsonResult.getData().put("userId", hoUserBasic.getId());
        jsonResult.getData().put("openid", hoUserBasic.getOpenId());
        jsonResult.getData().put("userType", hoUserBasic.getUserType());
        jsonResult.getData().put("ifApproved", hoUserBasic.getIfApproved());
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 网红认证
     *
     * @param params
     * @throws Exception
     */
    public JsonResult applyApproved(Map<String, String> params) throws Exception {

        JsonResult jsonResult = new JsonResult();
        //参数校验
        String platFormId = params.get("platFormId");
        String platFormImgs = params.get("platFormImgs");
        String platFormUserId = params.get("platFormUserId");
        String fanNums = params.get("fanNums");
        String age = params.get("age");
        String thumpUpNums = params.get("thumpUpNums");
        String workNums = params.get("workNums");
        String hasShop = params.get("hasShop");
        String abilityIds = params.get("abilityIds");
        String inviteCode=params.get("inviteCode");
        String personalImgs = params.get("personalImgs");
        String idCardPic = params.get("idCardPic");
        String idCardUpPic = params.get("idCardUpPic");
        String idCardDownPic = params.get("idCardDownPic");
        String idCardNumber = params.get("idCardNumber");
        String openid = params.get("openid");
        String userId = params.get("userId");

        String strings[] = new String[]{"userId","platFormImgs", "openid", "platFormId", "platFormUserId", "fanNums", "age", "thumpUpNums", "workNums", "hasShop", "abilityIds", "personalImgs", "idCardPic", "idCardUpPic", "idCardDownPic", "idCardNumber"};
        ParamsUtil.checkParamIfNull(params, strings);

        //查询用户
        HoUserBasic hoUserBasic = findByOpenId(openid);
        if (hoUserBasic == null||!hoUserBasic.getId().equals(userId)) {
            jsonResult.globalError("用户不存在");
            return jsonResult;
        }

        if(Double.valueOf(hoUserBasic.getIfApproved())==1){
            jsonResult.globalError("用户已审核");
            return jsonResult;
        }

        if(Double.valueOf(hoUserBasic.getIfApproved())==0){
            jsonResult.globalError("用户正在审核");
            return jsonResult;
        }

        //保存网红额外信息
        HoUserStar hoUserStar = new HoUserStar();
        hoUserStar.setFansNums(Integer.parseInt(fanNums));
        hoUserStar.setPersonalImgs(personalImgs);
        hoUserStar.setPlatformId(platFormId);
        hoUserStar.setPlatformImgs(platFormImgs);
        hoUserStar.setPlatformUserId(platFormUserId);
        hoUserStar.setThumbUpNums(Integer.parseInt(thumpUpNums));
        hoUserStar.setUserId(hoUserBasic.getId());
        hoUserStar.setWorkNums(Integer.parseInt(workNums));
        hoUserStar.preInsert();
        hoUserStarDao.insert(hoUserStar);

        //更新用户信息
        hoUserBasic.setUserType("1");
        hoUserBasic.setIfApproved("0");
        hoUserBasic.setHasShop(hasShop);
        hoUserBasic.setIdCardPic(idCardPic);
        hoUserBasic.setIdCardDownPic(idCardDownPic);
        hoUserBasic.setIdCardUpPic(idCardUpPic);
        hoUserBasic.setIdCardNumber(idCardNumber);
        hoUserBasic.setAge(Integer.parseInt(age));
        //邀请码
        if (StringUtils.isNotEmpty(inviteCode)) {
            HoMarketer hoMarketer = new HoMarketer();
            hoMarketer.setUserCode(inviteCode);
            hoMarketer = hoMarketerDao.selectOne(hoMarketer);
            if (hoMarketer.getId() != null) {
                hoUserBasic.setMarketerId(hoMarketer.getId());
            }
        }
        hoUserBasicDao.updateByPrimaryKeySelective(hoUserBasic);

        //保存网红技能
        for (String abilityId : abilityIds.split(",")) {
            HoUserTag hoUserTag = new HoUserTag();
            hoUserTag.setUserId(hoUserBasic.getId());
            hoUserTag.setDictId(abilityId);
            hoUserTagDao.insert(hoUserTag);
        }

        jsonResult.globalSuccess();
        return jsonResult;
    }

    public JsonResult initData(Map<String, String> params) throws Exception {

        JsonResult jsonResult = new JsonResult();

        String filePath = params.get("filePath");

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {

            JSONObject jsonObject = (JSONObject) JSONObject.parse(line);
            HoUserBasic hoUserBasic = new HoUserBasic();
            hoUserBasic.setAge(jsonObject.getInteger("age") == null ? 0 : jsonObject.getInteger("age"));
            hoUserBasic.setIdCardNumber(jsonObject.getString("id_number"));
            hoUserBasic.setIdCardUpPic(jsonObject.getString("hand_id_2"));
            hoUserBasic.setIdCardPic(jsonObject.getString("hand_id_1"));
            hoUserBasic.setIdCardDownPic(jsonObject.getString("hand_id_3"));
            hoUserBasic.setHasShop(jsonObject.getString("hasshop") == null ? "0" : jsonObject.getString("hasshop"));
            hoUserBasic.setIfApproved(jsonObject.getString("approved") == null ? "-1" : jsonObject.getString("approved"));
            hoUserBasic.setUserType(jsonObject.getInteger("approve_type").toString());
            hoUserBasic.setMarketerId(jsonObject.getString("yqm"));
            hoUserBasic.setId(jsonObject.getString("_id"));
            hoUserBasic.setOpenId(jsonObject.getString("_openid"));
            hoUserBasic.setPhoneNo(jsonObject.getString("phoneNumber"));
            hoUserBasic.setCountry(jsonObject.getString("user_loc"));
            hoUserBasic.setCreateDate(new Date(jsonObject.getLong("createtime")));
            hoUserBasic.setUpdateDate(hoUserBasic.getCreateDate());
            hoUserBasic.setEnableFlag("1");


            JSONObject userObject = jsonObject.getJSONObject("users");
            hoUserBasic.setAvatarUrl(userObject.getString("avatarUrl"));
            hoUserBasic.setGender(userObject.getString("gender"));
            hoUserBasic.setEncryptedData(userObject.getString("encryptedData"));
            hoUserBasic.setWxName(userObject.getString("nickName"));

            hoUserBasicDao.insert(hoUserBasic);


        }

        bufferedReader.close();

        jsonResult.globalSuccess();
        return jsonResult;
    }


    public JsonResult initDataStar(Map<String, String> params) throws Exception {

        JsonResult jsonResult = new JsonResult();

        String filePath = params.get("filePath");

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {

            JSONObject jsonObject = (JSONObject) JSONObject.parse(line);

            String type = jsonObject.getString("approve_type");
            if (StringUtils.isNotEmpty(type) && type.equals("1.0")) {
                HoUserStar hoUserStar = new HoUserStar();
                hoUserStar.setWorkNums(jsonObject.getInteger("work_num") == null ? 0 : jsonObject.getInteger("work_num"));
                hoUserStar.setUserId(jsonObject.getString("_id"));

                String lnums = jsonObject.getString("like_num");
                if (StringUtils.isEmpty(lnums)) {
                    lnums = "0";
                }
                if (lnums.contains("w")) {
                    lnums = lnums.split("w")[0];
                    lnums = lnums + "0000";
                }
                if (lnums.contains("万")) {
                    lnums = lnums.split("万")[0];
                    lnums = lnums + "0000";
                }
                hoUserStar.setThumbUpNums(Integer.parseInt(lnums));


                hoUserStar.setFansNums(jsonObject.getInteger("bind_fss") == null ? 0 : jsonObject.getInteger("bind_fss"));
                hoUserStar.setPlatformUserId(jsonObject.getString("terrace_id"));
                hoUserStar.setPlatformImgs(jsonObject.getString("terrace_screenshot"));
                hoUserStar.setPlatformId(jsonObject.getString("terrace"));
                JSONArray jsonArray = jsonObject.getJSONArray("whImages");
                String personalImages = "";
                if (jsonArray != null && jsonArray.size() != 0) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        if (i == jsonArray.size() - 1) {
                            personalImages = personalImages + jsonArray.getString(i);
                        } else {
                            personalImages = personalImages + jsonArray.getString(i) + ",";
                        }
                    }
                }
                hoUserStar.setPersonalImgs(personalImages);
                hoUserStar.preInsert();
                hoUserStarDao.insert(hoUserStar);
            }

        }

        bufferedReader.close();

        jsonResult.globalSuccess();
        return jsonResult;
    }


    public JsonResult initDataSell(Map<String, String> params) throws Exception {

        JsonResult jsonResult = new JsonResult();

        String filePath = params.get("filePath");

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {

            JSONObject jsonObject = (JSONObject) JSONObject.parse(line);

            String type = jsonObject.getString("approve_type");
            if (StringUtils.isNotEmpty(type) && type.equals("2.0")) {
                HoUserSeller hoUserSeller = new HoUserSeller();
                hoUserSeller.setUserId(jsonObject.getString("_id"));
                String industry2 = jsonObject.getString("industry2");
                if (StringUtils.isNotEmpty(industry2)) {
                    if (industry2.trim().equals("零食")) {
                        hoUserSeller.setIndustryId("027138bb52c54addbad5675c4506f0e7");
                    } else {
                        System.out.println("id: " + jsonObject.getString("_id"));
                        HoDict hoDict = hoDictDao.selectByTypeAndValue("sellerTag", industry2);
                        if (hoDict != null) {
                            hoUserSeller.setIndustryId(hoDict.getId());
                        } else {
                            String industry1 = jsonObject.getString("industry1");

                            if (StringUtils.isNotEmpty(industry2)) {
                                if (industry1.trim().equals("零食")) {
                                    hoUserSeller.setIndustryId("027138bb52c54addbad5675c4506f0e7");
                                } else {
                                    hoDict = hoDictDao.selectByTypeAndValue("sellerTag", industry1);
                                    if (hoDict != null) {
                                        hoUserSeller.setIndustryId(hoDict.getId());
                                    }
                                }
                            }
                        }
                    }
                }
                hoUserSeller.setBusinessLicense(jsonObject.getString("shop_id"));
                hoUserSeller.preInsert();
                hoUserSellerDao.insert(hoUserSeller);
            }

        }

        bufferedReader.close();

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 获取网红列表
     *
     * @param params
     * @return
     */
    public JsonResult listByStar(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String pageNumber = params.get("pageNumber");
        String pageSize = params.get("pageSize");
        String platType = params.get("platType");
        String tag = params.get("tag");

        ParamsUtil.checkParamIfNull(params, new String[]{"platType", "pageSize", "pageNumber"});

        //查询全部平台
        if (platType.equals("ALL")) {
            platType = "";
        }
        List<String> tagList = new ArrayList<>();
        if (StringUtils.isNotEmpty(tag)) {
            for (String each : tag.split(",")) {
                tagList.add(each);
            }
        }

        PageHelper.startPage(Integer.parseInt(pageNumber), Integer.parseInt(pageSize), false);
        List<HoUserStarListRepo> userStarRepoList = hoUserStarDao.listByStar(platType, tagList);
        Page<HoUserStarListRepo> page = new Page<>(Integer.parseInt(pageNumber), Integer.parseInt(pageSize), userStarRepoList);
        jsonResult.getData().put("pageData", page);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 查看网红用户详情
     *
     * @param params
     * @return
     */
    public JsonResult userStarInfo(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String userId = params.get("userId");
        ParamsUtil.checkParamIfNull(params, new String[]{"userId"});

        HoUserBasic hoUserBasic = hoUserBasicDao.findUniqueByProperty("id", userId);
        if (hoUserBasic == null) {
            jsonResult.globalError("用户不存在");
            return jsonResult;
        }

        //网红
        if (hoUserBasic.getUserType().equals("1")) {
            //网红扩展信息
            HoUserStar hoUserStar = hoUserStarDao.findUniqueByProperty("user_id", userId);
            if (hoUserStar == null) {
                jsonResult.globalError("用户扩展信息不存在");
                return jsonResult;
            }
            //网红平台
            HoDict hoDict = hoDictDao.findUniqueByProperty("id", hoUserStar.getPlatformId());
            hoUserStar.setPlatformName(hoDict.getDictValue());

            //网红标签
            List<HoUserTag> tagList = hoUserTagDao.findListByUserId(userId);
            String tags = new String();
            if (!CollectionUtils.isEmpty(tagList)) {
                for (int i = 0; i < tagList.size(); i++) {
                    if (i == tagList.size() - 1) {
                        tags = tags + tagList.get(i).getDictValue();
                    } else {
                        tags = tags + tagList.get(i).getDictValue() + ",";
                    }
                }
            }
            hoUserStar.setTags(tags);
            jsonResult.getData().put("userExtraInfo", hoUserStar);
        }
        jsonResult.getData().put("userBasicInfo", hoUserBasic);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 网红本人查看资料
     *
     * @param params
     * @return
     */
    public JsonResult userStarSelfInfo(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String userId = params.get("userId");
        String openId = params.get("openId");
        ParamsUtil.checkParamIfNull(params, new String[]{"openId", "userId"});


        HoUserStarSelfInfo hoUserStarSelfInfo = hoUserBasicDao.starSelfInfo(userId, openId);
        if(hoUserStarSelfInfo==null){
            jsonResult.globalError("用户不存在");
            return jsonResult;
        }
        jsonResult.getData().put("hoUserStarSelfInfo", hoUserStarSelfInfo);

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 更新网红本人资料
     *
     * @param params
     * @return
     */
    public JsonResult updateStarSelfInfo(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String platFormId = params.get("platFormId");
        String fansNums = params.get("fansNums");
        String age = params.get("age");
        String thumbUpNums = params.get("thumbUpNums");
        String workNums = params.get("workNums");
        String hasShop = params.get("hasShop");
        String tagIds = params.get("tagIds");
        String personalImgs = params.get("personalImgs");
        String platformImgs = params.get("platformImgs");
        String userId = params.get("userId");
        String openId = params.get("openId");

        ParamsUtil.checkParamIfNull(params, new String[]{"openId", "userId", "platformImgs", "personalImgs", "tagIds", "hasShop", "workNums", "thumbUpNums", "age", "fansNums", "platFormId"});

        //查询 hoUserBasic
        HoUserBasic hoUserBasicQuery=new HoUserBasic();
        hoUserBasicQuery.setId(userId);
        hoUserBasicQuery.setOpenId(openId);
        HoUserBasic hoUserBasic=hoUserBasicDao.selectOne(hoUserBasicQuery);

        if(hoUserBasic==null){
            jsonResult.globalError("用户不存在");
            return jsonResult;
        }

        //更新 hoUserBasic
        hoUserBasic.setAge(Integer.parseInt(age));
        hoUserBasic.setHasShop(hasShop);
        hoUserBasic.setUpdateDate(new Date());
        hoUserBasicDao.updateByPrimaryKeySelective(hoUserBasic);

        //更新 hoUserStar
        HoUserStar hoUserStar=hoUserStarDao.findUniqueByProperty("user_id",userId);
        hoUserStar.setFansNums(Integer.parseInt(fansNums));
        hoUserStar.setPlatformId(platFormId);
        hoUserStar.setThumbUpNums(Integer.parseInt(thumbUpNums));
        hoUserStar.setWorkNums(Integer.parseInt(workNums));
        hoUserStar.setPlatformImgs(personalImgs);
        hoUserStar.setPlatformImgs(platformImgs);
        hoUserStarDao.updateByPrimaryKeySelective(hoUserStar);

        //更新 hoUserTag
        HoUserTag hoUserTag=new HoUserTag();
        hoUserTag.setUserId(userId);
        hoUserTagDao.delete(hoUserTag);
        for(String str:tagIds.split(",")){
            if(StringUtils.isNotEmpty(str)){
                HoUserTag tag=new HoUserTag();
                tag.setUserId(userId);
                tag.setDictId(str);
                tag.preInsert();
                hoUserTagDao.insert(tag);
            }
        }

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 绑定手机号
     * @param params
     * @return
     */
    public JsonResult bindPhone(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String phoneNo=params.get("phoneNo");
        String code=params.get("code");
        String userId=params.get("userId");

        ParamsUtil.checkParamIfNull(params,new String[]{"userId","code","phoneNo"});

        int result=hoSmsRecordsDao.verifyCode(phoneNo,code);
        if(result<0){
            jsonResult.globalError("验证码错误或失效");
            return jsonResult;
        }

        HoUserBasic hoUserBasic=hoUserBasicDao.selectByPrimaryKey(userId);
        if(hoUserBasic==null){
            jsonResult.globalError("用户不存在");
            return jsonResult;
        }

        hoUserBasic.setPhoneNo(phoneNo);
        hoUserBasicDao.updateByPrimaryKeySelective(hoUserBasic);

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 查看是否绑定手机号
     * @param params
     * @return
     */
    public JsonResult ifBindPhone(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String userId=params.get("userId");
        ParamsUtil.checkParamIfNull(params,new String[]{"userId"});
        String ifBindPhone="1";
        HoUserBasic hoUserBasic=hoUserBasicDao.selectByPrimaryKey(userId);
        if(hoUserBasic==null){
            jsonResult.globalError("用户不存在");
            return jsonResult;
        }
        if(StringUtils.isEmpty(hoUserBasic.getPhoneNo())){
            ifBindPhone="0";
        }

        jsonResult.getData().put("ifBindPhone",ifBindPhone);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 商家用户申请认证
     * @param params
     * @return
     */
    public JsonResult sellerApplyApproved(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String idCardNumber=params.get("idCardNumber");
        String inviteCode=params.get("inviteCode");
        String industryId=params.get("industryId");
        String idCardPic = params.get("idCardPic");
        String idCardUpPic = params.get("idCardUpPic");
        String idCardDownPic = params.get("idCardDownPic");
        String businessLicense=params.get("businessLicense");
        String certLicense=params.get("certLicense");
        String userId=params.get("userId");
        String openId=params.get("openid");

        ParamsUtil.checkParamIfNull(params,new String[]{"openid","userId","idCardDownPic","idCardUpPic","idCardPic","industryId","idCardNumber"});

        //查询用户
        HoUserBasic hoUserBasic = findByOpenId(openId);
        if (hoUserBasic == null||!hoUserBasic.getId().equals(userId)) {
            jsonResult.globalError("用户不存在");
            return jsonResult;
        }

        if(Double.valueOf(hoUserBasic.getIfApproved())==1){
            jsonResult.globalError("用户已审核");
            return jsonResult;
        }

        if(Double.valueOf(hoUserBasic.getIfApproved())==0){
            jsonResult.globalError("用户正在审核");
            return jsonResult;
        }

        //更新 hoUserBasic
        hoUserBasic.setUserType("2");
        hoUserBasic.setIfApproved("0");
        hoUserBasic.setIdCardDownPic(idCardDownPic);
        hoUserBasic.setIdCardPic(idCardPic);
        hoUserBasic.setIdCardDownPic(idCardDownPic);
        hoUserBasic.setIdCardUpPic(idCardUpPic);
        hoUserBasic.setIdCardNumber(idCardNumber);
        //邀请码
        if (StringUtils.isNotEmpty(inviteCode)) {
            HoMarketer hoMarketer = new HoMarketer();
            hoMarketer.setUserCode(inviteCode);
            hoMarketer = hoMarketerDao.selectOne(hoMarketer);
            if (hoMarketer.getId() != null) {
                hoUserBasic.setMarketerId(hoMarketer.getId());
            }
        }
        hoUserBasicDao.updateByPrimaryKeySelective(hoUserBasic);

        //插入 HoUserSeller
        HoUserSeller hoUserSeller=new HoUserSeller();
        hoUserSeller.setIndustryId(industryId);
        hoUserSeller.setBusinessLicense(businessLicense);
        hoUserSeller.setUserId(userId);
        hoUserSeller.setCertLicense(certLicense);
        hoUserSeller.preInsert();
        hoUserSellerDao.insert(hoUserSeller);

        jsonResult.globalSuccess();
        return jsonResult;
    }
}
