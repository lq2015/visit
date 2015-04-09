package com.miaxis.visit.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 工作单附件
 * @author liu.qiao
 *
 */
@Entity
@Table(name = "vt_job_bill_pic", catalog = "visit")
public class JobBillPic implements java.io.Serializable {

	private Integer id;
	private Integer jbJobId;
	private String jbPicUrl;
	private String jbPic;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "JB_JOB_ID")
	public Integer getJbJobId() {
		return this.jbJobId;
	}

	public void setJbJobId(Integer jbJobId) {
		this.jbJobId = jbJobId;
	}

	@Column(name = "JB_PIC_URL", length = 250)
	public String getJbPicUrl() {
		return this.jbPicUrl;
	}

	public void setJbPicUrl(String jbPicUrl) {
		this.jbPicUrl = jbPicUrl;
	}

	@Column(name = "JB_PIC", length = 16777215)
	public String getJbPic() {
		return this.jbPic;
	}

	public void setJbPic(String jbPic) {
		this.jbPic = jbPic;
	}

}