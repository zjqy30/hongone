package com.hone.applet.service;

import com.hone.dao.HoDictDao;
import com.hone.dao.HoServiceTemplateDao;
import com.hone.entity.HoDict;
import com.hone.entity.HoServiceTemplate;
import com.hone.system.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/7/9.
 */

@Service
@Transactional
public class HoCommonService {


    @Autowired
    private HoServiceTemplateDao hoServiceTemplateDao;
    @Autowired
    private HoDictDao hoDictDao;

    /**
     * 服务类型列表
     * @param params
     * @return
     */
    public JsonResult serviceList(Map<String, String> params) {
        JsonResult jsonResult=new JsonResult();

        List<HoServiceTemplate> serviceTemplateList=hoServiceTemplateDao.listAll();

        jsonResult.getData().put("serviceTemplateList",serviceTemplateList);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 获取入驻网红数量
     * @param params
     * @return
     */
    public JsonResult fansNums(Map<String, String> params) {
        JsonResult jsonResult=new JsonResult();

        HoDict hoDict=hoDictDao.findUniqueByProperty("dict_type","fansNums");

        jsonResult.getData().put("fansNums",hoDict.getDictValue());
        jsonResult.globalSuccess();
        return jsonResult;
    }
}
