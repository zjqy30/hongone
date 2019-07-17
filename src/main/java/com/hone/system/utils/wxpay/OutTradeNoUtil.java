package com.hone.system.utils.wxpay;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lijia on 2019/6/10.
 */
public class OutTradeNoUtil {


    /**
     *  生成流水号
     * @param type  PY 1  RA 2  订单号 3
     * @returnd
     */
    public static String outTradeNo(String type) {
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");//该时间格式用于交易流水号设置
        String timeStr1 = sdf1.format(date);//得到当前时间，用于设置交易流水
        String no = "0000000";//七位随机数字
        no = String.valueOf((int) (Math.random() * 10000000));
        String outTradeNo = "";//初始化交易流水号为空
        outTradeNo = timeStr1.substring(0, 6) + type + timeStr1.substring(6, 12) + timeStr1.substring(12) + no;
        return outTradeNo;
    }

    public static void main(String[] args){
        String no=outTradeNo("1");
        System.out.println(no);
    }

}
