package com.optimon.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.optimon.entities.EndpointDetails;
import com.optimon.entities.InboundMetricDetails;
import com.optimon.entities.Maintenance;
import com.optimon.entities.QueueMetricDetails;
import com.optimon.model.InboundDetailsModel;
import com.optimon.model.MetricsDetailsModel;
import com.optimon.model.ProcessHL7MessageResponse;
import com.optimon.model.QueueDetailsModel;
import com.optimon.repo.EndpointDetailsRepository;
import com.optimon.repo.InboundDetailsRepository;
import com.optimon.repo.MaintenanceRepo;
import com.optimon.repo.QueueMetricDetailsRepository;

@Service
public class MetricsDetailsService {

	@Autowired
	private InboundDetailsRepository inboundDetailsRepo;
	@Autowired
	private QueueMetricDetailsRepository queueMetricDetailsRepo;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private EndpointDetailsRepository endpointDetailsRepo;
	@Autowired
	private MaintenanceRepo maintenanceRepo;
	@Autowired
	private SMSService smsService;

	public static int count = 0;

	public synchronized String consumeMetricDetails(MetricsDetailsModel metrics) {
		String status = "Success";
		List<QueueDetailsModel> queueDetails = metrics.getQueueDetails();
		List<InboundDetailsModel> inboundDetails = metrics.getInboundServices();
		Integer trustId = metrics.getTrustId();
		if (metrics.getTrustId() > 0 && metrics.getTrustId() != null) {

			if (inboundDetails.size() > 0) {
				// System.out.println("inside inbound if number 1 trust_id
				// :"+metrics.getTrustId());System.out.println("inside inbound else number 1
				// trust_id :"+metrics.getTrustId());
				List<InboundMetricDetails> inBndDetails = new ArrayList<>();
				Long inboundMaxNum = inboundDetailsRepo.getMaxBatchNumber();
				for (InboundDetailsModel inbound : inboundDetails) {
					InboundMetricDetails imd = new InboundMetricDetails();
					imd.setCreatedOn(new Date());
					imd.setServiceName(inbound.getServiceName());
					imd.setTimeDelay(inbound.getServiceDelayTime());
					if (inboundMaxNum != null) {
						imd.setBatch_no(inboundMaxNum + 1);
					} else {
						imd.setBatch_no(1l);
					}
					if (metrics.getTrustId() == 1) {
						imd.setTrustId(metrics.getTrustId());
						imd.setTrustName("LNWH");
					} else if (metrics.getTrustId() == 2) {
						imd.setTrustId(metrics.getTrustId());
						imd.setTrustName("THH");
					} else if (metrics.getTrustId() == 3) {
						imd.setTrustId(metrics.getTrustId());
						imd.setTrustName("ICHNT");
					} else if (metrics.getTrustId() == 4) {
						imd.setTrustId(metrics.getTrustId());
						imd.setTrustName("LNWH-OnPrem");
					} else if (metrics.getTrustId() == 5) {
						imd.setTrustId(metrics.getTrustId());
						imd.setTrustName("THH-OnPrem");
					} else if (metrics.getTrustId() == 6) {
						imd.setTrustId(metrics.getTrustId());
						imd.setTrustName("NWLCLOUD");
					} else {
						status = "failed";
						break;
					}
					inBndDetails.add(imd);

				}
				inboundDetailsRepo.saveAll(inBndDetails);
			} else {
				// System.out.println("inside inbound else number 2 trust_id
				// :"+metrics.getTrustId());
				InboundMetricDetails imd = new InboundMetricDetails();
				imd.setCreatedOn(new Date());
				imd.setServiceName("No service available");
				imd.setTimeDelay("0");
				Long inboundMaxNum = inboundDetailsRepo.getMaxBatchNumber();
				imd.setBatch_no(inboundMaxNum + 1);
				if (metrics.getTrustId() == 1) {
					imd.setTrustId(metrics.getTrustId());
					imd.setTrustName("LNWH");
				} else if (metrics.getTrustId() == 2) {
					imd.setTrustId(metrics.getTrustId());
					imd.setTrustName("THH");
				} else if (metrics.getTrustId() == 3) {
					imd.setTrustId(metrics.getTrustId());
					imd.setTrustName("ICHNT");
				} else if (metrics.getTrustId() == 4) {
					imd.setTrustId(metrics.getTrustId());
					imd.setTrustName("LNWH-OnPrem");
				} else if (metrics.getTrustId() == 5) {
					imd.setTrustId(metrics.getTrustId());
					imd.setTrustName("THH-OnPrem");
				} else if (metrics.getTrustId() == 6) {
					imd.setTrustId(metrics.getTrustId());
					imd.setTrustName("LAS");
				}
				inboundDetailsRepo.save(imd);
			}
			if (queueDetails.size() > 0) {
				System.out.println("inside queue if number 3 trust_id :" + metrics.getTrustId());
				List<QueueMetricDetails> qmDetails = new ArrayList<>();
				Long queueMaxNum = queueMetricDetailsRepo.getMaxBatchNumber();
				for (QueueDetailsModel queue : queueDetails) {
					QueueMetricDetails qmd = new QueueMetricDetails();
					qmd.setCreatedOn(new Date());
					qmd.setQueueName(queue.getQueueName());
					if (queueMaxNum != null) {
						qmd.setBatchNo(queueMaxNum + 1);
					} else {
						qmd.setBatchNo(1l);
					}
					qmd.setPendingQueueCount(queue.getQueuePndngCount());
					if (metrics.getTrustId() == 1) {
						qmd.setTrustId(metrics.getTrustId());
						qmd.setTrustName("LNWH");
					} else if (metrics.getTrustId() == 2) {
						qmd.setTrustId(metrics.getTrustId());
						qmd.setTrustName("THH");
					} else if (metrics.getTrustId() == 3) {
						qmd.setTrustId(metrics.getTrustId());
						qmd.setTrustName("ICHNT");
					} else if (metrics.getTrustId() == 4) {
						qmd.setTrustId(metrics.getTrustId());
						qmd.setTrustName("LNWH-OnPrem");
					} else if (metrics.getTrustId() == 5) {
						qmd.setTrustId(metrics.getTrustId());
						qmd.setTrustName("THH-OnPrem");
					} else if (metrics.getTrustId() == 6) {
						qmd.setTrustId(metrics.getTrustId());
						qmd.setTrustName("LAS");
					} else {
						status="failed";
						break;
					}
					qmDetails.add(qmd);
					// System.out.println("added to list..");
				}
				queueMetricDetailsRepo.saveAll(qmDetails);
			} else {
				// System.out.println("inside queue else number 4 trust_id
				// :"+metrics.getTrustId());
				QueueMetricDetails qmd = new QueueMetricDetails();
				qmd.setCreatedOn(new Date());
				Long queueMaxNum = queueMetricDetailsRepo.getMaxBatchNumber();
				qmd.setBatchNo(queueMaxNum + 1);
				qmd.setPendingQueueCount("No pending queues");
				qmd.setQueueName("No Pending Queues");
				if (metrics.getTrustId() == 1) {
					qmd.setTrustId(metrics.getTrustId());
					qmd.setTrustName("LNWH");
				} else if (metrics.getTrustId() == 2) {
					qmd.setTrustId(metrics.getTrustId());
					qmd.setTrustName("THH");
				} else if (metrics.getTrustId() == 3) {
					qmd.setTrustId(metrics.getTrustId());
					qmd.setTrustName("ICHNT");
				} else if (metrics.getTrustId() == 4) {
					qmd.setTrustId(metrics.getTrustId());
					qmd.setTrustName("LNWH-OnPrem");
				} else if (metrics.getTrustId() == 5) {
					qmd.setTrustId(metrics.getTrustId());
					qmd.setTrustName("THH-OnPrem");
				} else if (metrics.getTrustId() == 6) {
					qmd.setTrustId(metrics.getTrustId());
					qmd.setTrustName("LAS");
				} else {
					status="failed";
				}
				queueMetricDetailsRepo.save(qmd);
			}
		}
		if(status.equalsIgnoreCase("success")){
			status = "Records inserted successfully..!";
		}else {
			status= "No Records were inserted";
		}
		return status;
	}

