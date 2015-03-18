package com.miaxis.visit.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 服务单位信息
 * @author liu.qiao
 *
 */
@Entity
@Table(name = "vt_unit_info")
public class UnitInfo implements java.io.Serializable {
	
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 单位名称
	 */
	private String uiName;
	/**
	 * 联系地址
	 */
	private String uiAddress;
	/**
	 * 联系人
	 */
	private String uiLinkman;
	/**
	 * 联系电话
	 */
	private String uiTelephone;
	/**
	 * 联系手机
	 */
	private Integer uiMobile;
	/**
	 * 服务项目
	 */
	private String uiServeItem;
	/**
	 * 服务开始时间
	 */
	private Date uiServeStart;
	/**
	 * 服务结束时间
	 */
	private Date uiServeEnd;
	/**
	 * 建档日期
	 */
	private Date uiCrdate;
	/**
	 * 操作人
	 */
	private String uiOperator;
	/**
	 * 操作日期
	 */
	private Date uiOperateTime;
	/**
	 * 状态 0 正常 9 注销
	 */
	private String uiStatus;

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "UI_NAME", nullable = false, length = 250)
	public String getUiName() {
		return this.uiName;
	}

	public void setUiName(String uiName) {
		this.uiName = uiName;
	}

	@Column(name = "UI_ADDRESS", length = 250)
	public String getUiAddress() {
		return this.uiAddress;
	}

	public void setUiAddress(String uiAddress) {
		this.uiAddress = uiAddress;
	}

	@Column(name = "UI_LINKMAN", length = 20)
	public String getUiLinkman() {
		return this.uiLinkman;
	}

	public void setUiLinkman(String uiLinkman) {
		this.uiLinkman = uiLinkman;
	}

	@Column(name = "UI_TELEPHONE", length = 20)
	public String getUiTelephone() {
		return this.uiTelephone;
	}

	public void setUiTelephone(String uiTelephone) {
		this.uiTelephone = uiTelephone;
	}

	@Column(name = "UI_MOBILE")
	public Integer getUiMobile() {
		return this.uiMobile;
	}

	public void setUiMobile(Integer uiMobile) {
		this.uiMobile = uiMobile;
	}

	@Column(name = "UI_SERVE_ITEM", length = 250)
	public String getUiServeItem() {
		return this.uiServeItem;
	}

	public void setUiServeItem(String uiServeItem) {
		this.uiServeItem = uiServeItem;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UI_SERVE_START", length = 0)
	public Date getUiServeStart() {
		return this.uiServeStart;
	}

	public void setUiServeStart(Date uiServeStart) {
		this.uiServeStart = uiServeStart;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UI_SERVE_END", length = 0)
	public Date getUiServeEnd() {
		return this.uiServeEnd;
	}

	public void setUiServeEnd(Date uiServeEnd) {
		this.uiServeEnd = uiServeEnd;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UI_CRDATE", length = 0)
	public Date getUiCrdate() {
		return this.uiCrdate;
	}

	public void setUiCrdate(Date uiCrdate) {
		this.uiCrdate = uiCrdate;
	}

	@Column(name = "UI_OPERATOR", length = 32)
	public String getUiOperator() {
		return this.uiOperator;
	}

	public void setUiOperator(String uiOperator) {
		this.uiOperator = uiOperator;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UI_OPERATE_TIME", length = 0)
	public Date getUiOperateTime() {
		return this.uiOperateTime;
	}

	public void setUiOperateTime(Date uiOperateTime) {
		this.uiOperateTime = uiOperateTime;
	}

	@Column(name = "UI_status", length = 1)
	public String getUiStatus() {
		return this.uiStatus;
	}

	public void setUiStatus(String uiStatus) {
		this.uiStatus = uiStatus;
	}

}