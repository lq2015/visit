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

import com.miaxis.common.util.CodeNameEnum;
import com.miaxis.system.entity.User;

/**
 * 派工申请
 * @author liu.qiao
 *
 */
@Entity
@Table(name = "vt_job_apply")
public class JobApply implements java.io.Serializable {

	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 需服务网点ID
	 */
	private String jaJobBank;
	/**
	 * 服务项目ID
	 */
	private String jaServeItemId;
	/**
	 * 服务项目
	 */
	private String jaServeItem;
	/**
	 * 工作内容简述
	 */
	private String jaJobContent;
	/**
	 * 服务时间
	 */
	private Date jaJobDate;
	/**
	 * 申请人ID
	 */
	private String jaApplyMan;
	/**
	 * 申请日期
	 */
	private Date jaApplyTime;
	/**
	 * 审批人
	 */
	private String jaApproveMan;
	/**
	 * 审批日期
	 */
	private Date jaApproveTime;
	/**
	 * 工作单号
	 */
	private Integer jaJobId;
	/**
	 * 状态：0录入  1申请 2已派工 3驳回 9取消
	 */
	private String jaStatus;
	/**
	 * 备注
	 */
	private String jaMemo;
	
	/**
	 * 银行网点
	 */
	private BankInfo bankInfo;
	/**
	 * 申请用户
	 */
	private User applyUser;
	/**
	 * 审核用户
	 */
	private User approveUser;

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
	public String getJaJobBank() {
		if(this.bankInfo!=null){
			return this.bankInfo.getId();
		}else{
			return this.jaJobBank;
		}
	}

	public void setJaJobBank(String jaJobBank) {
		this.jaJobBank = jaJobBank;
	}
	
	@Column(name = "JA_SERVE_ITEM_ID", nullable = false, length = 250)
	public String getJaServeItemId() {
		return this.jaServeItemId;
	}

	public void setJaServeItemId(String jaServeItemId) {
		this.jaServeItemId = jaServeItemId;
	}

	@Column(name = "JA_SERVE_ITEM", nullable = false, length = 250)
	public String getJaServeItem() {
		return this.jaServeItem;
	}

	public void setJaServeItem(String jaServeItem) {
		this.jaServeItem = jaServeItem;
	}

	@Column(name = "JA_JOB_CONTENT", length = 250)
	public String getJaJobContent() {
		return this.jaJobContent;
	}

	public void setJaJobContent(String jaJobContent) {
		this.jaJobContent = jaJobContent;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "JA_JOB_DATE", length = 0)
	public Date getJaJobDate() {
		return this.jaJobDate;
	}

	public void setJaJobDate(Date jaJobDate) {
		this.jaJobDate = jaJobDate;
	}

	@Transient
	public String getJaApplyMan() {
		return this.jaApplyMan;
	}

	public void setJaApplyMan(String jaApplyMan) {
		this.jaApplyMan = jaApplyMan;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "JA_APPLY_TIME", length = 0)
	public Date getJaApplyTime() {
		return this.jaApplyTime;
	}

	public void setJaApplyTime(Date jaApplyTime) {
		this.jaApplyTime = jaApplyTime;
	}

	@Transient
	public String getJaApproveMan() {
		return this.jaApproveMan;
	}

	public void setJaApproveMan(String jaApproveMan) {
		this.jaApproveMan = jaApproveMan;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "JA_APPROVE_TIME", length = 0)
	public Date getJaApproveTime() {
		return this.jaApproveTime;
	}

	public void setJaApproveTime(Date jaApproveTime) {
		this.jaApproveTime = jaApproveTime;
	}

	@Column(name = "JA_JOB_ID")
	public Integer getJaJobId() {
		return this.jaJobId;
	}

	public void setJaJobId(Integer jaJobId) {
		this.jaJobId = jaJobId;
	}

	@Column(name = "JA_STATUS", nullable = false, length = 1)
	public String getJaStatus() {
		return this.jaStatus;
	}

	public void setJaStatus(String jaStatus) {
		this.jaStatus = jaStatus;
	}

	@Column(name = "JA_MEMO", length = 250)
	public String getJaMemo() {
		return this.jaMemo;
	}

	public void setJaMemo(String jaMemo) {
		this.jaMemo = jaMemo;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "JA_JOB_BANK")
	public BankInfo getBankInfo() {
		return bankInfo;
	}

	public void setBankInfo(BankInfo bankInfo) {
		this.bankInfo = bankInfo;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "JA_APPLY_MAN")
	public User getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(User applyUser) {
		this.applyUser = applyUser;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "JA_APPROVE_MAN")
	public User getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(User approveUser) {
		this.approveUser = approveUser;
	}


	/**
	 * 记录状态
	 * @author liu.qiao
	 */
	public static class Status extends CodeNameEnum<String> {
		public static Status INPUT = new Status("0", "录入");
		public static Status APPLY = new Status("1", "申请");
		public static Status DISPATCH = new Status("2", "已派工");
		public static Status REJECT = new Status("3", "驳回");
		public static Status CANCEL = new Status("9", "取消");

		public static Status[] values() {
			return new Status[] {INPUT, APPLY, CANCEL,DISPATCH };
		}

		public Status(String code, String name) {
			super(code, name);
		}
	}
}