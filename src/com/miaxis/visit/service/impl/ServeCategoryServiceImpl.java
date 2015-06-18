package com.miaxis.visit.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.miaxis.common.base.service.CommonServiceImpl;
import com.miaxis.common.exception.BusinessException;
import com.miaxis.visit.entity.ServeCategory;
import com.miaxis.visit.service.ServeCategoryService;

@Service("serveCategoryService")
public class ServeCategoryServiceImpl extends CommonServiceImpl implements
		ServeCategoryService {

	@Override
	public void addServeCategory(ServeCategory serveCategory) {
		String category = serveCategory.getScCategory();

		/********************************************************
		 * 检测类别名称是否重复
		 ********************************************************/
		String hql = " from ServeCategory where  scCategory = '" + category+ "'";
		List<ServeCategory> list = this.commonDao.getListByHql(
				ServeCategory.class, hql);

		if (list.size() > 0) {
			throw new BusinessException("类别名称【" + category + "】已经存在,请更换新的类别名称。");
		}

		/********************************************************
		 * 根据父类ID,生成ID
		 * 所有层级的类别起始号为10;如10，11，12...
		 * 每层级的类别代码为2位(这里假定每一级类别不超过99个)，前面加上父类代码
		 ********************************************************/
		String parentCategory = serveCategory.getScParent();
		if(parentCategory==null) parentCategory="";
		String hql2 ="";
		String id = "";
		
		if(parentCategory.equals("")){
			hql2 = "select max(id) from ServeCategory where scLevel=1 ";
			id = (String)this.commonDao.getColumnValueByHql(hql2);
			if(id==null) id="";
			if(id.equals("")){
				id ="10";
			}
			
		}else{
			hql2 = "select max(id) from ServeCategory where  scParent =  '" + parentCategory+ "'";
			id = (String)this.commonDao.getColumnValueByHql(hql2);
			if(id==null) id="";
			if(id.equals("")){
				id = parentCategory.concat("10");
			}
		}
		
		Integer newId = Integer.parseInt(id)+1;
		id = newId.toString();
		serveCategory.setId(id);
		
		this.commonDao.save(serveCategory);
	}

	@Override
	public void updateServeCategory(ServeCategory serveCategory) {
		String id = serveCategory.getId();
		ServeCategory category = this.get(ServeCategory.class, id);
		if (category == null) {
			throw new BusinessException("服务类别【" + category.getScCategory()
					+ "】的信息不存在。");
		}

		/********************************************************
		 * 检测类别名称是否重复
		 ********************************************************/
		String hql = " from ServeCategory where  scCategory = '" + category
				+ "' and id<>'" + serveCategory.getId() + "'";
		List<ServeCategory> list = this.commonDao.getListByHql(
				ServeCategory.class, hql);

		if (list.size() > 0) {
			throw new BusinessException("类别名称【" + category + "】已经存在,请更换新的类别名称。");
		}
		
		this.commonDao.updateEntitie(serveCategory);
	}

	@Override
	public void deleteServeCategory(String id) {
		ServeCategory serveCategory = (ServeCategory) this.commonDao.get(ServeCategory.class, id);

		if (serveCategory == null) {
			throw new BusinessException("该服务类别信息不存在!");
		} 
		
		String hql = " from ServeCategory where  scParent = '" + id + "'";
		List<ServeCategory> list = this.commonDao.getListByHql(ServeCategory.class, hql);
		
		if(list.size()>0){
			throw new BusinessException("该服务类别存在子类别,不能删除!");
		}
			
		this.commonDao.delete(serveCategory);
	}
}
