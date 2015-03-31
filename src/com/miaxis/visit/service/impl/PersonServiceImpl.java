package com.miaxis.visit.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.miaxis.common.base.service.CommonServiceImpl;
import com.miaxis.common.exception.BusinessException;
import com.miaxis.common.util.ContextHolderUtils;
import com.miaxis.system.entity.User;
import com.miaxis.visit.entity.FingerInfo;
import com.miaxis.visit.entity.PersonCert;
import com.miaxis.visit.entity.PersonInfo;
import com.miaxis.visit.entity.PersonPhoto;
import com.miaxis.visit.entity.UnitInfo;
import com.miaxis.visit.service.PersonService;

@Service("personService")
public class PersonServiceImpl extends CommonServiceImpl implements PersonService {

	@Override
	public void addPerson(PersonInfo personInfo,Map fingerMap,String photoData,String certData) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		User user = (User) request.getSession().getAttribute("userSession");
		
		/****************************************************
		 * 保存基本信息
		 ****************************************************/
		personInfo.setPiStatus(PersonInfo.Status.NORMAL.getCode());
		personInfo.setPiCrdate(new Date());
		personInfo.setPiOperator(user.getId());
		personInfo.setPiOperateTime(new Date());
		
		UnitInfo unitInfo = this.get(UnitInfo.class, personInfo.getPiWorkUnit());
		personInfo.setUnitInfo(unitInfo);
		Integer persontId = (Integer) this.commonDao.save(personInfo);
		
		/****************************************************
		 * 保存人员照片信息
		 ****************************************************/
		if(!photoData.equals("")){
			PersonPhoto personPhoto = new PersonPhoto();
			personPhoto.setId(persontId);
			personPhoto.setPhPhoto(photoData);
			this.commonDao.save(personPhoto);
		}
		
		/****************************************************
		 * 保存人员证件信息
		 ****************************************************/
		if(!certData.equals("")){
			PersonCert personCert = new PersonCert();
			personCert.setId(personInfo.getId());
			personCert.setPcCert(certData);
			this.commonDao.save(personCert);
		}
		
		/****************************************************
		 * 保存指纹信息
		 ****************************************************/
		this.commonDao.executeHql("delete from FingerInfo where fiPerson="+persontId);
		
		for(int i=0;i<10;i++){
			String fiTemplate=(String)fingerMap.get("finger"+i);
			if(StringUtils.isNotEmpty(fiTemplate)){
				FingerInfo fingerInfo = new FingerInfo();
				fingerInfo.setFiCode(i);
				fingerInfo.setFiName(fingerInfo.getCodeName(i));
				fingerInfo.setFiTemplate(fiTemplate);
				fingerInfo.setFiPerson(persontId);
				fingerInfo.setFiGatherer(user.getId());
				fingerInfo.setFiGatherTime(new Date());
				this.commonDao.save(fingerInfo);
			}
		}
	}

	@Override
	public void updatePerson(PersonInfo personInfo,Map fingerMap,String photoData,String certData) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		User user = (User) request.getSession().getAttribute("userSession");
		if(photoData==null) photoData="";
		
		Integer id = personInfo.getId();
		PersonInfo person = this.get(PersonInfo.class, id);
		if(person==null){
			throw new BusinessException("人员【"+personInfo.getPiName()+"】的记录信息不存在。" );
		}
		
		/****************************************************
		 * 更新基本信息
		 ****************************************************/
		personInfo.setPiStatus(person.getPiStatus());
		personInfo.setPiCrdate(person.getPiCrdate());
		
		UnitInfo unitInfo = this.get(UnitInfo.class, personInfo.getPiWorkUnit());
		personInfo.setUnitInfo(unitInfo);
		
		/****************************************************
		 * 更新人员照片信息,不更新时,取原来的路径
		 ****************************************************/
		if(photoData.equals("")){
			personInfo.setPiPhotoUrl(person.getPiPhotoUrl());
		}else{
			PersonPhoto personPhoto = new PersonPhoto();
			personPhoto.setId(personInfo.getId());
			personPhoto.setPhPhoto(photoData);
			this.commonDao.updateEntitie(personPhoto);
		}
		
		/****************************************************
		 * 更新人员证件信息，不更新时,取原来的路径
		 ****************************************************/
		if(certData.equals("")){
			personInfo.setPiCertUrl(person.getPiCertUrl());
		}else{
			PersonCert personCert = new PersonCert();
			personCert.setId(personInfo.getId());
			personCert.setPcCert(certData);
			this.commonDao.updateEntitie(personCert);
		}
		
		personInfo.setPiOperateTime(new Date());
		personInfo.setPiOperator(user.getId());
		this.commonDao.updateEntitie(personInfo);
		
		
		/****************************************************
		 * 更新指纹信息
		 ****************************************************/
		this.commonDao.executeHql("delete from FingerInfo where fiPerson="+personInfo.getId());
		
		for(int i=0;i<10;i++){
			String fiTemplate=(String)fingerMap.get("finger"+i);
			if(StringUtils.isNotEmpty(fiTemplate)){
				FingerInfo fingerInfo = new FingerInfo();
				fingerInfo.setFiCode(i);
				fingerInfo.setFiName(fingerInfo.getCodeName(i));
				fingerInfo.setFiTemplate(fiTemplate);
				fingerInfo.setFiPerson(personInfo.getId());
				fingerInfo.setFiGatherer(user.getId());
				fingerInfo.setFiGatherTime(new Date());
				this.commonDao.save(fingerInfo);
			}
		}
	}
	

	@Override
	public void delePerson(Integer personId) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		User user = (User) request.getSession().getAttribute("userSession");
		
		PersonInfo personInfo = this.commonDao.get(PersonInfo.class, personId);
		if (personInfo == null) {
			throw new BusinessException("删除记录学员信息不存在!");
		} else {
//			if(!personInfo.getPiStatus().equals("0")){
//				throw new BusinessException("人员【"+personInfo.getPiName()+"】已经正常启用，不能进行删除!");
//			}
			
			/****************************************************
			 * 删除人员照片
			 ****************************************************/
			PersonPhoto personPhoto = this.commonDao.get(PersonPhoto.class,personId);
			if(personPhoto!=null){
				this.commonDao.delete(personPhoto);
			}
			
			/****************************************************
			 * 删除人员证件信息
			 ****************************************************/
			PersonCert personCert = this.commonDao.get(PersonCert.class,personId);
			if(personCert!=null){
				this.commonDao.delete(personCert);
			}
			
			/****************************************************
			 * 删除人员指纹
			 ****************************************************/
			List<FingerInfo> list = (List<FingerInfo>)this.getListByHql(FingerInfo.class,
					" from FingerInfo t where  t.fiPerson=" + personId );
			
			for(FingerInfo fingerInfo :list){
				this.commonDao.delete(fingerInfo);
			}
			
			/****************************************************
			 * 删除人员
			 ****************************************************/
			this.commonDao.delete(personInfo);
		}
	}
	
	/**
	 * 更新状态
	 */
	public void updateStatus(Integer id, String status){
		HttpServletRequest request = ContextHolderUtils.getRequest();
		User user = (User) request.getSession().getAttribute("userSession");
		
		PersonInfo personInfo = this.commonDao.get(PersonInfo.class, id);
		if (personInfo == null) {
			throw new BusinessException("修改记录学员信息不存在!");
		} else {
			personInfo.setPiStatus(status);
			this.commonDao.updateEntitie(personInfo);
		}
	}
}
