package com.hone.system.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiJia on 2017/12/18.
 * 生成二维码
 */
public class WxAppletQrCode {

    public static String createQR(String accessToken,String marketerId) {
        RestTemplate rest = new RestTemplate();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String filename="";
        try {
            String url = "https://api.weixin.qq.com/wxa/getwxacode?access_token="+accessToken;
            Map<String,Object> param = new HashMap<>();
            param.put("path", "pages/index/index?id="+marketerId);
            param.put("width", 430);
//            param.put("auto_color",true);  自动配置颜色
//            param.put("is_hyaline",true);  底色透明
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity requestEntity = new HttpEntity(param, headers);
            ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
            byte[] result = entity.getBody();
            inputStream = new ByteArrayInputStream(result);
            //获取项目根路径
            String relativelyPath = System.getProperty("user.dir");
            String mkdirs=relativelyPath+"/wxqrcode/"+DateUtils.YYYYMMDD()+"/";
            filename=relativelyPath+"/wxqrcode/"+DateUtils.YYYYMMDD()+"/"+marketerId+".jpg";
            File filedirs = new File(mkdirs);
            File file=new File(filename);
            if (!filedirs.isDirectory()){
                filedirs.mkdirs();
            }
            outputStream = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return filename;
    }
}

