package com.hone.applet.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hone.dao.HoCollectDao;
import com.hone.dao.HoUserBasicDao;
import com.hone.entity.HoAccountCharge;
import com.hone.entity.HoCollect;
import com.hone.entity.HoUserBasic;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.Page;
import com.hone.system.utils.ParamsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/5/30.
 */

@Service
@Transactional
public class HoCollectService {

    @Autowired
    private HoCollectDao hoCollectDao;
    @Autowired
    private HoUserBasicDao hoUserBasicDao;


    /**
     * 添加收藏
     * @param params
     * @return
     * @throws Exception
     */
    public JsonResult edit(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String userId=params.get("userId");
        String objectId=params.get("objectId");

        ParamsUtil.checkParamIfNull(params,new String[]{"objectId","userId"});

        //校验用户信息
        HoUserBasic hoUserBasic=hoUserBasicDao.selectByPrimaryKey(userId);
        HoUserBasic hoUserBasicObject=hoUserBasicDao.selectByPrimaryKey(objectId);
        if(hoUserBasic==null){
            jsonResult.globalError("当前用户不存在");
            return jsonResult;
        }
        if(hoUserBasicObject==null){
            jsonResult.globalError("收藏用户不存在");
            return jsonResult;
        }
        //判断是否收藏
        HoCollect hoCollectQuery=new HoCollect();
        hoCollectQuery.setUserId(userId);
        hoCollectQuery.setObjectId(objectId);
        hoCollectQuery=hoCollectDao.selectOne(hoCollectQuery);
        if(hoCollectQuery!=null&&hoCollectQuery.getId()!=null){
            jsonResult.globalError("不可重复收藏");
            return jsonResult;
        }
        //添加收藏记录
        HoCollect hoCollect=new HoCollect();
        hoCollect.setUserId(userId);
        hoCollect.setObjectId(objectId);
        hoCollect.setCollectType(hoUserBasic.getUserType());
        hoCollect.preInsert();
        hoCollectDao.insert(hoCollect);

        jsonResult.globalSuccess();
        return jsonResult;
    }

    /**
     * 取消收藏
     * @param params
     * @return
     * @throws Exception
     */
    public JsonResult cancel(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();
        String userId=params.get("userId");
        String objectId=params.get("objectId");

        ParamsUtil.checkParamIfNull(params,new String[]{"objectId","userId"});

        //删除收藏记录
        HoCollect hoCollectQuery=new HoCollect();
        hoCollectQuery.setUserId(userId);
        hoCollectQuery.setObjectId(objectId);
        hoCollectDao.delete(hoCollectQuery);

        jsonResult.globalSuccess();
        return jsonResult;
    }


    public JsonResult initData(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String filePath=params.get("filePath");

        BufferedReader bufferedReader=new BufferedReader(new FileReader(filePath));
        String line="";
        while ((line=bufferedReader.readLine())!=null){

            JSONObject jsonObject= (JSONObject) JSONObject.parse(line);

            HoCollect hoCollect=new HoCollect();
            hoCollect.setObjectId(jsonObject.getString("whId"));
            hoCollect.setUserId(jsonObject.getString("userId"));
            hoCollect.setId(jsonObject.getString("_id"));
            hoCollect.setCreateDate(new Date(jsonObject.getLong("createtime")));
            hoCollect.setUpdateDate(hoCollect.getCreateDate());
            hoCollect.setEnableFlag("1");
            hoCollectDao.insert(hoCollect);

            /**
             *
             * select  a.user_id,b.wx_name,a.object_id,c.wx_name  from ho_collect a
             left join ho_user_basic b on a.user_id=b.id
             LEFT JOIN ho_user_basic c on c.id=a.object_id
             *
             *
             */

        }

        bufferedReader.close();

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 查看是否收藏
     * @param params
     * @return
     */
    public JsonResult ifCollect(Map<String, String> params) {
        JsonResult jsonResult=new JsonResult();


        String userId=params.get("userId");
        String objectId=params.get("objectId");

        HoCollect hoCollect=new HoCollect();
        hoCollect.setUserId(userId);
        hoCollect.setObjectId(objectId);

        HoCollect hoCollect2=hoCollectDao.selectOne(hoCollect);
        if(hoCollect2!=null){
            jsonResult.getData().put("ifCollect","1");
        }else {
            jsonResult.getData().put("ifCollect","0");
        }

        jsonResult.globalSuccess();
        return jsonResult;
    }

    /**
     * 收藏列表
     * @param params
     * @return
     */
    public JsonResult list(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        Integer pageNumber=Integer.parseInt(params.get("pageNumber"));
        Integer pageSize=Integer.parseInt(params.get("pageSize"));
        String  userId=params.get("userId");

        ParamsUtil.checkParamIfNull(params,new String[]{"userId","pageSize","pageNumber"});

        PageHelper.startPage(pageNumber,pageSize,false);
        List<HoCollect> collectList=hoCollectDao.listForApi(userId);
        Page<HoCollect> page=new Page<>(pageNumber,pageSize,collectList);
        jsonResult.getData().put("pageData",page);
        jsonResult.globalSuccess();
        return jsonResult;
    }

}
