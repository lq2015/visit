package com.miaxis.visit.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.miaxis.common.base.service.CommonServiceImpl;
import com.miaxis.common.exception.BusinessException;
import com.miaxis.common.util.Base64Utils;
import com.miaxis.common.util.CommonUtil;
import com.miaxis.common.util.ContextHolderUtils;
import com.miaxis.common.util.DateUtil;
import com.miaxis.common.util.ImageUtils;
import com.miaxis.visit.service.PublicService;

@Service("publicService")
public class PublicServiceImpl extends CommonServiceImpl implements PublicService {

	@Override
	public Map uploadPic(MultipartFile fileUpload, String photoData, String savePath,boolean isMark) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		
		String yearmm = DateUtil.getYYYYMM(new Date());

		String relative_path = savePath; // 存储路径
		relative_path = relative_path+"/"+yearmm; //路径格式 upload/person/4位年2位月
		String full_path = request.getSession().getServletContext().getRealPath(relative_path); // 绝对路径
		
		File file = new File(full_path);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		String fileName = "";
		String filePath = "";
		String picData = "";

		/****************************************************
		 * Base64文件图片数据上传
		 ****************************************************/
		if (StringUtils.isNotEmpty(photoData)) {
			fileName = CommonUtil.fileNameGenerator("jpg");
			Base64Utils.GenerateImage(photoData, full_path + "/" + fileName);
		} else {
			/****************************************************
			 * 上传文件
			 ****************************************************/
			if (!fileUpload.getOriginalFilename().equals("")) {
				try {
					String[] suffixsRange = { "jpg", "gif", "jpeg", "png" };
					fileName = CommonUtil.uploadPic(fileUpload, full_path,
							null, suffixsRange);
				} catch (BusinessException e) {
					throw new BusinessException(e.getMessage());
				}
			}
		}

		if(!fileName.equals("")){
			/****************************************************
			 * 保存加时间水印
			 ****************************************************/
			if(isMark){
				File picture = new File(full_path + "/" + fileName);
				BufferedImage sourceImg = null;

				try {
					sourceImg = ImageIO.read(new FileInputStream(picture));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				if (sourceImg != null) {
					int _width = sourceImg.getWidth();
					int _height = sourceImg.getHeight();
					String nowDateStr = DateUtil.getNowdateFormat(new Date(),
							"yyyy-MM-dd HH:mm");
					ImageUtils.pressText(full_path + "/" + fileName, nowDateStr, "黑体",
							Font.BOLD | Font.ITALIC, 16, Color.RED,
							(_width / 2) - 40, (_height - 20), 0.3f);
				}
			}

			/****************************************************
			 * 提取处理后的照片Base64文件图片数据
			 ****************************************************/
			picData = Base64Utils.GetImageStr(full_path + "/" + fileName);

			if (!picData.equals("")) {
				filePath = relative_path + "/" + fileName;
			}
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("filePath", filePath);
		map.put("picData", picData);
		return map;
	}

	@Override
	public void generatePic(String photoData, String picName) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		
        if (picName != null && picName.length() > 0) {
        	picName = picName.replace("\\", "/");
        	String relative_path = picName.substring(0, picName.lastIndexOf("/"));
            String path = request.getSession().getServletContext().getRealPath(relative_path); // 绝对路径
            
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }

            String pohtoPath = request.getSession().getServletContext().getRealPath(picName); // 绝对路径
            File pohtofile = new File(pohtoPath);
            if (!pohtofile.exists()) {
                if(photoData!=null){
                    Base64Utils.GenerateImage(photoData, pohtoPath);
                }
            }
        }
	}


}
