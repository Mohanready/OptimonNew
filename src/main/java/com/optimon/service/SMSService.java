package com.optimon.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optimon.entities.CriticalInterfaces;
import com.optimon.entities.CriticalInterfacesLog;
import com.optimon.model.InboundDetailsModel;
import com.optimon.model.QueueDetailsModel;
import com.optimon.repo.CriticalInterfaceLogRepository;
import com.optimon.repo.CriticalInterfacesRepository;

@Service
public class SMSService {

	@Autowired
	private CriticalInterfacesRepository criticalInterfacesRepository;
	@Autowired
	private CriticalInterfaceLogRepository criticalInterfaceLogRepo;

	public void checkTheCriticalInterfce(List<QueueDetailsModel> queueDetails, Integer trustId) {
		if (queueDetails.size() > 0) {
			List<CriticalInterfaces> crtcIntrfcs = criticalInterfacesRepository.findCriticalInterfacebyId(trustId);
			for (CriticalInterfaces ci : crtcIntrfcs) {
				for (QueueDetailsModel queue : queueDetails) {

					if (queue.getQueueName().equalsIgnoreCase(ci.getEndpointName())) {
						CriticalInterfacesLog ciLog = criticalInterfaceLogRepo
								.getCiLogDetailsByEndpointName(ci.getEndpointName());
						if (ciLog != null) {
							ciLog.setQueueCount(queue.getQueuePndngCount());
							ciLog.setUpdatedOn(new Date());
							criticalInterfaceLogRepo.save(ciLog);
						} else {
							CriticalInterfacesLog cil = new CriticalInterfacesLog();
							cil.setEndpointName(ci.getEndpointName());
							cil.setCreatedOn(new Date());
							cil.setUpdatedOn(new Date());
							cil.setQueueCount(queue.getQueuePndngCount());
							cil.setDeleted(false);
							cil.setTrustId(trustId);
							cil.setTrustName(ci.getTrustName());
							criticalInterfaceLogRepo.save(cil);
						}
					}
				}
			}
		} else {
			System.out.println("endpoint queue size is empty...");
		}
	}

}
