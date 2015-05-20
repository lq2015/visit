package com.miaxis.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.miaxis.common.base.IdEntity;


/**
 * 系统角色
 * @author liu.qiao
 *
 */
@Entity
@Table(name = "t_s_role")
public class Role extends IdEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 是否固化  0 非固化 1固化
	 */
	private String disable; 
	/**
	 * 备注
	 */
	private String memo;

	@Column(name = "rolename", nullable = false, length = 100)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	@Column(name = "disable", nullable = false, length = 2)
	public String getDisable() {
		return disable;
	}
	
	public void setDisable(String disable) {
		this.disable = disable;
	}
	
	@Column(name = "memo", nullable = false, length = 256)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}