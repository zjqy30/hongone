package com.hone.system.utils.suantao;


import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
/**
 * 
 * <P>Description: rsa工具类(必须要用此工具类生成的公钥私钥才能使用下面的加解密方法)</P>
 * @ClassName: RSAUtil
 * @author jie.xu  2017年3月29日 下午2:56:33
 * @see
 */
public class RSAUtil {

    public static final String PUBLIC_KEY = "publicKey";

    public static final String PRIVATE_KEY = "privateKey";

	public static void main(String[] args) throws Exception {
		//RSAUtil.createKey("D:/key/");
		String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCfdBEE7SK6lTS+YKRVzY7LVvifelwp/z+uJ12DSFzWL5zudx6yrUshGdjFDoKYHPeUrS+1GP7q09GyNVCmdOII6L3l00nvqTy7QI2eIlkdXdH9kJPmyGMypHSZu+gi9rKoEAltZuEaHBi3QBVvoN9oVj06JhzG8icoExZEFbNDNwIDAQAB";
		String privateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ90EQTtIrqVNL5gpFXNjstW+J96XCn/P64nXYNIXNYvnO53HrKtSyEZ2MUOgpgc95StL7UY/urT0bI1UKZ04gjoveXTSe+pPLtAjZ4iWR1d0f2Qk+bIYzKkdJm76CL2sqgQCW1m4RocGLdAFW+g32hWPTomHMbyJygTFkQVs0M3AgMBAAECgYB/XdneYoEUIuy/iTibZRfDqtPrp3SJA19dgAo9PIcTe3LSNIpr1OqnJRs2xJFiafkFZJUwBihRcSMWQ2oJnGGT0yYVJtUB3kRLuyfwT9wLfigvb6u0GCls+cGh5FOVELH8/DszNvC9z1uYJw0bb99sc4fBpVorEjq3G+rV3g+DSQJBANM3ddkcziwGkfzhMmUN/gvFo94zjQVnIYi43EqRD5jmCJGNtPjk//V/VlwBLXtSj3HmeVkxyenwzRRZvntSM6UCQQDBQvhQJ+8WuwZJetiWdUtP7+4hpMf4GFx2HfKmH4y9vvEP/e+cT3UxMwDc2t5suACNWZkwWPaatfSKhk8/lHSrAkEAluQhEbjtZTBQbOromJ71Za2NErmbPkfeAhGmdrLJBL8ZUfp9Ve4rGVQd+t5wgpCJn6I6AMl76N+5cpdmaC1ibQJBAJaeHvA5bziD4c56kzoPh/Zww2ItDjvooIbdqDKX/BQVL8KK4acsteT2HwIBAcAxVxzSa3UmoGrKzB75teGTTakCQAI2Wa332ti1TOlezG10dUt/v22pOKZSX9ZYDeVPMZH+vGVXoxuTINYcjVBQMYr/aJrsA9KA3hv4cs1FW6d12M0=";
		for(int i=0;i<10000;i++){
			System.out.println("加密前的数据:"+i);
			String estr=RSAUtil.encryptByPublicKey(i+"啊人个#&*（）*&%%…………们", publicKey);
			System.out.println("加密后的数据:"+estr);
			String dstr=RSAUtil.decryptByPrivateKey(estr, privateKey);
			System.out.println("解密后的数据:"+dstr);
			System.out.println();
		}
	}

