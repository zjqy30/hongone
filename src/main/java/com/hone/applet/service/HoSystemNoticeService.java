package com.hone.applet.service;

import com.alibaba.fastjson.JSONObject;
import com.hone.dao.HoBannersDao;
import com.hone.dao.HoSystemNoticeDao;
import com.hone.entity.HoBanners;
import com.hone.entity.HoSystemNotice;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.ParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/5/30.
 */

@Service
@Transactional
public class HoSystemNoticeService {

    @Autowired
    private HoSystemNoticeDao hoSystemNoticeDao;


    /**
     * 获取系统通知
     * @param params
     * @return
     */
    public JsonResult list(Map<String, String> params) {
        JsonResult jsonResult=new JsonResult();

        List<HoSystemNotice> systemNoticeList=hoSystemNoticeDao.list();
        jsonResult.getData().put("systemNoticeList",systemNoticeList);
        jsonResult.globalSuccess();
        return jsonResult;
    }


}
