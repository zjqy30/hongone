package com.hone.pc.backend.service;

import com.hone.dao.HoOfferTemplateDao;
import com.hone.entity.HoOfferTemplate;
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
public class OfferTemplateService {

    @Autowired
    private HoOfferTemplateDao hoOfferTemplateDao;


    /**
     * 需求模板列表
     * @param params
     * @return
     */
    public JsonResult list(Map<String, String> params) {
        JsonResult jsonResult = new JsonResult();

        List<HoOfferTemplate> templateList=hoOfferTemplateDao.listAll();

        jsonResult.getData().put("templateList",templateList);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 删除需求模板
     * @param params
     * @return
     */
    public JsonResult del(Map<String, String> params) {
        JsonResult jsonResult = new JsonResult();

        String id=params.get("id");
        HoOfferTemplate hoOfferTemplate=hoOfferTemplateDao.selectByPrimaryKey(id);
        hoOfferTemplate.setEnableFlag("0");
        hoOfferTemplateDao.updateByPrimaryKeySelective(hoOfferTemplate);

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 新建需求模板
     * @param params
     * @return
     */
    public JsonResult create(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String title=params.get("title");
        String infos=params.get("infos");
        String minPrice=params.get("minPrice");
        String maxPrice=params.get("maxPrice");

        ParamsUtil.checkParamIfNull(params,new String[]{"title","infos","minPrice","maxPrice"});

        HoOfferTemplate hoOfferTemplate=new HoOfferTemplate();
        hoOfferTemplate.setTitle(title);
        hoOfferTemplate.setInfos(infos);
        hoOfferTemplate.setMinPrice(minPrice);
        hoOfferTemplate.setMaxPrice(maxPrice);
        hoOfferTemplate.preInsert();
        hoOfferTemplateDao.insert(hoOfferTemplate);

        jsonResult.globalSuccess();
        return jsonResult;
    }
}
