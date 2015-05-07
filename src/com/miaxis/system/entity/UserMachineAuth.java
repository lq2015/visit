package com.miaxis.system.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.miaxis.common.base.IdEntity;

/**
 * 用户使用机器授权信息
 * @author liu.qiao
 *
 */
@Entity
@Table(name = "t_s_user_machine_auth")
public class UserMachineAuth extends IdEntity implements java.io.Serializable {
	
	/**
	 * 用户
	 */
	private String userid;
	/**
	 * 机器特征码
	 */
	private String machinecode;
	/**
	 * 机器名称
	 */
	private String machinename;
	/**
	 * 授权结束日期
	 */
	private Date authenddate;
	/**
	 * 备注
	 */
	private String memo;

	@Column(name = "USERID", length = 32)
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "MACHINECODE", length = 50)
	public String getMachinecode() {
		return this.machinecode;
	}

	public void setMachinecode(String machinecode) {
		this.machinecode = machinecode;
	}

	@Column(name = "MACHINENAME", length = 100)
	public String getMachinename() {
		return this.machinename;
	}

	public void setMachinename(String machinename) {
		this.machinename = machinename;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "AUTHENDDATE", length = 7)
	public Date getAuthenddate() {
		return this.authenddate;
	}

	public void setAuthenddate(Date authenddate) {
		this.authenddate = authenddate;
	}

	@Column(name = "MEMO", length = 200)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}