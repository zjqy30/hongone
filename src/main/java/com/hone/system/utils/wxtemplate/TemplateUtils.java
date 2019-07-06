package com.hone.system.utils.wxtemplate;

import com.hone.system.utils.AccessTokenUtils;
import com.hone.system.utils.DateUtils;
import com.hone.system.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lijia on 2019/6/19.
 */
@Component
public class TemplateUtils {


    @Value("${applet.appid}")
    private  String appId;
    @Value("${applet.secret}")
    private String secret;
    @Autowired
    private AccessTokenUtils accessTokenUtils;


    public void addTemplate(List<String> values,String pagePath,String templateId,String openId,String formId) {

        List<TemplateResponse> list = new ArrayList<>();
        if(!CollectionUtils.isEmpty(values)){
            for(int i=1;i<values.size()+1;i++){
                TemplateResponse templateResponse1 = new TemplateResponse();
                templateResponse1.setColor("#000000");
                templateResponse1.setName("keyword"+i);
                templateResponse1.setValue(values.get(i-1));
                list.add(templateResponse1);
            }
        }

        Template template = new Template();
        template.setTemplateId(templateId);
        template.setTemplateParamList(list);
        template.setTopColor("#000000");
        if(StringUtils.isEmpty(pagePath)){
            pagePath="pages/index/index";
        }
        template.setPage(pagePath);
        template.setToUser(openId);
        getTemplate(template, formId);
    }

    public void getTemplate(Template template, String formId) {
        //获取 access_token
        String accessToken=accessTokenUtils.getAccessToken();
        template.setForm_id(formId);
        String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + accessToken + "&form_id=" + formId;
        String result = HttpUtils.httpRequest(url, "POST", template.toJSON());
        System.out.println("付款通知：：：" + result);
    }

}
