package com.hone.system.utils;

import com.hone.dao.HoAccessTokenDao;
import com.hone.entity.HoAccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Lijia on 2019/6/14.
 */

@Component
public class AccessTokenUtils {

    @Value("${applet.appid}")
    private  String appId;
    @Value("${applet.secret}")
    private String secret;


    @Autowired
    private HoAccessTokenDao accessTokenDao;

    public  String getAccessToken(){

        Date now=new Date();
        HoAccessToken accessToken=accessTokenDao.findLast();
        if(accessToken==null||accessToken.getExpireDate().before(now)){
            String access_token_url = "https://api.weixin.qq.com/cgi-bin/token";
            String param = "grant_type=client_credential&appid="+appId+"&secret="+secret;
            String access_token = HttpUtils.sendPost(access_token_url, param);
            access_token = access_token.substring(access_token.indexOf(":") + 2, access_token.indexOf(",") - 1);
            System.out.println(access_token);

            accessTokenDao.deleteAll();

            HoAccessToken hoAccessToken=new HoAccessToken();
            hoAccessToken.setAccessToken(access_token);
            hoAccessToken.setExpireDate(DateUtils.formatStringToDate(DateUtils.getDelayHoursDate(2)));
            hoAccessToken.preInsert();
            accessTokenDao.insert(hoAccessToken);
            return access_token;
        }else {
            return accessToken.getAccessToken();
        }
    }


}
