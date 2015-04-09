package com.miaxis.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.miaxis.common.exception.BusinessException;

/**
 * 一般工具类
 * 
 * @author Fan.YuFeng
 * @param <T>
 * 
 */
public class CommonUtil<T> {
	/**
	 * WINDOWS行分隔符
	 */
	public static final String LINE_SEPARATOR_WINDOWS = "\r\n";

	/**
	 * 文件最大容量
	 */
	public static final Integer FILE_MAX_SIZE = 10 * 1024 * 1024;
	/**
	 * 文件存储路径
	 * 
	 * @param length
	 * @return
	 */
	public static final String PERSON_PICTURE = "upload/person"; //人员照片
	public static final String UNIT_PACT = "upload/pact";  //合同附件
	public static final String JOB = "upload/job";  //维修单
	public static final String UPLOAD_TEMP = "upload/temp";

	// 获取随机数
	public static String getCardId(int length) {
		String val = "";

		Random random = new Random();
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

			if ("char".equalsIgnoreCase(charOrNum)) // 字符串
			{
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) // 数字
			{
				val += String.valueOf(random.nextInt(10));
			}
		}

		return val;
	}

	/**
	 * 图片文件后缀
	 */
	public static final List<String> IMAGER_SUFFIX = new ArrayList<String>();
	static {
		IMAGER_SUFFIX.add("jpg");
		IMAGER_SUFFIX.add("gif");
		IMAGER_SUFFIX.add("png");
		IMAGER_SUFFIX.add("bmp");
		IMAGER_SUFFIX.add("jpeg");
	}

	/**
	 * 文件名流水号长度
	 */
	public static final int SERIAL_NUM_LENGTH = 10000;

	/**
	 * 获取文件大小
	 * 
	 * @param filePath
	 */
	public static int getFileByte(String filePath) {
		File f = new File(filePath);
		try {
			FileInputStream fis = new FileInputStream(f);
			try {
				int fileSize = fis.available();
				return fileSize;
			} catch (IOException e1) {
				e1.printStackTrace();
				return 0;
			}
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
			return 0;
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true,否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 文件名生成策略：日期+随机数
	 * 
	 * @param suffix
	 *            文件后缀
	 * @return
	 */
	public static String fileNameGenerator(String suffix) {
		Calendar now = new GregorianCalendar();
		// now.add(Calendar.MONTH, 1);
		int month = now.get(Calendar.MONTH);
		month = month + 1;

		String result = ""
				+ now.get(Calendar.YEAR)
				+ month
				+ now.get(Calendar.DAY_OF_MONTH)
				+ now.get(Calendar.HOUR_OF_DAY)
				+ now.get(Calendar.MINUTE)
				+ now.get(Calendar.SECOND)
				+ now.get(Calendar.MILLISECOND)
				+ "_"
				+ (new Random((new Date()).hashCode()))
						.nextInt(SERIAL_NUM_LENGTH) + "." + suffix;

		return result;
	}

	/**
	 * 文件名生成策略：日期+随机数
	 * 
	 * @param suffix
	 *            文件后缀
	 * @return
	 */
	public static String filePathGenerator() {
		Calendar now = new GregorianCalendar();

		String result = ""
				+ now.get(Calendar.YEAR)
				+ now.get(Calendar.MONTH)
				+ now.get(Calendar.DAY_OF_MONTH)
				+ now.get(Calendar.HOUR_OF_DAY)
				+ now.get(Calendar.MINUTE)
				+ now.get(Calendar.SECOND)
				+ now.get(Calendar.MILLISECOND)
				+ "_"
				+ (new Random((new Date()).hashCode()))
						.nextInt(SERIAL_NUM_LENGTH);

		return result;
	}

	/**
	 * 获取资源文件配置的某个key的值
	 * 
	 * @param entityName
	 *            当前class A.class
	 * @param resourse
	 *            资源文件的名字 b.properties
	 * @param pathKey
	 *            关键字
	 * @return 关键字的值
	 */
	public static String getKeyValue(String resourse, String pathKey) {
		InputStream ips = CommonUtil.class.getClassLoader()
				.getResourceAsStream(resourse);
		Properties p = new Properties();
		try {
			p.load(ips);
			return p.getProperty(pathKey);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param fileUpload
	 * @param path
	 * @return
	 */
	public static String uploadPic(MultipartFile fileUpload, String path,
			String newFileName, String[] suffixsRange) {
		String fileName = fileUpload.getOriginalFilename();
		if ("".equals(fileName) || fileName == null) {
			return "";
		}

		// 限定文件大小
		if (fileUpload.getSize() > 800000) {
			throw new BusinessException("上传文件大小不能超过80K!");
		}

		String[] suffixs = fileName.split("\\.");
		String suffix = suffixs[suffixs.length - 1];

		// 判断文件类型
		if (suffixsRange.length > 0) {
			String suffixsRangeStr = "";
			for (int i = 0; i < suffixsRange.length; i++) {
				if (suffixsRangeStr.equals("")) {
					suffixsRangeStr = suffixsRange[i];
				} else {
					suffixsRangeStr = suffixsRangeStr + "," + suffixsRange[i];
				}
			}

			if (suffixsRangeStr.indexOf(suffix) < 0) {
				throw new BusinessException("只支持文件类型为【" + suffixsRangeStr
						+ "】的文件!");
			}
		}

		if (newFileName == null || newFileName.equals("")) {
			// 转化后的文件名字
			newFileName = CommonUtil.fileNameGenerator(suffix);
		} else {
			newFileName = newFileName + "." + suffix;
		}

		// 生成文件
		File targetFile = new File(path, newFileName);
		try {
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			fileUpload.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newFileName;
	}

	public static byte[] readFile(String filePath) throws Exception {
		InputStream fis = null;
		byte[] buf = new byte[0];
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			buf = new byte[fis.available()];

			int len = 0;
			int temp = 0; // 所有读取的内容都使用temp接收
			while ((temp = fis.read()) != -1) { // 当没有读取完时，继续读取
				buf[len] = (byte) temp;
				len++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("读取文件失败：" + filePath);
		} finally {
			try {
				fis.close();
				fis = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return buf;
	}

	/**
	 * 32位MD5加密
	 * 
	 * @param inputStr
	 *            被加密字符串
	 * @return 加密字符串
	 */
	public static String md5(String inputStr) {
		String s = null;

		byte[] source = inputStr.getBytes();
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 获取用户真实的IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr() {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		// 代理头信息
		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}
	
	/**
	 * 检测相对路径的文件是否在本地存在
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExists(String fileName) {
		if(fileName==null) fileName="";
		if(!fileName.equals("")){
			HttpServletRequest request = ContextHolderUtils.getRequest();
			String filePath = request.getSession().getServletContext()
					.getRealPath(fileName); // 绝对路径
			File file = new File(filePath);
			return file.exists();
		}else{
			return true;
		}
		
	}
}
