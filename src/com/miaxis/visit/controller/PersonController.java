package com.miaxis.visit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.miaxis.common.base.CommonController;
import com.miaxis.common.exception.BusinessException;
import com.miaxis.common.util.CommonUtil;
import com.miaxis.common.util.PageConfig;
import com.miaxis.common.util.QueryCondition;
import com.miaxis.system.entity.User;
import com.miaxis.visit.entity.FingerInfo;
import com.miaxis.visit.entity.PersonCert;
import com.miaxis.visit.entity.PersonInfo;
import com.miaxis.visit.entity.PersonPhoto;
import com.miaxis.visit.entity.UnitInfo;
import com.miaxis.visit.service.PersonService;
import com.miaxis.visit.service.PublicService;

/**
 * 访客人员管理
 * 
 * @author liu.qiao
 * 
 */

@Controller
@RequestMapping("/person")
public class PersonController extends CommonController {
	@Autowired
	public PersonService personService;
	@Autowired
	public PublicService publicService;
	
	
	/**
	 * 主页
	 * 
	 * @param role
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "main")
	public ModelAndView main(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = this.getModelMainMav("WEB-INF/pages/visit/person/list");
		return mav;
	}
	
	/**
	 * 获取列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "list")
	@ResponseBody
	public Map list(String page, String sort, String order, String rows,String qPiWorkUnit,String qPiName) {
		
		/**
		 * 初始化分页对象
		 */
		PageConfig pageConfig = new PageConfig();
		pageConfig.setPaging(false);
		
		QueryCondition qc = new QueryCondition();
		if(StringUtils.isNotEmpty(sort)){
			if (StringUtils.equals(order.toUpperCase(), QueryCondition.DESC)){
				qc.desc(sort);
			}else{
				qc.asc(sort);
			}
		}
		qc.asc("id");
		
		if (StringUtils.isNotEmpty(qPiWorkUnit)) {
			qc.eq("unitInfo.id", Integer.parseInt(qPiWorkUnit));
		}
		
		if (StringUtils.isNotEmpty(qPiName)) {
			qc.like("piName", qPiName.concat("%"));
		}
		
		List list = commonService.getPageList(PersonInfo.class,pageConfig,qc);
		return this.buidResultMap(list,list.size());
	}


	/**
	 * 修改或新增
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "insertOrUpdate")
	public ModelAndView insertOrUpdate(Integer id,String operationType,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("WEB-INF/pages/visit/person/detail");
		User user = (User) request.getSession().getAttribute("userSession");
		String photoData="";
		String certData="";

		if (operationType.equals("edit")) {
			PersonInfo personInfo = commonService.get(PersonInfo.class, id);
			
			/****************************************************
			 * 判断图片文件是否存在，不存在，根据Base64数据创建图片文件，加载人员图片数据
			 ****************************************************/
			if(!CommonUtil.isFileExists(personInfo.getPiPhotoUrl())){
				PersonPhoto personPhoto = this.commonService.get(PersonPhoto.class, personInfo.getId());
				if(personPhoto!=null){
					photoData = personPhoto.getPhPhoto();
					publicService.generatePic(photoData, personInfo.getPiPhotoUrl());	
				}
			}
			
			/****************************************************
			 * 判断证件文件是否存在，不存在，根据Base64数据创建图片文件，加载人员图片数据
			 ****************************************************/
			if(!CommonUtil.isFileExists(personInfo.getPiCertUrl())){
				PersonCert personCert = this.commonService.get(PersonCert.class, personInfo.getId());
				if(personCert!=null){
					certData = personCert.getPcCert();
					publicService.generatePic(certData, personInfo.getPiCertUrl());	
				}
			}
			
			/****************************************************
			 * 加载人员指纹信息
			 ****************************************************/
			String hql = " from FingerInfo t WHERE  t.fiPerson=" + personInfo.getId();
			List<FingerInfo> fingerInfoList = (List<FingerInfo>) commonService.getListByHql(FingerInfo.class, hql);
		
			for (FingerInfo fingerInfo : fingerInfoList) {
				mav.getModelMap().put("finger" + fingerInfo.getFiCode(),fingerInfo.getFiTemplate());
			}
			
			mav.getModelMap().put("fingerInfoList", fingerInfoList);
			mav.getModelMap().put("personInfo", personInfo);
			
			mav.getModelMap().put("operationType", "edit");
		} else {
			mav.getModelMap().put("operationType", "insert");
		}

		return mav;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "del", method = RequestMethod.POST)
	@ResponseBody
	public Map del(Integer id) {

		try {
			personService.delePerson(id);
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage(),"1");
		} catch (Exception e) {
			return this.buidMessageMap("人员信息删除失败!", "1");
		}
		return this.buidMessageMap("人员信息删除成功!",	"0");
	}

	/**
	 * 保存
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public Map save(@RequestParam(value = "files", required = false) MultipartFile[] files ,
			PersonInfo personInfo, HttpServletRequest request) {
		String operationType = request.getParameter("operationType");
		String photoData = request.getParameter("photoData");
		String certData ="";
		
		String msg = operationType.equals("edit") ? "修改" : "新增";
		
		/****************************************************
		 * 上传图片处理
		 ****************************************************/
		MultipartFile photofile = files[0];//照片文件
		MultipartFile certfile	= files[1];//证件文件
		
		/****************************************************
		 * 指纹信息
		 ****************************************************/
		Map<String, String> fingerMap = new HashMap<String, String>();
		for(int i=0;i<10;i++){
			String finger=request.getParameter("finger"+i);
			fingerMap.put("finger"+i, finger);
		}
		
		try {
			/****************************************************
			 * 照片数据保存
			 ****************************************************/
			Map<String, String> picMap = publicService.uploadPic(photofile, photoData, 
					CommonUtil.PERSON_PICTURE, false);
			
			if (!picMap.get("filePath").equals("")) {
				personInfo.setPiPhotoUrl(picMap.get("filePath"));
				photoData = picMap.get("picData");
			}
			
			/****************************************************
			 * 证件数据保存
			 ****************************************************/
			Map<String, String> certMap = publicService.uploadPic(certfile, null, 
					CommonUtil.PERSON_PICTURE, false);
			
			if (!certMap.get("filePath").equals("")) {
				personInfo.setPiCertUrl(certMap.get("filePath"));
				certData = certMap.get("picData");
			}
			
			if (operationType.equals("edit")) {
				personService.updatePerson(personInfo, fingerMap, photoData,certData);
			} else {
				personService.addPerson(personInfo, fingerMap, photoData,certData);
			}
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage(),"1");
		} catch (Exception e) {
			return this.buidMessageMap(msg + "人员失败", "1");
		}

		return this.buidMessageMap(msg + "人员成功", "0");
	}
	
	/**
	 * 修改记录状态
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(params = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map updateStatus(Integer id,String status) {
		try {
			personService.updateStatus(id,status);
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage(),"1");
		} catch (Exception e) {
			e.printStackTrace();
			return this.buidMessageMap("修改记录状态失败", "1");
		}
		return this.buidMessageMap("修改记录操作成功!","0");
	}
	
	/**
	 * 获取单位
	 * @return
	 */
	@RequestMapping(params = "getWorkUnit")
	@ResponseBody
	public List getWorkUnit() {
		String  hql = " from UnitInfo where  uiStatus = '1'" ;
		List<UnitInfo> list = commonService.getListByHql(UnitInfo.class, hql);
		return list;
	}
}