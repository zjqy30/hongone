package com.hone.applet.service;

import com.alibaba.fastjson.JSONObject;
import com.hone.applet.controller.HoWxPayController;
import com.hone.dao.HoAccountChargeDao;
import com.hone.dao.HoBannersDao;
import com.hone.dao.HoOffersDao;
import com.hone.dao.HoPayFlowDao;
import com.hone.entity.HoAccountCharge;
import com.hone.entity.HoBanners;
import com.hone.entity.HoOffers;
import com.hone.entity.HoPayFlow;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.MD5Util;
import com.hone.system.utils.ParamsUtil;
import com.hone.system.utils.wxpay.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.*;

/**
 * Created by Lijia on 2019/5/30.
 */

@Service
@Transactional
public class HoWxPayService {

    private static Logger logger = LoggerFactory.getLogger(HoWxPayService.class);
    private static final String WeChatPayUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    private static final String WeChatPayRefundUrl2 = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    private static final String notifyUrl = "http://l1838324x8.imwork.net/hone/applet/wxpay/callBack";
    private static final String body = "红腕";

    @Value("${applet.appid}")
    private String appId;
    @Value("${applet.mchid}")
    private String mchId;
    @Value("${applet.key}")
    private String key;

    @Autowired
    private HoOffersDao hoOffersDao;
    @Autowired
    private HoPayFlowDao hoPayFlowDao;
    @Autowired
    private HoAccountChargeDao hoAccountChargeDao;


    /**
     * 唤醒微信支付
     *
     * @param params
     * @return
     */
    public JsonResult callPay(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String offerId = params.get("offerId");
        String openid = params.get("openid");
        String userId = params.get("userId");
        String totalMoney = params.get("totalMoney");

        ParamsUtil.checkParamIfNull(params, new String[]{"totalMoney", "openid", "userId", "offerId"});

        HoOffers hoOffers = hoOffersDao.selectByPrimaryKey(offerId);
        if (hoOffers == null) {
            jsonResult.globalError("需求不存在");
            return jsonResult;
        }
        if (!hoOffers.getStatus().equals("CR")) {
            jsonResult.globalError("当前订单状态有误");
            return jsonResult;
        }
        if (!hoOffers.getUserId().equals(userId)) {
            jsonResult.globalError("用户信息有误");
            return jsonResult;
        }
        if (new BigDecimal(totalMoney).compareTo(new BigDecimal(hoOffers.getPrice())) != 0) {
            jsonResult.globalError("支付金额有误");
            return jsonResult;
        }

        HoPayFlow payFlow = hoPayFlowDao.findByOfferIdAndType(offerId, "PY");
        if (payFlow == null) {
            String outTradeNo = OutTradeNoUtil.outTradeNo("1");
            //预支付订单生成
            jsonResult = appletWechatPay(openid, outTradeNo, String.valueOf(hoOffers.getPrice()), params.get("spbillCreateIp"));

            HoPayFlow hoPayFlow = new HoPayFlow();
            hoPayFlow.setUserId(userId);
            hoPayFlow.setTotalFee(new BigDecimal(hoOffers.getPrice()));
            hoPayFlow.setTransType("PY");
            hoPayFlow.setStatus("0");
            hoPayFlow.setOfferId(hoOffers.getId());
            hoPayFlow.setOutTradeNo(outTradeNo);
            hoPayFlow.preInsert();
            hoPayFlowDao.insert(hoPayFlow);
        } else {
            if (payFlow.getStatus().equals("1")) {
                jsonResult.globalError("当前订单已经支付");
                return jsonResult;
            }
            if (payFlow.getTotalFee().compareTo(new BigDecimal(hoOffers.getPrice())) != 0) {
                jsonResult.globalError("支付金额有误");
                return jsonResult;
            }
            String outTradeNo = payFlow.getOutTradeNo();
            //预支付订单生成
            jsonResult = appletWechatPay(openid, outTradeNo, String.valueOf(hoOffers.getPrice()), params.get("spbillCreateIp"));
        }

        return jsonResult;
    }