	public List<InboundMetricDetails> getInboundMetricDetails(Integer trustId) {
		List<InboundMetricDetails> inboundDetails = inboundDetailsRepo.getInboundDetails(trustId);
		List<Maintenance> maintenanceDetails = maintenanceRepo.getMaintenances(trustId, "service");
		if (inboundDetails.size() > 0 && maintenanceDetails.size() > 0) {
			for (InboundMetricDetails imd : inboundDetails) {
				for (Maintenance mance : maintenanceDetails) {
					if (imd.getServiceName().equalsIgnoreCase(mance.getServiceName())) {
						imd.setTimeDelay(imd.getTimeDelay() + ", "+mance.getReason());
					}
				}

			}

			return inboundDetails;
		} else if (inboundDetails.size() > 0) {
			return inboundDetails;
		}
		return null;
	}

	public List<QueueMetricDetails> getQueueMetricDetails(Integer trustId) {
		List<QueueMetricDetails> queueDetails = queueMetricDetailsRepo.getQueudMetricDetails(trustId);
		List<Maintenance> maintenanceDetails = maintenanceRepo.getMaintenances(trustId, "queue");
		if (queueDetails.size() > 0 && maintenanceDetails.size() > 0) {

			for (QueueMetricDetails qmd : queueDetails) {
				for (Maintenance mance : maintenanceDetails) {
					if (qmd.getQueueName().equalsIgnoreCase(mance.getServiceName())) {
						qmd.setPendingQueueCount(qmd.getPendingQueueCount() + " , "+mance.getReason());
					}
				}

			}
			return queueDetails;
		} else if (queueDetails.size() > 0) {
			return queueDetails;
		}
		return null;
	}

