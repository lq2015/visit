package com.miaxis.visit.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.miaxis.common.springmvc.DateJsonSerializer;
import com.miaxis.common.util.CodeNameEnum;
import com.miaxis.system.entity.User;

/**
 * 派工登记
 * @author liu.qiao
 *
 */
@Entity
@Table(name = "vt_job_dispatch")
public class JobDispatch implements java.io.Serializable {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 需服务网点ID
	 */
	private String jdJobBank;
	/**
	 * 服务单位ID
	 */
	private Integer jdUnit;
	/**
	 * 服务人员IDS ,中间以,号隔开
	 */
	private String jdPersonIds;
	/**
	 * 服务人员姓名，中间以,号隔开
	 */
	private String jdPersonNames;
	/**
	 * 服务项目ID
	 */
	private String jdServeItemId;
	/**
	 * 服务项目名称
	 */
	private String jdServeItem;
	/**
	 * 工作内容简述
	 */
	private String jdJobContent;
	/**
	 * 服务有效开始日期
	 */
	private Date jdServeStart;
	/**
	 * 服务有效结束日期
	 */
	private Date jdServeEnd;
	/**
	 * 操作人ID
	 */
	private String jdOperator;
	/**
	 * 操作日期
	 */
	private Date jdOperateTime;
	/**
	 * 签到日期
	 */
	private Date jdSignTime;
	/**
	 * 签离日期
	 */
	private Date jdOutTime;
	/**
	 * 状态：0录入  1派工  2签到  3签离  9取消
	 */
	private String jdStatus;
	/**
	 * 短信是否已送：0未发送  1已发送
	 */
	private String jdIsSms;
	/**
	 * 备注
	 */
	private String jdMemo;
	
	/**
	 * 服务单位
	 */
	private UnitInfo unit;
	
	/**
	 * 银行网点
	 */
	private BankInfo bankInfo;
	/**
	 * 操作用户
	 */
	private User user;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Transient
	public String getJdJobBank() {
		if(this.bankInfo!=null){
			return this.bankInfo.getId();
		}else{
			return this.jdJobBank;
		}
	}

	public void setJdJobBank(String jdJobBank) {
		this.jdJobBank = jdJobBank;
	}
	
	@Transient
	public Integer getJdUnit() {
		if(this.unit!=null){
			return this.unit.getId();
		}else{
			return this.jdUnit;
		}
	}

	public void setJdUnit(Integer jdUnit) {
		this.jdUnit = jdUnit;
	}

	@Column(name = "JD_PERSON_IDS", length = 250)
	public String getJdPersonIds() {
		return this.jdPersonIds;
	}

	public void setJdPersonIds(String jdPersonIds) {
		this.jdPersonIds = jdPersonIds;
	}

	@Column(name = "JD_PERSON_NAMES", length = 250)
	public String getJdPersonNames() {
		return this.jdPersonNames;
	}

	public void setJdPersonNames(String jdPersonNames) {
		this.jdPersonNames = jdPersonNames;
	}
	
	@Column(name = "JD_SERVE_ITEM_ID", length = 250)
	public String getJdServeItemId() {
		return this.jdServeItemId;
	}

	public void setJdServeItemId(String jdServeItemId) {
		this.jdServeItemId = jdServeItemId;
	}

	@Column(name = "JD_SERVE_ITEM", nullable = false, length = 250)
	public String getJdServeItem() {
		return this.jdServeItem;
	}

	public void setJdServeItem(String jdServeItem) {
		this.jdServeItem = jdServeItem;
	}

	@Column(name = "JD_JOB_CONTENT", length = 250)
	public String getJdJobContent() {
		return this.jdJobContent;
	}

	public void setJdJobContent(String jdJobContent) {
		this.jdJobContent = jdJobContent;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "JD_SERVE_START", length = 0)
	public Date getJdServeStart() {
		return this.jdServeStart;
	}

	public void setJdServeStart(Date jdServeStart) {
		this.jdServeStart = jdServeStart;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "JD_SERVE_END", length = 0)
	public Date getJdServeEnd() {
		return this.jdServeEnd;
	}

	public void setJdServeEnd(Date jdServeEnd) {
		this.jdServeEnd = jdServeEnd;
	}
	
	@Transient
	public String getJdOperator() {
		return this.jdOperator;
	}

	public void setJdOperator(String jdOperator) {
		this.jdOperator = jdOperator;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=DateJsonSerializer.class)
	@Column(name = "JD_OPERATE_TIME", length = 0)
	public Date getJdOperateTime() {
		return this.jdOperateTime;
	}

	public void setJdOperateTime(Date jdOperateTime) {
		this.jdOperateTime = jdOperateTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "JD_SIGN_TIME", length = 0)
	public Date getJdSignTime() {
		return this.jdSignTime;
	}

	public void setJdSignTime(Date jdSignTime) {
		this.jdSignTime = jdSignTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "JD_OUT_TIME", length = 0)
	public Date getJdOutTime() {
		return this.jdOutTime;
	}
	
	public void setJdOutTime(Date jdOutTime) {
		this.jdOutTime = jdOutTime;
	}

	@Column(name = "JD_STATUS", nullable = false, length = 1)
	public String getJdStatus() {
		return this.jdStatus;
	}

	public void setJdStatus(String jdStatus) {
		this.jdStatus = jdStatus;
	}

	@Column(name = "JD_IS_SMS", nullable = false, length = 1)
	public String getJdIsSms() {
		return this.jdIsSms;
	}

	public void setJdIsSms(String jdIsSms) {
		this.jdIsSms = jdIsSms;
	}

	@Column(name = "JD_MEMO", length = 250)
	public String getJdMemo() {
		return this.jdMemo;
	}

	public void setJdMemo(String jdMemo) {
		this.jdMemo = jdMemo;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "JD_UNIT")
	public UnitInfo getUnit() {
		return unit;
	}

	public void setUnit(UnitInfo unit) {
		this.unit = unit;
	}
	
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "JD_JOB_BANK")
	public BankInfo getBankInfo() {
		return bankInfo;
	}

	public void setBankInfo(BankInfo bankInfo) {
		this.bankInfo = bankInfo;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "JD_OPERATOR")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 记录状态
	 * @author liu.qiao
	 */
	public static class Status extends CodeNameEnum<String> {
		public static Status INPUT = new Status("0", "录入");
		public static Status DISPATCH = new Status("1", "派工");
		public static Status SIGN = new Status("2", "签到");
		public static Status OUT = new Status("3", "签离");
		public static Status CANCEL = new Status("9", "取消");

		public static Status[] values() {
			return new Status[] {INPUT,DISPATCH, SIGN, CANCEL,OUT };
		}

		public Status(String code, String name) {
			super(code, name);
		}
	}
	
	/**
	 * 短信是否已发送
	 * @author liu.qiao
	 */
	public static class IsSms extends CodeNameEnum<String> {
		public static IsSms NO = new IsSms("0", "否");
		public static IsSms YES = new IsSms("1", "是");

		public static IsSms[] values() {
			return new IsSms[] {YES, NO };
		}

		public IsSms(String code, String name) {
			super(code, name);
		}
	}
}