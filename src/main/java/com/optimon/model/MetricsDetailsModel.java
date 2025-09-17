package com.optimon.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class MetricsDetailsModel {
	@JsonProperty("trust_id")
	private Integer trustId;
	@JsonProperty("inbound_services")
	private List<InboundDetailsModel> inboundServices;
	@JsonProperty("queue_details")
	private List<QueueDetailsModel> queueDetails;

	public Integer getTrustId() {
		return trustId;
	}

	public void setTrustId(Integer trustId) {
		this.trustId = trustId;
	}

	public List<InboundDetailsModel> getInboundServices() {
		return inboundServices;
	}

	public void setInboundServices(List<InboundDetailsModel> inboundServices) {
		this.inboundServices = inboundServices;
	}

	public List<QueueDetailsModel> getQueueDetails() {
		return queueDetails;
	}

	public void setQueueDetails(List<QueueDetailsModel> queueDetails) {
		this.queueDetails = queueDetails;
	}

}
