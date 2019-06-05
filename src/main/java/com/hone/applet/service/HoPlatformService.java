package com.hone.applet.service;

import com.github.pagehelper.PageHelper;
import com.hone.dao.HoPlatformDao;
import com.hone.entity.HoPlatform;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.Page;
import com.hone.system.utils.ParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/5/30.
 */

@Service
@Transactional
public class HoPlatformService {

    @Autowired
    private HoPlatformDao hoPlatformDao;


    /**
     * 获取平台列表
     * @param params
     * @return
     */
    public JsonResult list(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String pageNumber=params.get("pageNumber");
        String pageSize=params.get("pageSize");

        ParamsUtil.checkParamIfNull(params,new String[]{"pageSize","pageNumber"});

        PageHelper.startPage(Integer.parseInt(pageNumber),Integer.parseInt(pageSize),false);
        HoPlatform hoPlatform=new HoPlatform();
        List<HoPlatform> hoPlatformList=  hoPlatformDao.select(hoPlatform);
        Page<HoPlatform> hoPlatformPage=new Page<>(Integer.parseInt(pageNumber),Integer.parseInt(pageSize),hoPlatformList);

        jsonResult.getData().put("page",hoPlatformPage);
        return jsonResult;
    }

    /**
     * 新增平台
     * @param params
     * @return
     * @throws Exception
     */
    public JsonResult save(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String platformName=params.get("platformName");
        String platformIcon=params.get("platformIcon");
        String platformCode=params.get("platformCode");


        ParamsUtil.checkParamIfNull(params,new String[]{"platformCode","platformName","platformIcon"});

        HoPlatform hoPlatform=new HoPlatform();
        hoPlatform.setPlatformIcon(platformIcon);
        hoPlatform.setPlatformName(platformName);
        hoPlatform.setPlatformCode(platformCode);
        hoPlatform.preInsert();
        hoPlatformDao.insert(hoPlatform);

        jsonResult.globalSuccess();
        return jsonResult;
    }
}