    /**
     * 生成微信小程序支付预支付订单
     *
     * @param openId
     * @param payFlowId
     * @param totalMoney
     * @param spbillCreateIp
     * @return
     * @throws DocumentException
     */
    public JsonResult appletWechatPay(String openId, String payFlowId, String totalMoney, String spbillCreateIp) throws DocumentException {
        JsonResult jsonResult = new JsonResult();

        String nonce_str = UUIDHexGenerator.generate();//随机字符串
        double totalFeeTemp = Double.parseDouble(totalMoney) * 100;//订单总金额，单位为分
        int totalFee = (int) totalFeeTemp;//订单总金额，单位为分
        PaymentPo paymentPo = new PaymentPo();
        paymentPo.setAppid(appId);
        paymentPo.setMch_id(mchId);
        paymentPo.setNonce_str(nonce_str);
        paymentPo.setBody(body);
        paymentPo.setOut_trade_no(payFlowId);
        paymentPo.setTotal_fee(totalFee + "");
        paymentPo.setSpbill_create_ip(spbillCreateIp);
        paymentPo.setNotify_url(notifyUrl);
        paymentPo.setTrade_type("JSAPI");
        paymentPo.setOpenid(openId);
        // 把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap();
        sParaTemp.put("appid", paymentPo.getAppid());
        sParaTemp.put("mch_id", paymentPo.getMch_id());
        sParaTemp.put("nonce_str", paymentPo.getNonce_str());
        sParaTemp.put("body", paymentPo.getBody());
        sParaTemp.put("out_trade_no", paymentPo.getOut_trade_no());
        sParaTemp.put("total_fee", paymentPo.getTotal_fee());
        sParaTemp.put("spbill_create_ip", paymentPo.getSpbill_create_ip());
        sParaTemp.put("notify_url", paymentPo.getNotify_url());
        sParaTemp.put("trade_type", paymentPo.getTrade_type());
        sParaTemp.put("openid", paymentPo.getOpenid());
        // 除去数组中的空值和签名参数
        Map<String, String> sPara = WeChatPayUtil.paraFilter(sParaTemp);
        String prestr = WeChatPayUtil.createLinkString(sPara); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

        //MD5运算生成签名
        String mysign = WeChatPayUtil.sign(prestr, "&key="+key, "utf-8").toUpperCase();
        paymentPo.setSign(mysign);
        //打包要发送的xml
        String respXml = MessageUtil.messageToXML(paymentPo);
        // 打印respXml发现，得到的xml中有“__”不对，应该替换成“_”
        respXml = respXml.replace("__", "_");
        String param = respXml;
        String result = WeChatPayUtil.httpRequest(WeChatPayUrl, "POST", param);
        // 将解析结果存储在HashMap中
        Map map = new HashMap();
        InputStream in = new ByteArrayInputStream(result.getBytes());
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(in);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        @SuppressWarnings("unchecked")
        List<Element> elementList = root.elements();
        for (Element element : elementList) {
            map.put(element.getName(), element.getText());
        }
        // 返回信息
        String return_code = (String) map.get("return_code");//返回状态码
        String return_msg = (String) map.get("return_msg");//返回信息
        if (return_code.contains("FAIL")) {
            jsonResult.globalError(return_msg);
        }
        if (return_code.equalsIgnoreCase("SUCCESS")) {
            // 业务结果
            String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
            String nonceStr = UUIDHexGenerator.generate();
            Long timeStamp = System.currentTimeMillis() / 1000;
            String stringSignTemp = "appId=" + appId + "&nonceStr=" + nonceStr + "&package=prepay_id=" + prepay_id + "&signType=MD5&timeStamp=" + timeStamp;
            //再次签名
            String paySign = WeChatPayUtil.sign(stringSignTemp, "&key="+key, "utf-8").toUpperCase();
            System.out.println("二次签名生成的paySign:" + paySign);
            map.put("paySign", paySign);
            map.put("nonce_str", nonceStr);
            map.put("timeStamp", String.valueOf(timeStamp));
            jsonResult.getData().put("map", map);
            jsonResult.globalSuccess();
        }

        return jsonResult;
    }

