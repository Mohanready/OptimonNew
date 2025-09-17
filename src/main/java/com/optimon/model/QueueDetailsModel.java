package com.optimon.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class QueueDetailsModel {
	@JsonProperty("queue_name")
	private String queueName;
	@JsonProperty("queue_pndng_count")
	private String queuePndngCount;

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getQueuePndngCount() {
		return queuePndngCount;
	}

	public void setQueuePndngCount(String queuePndngCount) {
		this.queuePndngCount = queuePndngCount;
	}

}
