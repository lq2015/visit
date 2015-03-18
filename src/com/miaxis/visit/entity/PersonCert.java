package com.miaxis.visit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 服务人员工作证信息
 * @author liu.qiao
 *
 */
@Entity
@Table(name = "vt_person_cert")
public class PersonCert implements java.io.Serializable {

	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 人员ID
	 */
	private Integer pcPersonId;
	/**
	 * 证件数据
	 */
	private String pcCert;

	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "PC_PERSON_ID")
	public Integer getPcPersonId() {
		return this.pcPersonId;
	}

	public void setPcPersonId(Integer pcPersonId) {
		this.pcPersonId = pcPersonId;
	}

	@Column(name = "PC_CERT", length = 16777215)
	public String getPcCert() {
		return this.pcCert;
	}

	public void setPcCert(String pcCert) {
		this.pcCert = pcCert;
	}

}