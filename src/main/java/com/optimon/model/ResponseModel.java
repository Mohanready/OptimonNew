package com.optimon.model;

import java.util.List;

import com.optimon.entities.InboundMetricDetails;
import com.optimon.entities.QueueMetricDetails;

public class ResponseModel {
	private List<InboundMetricDetails> inboundDetails;
	private List<QueueMetricDetails> queueDetails;

	public List<InboundMetricDetails> getInboundDetails() {
		return inboundDetails;
	}

	public void setInboundDetails(List<InboundMetricDetails> inboundDetails) {
		this.inboundDetails = inboundDetails;
	}

	public List<QueueMetricDetails> getQueueDetails() {
		return queueDetails;
	}

	public void setQueueDetails(List<QueueMetricDetails> queueDetails) {
		this.queueDetails = queueDetails;
	}

}
