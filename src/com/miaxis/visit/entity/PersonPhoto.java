package com.miaxis.visit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 服务人员照片信息
 * @author liu.qiao
 *
 */
@Entity
@Table(name = "vt_person_photo")
public class PersonPhoto implements java.io.Serializable {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 照片数据
	 */
	private String phPhoto;

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "PH_PHOTO", length = 16777215)
	public String getPhPhoto() {
		return this.phPhoto;
	}

	public void setPhPhoto(String phPhoto) {
		this.phPhoto = phPhoto;
	}

}