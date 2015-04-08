package com.miaxis.visit.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.miaxis.common.util.CodeNameEnum;

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
	private String uiMobile;
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
	 * 状态 0 录入 1正常 9 注销
	 */
	private String uiStatus;
	/**
	 * 备注
	 */
	private String uiMemo;

	@Id
	@GeneratedValue(strategy = IDENTITY)
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
	public String getUiMobile() {
		return this.uiMobile;
	}

	public void setUiMobile(String uiMobile) {
		this.uiMobile = uiMobile;
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

	@Temporal(TemporalType.TIMESTAMP)
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
	
	@Column(name = "UI_MEMO", length = 250)
	public String getUiMemo() {
		return uiMemo;
	}

	public void setUiMemo(String uiMemo) {
		this.uiMemo = uiMemo;
	}
	
	/**
	 * 记录状态
	 * @author liu.qiao
	 *
	 */
	public static class Status extends CodeNameEnum<String> {
		public static Status NORMAL = new Status("1", "正常");
		public static Status CANCEL = new Status("9", "注销");

		public static Status[] values() {
			return new Status[] { NORMAL, CANCEL };
		}

		public Status(String code, String name) {
			super(code, name);
		}
	}
}