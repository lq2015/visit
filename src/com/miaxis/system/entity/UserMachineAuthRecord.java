package com.miaxis.system.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.miaxis.common.base.IdEntity;

/**
 * 机器授权申请
 * @author liu.qiao
 *
 */
@Entity
@Table(name = "t_s_user_machine_auth_record")
@PrimaryKeyJoinColumn(name = "id")
public class UserMachineAuthRecord  extends IdEntity implements java.io.Serializable {
	
	/**
	 * 登陆用户名
	 */
	private String loginname;
	/**
	 * 机器特征
	 */
	private String machinecode;
	/**
	 * 机器名
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
	/**
	 * 审核人
	 */
	private String approveMan;
	/**
	 * 审核时间
	 */
	private Date approveTime;
	/**
	 * 0 申请 1审核通过 2审核不通过
	 */
	private String status;

	@Column(name = "LOGINNAME", length = 32)
	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
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
	@Column(name = "APPROVE_MAN", length = 32)
	public String getApproveMan() {
		return this.approveMan;
	}

	public void setApproveMan(String approveMan) {
		this.approveMan = approveMan;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "APPROVE_TIME", length = 7)
	public Date getApproveTime() {
		return this.approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}