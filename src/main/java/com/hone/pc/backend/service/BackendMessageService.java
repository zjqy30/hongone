package com.hone.pc.backend.service;

import com.hone.dao.HoBackendMessageDao;
import com.hone.dao.HoSystemRoleDao;
import com.hone.dao.HoSystemUserDao;
import com.hone.entity.HoBackendMessage;
import com.hone.entity.HoSystemRole;
import com.hone.entity.HoSystemUser;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.JwtTokenUtils;
import com.hone.system.utils.ParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/6/13.
 */

@Transactional
@Service
public class BackendMessageService {

    @Autowired
    private HoBackendMessageDao hoBackendMessageDao;


    /*
      *后台消息列表
     */
    public JsonResult list(Map<String, String> params) {
        JsonResult jsonResult=new JsonResult();

        List<HoBackendMessage> messageList=hoBackendMessageDao.findAll();

        jsonResult.getData().put("messageList",messageList);
        jsonResult.globalSuccess();
        return jsonResult;
    }





}