    @Transactional(propagation = Propagation.NESTED)
    public String callBack(String xmlString, HttpServletResponse response, Map<String, String> map) throws Exception {
        String resXml = "";
        String outTradeNo = "";
        if (map.get("return_code").equals("SUCCESS")) {
            //验证签名
            SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
            Document doc = DocumentHelper.parseText(xmlString);
            Element root = doc.getRootElement();
            for (Iterator iterator = root.elementIterator(); iterator.hasNext(); ) {
                Element e = (Element) iterator.next();
                parameters.put(e.getName(), e.getText());
            }
            //生成签名--小程序
            String sign2 = createSign("UTF-8", parameters);
            String returnSign = map.get("sign");
            logger.info("微信统一下单回调生成签名为：" + sign2);
            logger.info("微信统一下单回调微信返回签名为：" + returnSign);
            double totalFee = Double.parseDouble(map.get("total_fee")) / 100;//交易金额（分为单位）
            outTradeNo = map.get("out_trade_no");//交易流水号
            String transactionId = map.get("transaction_id");//微信支付订单号
            String openid = map.get("openid");//买家用户标识
            if (sign2.equals(returnSign)) {
                try {
                    logger.info("回调时签名验证成功-------");
                    //开始处理业务逻辑

                    //获取交易流水
                    HoPayFlow hoPayFlow=hoPayFlowDao.findByOutTradeNo(outTradeNo);
                    if(hoPayFlow==null){
                        throw new Exception("支付流水不存在,out_trade_no="+outTradeNo);
                    }

                    //更新交易流水
                    hoPayFlow.setStatus("1");
                    hoPayFlowDao.updateByPrimaryKeySelective(hoPayFlow);

                    //更新需求订单
                    HoOffers hoOffers=hoOffersDao.selectByPrimaryKey(hoPayFlow.getOfferId());
                    if(hoOffers!=null){
                        hoOffers.setStatus("PY");
                        hoOffers.setUpdateDate(new Date());
                        hoOffersDao.updateByPrimaryKeySelective(hoOffers);
                    }

                    //插入账户余额变动
                    HoAccountCharge hoAccountCharge=new HoAccountCharge();
                    hoAccountCharge.setChargeStatus("1");
                    hoAccountCharge.setChargeType("PY");
                    hoAccountCharge.setTotalFee(new BigDecimal(hoOffers.getPrice()));
                    hoAccountCharge.setUserId(hoOffers.getUserId());
                    hoAccountCharge.setOutTradeNo(outTradeNo);
                    hoAccountCharge.setOfferId(hoOffers.getId());
                    hoAccountCharge.preInsert();
                    hoAccountChargeDao.insert(hoAccountCharge);

                    //业务处理结束
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                } catch (Exception e) {
                    //打印日志
                    e.printStackTrace();
                    logger.error("微信回调函数处理出错", e);
                    //失败处理
                   // notifyFailDeal(e.getMessage(), outTradeNo, String.valueOf(totalFee));
                    //事务回滚
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
            } else {
                //签名不正确
                logger.error("微信支付签名错误,交易号:" + outTradeNo + ",订单号:");
                //失败处理
                //notifyFailDeal("签名不正确",outTradeNo,String.valueOf(totalFee));

            }
        } else if (map.get("return_code").equals("FAIL")) {
            logger.info("-----通信失败-------");
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[签名失败]]></return_msg>" + "</xml> ";
        }


    BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
        response.getOutputStream().close();
        return map.toString();
}

    public String createSign(String characterEncoding, SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set<Map.Entry<Object, Object>> es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator<Map.Entry<Object, Object>> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" +key);
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }


