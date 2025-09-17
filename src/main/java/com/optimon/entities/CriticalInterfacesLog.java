package com.optimon.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "critical_interfaces_log")
public class CriticalInterfacesLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "endpoint_name")
	private String endpointName;
	@Column(name = "is_deleted")
	private boolean isDeleted;
	@CreationTimestamp
	@Column(name = "created_on", updatable = false)
	private Date createdOn;
	@UpdateTimestamp
	@Column(name = "updated_on")
	private Date updatedOn;
	@Column(name = "trust_id")
	private int trustId;
	@Column(name = "queue_count")
	private int queueCount;
	@Column(name = "trust_name")
	private String trustName;

	public String getTrustName() {
		return trustName;
	}

	public void setTrustName(String trustName) {
		this.trustName = trustName;
	}

	public CriticalInterfacesLog() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEndpointName() {
		return endpointName;
	}

	public void setEndpointName(String endpointName) {
		this.endpointName = endpointName;
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

	public int getTrustId() {
		return trustId;
	}

	public void setTrustId(int trustId) {
		this.trustId = trustId;
	}

	public int getQueueCount() {
		return queueCount;
	}

	public void setQueueCount(String queueCount) {
		String[] countInt = queueCount.split(",");
		String tempCount = "";
		if (countInt.length > 1) {
			for (String s : countInt) {
				tempCount = tempCount + s;
			}
		}
		if (tempCount.isEmpty()) {
			this.queueCount = Integer.parseInt(queueCount);
		} else {
			this.queueCount = Integer.parseInt(tempCount);

		}
	}

}
