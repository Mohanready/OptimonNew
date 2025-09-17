package com.optimon.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class InboundDetailsModel {
	@JsonProperty("service_name")
	private String serviceName;
	@JsonProperty("service_delay_time")
	private String serviceDelayTime;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceDelayTime() {
		return serviceDelayTime;
	}

	public void setServiceDelayTime(String serviceDelayTime) {
		this.serviceDelayTime = serviceDelayTime;
	}

}
