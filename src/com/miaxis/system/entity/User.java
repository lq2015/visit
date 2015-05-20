package com.miaxis.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.miaxis.common.base.IdEntity;

/**
 * 系统用户
 * @author liu.qiao
 *
 */
@Entity
@Table(name = "t_s_user")
@PrimaryKeyJoinColumn(name = "id")
public class User extends IdEntity {
	private static final long serialVersionUID = 1L;
	public static String initial_Pwd = "123456";
	
	private String userName;// 用户名
	private String realName;// 真实姓名
	private String password;// 用户密码
	private String status;// 状态 0 录入 1启用  2注销
	private String mobilePhone;// 手机
	private String officePhone;// 办公电话
	private String email;// 邮箱
	private String departmant; //所属部门
	private String persontype; //人员类型 1驾校  2管理部门 3运维机构
	private String fix; //是否固化  0 非固化 1固化

	@Column(name = "username", nullable = false, length = 10)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "realname", length = 50)
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(name = "password", length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "mobilePhone", length = 30)
	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name = "officePhone", length = 20)
	public String getOfficePhone() {
		return this.officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "status")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "DEPARTMANT", length = 20)
	public String getDepartmant() {
		return this.departmant;
	}

	public void setDepartmant(String departmant) {
		this.departmant = departmant;
	}

	@Column(name = "PERSONTYPE", length = 1)
	public String getPersontype() {
		return this.persontype;
	}

	public void setPersontype(String persontype) {
		this.persontype = persontype;
	}
	
	@Column(name = "FIX", length = 1)
	public String getFix() {
		return fix;
	}

	public void setFix(String fix) {
		this.fix = fix;
	}
}