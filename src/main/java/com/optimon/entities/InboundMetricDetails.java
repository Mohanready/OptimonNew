package com.optimon.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "inbound_metric_details")
public class InboundMetricDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "trust_id")
	private int trustId;
	@Column(name = "trust_name")
	private String trustName;
	@Column(name = "service_name")
	private String serviceName;
	@Column(name = "time_delay")
	private String timeDelay;
	@Column(name="batch_no")
	private Long batch_no;
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

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getTimeDelay() {
		return timeDelay;
	}

	public void setTimeDelay(String timeDelay) {
		this.timeDelay = timeDelay;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(Long batch_no) {
		this.batch_no = batch_no;
	}
	
}