    @SuppressWarnings("deprecation")
    public String wechatPayRefundForApplet(String outTradeNo, String outRefundNo,String totalFee, String refundFee) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException, UnrecoverableKeyException {
        String tradeResult="";
        Resource resource = new ClassPathResource("apiclient_cert.p12");
        File file = resource.getFile();
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(file);
        try{
            keyStore.load(instream,mchId.toCharArray());
        }finally{
            instream.close();
        }
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        String isSuccess = "";
        try{
            //生成随机字符串,不长于32位。推荐随机数生成算法
            String nonceStr = RandomStringUtils.random(32,"abcdefghijklmnopqrstuvwxyz0123456789").toString();
            String signType = "MD5";//签名类型
            int totalFeeEnd = (int)(Double.parseDouble(totalFee)*100);//订单总金额，单位为分
            int refundFeeEnd = (int)(Double.parseDouble(refundFee)*100);//退款金额，单位为分
            SortedMap<Object,Object> parameters = new TreeMap<Object, Object>();
            parameters.put("appid", appId);
            parameters.put("mch_id", mchId);
            parameters.put("nonce_str", nonceStr);
            parameters.put("out_trade_no", outTradeNo);
            parameters.put("out_refund_no", outRefundNo);
            parameters.put("refund_fee", refundFeeEnd);
            parameters.put("total_fee", totalFeeEnd);
            parameters.put("sign_type", signType);
            String sign = createSign("UTF-8",parameters);//生成签名
            String xml = XmltoJsonUtil.getRequestXML(parameters,sign);// 获取xml结果
            System.out.println(xml);
            //向微信提交请求
            HttpPost httpost = new HttpPost(WeChatPayRefundUrl2); // 设置响应头信息
            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            httpost.setEntity(new StringEntity(xml, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httpost);
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(response.getEntity(),"UTF-8");
            logger.info("微信退款请求结果："+content);
            System.out.println(content);
            EntityUtils.consume(entity);
            response.close();
            httpclient.close();
            Map<String, String> map = XmltoJsonUtil.xmlToMap(content);
            String return_code =  map.get("return_code");
            if(return_code.equalsIgnoreCase("FAIL")){
                tradeResult="FAIL";
                logger.error("微信退款失败,outTradeNo:"+outTradeNo+"  return_msg："+map.get("return_msg"));
            }else if(return_code.equalsIgnoreCase("SUCCESS")){
                if(map.get("result_code").equalsIgnoreCase("SUCCESS")){
                    tradeResult="SUCCESS";
                }else{
                    tradeResult="FAIL";
                    logger.error("微信退款失败,outTradeNo:"+outTradeNo+"  return_msg："+map.get("return_msg"));
                }
            }
        }catch(Exception e){
            logger.error("微信退款异常", e);
        }
        return tradeResult;
    }



    /**
     * 微信扫码支付
     *
     * @param openId
     * @param payFlowId
     * @param totalMoney
     * @param spbillCreateIp
     * @return
     * @throws DocumentException
     */
    public JsonResult nativeWechatPay(String openId, String payFlowId, String totalMoney, String spbillCreateIp) throws DocumentException {
        JsonResult jsonResult = new JsonResult();

        String nonce_str = UUIDHexGenerator.generate();//随机字符串
        double totalFeeTemp = Double.parseDouble(totalMoney) * 100;//订单总金额，单位为分
        int totalFee = (int) totalFeeTemp;//订单总金额，单位为分
        PaymentPo paymentPo = new PaymentPo();
        paymentPo.setAppid(appId);
        paymentPo.setMch_id(mchId);
        paymentPo.setNonce_str(nonce_str);
        paymentPo.setBody(body);
        paymentPo.setOut_trade_no(payFlowId);
        paymentPo.setTotal_fee(totalFee + "");
        paymentPo.setSpbill_create_ip(spbillCreateIp);
        paymentPo.setNotify_url(notifyUrl);
        paymentPo.setTrade_type("NATIVE");
        paymentPo.setOpenid(openId);
        // 把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap();
        sParaTemp.put("appid", paymentPo.getAppid());
        sParaTemp.put("mch_id", paymentPo.getMch_id());
        sParaTemp.put("nonce_str", paymentPo.getNonce_str());
        sParaTemp.put("body", paymentPo.getBody());
        sParaTemp.put("out_trade_no", paymentPo.getOut_trade_no());
        sParaTemp.put("total_fee", paymentPo.getTotal_fee());
        sParaTemp.put("spbill_create_ip", paymentPo.getSpbill_create_ip());
        sParaTemp.put("notify_url", paymentPo.getNotify_url());
        sParaTemp.put("trade_type", paymentPo.getTrade_type());
        sParaTemp.put("openid", paymentPo.getOpenid());
        // 除去数组中的空值和签名参数
        Map<String, String> sPara = WeChatPayUtil.paraFilter(sParaTemp);
        String prestr = WeChatPayUtil.createLinkString(sPara); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

        //MD5运算生成签名
        String mysign = WeChatPayUtil.sign(prestr, "&key="+key, "utf-8").toUpperCase();
        paymentPo.setSign(mysign);
        //打包要发送的xml
        String respXml = MessageUtil.messageToXML(paymentPo);
        // 打印respXml发现，得到的xml中有“__”不对，应该替换成“_”
        respXml = respXml.replace("__", "_");
        String param = respXml;
        String result = WeChatPayUtil.httpRequest(WeChatPayUrl, "POST", param);
        // 将解析结果存储在HashMap中
        Map map = new HashMap();
        InputStream in = new ByteArrayInputStream(result.getBytes());
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(in);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        @SuppressWarnings("unchecked")
        List<Element> elementList = root.elements();
        for (Element element : elementList) {
            map.put(element.getName(), element.getText());
        }
        // 返回信息
        String return_code = (String) map.get("return_code");//返回状态码
        String return_msg = (String) map.get("return_msg");//返回信息
        if (return_code.contains("FAIL")) {
            jsonResult.globalError(return_msg);
        }
        if (return_code.equalsIgnoreCase("SUCCESS")) {

            //获取二维码内容
            String code_url=map.get("code_url").toString();
            //把二维码内容转成二维码

            jsonResult.getData().put("map", map);
            jsonResult.globalSuccess();
        }

        return jsonResult;
    }

}
