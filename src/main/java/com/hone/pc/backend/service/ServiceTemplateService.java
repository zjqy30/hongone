package com.hone.pc.backend.service;

import com.hone.dao.HoOfferTemplateDao;
import com.hone.dao.HoServiceTemplateDao;
import com.hone.entity.HoOfferTemplate;
import com.hone.entity.HoServiceTemplate;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.ParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/6/15.
 */

@Service
@Transactional
public class ServiceTemplateService {

    @Autowired
    private HoServiceTemplateDao hoServiceTemplateDao;


    /**
     * 服务模板列表
     * @param params
     * @return
     */
    public JsonResult list(Map<String, String> params) {
        JsonResult jsonResult = new JsonResult();

        List<HoServiceTemplate> templateList=hoServiceTemplateDao.listAll();

        jsonResult.getData().put("templateList",templateList);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 删除服务模板
     * @param params
     * @return
     */
    public JsonResult del(Map<String, String> params) {
        JsonResult jsonResult = new JsonResult();

        String id=params.get("id");
        HoServiceTemplate serviceTemplate=hoServiceTemplateDao.selectByPrimaryKey(id);
        serviceTemplate.setEnableFlag("0");
        hoServiceTemplateDao.updateByPrimaryKeySelective(serviceTemplate);

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 新建服务模板
     * @param params
     * @return
     */
    public JsonResult create(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String title=params.get("title");
        String infos=params.get("infos");
        String sort=params.get("sort");

        ParamsUtil.checkParamIfNull(params,new String[]{"title","infos","sort"});

        HoServiceTemplate serviceTemplate=new HoServiceTemplate();
        serviceTemplate.setTitle(title);
        serviceTemplate.setInfos(infos);
        serviceTemplate.setSort(Integer.parseInt(sort));
        serviceTemplate.preInsert();
        hoServiceTemplateDao.insert(serviceTemplate);

        jsonResult.globalSuccess();
        return jsonResult;
    }
}
