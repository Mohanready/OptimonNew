package com.optimon.entities;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "queue_metric_details")
public class QueueMetricDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "trust_id")
	private int trustId;
	@Column(name = "trust_name")
	private String trustName;
	@Column(name = "queue_name")
	private String queueName;
	@Column(name = "pending_queue_count")
	private String pendingQueueCount;
	@Column(name="batch_no")
	private Long batchNo;
	@Column(name = "created_on")
	private Date createdOn;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	public String getPendingQueueCount() {
		return pendingQueueCount;
	}
	public void setPendingQueueCount(String pendingQueueCount) {
		this.pendingQueueCount = pendingQueueCount;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Long getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}
	
	
	
}
