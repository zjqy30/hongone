package com.hone.pc.backend.service;

import com.hone.dao.HoSystemUserDao;
import com.hone.entity.HoSystemUser;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.JwtTokenUtils;
import com.hone.system.utils.ParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by Lijia on 2019/6/13.
 */

@Transactional
@Service
public class SystemUserService {

    @Autowired
    private HoSystemUserDao hoSystemUserDao;


    /**
     * 系统管理员登录
     * @param params
     * @return
     */
    public JsonResult login(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String userName=params.get("userName");
        String passWord=params.get("passWord");

        ParamsUtil.checkParamIfNull(params,new String[]{"userName","passWord"});

        //查找用户
        HoSystemUser systemUser= hoSystemUserDao.findByUserNameAndPass(userName,passWord);

        if(systemUser==null){
            jsonResult.globalError("账号或密码错误");
            return jsonResult;
        }

        //生成token
        String token=JwtTokenUtils.createToken(systemUser.getId());

        systemUser.setPassword("******");
        jsonResult.getData().put("user",systemUser);
        jsonResult.getData().put("token",token);
        jsonResult.globalSuccess();
        return jsonResult;
    }
}
