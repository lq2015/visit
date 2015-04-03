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

/**
 * 评价主表
 */
@Entity
@Table(name = "vt_grade_master")
public class GradeMaster implements java.io.Serializable {
	
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 工作单号ID
	 */
	private Integer gmJobId;
	/**
	 * 被服务网点ID
	 */
	private String gmJobUnit;
	/**
	 * 服务单位ID
	 */
	private Integer gmUnit;
	/**
	 * 服务时间
	 */
	private Date gmJobDate;
	/**
	 * 综合得分
	 */
	private Integer gmScore;
	/**
	 * 综合评价描述
	 */
	private String gmDescribe;
	/**
	 * 建议
	 */
	private String gmSuggest;
	/**
	 * 操作人ID
	 */
	private String gmOperator;
	/**
	 * 操作日期
	 */
	private Date gmOperateTime;
	/**
	 * 备注
	 */
	private String gmMemo;

	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "GM_JOB_ID")
	public Integer getGmJobId() {
		return this.gmJobId;
	}

	public void setGmJobId(Integer gmJobId) {
		this.gmJobId = gmJobId;
	}

	@Column(name = "GM_JOB_UNIT", nullable = false, length = 32)
	public String getGmJobUnit() {
		return this.gmJobUnit;
	}

	public void setGmJobUnit(String gmJobUnit) {
		this.gmJobUnit = gmJobUnit;
	}

	@Column(name = "GM_UNIT", nullable = false)
	public Integer getGmUnit() {
		return this.gmUnit;
	}

	public void setGmUnit(Integer gmUnit) {
		this.gmUnit = gmUnit;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "GM_JOB_DATE", length = 0)
	public Date getGmJobDate() {
		return this.gmJobDate;
	}

	public void setGmJobDate(Date gmJobDate) {
		this.gmJobDate = gmJobDate;
	}

	@Column(name = "GM_SCORE")
	public Integer getGmScore() {
		return this.gmScore;
	}

	public void setGmScore(Integer gmScore) {
		this.gmScore = gmScore;
	}

	@Column(name = "GM_DESCRIBE", length = 250)
	public String getGmDescribe() {
		return this.gmDescribe;
	}

	public void setGmDescribe(String gmDescribe) {
		this.gmDescribe = gmDescribe;
	}

	@Column(name = "GM_SUGGEST", length = 250)
	public String getGmSuggest() {
		return this.gmSuggest;
	}

	public void setGmSuggest(String gmSuggest) {
		this.gmSuggest = gmSuggest;
	}

	@Column(name = "GM_OPERATOR", length = 32)
	public String getGmOperator() {
		return this.gmOperator;
	}

	public void setGmOperator(String gmOperator) {
		this.gmOperator = gmOperator;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "GM_OPERATE_TIME", length = 0)
	public Date getGmOperateTime() {
		return this.gmOperateTime;
	}

	public void setGmOperateTime(Date gmOperateTime) {
		this.gmOperateTime = gmOperateTime;
	}

	@Column(name = "GM_MEMO", length = 250)
	public String getGmMemo() {
		return this.gmMemo;
	}

	public void setGmMemo(String gmMemo) {
		this.gmMemo = gmMemo;
	}

}