	public MetricsDetailsModel callEnsembleTrustAPI(String url) {
		return restTemplate.getForObject(url, MetricsDetailsModel.class);
	}

	// Dummy Method please ignore
	public MetricsDetailsModel checkEnsembleTrustAPI(String url) {
		return restTemplate.getForObject(url, MetricsDetailsModel.class);
	}

	public String invokeEnsembleApis() {
		List<EndpointDetails> epDetails = endpointDetailsRepo.findAll();
		for (EndpointDetails epd : epDetails) {
			MetricsDetailsModel mdm = callEnsembleTrustAPI(epd.getUrl());
			String status = consumeMetricDetails(mdm);
			System.out.println(status + " " + epd.getTrustName() + " Metrics..");
		}
		return "Job Successfully executed..!";

	}

	// Dummy method which will verify and print the JSON response from Ensemble
	public List<MetricsDetailsModel> checkEnsembleApis() {
		List<EndpointDetails> epDetails = endpointDetailsRepo.findAll();
		List<MetricsDetailsModel> lmds = new ArrayList<>();
		/*
		 * Random rand = new Random(); List<InboundDetailsModel> lidm= new
		 * ArrayList<>(); List<QueueDetailsModel> lqdm= new ArrayList<>(); List<String>
		 * stack = new ArrayList<>(); InboundDetailsModel idm= new
		 * InboundDetailsModel(); Integer num = rand.nextInt(90) + 10;
		 * idm.setServiceName("Test-Service-dummy");
		 * idm.setServiceDelayTime(num.toString()); QueueDetailsModel qdm = new
		 * QueueDetailsModel(); Integer num1 = rand.nextInt(90) + 10;
		 * qdm.setQueueName("Test-Queue-Dummy.");
		 * qdm.setQueuePndngCount(num1.toString()); lidm.add(idm); lqdm.add(qdm);
		 * MetricsDetailsModel mdm= new MetricsDetailsModel(); mdm.setTrustId(3);
		 * mdm.setInboundServices(lidm); mdm.setQueueDetails(lqdm);
		 */

		for (EndpointDetails epd : epDetails) {
			MetricsDetailsModel mdm = checkEnsembleTrustAPI(epd.getUrl());
			lmds.add(mdm);
			// break;
		}

		return lmds;

	}

	public ResponseEntity<ProcessHL7MessageResponse> cosumeSOAPData(String jsonData) {
		ProcessHL7MessageResponse phm = new ProcessHL7MessageResponse();
		try {
			count++;
			BufferedWriter writer = new BufferedWriter(
					new FileWriter("E://OptimonPlus/windows-service/performace_log/performance_log_" + count + ".txt"));
			writer.write(jsonData);
			System.out.println(jsonData);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		phm.setProcessHL7MessageResult(
				"<![CDATA[MSH|^~+&|TWINKLE|HICOM|PCS|PAS|20230330155815||ACK|47121862|P|2.4|47121862||||||| MSA|AA|47121862|||| ]]>");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<ProcessHL7MessageResponse> entityModel = new ResponseEntity<>(phm, headers, HttpStatus.OK);
		return entityModel;

	}

}
