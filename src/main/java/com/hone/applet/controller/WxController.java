package com.hone.applet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hone.dao.HoUserBasicDao;
import com.hone.dao.HoWxFormidDao;
import com.hone.entity.HoUserBasic;
import com.hone.entity.HoWxFormid;
import com.hone.pc.www.controller.HoWebsiteMessageController;
import com.hone.system.utils.DateUtils;
import com.hone.system.utils.HttpUtils;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.ParamsUtil;
import com.hone.system.utils.wxdecrypt.WxDecryptData;
import com.hone.system.utils.wxtemplate.TemplateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Lijia on 2019/5/29.
 */

@RestController
@RequestMapping("/applet/wx")
public class WxController {

    private static Logger logger= LoggerFactory.getLogger(WxController.class);

    @Value("${applet.appid}")
    private String appid;
    @Value("${applet.secret}")
    private String secret;

    @Autowired
    private HoWxFormidDao hoWxFormidDao;
    @Autowired
    private TemplateUtils templateUtils;
    @Autowired
    private HoUserBasicDao hoUserBasicDao;

    @Autowired
    private SolrClient solrClient;

    @RequestMapping("/openId")
    public JsonResult getOpenId(@RequestBody Map<String,String> params){
        logger.info("获取微信openId");
        JsonResult jsonResult=new JsonResult();

        try {
            String code=params.get("code");
            ParamsUtil.checkParamIfNull(params,new String[]{"code"});

            String response = HttpUtils.sendGet("https://api.weixin.qq.com/sns/jscode2session", "appid=" + appid + "&secret="+secret+"&js_code=" + code + "&grant_type=authorization_code");
            System.out.println("获取openId返回信息："+response);
            JSONObject jsonObject=JSON.parseObject(response);
            if(StringUtils.isNotEmpty(jsonObject.getString("errcode"))&&jsonObject.getString("errcode").equals("40163")){
                jsonResult.globalError("code已经失效");
            }else if(StringUtils.isNotEmpty(jsonObject.getString("errcode"))&&jsonObject.getString("errcode").equals("40029")){
                jsonResult.globalError("code已经失效");
            }else {
                jsonResult.getData().put("openid", JSON.parseObject(response).getString("openid"));
                jsonResult.getData().put("session_key", JSON.parseObject(response).getString("session_key"));
                jsonResult.globalSuccess();
            }
        }catch (Exception e){
            logger.error("获取微信openId",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/decryptData")
    public JsonResult decryptData(@RequestBody Map<String,String> params){
        logger.info("解密微信私密信息，获取手机号");
        JsonResult jsonResult=new JsonResult();

        try {
            String encrypData=params.get("encrypData");
            String ivData=params.get("ivData");
            String sessionKey=params.get("sessionKey");
            String openid=params.get("openid");
            ParamsUtil.checkParamIfNull(params,new String[]{"openid","sessionKey","ivData","encrypData"});

            String result=WxDecryptData.decrypt(sessionKey,ivData,encrypData);
            JSONObject jsonObject=JSONObject.parseObject(result);
            jsonResult.getData().put("phoneNumber",jsonObject.getString("phoneNumber"));

            if(StringUtils.isNotEmpty(jsonObject.getString("phoneNumber"))){
                HoUserBasic hoUserBasic=hoUserBasicDao.findUniqueByProperty("open_id",openid);
                hoUserBasic.setPhoneNo(jsonObject.getString("phoneNumber"));
                hoUserBasicDao.updateByPrimaryKeySelective(hoUserBasic);
            }

        }catch (Exception e){
            logger.error("解密微信私密信息，获取手机号",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    @RequestMapping("/formId")
    public JsonResult formId(@RequestBody Map<String,String> params){
        logger.info("新增formid");
        JsonResult jsonResult=new JsonResult();

        try {
            String formId=params.get("formId");
            String userId=params.get("userId");
            String openId=params.get("openId");
            String outTradeNo=params.get("outTradeNo");
            ParamsUtil.checkParamIfNull(params,new String[]{"formId","userId","openId"});

            //过滤PC段产生的formId
            if(formId.contains("the formId is a mock one")){
                jsonResult.globalSuccess();
                return jsonResult;
            }

            //判断是否重复添加
            if(StringUtils.isNotEmpty(outTradeNo)){
                HoWxFormid wxFormid=hoWxFormidDao.findUniqueByProperty("out_trade_no",outTradeNo);
                if(wxFormid!=null){
                    hoWxFormidDao.delete(wxFormid);
                }
            }

            HoWxFormid wxFormid=new HoWxFormid();
            wxFormid.preInsert();
            wxFormid.setFormId(formId);
            wxFormid.setOpenId(openId);
            wxFormid.setUserId(userId);
            wxFormid.setOutTradeNo(outTradeNo);
            wxFormid.setExpireDate(DateUtils.formatStringToDate(DateUtils.getOneDaysDate(7)));
            hoWxFormidDao.insert(wxFormid);

            jsonResult.globalSuccess();
        }catch (Exception e){
            logger.error("新增formid",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/templateMsg")
    public JsonResult templateMsg(@RequestBody Map<String,String> params){
        logger.info("发送模板消息");
        JsonResult jsonResult=new JsonResult();

        try {

            List<String> values=new ArrayList<>();
            String value=params.get("value");
            if(!StringUtils.isEmpty(value)){
                for (String key:value.split(",")){
                    values.add(key);
                }
            }

            String pagePath="pages/index/index";
            String templateId=params.get("templateId");
            String openId=params.get("openId");
            String formId=params.get("formId");

            templateUtils.addTemplate(values,pagePath,templateId,openId,formId);

            jsonResult.globalSuccess();
        }catch (Exception e){
            logger.error("发送模板消息",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    public static void main(String[] args){
        System.out.println(String.format("www:%s:com","baidu"));//www.baidu.com
        System.out.println(String.format("www:%c:com",'a'));//www.a.com
        System.out.println(String.format("%+d",5));//+5
        System.out.println(String.format("%d",10/3));//3
        System.out.println(String.format("%03d",5));//005
        System.out.println(String.format("%,f",100000.0));//100,000.000000
    }


    @RequestMapping("/insert")
    public String insert(@RequestBody Map<String,String> params) throws IOException, SolrServerException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String dateString = sdf.format(new Date());
        try {
            SolrInputDocument doc = new SolrInputDocument();
            doc.setField("id", dateString);
            doc.setField("studentName", params.get("wx_name"));
            solrClient.add(doc);
            solrClient.commit();
            return dateString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping("/get")
    public String getDocumentById(@RequestBody Map<String,String> params) throws SolrServerException, IOException {
        SolrDocument document = solrClient.getById(params.get("id"));
        System.out.println(document);
        return document.toString();
    }

    @RequestMapping("/delete")
    public String delete(@RequestBody Map<String,String> params) {
        try {
            solrClient.deleteById(params.get("id"));
            solrClient.commit();
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }


    @RequestMapping("deleteAll")
    public String deleteAll() {
        try {
            solrClient.deleteByQuery("*:*");
            solrClient.commit();
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }


    @RequestMapping("/update")
    public String update(@RequestBody Map<String,String> params) throws IOException, SolrServerException {
        try {
            SolrInputDocument doc = new SolrInputDocument();
            doc.setField("id", params.get("id"));
            doc.setField("text",  params.get("message"));

            /*
             * 如果 spring.data.solr.host 里面配置到 core了, 那么这里就不需要传 itaem 这个参数 下面都是一样的 即
             * client.commit();
             */
            solrClient.add(doc);
            solrClient.commit();
            return doc.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping("/query")
    public  Object select(@RequestBody Map<String,String> params)
            throws SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();

        // 查询条件
        solrQuery.set("q", params.get("key"));

        // 排序
        //solrQuery.addSort("id", SolrQuery.ORDER.desc);

        // 分页
        solrQuery.setStart(Integer.parseInt(params.get("pageNumber")));
        solrQuery.setRows(Integer.parseInt(params.get("pageSize")));

        // 默认域
        if(!StringUtils.isEmpty(params.get("df"))){
            solrQuery.set("df",params.get("df"));
        }


        if(!StringUtils.isEmpty(params.get("fl"))){
            solrQuery.set("fl", params.get("fl"));
        }
        // 只查询指定域
//        solrQuery.set("fl", "content");

        // 开启高亮
        solrQuery.setHighlight(true);

        // 设置前缀
        solrQuery.setHighlightSimplePre("<span style='color:red'>");
        // 设置后缀
        solrQuery.setHighlightSimplePost("</span>");

        // solr数据库是 itaem
        QueryResponse queryResponse = solrClient.query(solrQuery);
        SolrDocumentList results = queryResponse.getResults();
//
//        // 数量，分页用
//        long total = results.getNumFound();// JS 使用 size=MXA 和 data.length 即可知道长度了（但不合理）

        // 获取高亮显示的结果, 高亮显示的结果和查询结果是分开放的
        Map<String, Map<String, List<String>>> highlight = queryResponse.getHighlighting();
        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("total", total);
        map.put("data", highlight);


        System.out.println(map);
        return results;

    }

}

