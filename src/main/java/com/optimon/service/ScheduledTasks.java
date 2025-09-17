package com.optimon.service;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.optimon.entities.CriticalInboundReceivers;
import com.optimon.entities.CriticalInterfacesLog;
import com.optimon.entities.InboundMetricDetails;
import com.optimon.entities.QueueMetricDetails;
import com.optimon.entities.SupportContactList;
import com.optimon.repo.CriticalInboundReceiverRepo;
import com.optimon.repo.CriticalInterfaceLogRepository;
import com.optimon.repo.InboundDetailsRepository;
import com.optimon.repo.QueueMetricDetailsRepository;
import com.optimon.repo.SupportContactListRepo;

@Component
public class ScheduledTasks {

	@Autowired
	private MetricsDetailsService metricDetailsService;
	@Autowired
	private EmailUtil emailUtil;
	@Autowired
	private CriticalInterfaceLogRepository criticalInterfaceLogRepository;
	@Autowired
	private SupportContactListRepo supportContactListRepo;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private InboundDetailsRepository inboundDetailsRepository;
	@Autowired
	private CriticalInboundReceiverRepo criticalInboundReceiverRepo;
	@Autowired
	private QueueMetricDetailsRepository queueMetricDetailsRepo;

	public ScheduledTasks() {

	}

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	// @Scheduled(fixedRate = 1000)
	@Scheduled(cron = "0 0/3 * * * *")
	public void lnwhCloudscheduler() {
		findAndSendEmail("LNWUH ", 1);
		findAndSendEmail("THH ", 2);
		findAndSendEmail("ICHNT ", 3);
		findAndSendEmail("NWLTIE ", 6);
		// findAndSendEmail("LNWH ", 4);
		// findAndSendEmail(5);
		log.info("The time is now {}", dateFormat.format(new Date()));
	}

	@Scheduled(cron = "0 0/30 17-23 * * 1-5")
	public void thhCloudscheduler() {
		log.info("The time is now {}", dateFormat.format(new Date()));
	}

	@Scheduled(cron = "0 0/30 0-09 * * 1-5")
	public void ichntCloudscheduler() {
		log.info("The time is now {}", dateFormat.format(new Date()));
	}

	@Scheduled(cron = "0 0/30 0-23 * * 6-7")
	public void lnwhOnpremscheduler() {
		log.info("The time is now {}", dateFormat.format(new Date()));
	}

	@Scheduled(cron = "0 0/30 0-23 * * 6-7")
	public void thhOnpremscheduler() {
		log.info("The time is now {}", dateFormat.format(new Date()));
	}

	private void findAndSendEmail(String trustName, Integer id) {
		List<InboundMetricDetails> ibms = metricDetailsService.getInboundMetricDetails(id);
		if (!ibms.isEmpty()) {
			for (InboundMetricDetails imd : ibms) {
				Date legacyDate = imd.getCreatedOn();
				Date currenDate = new Date();
				long diffInMs = Math.abs(currenDate.getTime() - legacyDate.getTime());
				long diffInMinutes = diffInMs / (60 * 1000);
				if (diffInMinutes > 3) {
					for (int i = 0; i < 4; i++) {
						emailUtil.sendCustomEmail(trustName, id);
					}
					break;
				} else {
					break;
				}
			}
		} else {
			System.out.println("NO data found..");
		}

	}

	@Scheduled(cron = "0 0/5 0-23 * * 1-7")
	public void checkInterfaceStatus() {
		List<CriticalInterfacesLog> activeLogs = criticalInterfaceLogRepository.getActiveLogRecords();
		if (activeLogs.size() > 0) {
			for (CriticalInterfacesLog cil : activeLogs) {
				Date currentTime = new Date();
				Date recordUpdatedTime = cil.getUpdatedOn();
				long differenceMillis = currentTime.getTime() - recordUpdatedTime.getTime();
				long differenceMinutes = differenceMillis / (60 * 1000);
				if (differenceMinutes > 5) {
					cil.setDeleted(true);
					cil.setUpdatedOn(new Date());
				}
				criticalInterfaceLogRepository.save(cil);
			}
		} else {
			System.out.println("NO active logs found..!");
		}

	}

