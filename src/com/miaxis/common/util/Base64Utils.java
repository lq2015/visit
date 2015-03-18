package com.miaxis.common.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;

/**
 * BASE64转换工具
 * 
 */
public class Base64Utils {
	public static String convertStrToBase64(String str) {
		byte[] b = str.getBytes();
		Base64 base64 = new Base64();
		b = base64.encode(b);
		String s = new String(b);
		return s;

	}

	public static String convertBase64ToStr(String str) {
		try {
			 byte[] b=str.getBytes();  
			 Base64 base64=new Base64();  
			 b=base64.decode(b);  
			 String s=new String(b);  
			 return s;  

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 图片转
	 * 
	 * @param imgFilePath
	 * @return
	 */
	public static String GetImageStr(String imgFilePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		byte[] data = null;

		// 读取图片字节数组
		try {
			InputStream in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 对字节数组Base64编码
		Base64 base64 = new Base64();
		byte[] b = base64.encodeBase64(data);
		String s = new String(b);
		return s;// 返回Base64编码过的字节数组字符串
	}

	/**
	 * 转成图片文件
	 * 
	 * @param imgStr
	 * @param imgFilePath
	 * @return
	 */
	public static boolean GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return false;
		Base64 base64 = new Base64();
		try {
			// Base64解码
			byte[] bytes = base64.decodeBase64(imgStr);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
}
