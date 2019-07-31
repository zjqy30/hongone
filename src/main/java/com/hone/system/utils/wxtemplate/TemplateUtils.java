package com.hone.system.utils.wxtemplate;

import com.hone.applet.controller.HoWxPayController;
import com.hone.dao.HoOffersDao;
import com.hone.dao.HoPayFlowDao;
import com.hone.dao.HoWxFormidDao;
import com.hone.entity.HoOffers;
import com.hone.entity.HoPayFlow;
import com.hone.entity.HoWxFormid;
import com.hone.system.utils.AccessTokenUtils;
import com.hone.system.utils.DateUtils;
import com.hone.system.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lijia on 2019/6/19.
 */
@Component
public class TemplateUtils {

    private static Logger logger= LoggerFactory.getLogger(TemplateUtils.class);


    @Value("${applet.appid}")
    private String appId;
    @Value("${applet.secret}")
    private String secret;
    @Autowired
    private AccessTokenUtils accessTokenUtils;
    @Autowired
    private HoPayFlowDao hoPayFlowDao;
    @Autowired
    private HoWxFormidDao hoWxFormidDao;
    @Autowired
    private HoOffersDao hoOffersDao;


    public void addTemplate(List<String> values, String pagePath, String templateId, String openId, String formId) {

        List<TemplateResponse> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(values)) {
            for (int i = 1; i < values.size() + 1; i++) {
                TemplateResponse templateResponse1 = new TemplateResponse();
                templateResponse1.setColor("#000000");
                templateResponse1.setName("keyword" + i);
                templateResponse1.setValue(values.get(i - 1));
                list.add(templateResponse1);
            }
        }

