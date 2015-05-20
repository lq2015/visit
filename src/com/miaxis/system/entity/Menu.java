package com.miaxis.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.miaxis.common.base.IdEntity;

/**
 * 系统菜单
 * @author liu.qiao
 *
 */
@Entity
@Table(name = "t_s_menu")
public class Menu  extends IdEntity {
	/**
	 * 菜单名称
	 */
	private String menuName;
	/**
	 * 链接页面
	 */
	private String linkPage;
	/**
	 * 菜单层级
	 */
	private Integer menuLevel;
	/**
	 * 排序号
	 */
	private Integer orderNum;
	/**
	 * 父菜单
	 */
	private String parentMenu;
	/**
	 * 图标索引
	 */
	private String icoIndex;
	/**
	 * 选中时图标索引
	 */
	private String selIcoIndex;
	/**
	 * 是否有效 0 无效 1有效
	 */
	private String isValid;
	/**
	 * easy ui treegrid展示时用
	 */
	private String _parentId;
	@Transient
	public String get_parentId() {
		return this.getParentMenu();
	}

	public void set_parentId(String _parentId) {
		this._parentId = _parentId;
	}

	@Column(name = "menuName", nullable = false, length = 100)
	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name = "linkPage", length = 100)
	public String getLinkPage() {
		return this.linkPage;
	}

	public void setLinkPage(String linkPage) {
		this.linkPage = linkPage;
	}

	@Column(name = "menuLevel", length = 1)
	public Integer getMenuLevel() {
		return this.menuLevel;
	}

	public void setMenuLevel(Integer menuLevel) {
		this.menuLevel = menuLevel;
	}

	@Column(name = "orderNum")
	public Integer getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	@Column(name = "parentMenu", length = 32)
	public String getParentMenu() {
		return this.parentMenu;
	}

	public void setParentMenu(String parentMenu) {
		this.parentMenu = parentMenu;
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

	@Column(name = "isValid", length = 1)
	public String getIsValid() {
		return this.isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

}