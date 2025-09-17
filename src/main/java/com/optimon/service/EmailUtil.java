package com.optimon.service;

import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;

import com.optimon.entities.CriticalInterfacesLog;
import com.optimon.entities.InboundMetricDetails;
import com.optimon.entities.QueueMetricDetails;

@Service
public class EmailUtil {
	
	public String toRecipients ="madhumohan.peddannagari@nhs.net,divya.reddy@nhs.net,sreevani.goli@nhs.net,abhishek.tirumalapuram@nhs.net,ashish.kotla@nhs.net";
	
	public void sendCritcalEmailAlerts(InboundMetricDetails cil, int differenceMinutes, String level) {
		Session session = emailProperties();

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("itsupport@optimusit.co.uk"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toRecipients));
			//message.setRecipients(Message.RecipientType.CC, InternetAddress.parse("madhumohan.peddannagari@nhs.net"));

			message.setSubject("Optimon Idle time InActivity Alert for " + cil.getServiceName() + " At "
					+ cil.getTrustName() + " TIE");
			message.setText("Dear Mail Crawler," + "\n\n Please do not spam my email!");

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			/*
			 * String messag = "<H3>Hi, <br>" + firstLine + "<br>" + secondLine + "<br>" +
			 * thirdLine + "<br><br><br>" + "Regards,<br>" + "Optimus Support" + "</H3>";
			 */
			String messag = "<H3>Hi, At " + cil.getTrustName() + " below Interface/Inbound is having idle time on "
					+ cil.getServiceName() + "<br>" + "Idle since from last " + differenceMinutes + " Minitues <br>"
					+ "Please respond immediately/ASAP" + "</H3>";
			mimeBodyPart.setContent(messag, "text/html; charset=utf-8");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			message.setContent(multipart);
			
			message.addHeader("X-Priority", "1");
            message.addHeader("Importance", "High");
			Transport.send(message);

			System.out.println("Email sent..");

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
	
	public void sendOutboundEmailAlerts(CriticalInterfacesLog cil, Long differenceMinutes, String level) {
		Session session = emailProperties();

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("itsupport@optimusit.co.uk"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toRecipients));
			//message.setRecipients(Message.RecipientType.CC, InternetAddress.parse("madhumohan.peddannagari@nhs.net"));

			message.setSubject("Optimon Queue up Alert for " + cil.getEndpointName() + " At "
					+ cil.getTrustName() + " TIE");
			message.setText("Dear Mail Crawler," + "\n\n Please do not spam my email!");

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			/*
			 * String messag = "<H3>Hi, <br>" + firstLine + "<br>" + secondLine + "<br>" +
			 * thirdLine + "<br><br><br>" + "Regards,<br>" + "Optimus Support" + "</H3>";
			 */
			String messag = "<H3>Hi, At " + cil.getTrustName() + " below Outbound is having messages in queue For "
					+ cil.getEndpointName() + "<br>" +" Queue Count :"+cil.getQueueCount()+ "<br>" + " since from:  " + differenceMinutes + " Minitues <br>"
					+ "Please respond immediately/ASAP" + "</H3>";
			mimeBodyPart.setContent(messag, "text/html; charset=utf-8");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			message.setContent(multipart);
			
			message.addHeader("X-Priority", "1");
            message.addHeader("Importance", "High");
			Transport.send(message);

			System.out.println("Email sent..");

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
	
	public void sendSolitonEmail(QueueMetricDetails qmd) {
		Session session = emailProperties();

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("itsupport@optimusit.co.uk"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toRecipients));
			//message.setRecipients(Message.RecipientType.CC, InternetAddress.parse("madhumohan.peddannagari@nhs.net"));

			message.setSubject("Optimon Queue up Alert for " + qmd.getQueueName() + " At "
					+ qmd.getTrustName() + " TIE");
			message.setText("Dear Mail Crawler," + "\n\n Please do not spam my email!");

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			/*
			 * String messag = "<H3>Hi, <br>" + firstLine + "<br>" + secondLine + "<br>" +
			 * thirdLine + "<br><br><br>" + "Regards,<br>" + "Optimus Support" + "</H3>";
			 */
			String messag = "<H3>Hi, At " + qmd.getTrustName() + " below Outbound is having messages in queue For "
					+ qmd.getQueueName() + "<br>" +" Queue Count :"+qmd.getPendingQueueCount()+ "<br>"
					+ "Please respond immediately/ASAP" + "</H3>";
			mimeBodyPart.setContent(messag, "text/html; charset=utf-8");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			message.setContent(multipart);
			
			message.addHeader("X-Priority", "1");
            message.addHeader("Importance", "High");
			Transport.send(message);

			System.out.println("Email sent..");

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	private Session emailProperties() {
		final String username = "Imperial.appmb-TIEAlerts@imperial.nhs.uk";
		final String password = "";

		Properties prop = new Properties();
		prop.put("mail.smtp.host", "cxhoutlook.dirone.imperial.nhs.uk");
		prop.put("mail.smtp.port", "25");
		prop.put("mail.debug", "true");
		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		return session;
	}

	public void sendCustomEmail(String trustName, int id) {
		
		Session session = emailProperties();
		
		try {

			Message message = new MimeMessage(session);
			String serverType = " OnPrem";
			message.setFrom(new InternetAddress("itsupport@optimusit.co.uk"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("imperial.ictoptimus@nhs.net"));
			// message.setRecipients(Message.RecipientType.CC,
			// InternetAddress.parse("sreevani.goli@nhs.net"));
			if (id == 1 || id == 2 || id == 3) {
				serverType = " Cloud";
			}
			message.setSubject(trustName + " " + serverType + " TIE Down.");
			message.setText("Dear Mail Crawler," + "\n\n Please do not spam my email!");

			StringBuffer msg = new StringBuffer();
			msg.append("This is my first email using Optimon Alerts \n");

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			/*
			 * String messag = "<H3>Hi, <br>" + firstLine + "<br>" + secondLine + "<br>" +
			 * thirdLine + "<br><br><br>" + "Regards,<br>" + "Optimus Support" + "</H3>";
			 */
			String messag = "<H3>Hi," + trustName + " " + serverType + " TIE is Down. <br>"
					+ "Please respond immediately/ASAP" + "</H3>";
			mimeBodyPart.setContent(messag, "text/html; charset=utf-8");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			message.setContent(multipart);

			Transport.send(message);

			System.out.println("Email sent..");

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	private void upload(Multipart multipart, String fileName) throws MessagingException {
		Random rand = new Random();
		int no = rand.nextInt();
		String contentId = Integer.toString(no);
		System.out.println(contentId);

		BodyPart messageBodyPart = new MimeBodyPart();
		String htmlText = "<img align=\" center \" src=\"cid:" + contentId + "\"><br>";
		messageBodyPart.setContent(htmlText + "<br>", "text/html");

		// add it
		multipart.addBodyPart(messageBodyPart);
		System.out.println(contentId);

		// second part (the image)
		messageBodyPart = new MimeBodyPart();
		DataSource fds = new FileDataSource(fileName);

		messageBodyPart.setDataHandler(new DataHandler(fds));
		messageBodyPart.setHeader("Content-ID", "<" + contentId + ">");
		System.out.println(contentId);

		// add image to the multipart
		multipart.addBodyPart(messageBodyPart);
	}

}
