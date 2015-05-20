package com.miaxis.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.miaxis.common.base.IdEntity;

/**
 * 角色菜单权限表
 */
@Entity
@Table(name = "t_s_role_menu_auth" )
public class RoleMenuAuth  extends IdEntity  {
	
	private String roleId;
	private String menuId;

	@Column(name = "roleId", nullable = false, length = 32)
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "menuId", nullable = false, length = 32)
	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

}