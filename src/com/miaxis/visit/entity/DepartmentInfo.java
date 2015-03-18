package com.miaxis.visit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 银行部门信息表
 * @author liu.qiao
 *
 */
@Entity
@Table(name = "vt_department_info")
public class DepartmentInfo implements java.io.Serializable {

	/**
	 * 部门编码
	 */
	private String id;
	/**
	 * 部门名称
	 */
	private String diName;
	/**
	 * 联系人
	 */
	private String diLinkman;
	/**
	 * 联系电话
	 */
	private String diTelephone;
	/**
	 * 备注
	 */
	private String diMemo;
	/**
	 * 状态：0  正常  9注销
	 */
	private String diStatus;

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 6)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "DI_NAME", nullable = false, length = 50)
	public String getDiName() {
		return this.diName;
	}

	public void setDiName(String diName) {
		this.diName = diName;
	}

	@Column(name = "DI_LINKMAN", length = 20)
	public String getDiLinkman() {
		return this.diLinkman;
	}

	public void setDiLinkman(String diLinkman) {
		this.diLinkman = diLinkman;
	}

	@Column(name = "DI_TELEPHONE", length = 20)
	public String getDiTelephone() {
		return this.diTelephone;
	}

	public void setDiTelephone(String diTelephone) {
		this.diTelephone = diTelephone;
	}

	@Column(name = "DI_MEMO", length = 250)
	public String getDiMemo() {
		return this.diMemo;
	}

	public void setDiMemo(String diMemo) {
		this.diMemo = diMemo;
	}

	@Column(name = "DI_STATUS", length = 1)
	public String getDiStatus() {
		return this.diStatus;
	}

	public void setDiStatus(String diStatus) {
		this.diStatus = diStatus;
	}

}