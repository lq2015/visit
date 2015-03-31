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
import com.miaxis.visit.entity.UnitInfo.Status;

/**
 * 服务单位合同信息
 * @author liu.qiao
 *
 */
@Entity
@Table(name = "vt_unit_pact")
public class UnitPact implements java.io.Serializable {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 单位ID
	 */
	private Integer upUnitId;
	/**
	 * 合同编号
	 */
	private String upNumber;
	/**
	 * 合同服务项目
	 */
	private String upServeItem;
	/**
	 * 合同开始日期
	 */
	private Date upServeStart;
	/**
	 * 合同结束日期
	 */
	private Date upServeEnd;
	/**
	 * 操作人ID
	 */
	private String upOperator;
	/**
	 * 操作日期
	 */
	private Date upOperateTime;
	/**
	 * 状态 0录入  1 正常 9 注销
	 */
	private String upStatus;
	/**
	 * 备注
	 */
	private String upMemo;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "UP_UNIT_ID")
	public Integer getUpUnitId() {
		return this.upUnitId;
	}

	public void setUpUnitId(Integer upUnitId) {
		this.upUnitId = upUnitId;
	}

	@Column(name = "UP_NUMBER", length = 20)
	public String getUpNumber() {
		return this.upNumber;
	}

	public void setUpNumber(String upNumber) {
		this.upNumber = upNumber;
	}

	@Column(name = "UP_SERVE_ITEM", length = 250)
	public String getUpServeItem() {
		return this.upServeItem;
	}

	public void setUpServeItem(String upServeItem) {
		this.upServeItem = upServeItem;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UP_SERVE_START", length = 0)
	public Date getUpServeStart() {
		return this.upServeStart;
	}

	public void setUpServeStart(Date upServeStart) {
		this.upServeStart = upServeStart;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UP_SERVE_END", length = 0)
	public Date getUpServeEnd() {
		return this.upServeEnd;
	}

	public void setUpServeEnd(Date upServeEnd) {
		this.upServeEnd = upServeEnd;
	}

	@Column(name = "UP_OPERATOR", length = 32)
	public String getUpOperator() {
		return this.upOperator;
	}

	public void setUpOperator(String upOperator) {
		this.upOperator = upOperator;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UP_OPERATE_TIME", length = 0)
	public Date getUpOperateTime() {
		return this.upOperateTime;
	}

	public void setUpOperateTime(Date upOperateTime) {
		this.upOperateTime = upOperateTime;
	}

	@Column(name = "UP_status", length = 1)
	public String getUpStatus() {
		return this.upStatus;
	}

	public void setUpStatus(String upStatus) {
		this.upStatus = upStatus;
	}

	@Column(name = "UP_MEMO", length = 250)
	public String getUpMemo() {
		return this.upMemo;
	}

	public void setUpMemo(String upMemo) {
		this.upMemo = upMemo;
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
			return new Status[] { NORMAL, CANCEL };
		}

		public Status(String code, String name) {
			super(code, name);
		}
	}

}