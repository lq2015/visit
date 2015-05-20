package com.miaxis.system.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.miaxis.common.base.IdEntity;

/**
 * 系统参数
 * 
 * @author Zhou.wf
 * 
 */
@Entity
@Table(name = "t_s_param")
public class SysParam extends IdEntity  {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String spName;
    private String spKey;
    private String spValue;
    private String spType;
    private String spStatus;
    private String spDefault;
    private String spOperator;
    private Date spOperateTime;
    private String spMemo;

    @Column(name = "SP_NAME", length = 20)
    public String getSpName() {
        return this.spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    @Column(name = "SP_KEY", nullable = false, length = 20)
    public String getSpKey() {
        return this.spKey;
    }

    public void setSpKey(String spKey) {
        this.spKey = spKey;
    }

    @Column(name = "SP_VALUE", length = 100)
    public String getSpValue() {
        return this.spValue;
    }

    public void setSpValue(String spValue) {
        this.spValue = spValue;
    }

    @Column(name = "SP_TYPE", length = 3)
    public String getSpType() {
        return this.spType;
    }

    @Transient
    public String getSpTypeText() {
        if ("0".equals(this.spType)) {
            return "系统参数";
        } else {
            return "业务参数";
        }
    }

    public void setSpType(String spType) {
        this.spType = spType;
    }

    @Column(name = "SP_STATUS", length = 2)
    public String getSpStatus() {
        return this.spStatus;
    }

    @Transient
    public String getSpStatusText() {
        if ("0".equals(this.spStatus)) {
            return "无效";
        } else {
            return "有效";
        }
    }

    public void setSpStatus(String spStatus) {
        this.spStatus = spStatus;
    }

    @Column(name = "SP_DEFAULT", length = 100)
    public String getSpDefault() {
        return this.spDefault;
    }

    public void setSpDefault(String spDefault) {
        this.spDefault = spDefault;
    }

    @Column(name = "SP_OPERATOR", length = 32)
    public String getSpOperator() {
        return this.spOperator;
    }

    public void setSpOperator(String spOperator) {
        this.spOperator = spOperator;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "SP_OPERATE_TIME", length = 7)
    public Date getSpOperateTime() {
        return this.spOperateTime;
    }

    public void setSpOperateTime(Date spOperateTime) {
        this.spOperateTime = spOperateTime;
    }

    @Column(name = "SP_MEMO", length = 200)
    public String getSpMemo() {
        return this.spMemo;
    }

    public void setSpMemo(String spMemo) {
        this.spMemo = spMemo;
    }
}