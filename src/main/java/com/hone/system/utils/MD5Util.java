package com.hone.system.utils;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;

/**
 * 
 * @author wangkai
 * @2016年5月31日 下午8:33:41
 * @desc:MD5工具
 */
public class MD5Util {

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	/**
	 * md5直接加密
	 * @param key		需要加密字符串
	 * @return
	 */
	public static String Md5DirectEncryption(String key) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(key.getBytes("utf-8"),0,key.getBytes("utf-8").length);
		return Hex.encodeHexString(md5.digest());
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
}