	/**
	 * @Author Plank
	 * @Description 获取公钥私钥
	 * @Date 16:03 2019/3/19
	 * @Param []
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
    public static Map<String,String> getKey() {
        Map<String, String> returnMap = new HashMap<>();
        Map<String, Object> keyMap=Rsa.genKeyPair();
        String publicKey=Rsa.getPublicKey(keyMap);
        String privateKey=Rsa.getPrivateKey(keyMap);

        returnMap.put(RSAUtil.PUBLIC_KEY, publicKey);
        returnMap.put(RSAUtil.PRIVATE_KEY, privateKey);
        return returnMap;
    }

	/**
	 * 
	 * <p>Title: 创建公钥私钥</p>
	 * <p>Description:  
	 * @param keyPath 存储目录
	 * @author jie.xu  2017年3月29日 下午2:18:47
	 */
	public static void createKey(String keyPath){
		if(keyPath==null||"".equals(keyPath)){
			return;
		}
		if(!keyPath.endsWith(File.separator)){
			keyPath=keyPath+File.separator;
		}
		File file=new File(keyPath);
		if(!file.exists()){
			file.mkdirs();
		}
		
		Map<String, Object> keyMap=Rsa.genKeyPair();
		String publicKey=Rsa.getPublicKey(keyMap);
		String privateKey=Rsa.getPrivateKey(keyMap);
		File publicFile=new File(keyPath+Rsa.PUBLIC_KEY_FILE_NAME);
		File privateFile=new File(keyPath+Rsa.PRIVATE_KEY_FILE_NAME);
		if(!publicFile.exists()){
			try {
				publicFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!privateFile.exists()){
			try {
				privateFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileOutputStream publicfos=null;
		FileOutputStream privatefos=null;
		try {
			publicfos=new FileOutputStream(publicFile);
			privatefos=new FileOutputStream(privateFile);
			publicfos.write(publicKey.getBytes());
			privatefos.write(privateKey.getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				publicfos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				privatefos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			publicfos=null;
			privatefos=null;
		}
		
	}
	/**
	 * 
	 * <p>Title: 用公钥加密数据(返回经过base64转码后的字符串)</p>
	 * <p>Description:  
	 * @param data 需要加密的明文
	 * @param publicKey 公钥
	 * @author jie.xu  2017年3月29日 下午12:00:58
	 */
	public static String encryptByPublicKey(String data,String publicKey){
		String encryptedData="";
			try {
				encryptedData= Base64Utils.encodeToString(Rsa.encryptByPublicKey(data.getBytes("utf-8"), publicKey));

			} catch (Exception e) {
				e.printStackTrace();
			}
		return encryptedData;
	}
	/**
	 * 
	 * <p>Title: 用私钥加密数据(返回经过base64转码后的字符串)</p>
	 * <p>Description:  
	 * @param data 要加密的数据
	 * @param privateKey 私钥
	 * @return
	 * @author jie.xu  2017年3月29日 下午1:20:40
	 */
	public static String encryptByPrivateKey(String data,String privateKey){
		String encryptedData="";
		try {
			encryptedData=Base64Utils.encodeToString(Rsa.encryptByPrivateKey(data.getBytes(), privateKey));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedData;
	}
	/**
	 * 
	 * <p>Title: 用私钥解密数据(返回明文字符串)</p>
	 * <p>Description:  
	 * @param base64Data 经过公钥加密并且转换为base64的字符串
	 * @param privateKey 私钥
	 * @author jie.xu  2017年3月29日 下午12:02:30
	 */
	public static String decryptByPrivateKey(String base64Data,String privateKey){
		byte[] encryptedDataByte=Base64Utils.decode(base64Data.getBytes());
		byte[] ddataByte=new byte[0];
		try {
			ddataByte=Rsa.decryptByPrivateKey(encryptedDataByte, privateKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(ddataByte);
	}
	/**
	 * 
	 * <p>Title: 用公钥解密数据(返回明文字符串)</p>
	 * <p>Description:  
	 * @param base64Data 经过公钥加密并且转换为base64的字符串
	 * @param publicKey 公钥
	 * @return
	 * @author jie.xu  2017年3月29日 下午1:21:50
	 */
	public static String decryptByPublicKey(String base64Data,String publicKey){
		byte[] encryptedDataByte=Base64Utils.decode(base64Data.getBytes());
		byte[] ddataByte=new byte[0];
		try {
			ddataByte=Rsa.decryptByPublicKey(encryptedDataByte, publicKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(ddataByte);
	}

	/**
	 * 
	 * <p>Title: 用私钥对信息生成数字签名(返回签名后并用base64转码后的字符串) </p>
	 * <p>Description:  
	 * @param data 要签名的数据
	 * @param privateKey 私钥
	 * @return
	 * @author jie.xu  2017年3月29日 下午1:33:24
	 */
	public static String sign(String data,String privateKey){
		String signString="";
		try {
			signString=Rsa.sign(data.getBytes(), privateKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return signString;
	}
	/**
	 * 
	 * <p>Title: 校验数字签名 </p>
	 * <p>Description:  
	 * @param data 需要校验的数据
	 * @param publicKey 公钥
	 * @param sign 签名
	 * @return
	 * @author jie.xu  2017年3月29日 下午1:39:24
	 */
	public static Boolean verify(String data,String publicKey,String sign){
		Boolean flag=Boolean.FALSE;
		try {
			flag=Rsa.verify(data.getBytes(), publicKey, sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}

abstract class Rsa {
	/**
	 * 公钥文件名字
	 */
	public static final String PUBLIC_KEY_FILE_NAME="public.key";
	/**
	 * 私钥文件名字
	 */
	public static final String PRIVATE_KEY_FILE_NAME="private.key";
	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * 签名算法
	 */
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/**
	 * 获取公钥的key
	 */
	private static final String PUBLIC_KEY = "RSAPublicKey";

	/**
	 * 获取私钥的key
	 */
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * 
	 * <p>
	 * Title: 生成公私钥
	 * </p>
	 * <p>
	 * Description:   
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author jie.xu 2017年3月29日 上午10:30:03
	 */
	public static Map<String, Object> genKeyPair() {
		KeyPairGenerator keyPairGen = null;
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		try {
			keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			keyPairGen.initialize(1024);
			KeyPair keyPair = keyPairGen.generateKeyPair();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			keyMap.put(PUBLIC_KEY, publicKey);
			keyMap.put(PRIVATE_KEY, privateKey);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return keyMap;
	}

	/**
	 * 
	 * <p>
	 * Title: 用私钥对信息生成数字签名
	 * </p>
	 * <p>
	 * Description:   
	 * </p>
	 * 
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 * @author jie.xu 2017年3月29日 上午10:40:46
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = Base64Utils.decode(privateKey.getBytes());
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(privateK);
		signature.update(data);
		return Base64Utils.encodeToString(signature.sign());
	}

	/**
	 * 
	 * <p>
	 * Title: 校验数字签名
	 * </p>
	 * <p>
	 * Description:   
	 * </p>
	 * 
	 * @param data
	 * @param publicKey
	 * @param sign
	 * @return
	 * @throws Exception
	 * @author jie.xu 2017年3月29日 上午10:40:19
	 */
	public static boolean verify(byte[] data, String publicKey, String sign)
			throws Exception {
		byte[] keyBytes = Base64Utils.decode(publicKey.getBytes());
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(publicK);
		signature.update(data);
		return signature.verify(Base64Utils.decode(sign.getBytes()));
	}

	/**
	 * 
	 * <p>
	 * Title: 根据私钥解密数据
	 * </p>
	 * <p>
	 * Description:   
	 * </p>
	 * 
	 * @param encryptedData
	 * @param privateKey
	 * @return
	 * @throws Exception
	 * @author jie.xu 2017年3月29日 上午10:39:46
	 */
	public static byte[] decryptByPrivateKey(byte[] encryptedData,
			String privateKey) throws Exception {
		byte[] keyBytes = Base64Utils.decode(privateKey.getBytes());
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher
						.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher
						.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/**
	 * 
	 * <p>
	 * Title: 根据公钥解密数据
	 * </p>
	 * <p>
	 * Description:   
	 * </p>
	 * 
	 * @param encryptedData
	 * @param publicKey
	 * @return
	 * @throws Exception
	 * @author jie.xu 2017年3月29日 上午10:39:21
	 */
	public static byte[] decryptByPublicKey(byte[] encryptedData,
			String publicKey) throws Exception {
		byte[] keyBytes = Base64Utils.decode(publicKey.getBytes());
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, publicK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher
						.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher
						.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/**
	 * 
	 * <p>
	 * Title: 根据公钥加密数据
	 * </p>
	 * <p>
	 * Description:   
	 * </p>
	 * 
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 * @author jie.xu 2017年3月29日 上午10:38:52
	 */
	public static byte[] encryptByPublicKey(byte[] data, String publicKey)
			throws Exception {
		byte[] keyBytes = Base64Utils.decode(publicKey.getBytes());
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	/**
	 * 
	 * <p>
	 * Title: 根据私钥加密数据
	 * </p>
	 * <p>
	 * Description:   
	 * </p>
	 * 
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 * @author jie.xu 2017年3月29日 上午10:38:18
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
			throws Exception {
		byte[] keyBytes = Base64Utils.decode(privateKey.getBytes());
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, privateK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	/**
	 * 
	 * <p>
	 * Title: 获取私钥(私钥数据都已经用base64转换成字符串)
	 * </p>
	 * <p>
	 * Description:   
	 * </p>
	 * 
	 * @param keyMap
	 * @return
	 * @author jie.xu 2017年3月29日 上午10:37:15
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return Base64Utils.encodeToString(key.getEncoded());
	}

	/**
	 * 
	 * <p>
	 * Title: 获取公钥(公钥数据都已经用base64转换成字符串)
	 * </p>
	 * <p>
	 * Description:   
	 * </p>
	 * 
	 * @param keyMap
	 * @return
	 * @author jie.xu 2017年3月29日 上午10:36:57
	 */
	public static String getPublicKey(Map<String, Object> keyMap) {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return Base64Utils.encodeToString(key.getEncoded());
	}
}
