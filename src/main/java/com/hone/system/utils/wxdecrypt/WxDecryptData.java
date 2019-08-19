package com.hone.system.utils.wxdecrypt;

import com.hone.applet.controller.WxController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;

/**
 * 微信敏感信息解密获取手机号
 */
public class WxDecryptData {

    private static Logger logger= LoggerFactory.getLogger(WxDecryptData.class);


    /**
     * 偶发性异常：没登录时用AES/CBC/PKCS7Padding，登录后用AES/CBC/PKCS5Padding
     * @param keyStr
     * @param ivStr
     * @param encDataStr
     * @return
     * @throws Exception
     */
    public static String decrypt(String keyStr, String ivStr, String encDataStr)throws Exception {
        try {
            logger.info("使用 AES/CBC/PKCS5Padding 解密手机号");
            byte[] encData = Base64Util.decode(encDataStr);
            byte[] iv =Base64Util.decode(ivStr);
            byte[] key = Base64Util.decode(keyStr);
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            return new String(cipher.doFinal(encData),"UTF-8");
        }catch (Exception e){
            logger.info("使用 AES/CBC/PKCS7Padding 解密手机号");
            byte[] encData = Base64Util.decode(encDataStr);
            byte[] iv =Base64Util.decode(ivStr);
            byte[] key = Base64Util.decode(keyStr);
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            return new String(cipher.doFinal(encData),"UTF-8");
        }
    }


    public static void main(String[] args) throws Exception {
        String encrypData ="xBPeVil0c6VF5RhPiSQvF5MTSo9uHoloFhFfwgAiuv1vJ0tVJguMvqp5bC+HPa+5wX/beqnyaMpmfoviS2erLNiea2khG5Tr3qEq1YifZ/JDdx0e8/skS+ex0vWW9iPZFG4maDdkd+vqhtM5j/5Yw+OBM1BSjcDdpZ2NdqQv2RJPhu0tH7mbhlAC9A9E8JSonBWrahXxMM7vk5L8u6XRTg==";
        String ivData ="aJFnP5oli34iF/a8Oz/Bdw==";
        String sessionKey ="9OeusFpReKj0ogc05J/Qww==";
        System.out.println(decrypt(sessionKey,ivData,encrypData));
    }

    /**
     * {"phoneNumber":"18261732399","purePhoneNumber":"18261732399","countryCode":"86","watermark":{"timestamp":1560763497,"appid":"wx38ca48e91da0acf9"}}
     *
     */

}
