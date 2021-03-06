package com.miaxis.visit.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.miaxis.common.base.service.CommonServiceImpl;
import com.miaxis.common.exception.BusinessException;
import com.miaxis.common.util.ContextHolderUtils;
import com.miaxis.system.entity.User;
import com.miaxis.visit.entity.UnitPact;
import com.miaxis.visit.entity.UnitPactPic;
import com.miaxis.visit.service.UnitPactService;

@Service("unitPactService")
public class UnitPactServiceImpl extends CommonServiceImpl implements UnitPactService {

	@Override
	public void addUnitPact(UnitPact unitPact,List list) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		User user = (User) request.getSession().getAttribute("userSession");
		
		unitPact.setUpOperateTime(new Date());
		unitPact.setUpOperator(user.getId());
		unitPact.setUpStatus(UnitPact.Status.INPUT.getCode());
		Integer pactId = (Integer)this.commonDao.save(unitPact);
		
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			String picUrl = (String)map.get("filePath");
			String picData = (String)map.get("picData");
			
			UnitPactPic unitPactPic = new UnitPactPic();
			unitPactPic.setUpPactId(pactId);
			unitPactPic.setUpPic(picData);
			unitPactPic.setUpPicUrl(picUrl);
			this.commonDao.save(unitPactPic);
		}
	}

	@Override
	public void updateUnitPact(UnitPact unitPact,List list) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		User user = (User) request.getSession().getAttribute("userSession");
		
		Integer id = unitPact.getId();
		UnitPact unit = this.get(UnitPact.class, id);
		if(unit==null){
			throw new BusinessException("服务合同【"+unitPact.getUpNumber()+"】的信息不存在。" );
		}
		
		unitPact.setUpOperateTime(new Date());
		unitPact.setUpOperator(user.getId());
		unitPact.setUpStatus(unit.getUpStatus());
		this.commonDao.updateEntitie(unitPact);
		
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			String picUrl = (String)map.get("filePath");
			String picData = (String)map.get("picData");
			
			UnitPactPic UnitPactPic = new UnitPactPic();
			UnitPactPic.setUpPactId(unitPact.getId());
			UnitPactPic.setUpPic(picData);
			UnitPactPic.setUpPicUrl(picUrl);
			this.commonDao.save(UnitPactPic);
		}
	}

	@Override
	public void deleUnitPact(Integer id) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		User user = (User) request.getSession().getAttribute("userSession");
		
		UnitPact unitPact = this.commonDao.get(UnitPact.class, id);
		if (unitPact == null) {
			throw new BusinessException("要删除服务合同信息不存在!");
		} 
		
		/*****************************************
		 * 删除该合同的下的图片数据
		 *****************************************/
		List<UnitPactPic> list = this.commonDao.getListByHql(UnitPactPic.class, "from UnitPactPic where upPactId="+id);
		for(UnitPactPic unitPactPic :list){
			this.commonDao.delete(unitPactPic);
		}
			
		this.commonDao.delete(unitPact);
	}

	@Override
	public void updateStatus(Integer id, String status) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		User user = (User) request.getSession().getAttribute("userSession");
		
		UnitPact unitPact = this.commonDao.get(UnitPact.class, id);
		if (unitPact == null) {
			throw new BusinessException("要修改服务合同信息不存在!");
		} else {
			unitPact.setUpStatus(status);
			this.commonDao.updateEntitie(unitPact);
		}
	}
}
