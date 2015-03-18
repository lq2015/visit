package com.miaxis.visit.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 指纹信息
 * @author liu.qiao
 *
 */
@Entity
@Table(name = "vt_finger_info")
public class FingerInfo implements java.io.Serializable {
	/**
	 * iD
	 */
	private Integer id;
	/**
	 * 人员内部编号
	 */
	private Integer fiPerson;
	/**
	 * 手指编码(0,1,2,3,4,5.6,7,8,9)分别对应（左手小拇指、左手无名指、左手中指、左手食指、左手大拇指、右手大拇指、右手食指、右手中指、右手无名指、右手小拇指）
	 */
	private String fiCode;
	/**
	 * 手指名称（左手小拇指、左手无名指、左手中指、左手食指、左手大拇指、右手大拇指、右手食指、右手中指、右手无名指、右手小拇指）
	 */
	private String fiName;
	/**
	 * 指纹模板
	 */
	private String fiTemplate;
	/**
	 * 指纹图像
	 */
	private String fiPicture;
	/**
	 * 采集人员
	 */
	private String fiGatherer;
	/**
	 * 采集日期
	 */
	private Date fiGatherTime;

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "FI_PERSON")
	public Integer getFiPerson() {
		return this.fiPerson;
	}

	public void setFiPerson(Integer fiPerson) {
		this.fiPerson = fiPerson;
	}

	@Column(name = "FI_CODE", length = 4)
	public String getFiCode() {
		return this.fiCode;
	}

	public void setFiCode(String fiCode) {
		this.fiCode = fiCode;
	}

	@Column(name = "FI_NAME", length = 20)
	public String getFiName() {
		return this.fiName;
	}

	public void setFiName(String fiName) {
		this.fiName = fiName;
	}

	@Column(name = "FI_TEMPLATE", length = 16777215)
	public String getFiTemplate() {
		return this.fiTemplate;
	}

	public void setFiTemplate(String fiTemplate) {
		this.fiTemplate = fiTemplate;
	}

	@Column(name = "FI_PICTURE", length = 16777215)
	public String getFiPicture() {
		return this.fiPicture;
	}

	public void setFiPicture(String fiPicture) {
		this.fiPicture = fiPicture;
	}

	@Column(name = "FI_GATHERER", length = 32)
	public String getFiGatherer() {
		return this.fiGatherer;
	}

	public void setFiGatherer(String fiGatherer) {
		this.fiGatherer = fiGatherer;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FI_GATHER_TIME", length = 0)
	public Date getFiGatherTime() {
		return this.fiGatherTime;
	}

	public void setFiGatherTime(Date fiGatherTime) {
		this.fiGatherTime = fiGatherTime;
	}

}