	// @Scheduled(cron = "0 0/1 0-23 * * 1-7")
	public void sendSMSForActiveEndPoints() {
		List<CriticalInterfacesLog> activeLogs = criticalInterfaceLogRepository.getActiveLogRecords();
		if (activeLogs.size() > 0 && activeLogs != null) {
			String api = "https://tailored.bt.com/cgphttp/servlet/sendmsg?username=ImperialNHS@nhs.net&password=bvQL9aDQ";
			for (CriticalInterfacesLog cil : activeLogs) {
				Date currentTime = new Date();
				Date recordcreatedTime = cil.getCreatedOn();
				long differenceMillis = currentTime.getTime() - recordcreatedTime.getTime();
				long differenceMinutes = differenceMillis / (60 * 1000);
				if ((differenceMinutes > 5 && differenceMinutes < 10) && (cil.getQueueCount() > 30)) {
					emailUtil.sendOutboundEmailAlerts(cil, differenceMinutes, "L1");
					// sendSMS(api, cil, differenceMinutes, "L1");
				} else if ((differenceMinutes > 5 && differenceMinutes < 10) && (cil.getQueueCount() > 50)) {
					sendSMS(api, cil, differenceMinutes, "L1");
				} else if ((differenceMinutes > 10 && differenceMinutes < 25) && (cil.getQueueCount() > 1500)) {
					sendSMS(api, cil, differenceMinutes, "L1");
				} else if ((differenceMinutes > 25 && differenceMinutes < 60) && cil.getQueueCount() > 5000) {
					sendSMS(api, cil, differenceMinutes, "L2");
				} else if ((differenceMinutes > 60 && differenceMinutes < 180) && cil.getQueueCount() > 12000) {
					sendSMS(api, cil, differenceMinutes, "L3");
				}
			}
		} else {

			System.out.println("No critical Interfaces were found..!");

		}

	}

	@Scheduled(cron = "0 0/5 0-23 * * 1-7")
	public void sendEmailForActiveEndPoints() {
		List<CriticalInterfacesLog> activeLogs = criticalInterfaceLogRepository.getActiveLogRecords();
		if (activeLogs.size() > 0 && activeLogs != null) {
			for (CriticalInterfacesLog cil : activeLogs) {
				Date currentTime = new Date();
				Date recordcreatedTime = cil.getCreatedOn();
				long differenceMillis = currentTime.getTime() - recordcreatedTime.getTime();
				long differenceMinutes = differenceMillis / (60 * 1000);
				if ((differenceMinutes > 5) && (differenceMinutes < 50) && (cil.getQueueCount() > 50)) {
					emailUtil.sendOutboundEmailAlerts(cil, differenceMinutes, "L1");
				} else if ((differenceMinutes > 100 && differenceMinutes < 110) && (cil.getQueueCount() > 50)) {
					emailUtil.sendOutboundEmailAlerts(cil, differenceMinutes, "L1");
				} else if ((differenceMinutes > 200 && differenceMinutes < 210) && (cil.getQueueCount() > 50)) {
					emailUtil.sendOutboundEmailAlerts(cil, differenceMinutes, "L1");
				} else if ((differenceMinutes > 400 && differenceMinutes < 410) && (cil.getQueueCount() > 50)) {
					emailUtil.sendOutboundEmailAlerts(cil, differenceMinutes, "L1");
				} else if ((differenceMinutes > 600 && differenceMinutes < 610) && (cil.getQueueCount() > 50)) {
					emailUtil.sendOutboundEmailAlerts(cil, differenceMinutes, "L1");
				} else if ((differenceMinutes > 1400 && differenceMinutes < 1410) && (cil.getQueueCount() > 50)) {
					emailUtil.sendOutboundEmailAlerts(cil, differenceMinutes, "L1");
				}

			}
		} else {

			System.out.println("No critical Interfaces were found..!");

		}

	}

	@Scheduled(cron = "0 0/3 0-23 * * 1-7")
	public void checkSolitonQueue() {
		List<QueueMetricDetails> queueDetails = queueMetricDetailsRepo.getQueudMetricDetails(3);

		if (queueDetails.size() > 0 && queueDetails != null) {
			for (QueueMetricDetails solitonQueue : queueDetails) {
				if (solitonQueue.getQueueName().equals("SOLITON_ORMORR_TCPIP_OUT")
						&& Integer.parseInt(solitonQueue.getPendingQueueCount()) > 10) {
					emailUtil.sendSolitonEmail(solitonQueue);
				}
			}

		} else {

			System.out.println("No critical Interfaces were found..!");

		}

	}

	private void sendSMS(String api, CriticalInterfacesLog cil, long differenceMinutes, String level) {
		List<SupportContactList> contactList = supportContactListRepo.getSupportDetails(level);
		// StringBuffer sb = new StringBuffer();
		for (SupportContactList scl : contactList) {

			String apiBuilder = api + "&" + "destination=" + scl.getMobileNumber() + "&" + "text="
					+ "Hi, Messages to below endpoint are stuck in the " + cil.getTrustName()
					+ " TIE. The Endpoint Name is: " + cil.getEndpointName() + " The current pending queue count is : "
					+ cil.getQueueCount() + " Since From last : " + differenceMinutes + " Minutes ";
			String response = restTemplate.getForObject(apiBuilder, String.class);
			System.out.println("SMS response " + response);
		}
	}

