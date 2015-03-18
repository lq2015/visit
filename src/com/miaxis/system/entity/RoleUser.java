package com.miaxis.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.miaxis.common.base.IdEntity;

/**
 * 角色用户表
 * @author liu.qiao
 *
 */
@Entity
@Table(name = "t_s_role_user")
public class RoleUser extends IdEntity implements java.io.Serializable {
	/**
	 * 角色ID
	 */
	private String roleId;
	/**
	 * 用户ID
	 */
	private String userId;

	@Column(name = "roleId", length = 32)
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "userId", length = 32)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}