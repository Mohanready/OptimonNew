package com.optimon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.optimon.entities.InboundMetricDetails;
import com.optimon.entities.QueueMetricDetails;
import com.optimon.model.MetricsDetailsModel;
import com.optimon.model.ResponseModel;
import com.optimon.service.MetricsDetailsService;
import com.optimon.service.SMSService;

@CrossOrigin
@RestController
public class MetricsDetailsController {

	@Autowired
	private MetricsDetailsService metricDetailsService;

	@Autowired
	private SMSService smsService;

	@PostMapping(value = "/consumeMetricDetails")
	public String consumeMetricDetails(@RequestBody MetricsDetailsModel metrics) {
		smsService.checkTheCriticalInterfce(metrics.getQueueDetails(), metrics.getTrustId());
		return metricDetailsService.consumeMetricDetails(metrics);

	}

	@GetMapping(value = "/getMetricDetails")
	public ResponseModel getMetricDetails(@RequestParam Integer trustId) {
		List<InboundMetricDetails> inboundDetails = metricDetailsService.getInboundMetricDetails(trustId);
		List<QueueMetricDetails> quDetails = metricDetailsService.getQueueMetricDetails(trustId);

		ResponseModel responseModel = new ResponseModel();
		responseModel.setInboundDetails(inboundDetails);
		responseModel.setQueueDetails(quDetails);
		return responseModel;
	}

	@GetMapping(value = "/check-api-status")
	public List<MetricsDetailsModel> checkApiAvailability() {
		return metricDetailsService.checkEnsembleApis();
	}
	// DE01HWSM/wcf/services/IHMService.svc/Address_2
	// for Testing support API
	/*
	 * @PostMapping(value = "/DE01HWSM/wcf/services/IHMService.svc/Address_2",
	 * produces={"text"}) public ResponseEntity<ProcessHL7MessageResponse>
	 * cosumeSoapData(@RequestBody String jsonData) { return
	 * metricDetailsService.cosumeSOAPData(jsonData); }
	 */

}
