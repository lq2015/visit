package com.miaxis.visit.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.miaxis.common.util.CodeNameEnum;
import com.miaxis.visit.entity.BankInfo.Status;

/**
 * 服务人员信息
 * @author liu.qiao
 *
 */
@Entity
@Table(name = "vt_person_info")
public class PersonInfo implements java.io.Serializable {

	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 人员工号
	 */
	private String piNumber;
	/**
	 * 姓名
	 */
	private String piName;
	/**
	 * 姓名拼音
	 */
	private String piPy;
	/**
	 * 身份证号
	 */
	private String piIdnum;
	/**
	 * 人员照片URL
	 */
	private String piPhotoUrl;
	/**
	 * 人员工作证URL
	 */
	private String piCertUrl;
	/**
	 * 性别：男、女
	 */
	private String piSex;
	/**
	 * 联系电话
	 */
	private String piTelephone;
	/**
	 * 联系手机
	 */
	private String piMobile;
	/**
	 * 工作单位
	 */
	private Integer piWorkUnit;
	/**
	 * 职务
	 */
	private String piPost;
	/**
	 * 建档日期
	 */
	private Date piCrdate;
	/**
	 * 操作人ID
	 */
	private String piOperator;
	/**
	 * 操作日期
	 */
	private Date piOperateTime;
	/**
	 * 人员状态 1正常 9 注销
	 */
	private String piStatus;
	
	/**
	 * 所属单位
	 */
	private UnitInfo unitInfo;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "PI_NUMBER", length = 20)
	public String getPiNumber() {
		return this.piNumber;
	}

	public void setPiNumber(String piNumber) {
		this.piNumber = piNumber;
	}

	@Column(name = "PI_NAME", nullable = false, length = 20)
	public String getPiName() {
		return this.piName;
	}

	public void setPiName(String piName) {
		this.piName = piName;
	}

	@Column(name = "PI_PY", length = 10)
	public String getPiPy() {
		return this.piPy;
	}

	public void setPiPy(String piPy) {
		this.piPy = piPy;
	}

	@Column(name = "PI_IDNUM", length = 20)
	public String getPiIdnum() {
		return this.piIdnum;
	}

	public void setPiIdnum(String piIdnum) {
		this.piIdnum = piIdnum;
	}

	@Column(name = "PI_PHOTO_URL", length = 250)
	public String getPiPhotoUrl() {
		return this.piPhotoUrl;
	}

	public void setPiPhotoUrl(String piPhotoUrl) {
		this.piPhotoUrl = piPhotoUrl;
	}
	
	@Column(name = "PI_CERT_URL", length = 250)
	public String getPiCertUrl() {
		return piCertUrl;
	}

	public void setPiCertUrl(String piCertUrl) {
		this.piCertUrl = piCertUrl;
	}

	@Column(name = "PI_SEX", length = 2)
	public String getPiSex() {
		return this.piSex;
	}

	public void setPiSex(String piSex) {
		this.piSex = piSex;
	}

	@Column(name = "PI_TELEPHONE", length = 20)
	public String getPiTelephone() {
		return this.piTelephone;
	}

	public void setPiTelephone(String piTelephone) {
		this.piTelephone = piTelephone;
	}

	@Column(name = "PI_MOBILE")
	public String getPiMobile() {
		return this.piMobile;
	}

	public void setPiMobile(String piMobile) {
		this.piMobile = piMobile;
	}
	
	@Column(name = "PI_POST", length = 100)
	public String getPiPost() {
		return this.piPost;
	}

	public void setPiPost(String piPost) {
		this.piPost = piPost;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PI_CRDATE", length = 0)
	public Date getPiCrdate() {
		return this.piCrdate;
	}

	public void setPiCrdate(Date piCrdate) {
		this.piCrdate = piCrdate;
	}

	@Column(name = "PI_OPERATOR", length = 32)
	public String getPiOperator() {
		return this.piOperator;
	}

	public void setPiOperator(String piOperator) {
		this.piOperator = piOperator;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PI_OPERATE_TIME", length = 0)
	public Date getPiOperateTime() {
		return this.piOperateTime;
	}

	public void setPiOperateTime(Date piOperateTime) {
		this.piOperateTime = piOperateTime;
	}

	@Column(name = "PI_status", length = 1)
	public String getPiStatus() {
		return this.piStatus;
	}

	public void setPiStatus(String piStatus) {
		this.piStatus = piStatus;
	}
	
	@Transient
	public Integer getPiWorkUnit() {
		if(this.unitInfo==null){
			return this.piWorkUnit;
		}else{
			return this.unitInfo.getId();
		}
	}

	public void setPiWorkUnit(Integer piWorkUnit) {
		this.piWorkUnit = piWorkUnit;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PI_WORK_UNIT")
	public UnitInfo getUnitInfo() {
		return unitInfo;
	}

	public void setUnitInfo(UnitInfo unitInfo) {
		this.unitInfo = unitInfo;
	}
	
	
	/**
	 * 记录状态
	 * @author liu.qiao
	 *
	 */
	public static class Status extends CodeNameEnum<String> {
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