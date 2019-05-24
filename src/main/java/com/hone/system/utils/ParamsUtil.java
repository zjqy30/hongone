package com.hone.system.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by Lijia on 2019/5/23.
 */
public class ParamsUtil {


    /**
     * 校验参数是否为空
     * @param params
     * @param keys
     * @throws Exception
     */
    public static void checkParamIfNull(Map<String,String> params,String[] keys) throws Exception {

        String value=null;
        if(params!=null&&params.size()!=0){
             if(keys!=null&&keys.length!=0){
                for (int i=0;i<keys.length;i++){
                    value=params.get(keys[i]);
                    if(StringUtils.isEmpty(value)){
                        throw new Exception("参数{} "+keys[i]+" 不能为空");
                    }
                }
             }
        }
    }

}
