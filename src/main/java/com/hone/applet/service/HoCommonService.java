package com.hone.applet.service;

import com.hone.dao.HoServiceTemplateDao;
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
}
