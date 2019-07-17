package com.hone.pc.backend.service;

import com.hone.dao.HoSystemNoticeDao;
import com.hone.entity.HoSystemNotice;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.ParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/6/14.
 */

@Transactional
@Service
public class SystemNoticeService {


    @Autowired
    private HoSystemNoticeDao hoSystemNoticeDao;

    /**
     * 系统公告列表
     * @param params
     * @return
     */
    public JsonResult list(Map<String, String> params) {
        JsonResult jsonResult=new JsonResult();

        List<HoSystemNotice> systemNoticeList=  hoSystemNoticeDao.list();

        jsonResult.getData().put("systemNoticeList",systemNoticeList);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 删除系统公告
     * @param params
     * @return
     */
    public JsonResult del(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String id=params.get("id");
        ParamsUtil.checkParamIfNull(params,new String[]{"id"});

        HoSystemNotice systemNotice=hoSystemNoticeDao.selectByPrimaryKey(id);
        systemNotice.setEnableFlag("0");
        hoSystemNoticeDao.updateByPrimaryKeySelective(systemNotice);

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 新建系统公告
     * @param params
     * @return
     */
    public JsonResult create(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String message=params.get("message");
        ParamsUtil.checkParamIfNull(params,new String[]{"message"});

        HoSystemNotice hoSystemNotice=new HoSystemNotice();
        hoSystemNotice.setMessage(message);
        hoSystemNotice.preInsert();
        hoSystemNoticeDao.insert(hoSystemNotice);

        jsonResult.globalSuccess();
        return jsonResult;
    }
}
