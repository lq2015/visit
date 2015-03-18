package com.miaxis.visit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 服务合同附件图片
 * @author liu.qiao
 *
 */
@Entity
@Table(name = "vt_unit_pact_pic")
public class UnitPactPic implements java.io.Serializable {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 合同ID
	 */
	private Integer upPactId;
	/**
	 * 合同图片URL
	 */
	private String upPicUrl;
	/**
	 * 合同图片数据
	 */
	private String upPic;
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "UP_PACT_ID")
	public Integer getUpPactId() {
		return this.upPactId;
	}

	public void setUpPactId(Integer upPactId) {
		this.upPactId = upPactId;
	}

	@Column(name = "UP_PIC_URL", length = 250)
	public String getUpPicUrl() {
		return this.upPicUrl;
	}

	public void setUpPicUrl(String upPicUrl) {
		this.upPicUrl = upPicUrl;
	}

	@Column(name = "UP_PIC", length = 16777215)
	public String getUpPic() {
		return this.upPic;
	}

	public void setUpPic(String upPic) {
		this.upPic = upPic;
	}

}