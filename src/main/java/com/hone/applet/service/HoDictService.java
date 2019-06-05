package com.hone.applet.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hone.dao.HoAccountChargeDao;
import com.hone.dao.HoDictDao;
import com.hone.entity.HoAccountCharge;
import com.hone.entity.HoDict;
import com.hone.system.utils.JsonResult;
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
public class HoDictService {

    @Autowired
    private HoDictDao hoDictDao;


    public JsonResult initData(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String filePath=params.get("filePath");

        BufferedReader bufferedReader=new BufferedReader(new FileReader(filePath));
        String line="";
        while ((line=bufferedReader.readLine())!=null){

            JSONObject jsonObject= (JSONObject) JSONObject.parse(line);
            HoDict hoDict=new HoDict();
            hoDict.setDictType("label");
            hoDict.setDictValue(jsonObject.getString("announ"));
            hoDict.setPid("0");
            hoDict.setDictSort("0");
            hoDict.setId(jsonObject.getString("_id"));
            hoDict.preInsert();
            hoDictDao.insert(hoDict);



        }

        bufferedReader.close();

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 新增字典表数据
     * @param params
     * @return
     */
    public JsonResult save(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String dictType=params.get("dictType");
        String content=params.get("content");


        JSONArray jsonArray=JSONObject.parseArray(content);

        System.out.println(jsonArray.getString(0));

        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            String desc=jsonObject.getString("desc");
            String values=jsonObject.getString("value");
            for(String value:values.split(",")){
                HoDict hoDict=new HoDict();
                hoDict.setDictType(dictType);
                hoDict.setPid("0");
                hoDict.setDictDesc(desc);
                hoDict.setDictValue(value);
                hoDict.preInsert();
                hoDictDao.insert(hoDict);
            }

        }

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 根据类型获取字典表数据
     * @param params
     * @return
     */
    public JsonResult listByType(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String type=params.get("type");
        ParamsUtil.checkParamIfNull(params,new String[]{"type"});
        List<HoDict> dictList=hoDictDao.listByType(type);
        jsonResult.globalSuccess();
        jsonResult.getData().put("dictList",dictList);
        return jsonResult;
    }


}
