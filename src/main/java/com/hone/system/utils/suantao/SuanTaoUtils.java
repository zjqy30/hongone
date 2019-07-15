package com.hone.system.utils.suantao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hone.applet.controller.HoSuanTaoController;
import com.hone.system.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Created by Lijia on 2019/5/29.
 *
 * 调用酸桃API工具类
 *
 *
 */
@Component
public class SuanTaoUtils {
    private static Logger logger= LoggerFactory.getLogger(SuanTaoUtils.class);


    private  static String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC6bs7qRX4ivXZX+lQUxFwgfiVib/g3RoaIhAvcj9dfnhgpi1fWgas9X0XiIBspNZlF9RYzHtem+G7Fi3hOMg2nGqQyAfQbUsPnAgMCfmrv0Pu09TQi8nr1HqKQyssMLpqQooR3V94uiOiVDkuYRwk+IpkEpzhkWT9TChHXhrB4YwIDAQAB";
    private  static String suanTaoUrl="https://www.suantao.com/open/index";
    private  static String password="ysc123...";

    /**
     * RSA加密
     * @param platformId
     * @param identityCode
     * @return
     */
    public static String createKey(Long platformId,String identityCode){
        SuanTao suanTao=new SuanTao();
        suanTao.setType(1);// 1 粉丝画像和月基础信息 0 只返回基础信息
        suanTao.setPassword(password);//访问密码
        suanTao.setPlatformId(platformId);//哪个平台
        suanTao.setIdentityCode(identityCode);//用户所在平台ID

        SuanTaoCommon suanTaoCommon=new SuanTaoCommon();
        suanTaoCommon.setUserName("hongonew");//访问用户名
        String str=JsonUtils.toJson(suanTao);
        System.out.println(str);
        String keys=RSAUtil.encryptByPublicKey(str, publicKey);
        System.out.println(keys);
        return keys;
    }

    /**
     * 调用酸桃api，获取网红信息
     * @param platformId      平台号
     * @param identityCode    用户所在平台ID
     * @return
     */
    public static String getDataFromSuanTao(Long platformId,String identityCode){

        //根据请求参数和用户信息生成RSA加密字符串
        String content=createKey(platformId,identityCode);

        SuanTaoCommon suanTaoCommon=new SuanTaoCommon();
        suanTaoCommon.setUserName("hongonew");
        suanTaoCommon.setContent(content);

        String result= HttpUtils.postJson(suanTaoUrl, JSON.toJSONString(suanTaoCommon));
        System.out.println("返回的加密数据："+result);
        logger.info("返回的加密数据："+result);
        JSONObject jsonObject=JSON.parseObject(result);
        System.out.println("返回的content:"+jsonObject.getString("content"));
        result=RSAUtil.decryptByPublicKey(jsonObject.getString("content"),publicKey);
        System.out.println("解密后的数据："+result);
        return result;

    }



    public static void main(String[] args){
        getDataFromSuanTao(22L,"yichan6666");
    }

}
