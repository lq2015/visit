package com.miaxis.visit.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.miaxis.common.base.service.CommonService;
import com.miaxis.visit.entity.PersonInfo;

/**
 * 人员信息管理
 * @author liu.qiao
 * 
 */
public interface PersonService extends CommonService {
	public void addPerson(PersonInfo personInfo,Map fingerMap,String photoData,String certData);
	public void updatePerson(PersonInfo personInfo,Map fingerMap,String photoData,String certData);
	public void delePerson(Integer personId);
	public void updateStatus(Integer id, String status);
}
