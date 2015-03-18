package com.miaxis.system.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.miaxis.common.base.IdEntity;

/**
 * 系统菜单按钮
 * @author liu.qiao
 *
 */
@Entity
@Table(name = "t_s_function")
public class Function extends IdEntity  implements java.io.Serializable {
	/**
	 * 按钮中文名
	 */
	private String funNameCn;
	/**
	 * 按钮英文名
	 */
	private String funNameEn;
	/**
	 * 按钮事件url
	 */
	private String funUrl;
	/**
	 * 按钮所属菜单
	 */
	private String parentMenu;
	/**
	 * 按钮类型  1 菜单按钮  2下拉式按钮 3 下拉子菜单 
	 */
	private String funType;
	/**
	 * 下拉子菜单所属按钮
	 */
	private String parentFun;
	/**
	 * 排序号
	 */
	private Integer orderNum;
	/**
	 * 是否有效  0 不可用  1可用
	 */
	private String isValid;
	/**
	 * 图标索引
	 */
	private String icoIndex;
	/**
	 * 选中时图标索引
	 */
	private String selIcoIndex;
	/**
	 * easy ui treegrid展示时用
	 */
	private String _parentId;
	/**
	 * 下拉子菜单
	 */
	private List<Function> children;

	@Transient
	public String get_parentId() {
		return this.getParentFun();
	}

	public void set_parentId(String _parentId) {
		this._parentId = _parentId;
	}
	
	@Transient
	public List<Function> getChildren() {
		return children;
	}

	public void setChildren(List<Function> children) {
		this.children = children;
	}

	@Column(name = "funNameCn", length = 50)
	public String getFunNameCn() {
		return this.funNameCn;
	}

	public void setFunNameCn(String funNameCn) {
		this.funNameCn = funNameCn;
	}

	@Column(name = "funNameEn", length = 50)
	public String getFunNameEn() {
		return this.funNameEn;
	}

	public void setFunNameEn(String funNameEn) {
		this.funNameEn = funNameEn;
	}

	@Column(name = "funUrl", length = 100)
	public String getFunUrl() {
		return this.funUrl;
	}

	public void setFunUrl(String funUrl) {
		this.funUrl = funUrl;
	}

	@Column(name = "parentMenu", length = 32)
	public String getParentMenu() {
		return this.parentMenu;
	}

	public void setParentMenu(String parentMenu) {
		this.parentMenu = parentMenu;
	}

	@Column(name = "funType", length = 1)
	public String getFunType() {
		return this.funType;
	}

	public void setFunType(String funType) {
		this.funType = funType;
	}

	@Column(name = "parentFun", length = 32)
	public String getParentFun() {
		return this.parentFun;
	}

	public void setParentFun(String parentFun) {
		this.parentFun = parentFun;
	}

	@Column(name = "orderNum")
	public Integer getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	@Column(name = "isValid", length = 1)
	public String getIsValid() {
		return this.isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	@Column(name = "icoIndex", length = 30)
	public String getIcoIndex() {
		return this.icoIndex;
	}

	public void setIcoIndex(String icoIndex) {
		this.icoIndex = icoIndex;
	}

	@Column(name = "selIcoIndex", length = 30)
	public String getSelIcoIndex() {
		return this.selIcoIndex;
	}

	public void setSelIcoIndex(String selIcoIndex) {
		this.selIcoIndex = selIcoIndex;
	}

}