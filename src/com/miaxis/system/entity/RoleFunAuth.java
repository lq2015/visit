package com.miaxis.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.miaxis.common.base.IdEntity;

/**
 * 角色菜单功能点权限
 * @author liu.qiao
 *
 */
@Entity
@Table(name = "t_s_role_fun_auth")
public class RoleFunAuth  extends IdEntity  implements java.io.Serializable {
	
	private String roleId;
	private String menuId;
	private String funId;

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

	@Column(name = "funId", nullable = false, length = 32)
	public String getFunId() {
		return this.funId;
	}

	public void setFunId(String funId) {
		this.funId = funId;
	}

}