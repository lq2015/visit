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
import com.miaxis.visit.entity.PersonInfo.Status;

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
	private Integer jaJobUnit;
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
	 * 状态：0录入  1申请 2已派工 9取消
	 */
	private String jaStatus;
	/**
	 * 备注
	 */
	private String jaMemo;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "JA_JOB_UNIT", nullable = false)
	public Integer getJaJobUnit() {
		return this.jaJobUnit;
	}

	public void setJaJobUnit(Integer jaJobUnit) {
		this.jaJobUnit = jaJobUnit;
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

	@Column(name = "JA_APPLY_MAN", length = 32)
	public String getJaApplyMan() {
		return this.jaApplyMan;
	}

	public void setJaApplyMan(String jaApplyMan) {
		this.jaApplyMan = jaApplyMan;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "JA_APPLY_TIME", length = 0)
	public Date getJaApplyTime() {
		return this.jaApplyTime;
	}

	public void setJaApplyTime(Date jaApplyTime) {
		this.jaApplyTime = jaApplyTime;
	}

	@Column(name = "JA_APPROVE_MAN", length = 32)
	public String getJaApproveMan() {
		return this.jaApproveMan;
	}

	public void setJaApproveMan(String jaApproveMan) {
		this.jaApproveMan = jaApproveMan;
	}

	@Temporal(TemporalType.DATE)
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
	
	/**
	 * 记录状态
	 * @author liu.qiao
	 */
	public static class Status extends CodeNameEnum<String> {
		public static Status INPUT = new Status("0", "录入");
		public static Status APPLY = new Status("1", "申请");
		public static Status DISPATCH = new Status("2", "已派工");
		public static Status CANCEL = new Status("9", "取消");

		public static Status[] values() {
			return new Status[] {INPUT, APPLY, CANCEL,DISPATCH };
		}

		public Status(String code, String name) {
			super(code, name);
		}
	}
}