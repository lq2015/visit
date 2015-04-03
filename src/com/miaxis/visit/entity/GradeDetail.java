package com.miaxis.visit.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 评价从表
 * @author liu.qiao
 *
 */
@Entity
@Table(name = "vt_grade_detail")
public class GradeDetail implements java.io.Serializable {

	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 主表ID
	 */
	private Integer gdMasterId;
	/**
	 * 评价项
	 */
	private String gdGradeItem;
	/**
	 * 评价项得分
	 */
	private Integer gdScore;

	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "GD_MASTER_ID", nullable = false)
	public Integer getGdMasterId() {
		return this.gdMasterId;
	}

	public void setGdMasterId(Integer gdMasterId) {
		this.gdMasterId = gdMasterId;
	}

	@Column(name = "GD_GRADE_ITEM", nullable = false, length = 200)
	public String getGdGradeItem() {
		return this.gdGradeItem;
	}

	public void setGdGradeItem(String gdGradeItem) {
		this.gdGradeItem = gdGradeItem;
	}

	@Column(name = "GD_SCORE")
	public Integer getGdScore() {
		return this.gdScore;
	}

	public void setGdScore(Integer gdScore) {
		this.gdScore = gdScore;
	}

}