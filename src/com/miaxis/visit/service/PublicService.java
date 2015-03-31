package com.miaxis.visit.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.miaxis.common.base.service.CommonService;

/**
 * 公共服务类
 * @author liu.qiao
 * 
 */
public interface PublicService extends CommonService {
	/**
	 * 上传照片，支持图片文件和base64数据两种方式上传
	 * @param fileUpload 
	 * @param photoData  BASE64
	 * @param path   本地保存的路径
	 * @param isMark 是否加水印标志
	 * @return  
	 */
	public Map uploadPic(MultipartFile fileUpload, String photoData,String savePath,boolean isMark);
	/**
	 * 根据BASE64照片数据生成图片
	 * @param photoData
	 */
	public void generatePic(String photoData,String path);
}
