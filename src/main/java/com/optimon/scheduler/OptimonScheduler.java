package com.optimon.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.optimon.service.MetricsDetailsService;

@Component
public class OptimonScheduler {

	@Autowired
	private MetricsDetailsService metricsDetailsService;

	//@Scheduled(cron = "*/2 * * * * *")
	public void fetchMetricDetailsScheduler() {
		String status=metricsDetailsService.invokeEnsembleApis();
		System.out.println(status);

	}

}
