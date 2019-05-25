package com.hone.service;

import com.auth0.jwt.interfaces.Claim;
import com.hone.dao.HoTokenDao;
import com.hone.entity.HoToken;
import com.hone.system.utils.JwtTokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * Created by Lijia on 2019/5/25.
 */

@Service
public class HoTokenService {

    @Autowired
    private HoTokenDao hoTokenDao;


    /**
     *验证token并且获取userId
     * @param params
     * @return
     */
    public boolean checkToken(Map<String,String> params){
        String token=params.get("token");


        if(StringUtils.isEmpty(token)){
            return true;
        }

        Map<String, Claim> claimMap= JwtTokenUtils.verifyToken(token);
        if(CollectionUtils.isEmpty(claimMap)){
            return true;
        }

        HoToken hoToken=hoTokenDao.selectByToken(token);
        if(hoToken==null){
            return true;
        }
        params.put("userId",hoToken.getUserId());

        return false;
    }

}
