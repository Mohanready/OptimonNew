package com.optimon.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "critical_inbound_receivers")
public class CriticalInboundReceivers {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "service_name")
	private String serviceName;
	@Column(name = "trust_id")
	private int trustId;
	@Column(name = "trust_name")
	private String trustName;
	@Column(name = "is_deleted")
	private boolean isDeleted;
	@Column(name = "created_on")
	private Date createdOn;
	@Column(name = "updated_on")
	private Date updatedOn;
	@Column(name = "day_idle_time")
	private Integer dayIdleTime;
	@Column(name = "night_idle_time")
	private Integer nightIdleTime;
	@Column(name = "wd_day_idle_time")
	private Integer weDayIdleTime;
	@Column(name = "wd_night_idle_time")
	private Integer weNightIdleTime;
	@Column(name = "is_monday_ignore")
	private Boolean isMondayIgnore;

	public Boolean getIsMondayIgnore() {
		return isMondayIgnore;
	}

	public void setIsMondayIgnore(Boolean isMondayIgnore) {
		this.isMondayIgnore = isMondayIgnore;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setReceiverName(String serviceName) {
		this.serviceName = serviceName;
	}

	public int getTrustId() {
		return trustId;
	}

	public void setTrustId(int trustId) {
		this.trustId = trustId;
	}

	public String getTrustName() {
		return trustName;
	}

	public void setTrustName(String trustName) {
		this.trustName = trustName;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Integer getDayIdleTime() {
		return dayIdleTime;
	}

	public void setDayIdleTime(Integer dayIdleTime) {
		this.dayIdleTime = dayIdleTime;
	}

	public Integer getNightIdleTime() {
		return nightIdleTime;
	}

	public void setNightIdleTime(Integer nightIdleTime) {
		this.nightIdleTime = nightIdleTime;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Integer getWeDayIdleTime() {
		return weDayIdleTime;
	}

	public void setWeDayIdleTime(Integer weDayIdleTime) {
		this.weDayIdleTime = weDayIdleTime;
	}

	public Integer getWeNightIdleTime() {
		return weNightIdleTime;
	}

	public void setWeNightIdleTime(Integer weNightIdleTime) {
		this.weNightIdleTime = weNightIdleTime;
	}
	
	
}