	@Scheduled(cron = "0 0/10 0-23 * * 1-7")
	public void checkforIdleTimeActivity() {
		List<Integer> trustIds = new ArrayList<>();
		trustIds.add(1);
		trustIds.add(2);
		trustIds.add(3);
		// trustIds.add(4);
		// trustIds.add(5);

		for (Integer in : trustIds) {
			List<InboundMetricDetails> inboundDetails = inboundDetailsRepository.getInboundDetails(in);
			List<CriticalInboundReceivers> inboundReceivers = criticalInboundReceiverRepo.findReceiversById(in);
			if (inboundDetails.size() > 0) {
				String api = "https://tailored.bt.com/cgphttp/servlet/sendmsg?username=ImperialNHS@nhs.net&password=bvQL9aDQ";
				for (InboundMetricDetails imd : inboundDetails) {
					for (CriticalInboundReceivers cir : inboundReceivers) {
						if (imd.getServiceName().equalsIgnoreCase(cir.getServiceName())) {
							String[] idleTime = imd.getTimeDelay().split(" ");
							int idlTm = Integer.parseInt(idleTime[0]);
							LocalTime now = LocalTime.now();
							LocalTime startTime = LocalTime.of(9, 0); // 8:00 AM
							LocalTime endTime = LocalTime.of(18, 0); // 6:00 PM
							LocalDate currentDate = LocalDate.now();

							// Get the day of the week
							DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
							// Check if it's a weekend or weekday
							int currentIdleTime = 0;
							if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
								currentIdleTime = getCurrentTimeHour(now, startTime, endTime);
								if (currentIdleTime == 1) {
									currentIdleTime = cir.getWeDayIdleTime();
								} else {
									currentIdleTime = cir.getWeNightIdleTime();
								}
							} else {
								currentIdleTime = getCurrentTimeHour(now, startTime, endTime);
								if (currentIdleTime == 1) {
									currentIdleTime = cir.getDayIdleTime();
								} else {
									currentIdleTime = cir.getNightIdleTime();
								}
							}
							if ((idlTm > currentIdleTime) && (idlTm > 30) && (!cir.getIsMondayIgnore())) {
								// sendIdleTimeSMS(api, imd, idlTm, "L1");
								emailUtil.sendCritcalEmailAlerts(imd, idlTm, "L1");
							} else if ((idlTm > currentIdleTime) && (idlTm > 40) && (!cir.getIsMondayIgnore())) {
								// sendIdleTimeSMS(api, imd, idlTm, "L2");
								emailUtil.sendCritcalEmailAlerts(imd, idlTm, "L2");
							} else if ((idlTm > currentIdleTime) && (idlTm > 50) && (!cir.getIsMondayIgnore())) {
								// sendIdleTimeSMS(api, imd, idlTm, "L3");
								emailUtil.sendCritcalEmailAlerts(imd, idlTm, "L3");
							}
						}
					}
				}
			}

		}

	}

	private int getCurrentTimeHour(LocalTime now, LocalTime startTime, LocalTime endTime) {
		int currentIdleTime;
		if (now.isAfter(startTime) && now.isBefore(endTime)) {
			currentIdleTime = 1;
		} else {
			currentIdleTime = 0;
		}
		return currentIdleTime;
	}

	private void sendIdleTimeSMS(String api, InboundMetricDetails cil, int differenceMinutes, String level) {
		List<SupportContactList> contactList = supportContactListRepo.getSupportDetails(level);
		// StringBuffer sb = new StringBuffer();
		for (SupportContactList scl : contactList) {

			String apiBuilder = api + "&" + "destination=" + scl.getMobileNumber() + "&" + "text="
					+ "Hi, No messages were received at below receiver at " + cil.getTrustName()
					+ " TIE. The Receiver Name is: " + cil.getServiceName() + " Since From last : " + differenceMinutes
					+ " Minutes ";
			String response = restTemplate.getForObject(apiBuilder, String.class);
			System.out.println("SMS response " + response);
		}
	}

	private StringBuffer getMobileNumbers(String level) {
		List<SupportContactList> contactList = supportContactListRepo.getSupportDetails(level);
		StringBuffer sb = new StringBuffer();
		for (SupportContactList scl : contactList) {
			if (sb.capacity() == 0) {
				sb.append(scl.getMobileNumber());
			} else {
				sb.append("," + scl.getMobileNumber());
			}
		}
		return sb;
	}
}
