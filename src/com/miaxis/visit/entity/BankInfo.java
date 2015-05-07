package com.miaxis.visit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.miaxis.common.util.CodeNameEnum;

/**
 * 支行网点表
 * 
 * @author liu.qiao
 * 
 */
@Entity
@Table(name = "vt_bank_info")
public class BankInfo implements java.io.Serializable {
	/**
	 * 网点编码
	 */
	private String id;
	/**
	 * 银行网点全称
	 */
	private String biName;
	/**
	 * 银行网点简称
	 */
	private String biShortenName;
	/**
	 * 联系电话
	 */
	private String biTelephone;
	/**
	 * 联系地址
	 */
	private String biAddress;
	/**
	 * 联系人
	 */
	private String biLinkman;
	/**
	 * 联系人手机
	 */
	private String biMobile;
	/**
	 * 备注
	 */
	private String biMemo;
	/**
	 * 状态：0 录入  1 正常  9注销
	 */
	private String biStatus;

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "BI_NAME", length = 250)
	public String getBiName() {
		return this.biName;
	}

	public void setBiName(String biName) {
		this.biName = biName;
	}

	@Column(name = "BI_SHORTEN_NAME", length = 100)
	public String getBiShortenName() {
		return this.biShortenName;
	}

	public void setBiShortenName(String biShortenName) {
		this.biShortenName = biShortenName;
	}

	@Column(name = "BI_TELEPHONE", length = 50)
	public String getBiTelephone() {
		return this.biTelephone;
	}

	public void setBiTelephone(String biTelephone) {
		this.biTelephone = biTelephone;
	}

	@Column(name = "BI_ADDRESS", length = 250)
	public String getBiAddress() {
		return this.biAddress;
	}

	public void setBiAddress(String biAddress) {
		this.biAddress = biAddress;
	}

	@Column(name = "BI_LINKMAN", length = 20)
	public String getBiLinkman() {
		return this.biLinkman;
	}

	public void setBiLinkman(String biLinkman) {
		this.biLinkman = biLinkman;
	}

	@Column(name = "BI_MOBILE")
	public String getBiMobile() {
		return this.biMobile;
	}

	public void setBiMobile(String biMobile) {
		this.biMobile = biMobile;
	}

	@Column(name = "BI_MEMO", length = 250)
	public String getBiMemo() {
		return this.biMemo;
	}

	public void setBiMemo(String biMemo) {
		this.biMemo = biMemo;
	}

	@Column(name = "BI_STATUS", length = 1)
	public String getBiStatus() {
		return this.biStatus;
	}

	public void setBiStatus(String biStatus) {
		this.biStatus = biStatus;
	}

	@Transient
	public String getStatusText() {
		String status = this.biStatus;
		if(status==null) status="";
		if(status.equals("")) return "";
		
		switch (Integer.parseInt(status)) {
		case 0:
			return BankInfo.Status.INPUT.getName();
		case 1:
			return BankInfo.Status.NORMAL.getName();
		case 9:
			return BankInfo.Status.CANCEL.getName();
		}
		return "";
	}
	
	/**
	 * 记录状态
	 * @author liu.qiao
	 *
	 */
	public static class Status extends CodeNameEnum<String> {
		public static Status INPUT = new Status("0", "录入");
		public static Status NORMAL = new Status("1", "正常");
		public static Status CANCEL = new Status("9", "注销");

		public static Status[] values() {
			return new Status[] { INPUT, NORMAL, CANCEL };
		}

		public Status(String code, String name) {
			super(code, name);
		}
	}
}