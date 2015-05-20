package com.miaxis.system.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.miaxis.common.base.IdEntity;
import com.miaxis.common.springmvc.DateJsonSerializer;


/**
 * 系统日志
 * @author liu.qiao
 *
 */
@Entity
@Table(name = "t_s_log")
public class Log extends IdEntity  {
	private User user;
	/**
	 * 日志等级
	 */
	private int loglevel;
	/**
	 * 日志发生时间
	 */
	private Date operatetime;
	/**
	 * 日志类型
	 */
	private int operatetype;
	/**
	 * 日志内容
	 */
	private String logcontent;
	/**
	 * 用户浏览器类型
	 */
	private String broswer;
	/**
	 * 日志说明
	 */
	private String note;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userid")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "loglevel")
	public int getLoglevel() {
		return this.loglevel;
	}

	public void setLoglevel(int loglevel) {
		this.loglevel = loglevel;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=DateJsonSerializer.class)
	@Column(name = "operatetime", nullable = false, length = 35)
	public Date getOperatetime() {
		return this.operatetime;
	}

	public void setOperatetime(Date operatetime) {
		this.operatetime = operatetime;
	}

	@Column(name = "operatetype")
	public int getOperatetype() {
		return this.operatetype;
	}

	public void setOperatetype(int operatetype) {
		this.operatetype = operatetype;
	}

	@Column(name = "logcontent", nullable = false, length = 2000)
	public String getLogcontent() {
		return this.logcontent;
	}

	public void setLogcontent(String logcontent) {
		this.logcontent = logcontent;
	}

	@Column(name = "note", length = 300)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	@Column(name = "broswer", length = 100)
	public String getBroswer() {
		return broswer;
	}

	public void setBroswer(String broswer) {
		this.broswer = broswer;
	}

}