package com.miaxis.visit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 服务类别
 * @author liu.qiao
 *
 */
@Entity
@Table(name = "vt_dic_serve_category")
public class ServeCategory implements java.io.Serializable {

	/**
	 * ID
	 */
	private String id;
	/**
	 * 类别名字
	 */
	private String scCategory;
	/**
	 * 父类ID
	 */
	private String scParent;
	/**
	 * 类别层级
	 */
	private Integer scLevel;
	/**
	 * 备注
	 */
	private String scMemo;

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 20)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "SC_CATEGORY", nullable = false, length = 250)
	public String getScCategory() {
		return this.scCategory;
	}

	public void setScCategory(String scCategory) {
		this.scCategory = scCategory;
	}

	@Column(name = "SC_PARENT", length = 20)
	public String getScParent() {
		return this.scParent;
	}

	public void setScParent(String scParent) {
		this.scParent = scParent;
	}

	@Column(name = "SC_LEVEL", nullable = false)
	public Integer getScLevel() {
		return this.scLevel;
	}

	public void setScLevel(Integer scLevel) {
		this.scLevel = scLevel;
	}

	@Column(name = "SC_MEMO", length = 250)
	public String getScMemo() {
		return this.scMemo;
	}

	public void setScMemo(String scMemo) {
		this.scMemo = scMemo;
	}

}