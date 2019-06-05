package com.hone.applet.service;

import com.alibaba.fastjson.JSONObject;
import com.hone.dao.HoWxFormidDao;
import com.hone.entity.HoCollect;
import com.hone.entity.HoWxFormid;
import com.hone.system.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.util.Map;

/**
 * Created by Lijia on 2019/5/30.
 */
@Service
@Transactional
public class HoWxFormidService {

    @Autowired
    private HoWxFormidDao hoWxFormidDao;



    public JsonResult initData(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String filePath=params.get("filePath");

        BufferedReader bufferedReader=new BufferedReader(new FileReader(filePath));
        String line="";
        while ((line=bufferedReader.readLine())!=null){

            JSONObject jsonObject= (JSONObject) JSONObject.parse(line);
            HoWxFormid hoWxFormid=new HoWxFormid();
            hoWxFormid.setUserId(jsonObject.getString("userId"));
            hoWxFormid.setOpenId(jsonObject.getString(""));
            hoWxFormid.setFormId(jsonObject.getString("formId"));
            hoWxFormid.setCreateDate(new Date(jsonObject.getLong("createtime")));
            hoWxFormid.setUpdateDate(hoWxFormid.getCreateDate());
            hoWxFormid.setId(jsonObject.getString("_id"));
            hoWxFormid.setEnableFlag("1");
            hoWxFormidDao.insert(hoWxFormid);

            /**
             *
             * select * from ho_wx_formid where user_id not in(select id from ho_user_basic);
             *
             *
             */

        }

        bufferedReader.close();

        jsonResult.globalSuccess();
        return jsonResult;
    }


}