        Template template = new Template();
        template.setTemplateId(templateId);
        template.setTemplateParamList(list);
        template.setTopColor("#000000");
        if (StringUtils.isEmpty(pagePath)) {
            pagePath = "pages/index/index";
        }
        template.setPage(pagePath);
        template.setToUser(openId);
        getTemplate(template, formId);
    }

    public void getTemplate(Template template, String formId) {
        //获取 access_token
        String accessToken = accessTokenUtils.getAccessToken();
        template.setForm_id(formId);
        String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + accessToken + "&form_id=" + formId;
        String result = HttpUtils.httpRequest(url, "POST", template.toJSON());
        logger.info("微信模板消息发送结果,"+result);
    }


    /**
     * 发送模板消息
     *
     * @param params openId 小程序用户标识 type 1 付款成功 2 用户审核
     */
    @Async
    public void sendMessage(Map<String, String> params) {
        //封装模板的内容
        List<String> listData = new ArrayList<>();
        //模板信息点击进入的页面地址
        String pagePath = "pages/index/index";
        //模板ID
        String templateId = "";
        //formId
        String formId = "";
        HoWxFormid hoWxFormid=null;
        try {
            logger.info("开始发送微信模板消息，"+params.toString());
            //睡眠3秒
            TimeUnit.SECONDS.sleep(3);
            String type = params.get("type");
            String openId = params.get("openId");
            if (StringUtils.isEmpty(type)) {
                return;
            }
            if (type.equals("1")) {
                /**
                 *订单付款成功
                 产品名称 付款金额 单号 付款时间
                 */
                String outTradeNo = params.get("outTradeNo");
                hoWxFormid = hoWxFormidDao.findUniqueByProperty("out_trade_no", outTradeNo);
                if (hoWxFormid != null) {
                    HoPayFlow payFlow = hoPayFlowDao.findByOutTradeNo(outTradeNo);
                    HoOffers hoOffers = hoOffersDao.selectByPrimaryKey(payFlow.getOfferId());
                    templateId = "SJKelqWVgE_GCPN_nAfNskAT9YuXxeSGt5NLXcNKOus";
                    formId = hoWxFormid.getFormId();

                    listData.add(hoOffers.getTitle());
                    listData.add(payFlow.getTotalFee().toString());
                    listData.add(hoOffers.getOrderNo());
                    listData.add(DateUtils.formatDateToString(payFlow.getUpdateDate()));

                    addTemplate(listData, pagePath, templateId, openId, formId);
                }
            }
            else if(type.equals("2")){
                /**网红审核  商家审核  订单审核  退款审核
                 * 审核内容 审核结果 审核时间
                 */
                templateId="jsqOWJt67phqjUOaKzmdGUuDJj9yU053Gb67A_Uw1P8";
                String result=params.get("result");
                String title=params.get("title");
                listData.add(title);
                listData.add(result);
                listData.add(DateUtils.formatDateToString(new Date()));

                hoWxFormid=hoWxFormidDao.findOneByOpenId(openId);
                if(hoWxFormid!=null){
                    addTemplate(listData, pagePath, templateId, openId, hoWxFormid.getFormId());
                }
            }
            else if(type.equals("3")){
                /** 订单删除
                 * 退款金额 退款原因 退款时间 订单编号
                 */

                templateId="sCuTG4iQ0W_tWybfvG-RIUgeSYEoziOm7QabJDUiq_8";
                String totalFee=params.get("totalFee");
                String reason=params.get("reason");
                String orderNo=params.get("orderNo");
                listData.add(totalFee);
                listData.add(reason);
                listData.add(DateUtils.formatDateToString(new Date()));
                listData.add(orderNo);

                hoWxFormid=hoWxFormidDao.findOneByOpenId(openId);
                if(hoWxFormid!=null){
                    addTemplate(listData, pagePath, templateId, openId, hoWxFormid.getFormId());
                }
            }
            else if(type.equals("4")){
                /**
                 * 服务确认完成
                 * 服务名称  完成时间  温馨提示
                 */
                templateId="uKXW82Y_InAwP4eecl7V2hgFmAg9Md9WXWuRSdQw1d8";
                String offerId=params.get("offerId");
                HoOffers hoOffers=hoOffersDao.selectByPrimaryKey(offerId);
                listData.add(hoOffers.getTitle());
                listData.add(DateUtils.formatDateToString(hoOffers.getFinshDate()));
                listData.add("商家和网红都已确认订单完成,如有问题请联系红腕客服");

                hoWxFormid=hoWxFormidDao.findOneByOpenId(openId);
                if(hoWxFormid!=null){
                    addTemplate(listData, pagePath, templateId, openId, hoWxFormid.getFormId());
                }
            }
            else  if(type.equals("5")){
                /**
                 * 服务开始提醒
                 * 服务名称  订单编号  服务提醒
                 */
                templateId="-82Q-SUif6lwooiPfHRR-9F7RRSJq7GzLgANQI3M1bU";
                String offerId=params.get("offerId");
                HoOffers hoOffers=hoOffersDao.selectByPrimaryKey(offerId);
                listData.add(hoOffers.getTitle());
                listData.add(hoOffers.getOrderNo());
                listData.add("恭喜你被商家选中,点我去查看订单。服务完成后记得确认喔~");

                hoWxFormid=hoWxFormidDao.findOneByOpenId(openId);
                if(hoWxFormid!=null){
                    addTemplate(listData, pagePath, templateId, openId, hoWxFormid.getFormId());
                }
            }
            else if(type.equals("6")){
                /**
                 * 抢单失败
                 * 预约项目   拒绝原因
                 */
                templateId="VYMy0Rpv3hRkNy7Bfu5SX8wEfTkoAcppbWbsVAIkW34";
                String title=params.get("title");
                String createDate=params.get("createDate");
                listData.add(title);
                listData.add("你在 "+createDate+" 参与的抢单失败,详情联系红腕客服");

                hoWxFormid=hoWxFormidDao.findOneByOpenId(openId);
                if(hoWxFormid!=null){
                    addTemplate(listData, pagePath, templateId, openId, hoWxFormid.getFormId());
                }
            }
        } catch (Exception e) {
            logger.error("发送微信模板消息失败",e);
        }

        if(hoWxFormid!=null){
            //删除 hoWxFormid
            hoWxFormidDao.delete(hoWxFormid);
        }

    }

}
