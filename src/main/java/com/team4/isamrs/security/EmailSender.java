package com.team4.isamrs.security;

import com.team4.isamrs.dto.updation.ComplaintResponseDTO;
import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.complaint.Complaint;
import com.team4.isamrs.model.reservation.ReservationReport;
import com.team4.isamrs.model.review.Review;
import com.team4.isamrs.model.user.*;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class EmailSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

    private final JavaMailSender emailSender;

    private final Path templatesLocation = Paths.get("templates");

    @Async
    public void sendEmail(String templateFilename, HashMap<String, String> variables, String subject, String sendTo) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setText(buildEmailFromTemplate(templateFilename, variables), true);
            helper.setTo(sendTo);
            helper.setSubject(subject);
            helper.setFrom("gajba.na.vodi@gmail.com");
            emailSender.send(mimeMessage);

        } catch (MessagingException | IOException e) {
            LOGGER.error("Failed to send email", e);
        }
    }

    public void sendRegistrationEmail(Customer customer, String token) {
        HashMap<String, String> variables = new HashMap<>();
        variables.put("name", customer.getFirstName());
        variables.put("link", "http://localhost:3000/confirm-registration/" + token);

        sendEmail("registration/confirmation.html", variables, "Registration Confirmation", customer.getUsername());
    }

    public void sendRegistrationApprovalEmail(RegistrationRequest registrationRequest) {
        HashMap<String, String> variables = new HashMap<>();
        variables.put("name", registrationRequest.getFirstName());
        variables.put("link", "http://localhost:3000/login");

        sendEmail("registration/approval.html", variables, "Your request has been APPROVED", registrationRequest.getUsername());
    }

    public void sendRegistrationRejectionEmail(RegistrationRequest registrationRequest) {
        HashMap<String, String> variables = new HashMap<>();
        variables.put("name", registrationRequest.getFirstName());
        variables.put("reason", registrationRequest.getRejectionReason());

        sendEmail("registration/rejection.html", variables, "Your request has been REJECTED", registrationRequest.getUsername());
    }

    public void sendAdministratorRegistrationEmail(Administrator administrator) {
        HashMap<String, String> variables = new HashMap<>();
        variables.put("name", administrator.getFirstName());
        variables.put("link", "http://localhost:3000/login");

        sendEmail("registration/administrator.html", variables, "Activate your administrator account", administrator.getUsername());
    }

    public void sendRemovalApprovalEmail(RemovalRequest removalRequest) {
        HashMap<String, String> variables = new HashMap<>();
        variables.put("name", removalRequest.getUser().getFirstName());

        sendEmail("removal/approval.html", variables, "Your account has been DELETED", removalRequest.getUser().getUsername());
    }

    public void sendRemovalRejectionEmail(RemovalRequest removalRequest) {
        HashMap<String, String> variables = new HashMap<>();
        variables.put("name", removalRequest.getUser().getFirstName());
        variables.put("reason", removalRequest.getRejectionReason());

        sendEmail("removal/rejection.html", variables, "Your request for account removal has been REJECTED", removalRequest.getUser().getUsername());
    }

    public void sendReportApprovalEmail(ReservationReport report) {
        Customer customer = report.getReservation().getCustomer();
        Advertisement advertisement = report.getReservation().getAdvertisement();
        Advertiser advertiser = report.getReservation().getAdvertisement().getAdvertiser();

        HashMap<String, String> variables = new HashMap<>();
        variables.put("customer_name", customer.getFirstName());
        variables.put("ad_title", advertisement.getTitle());
        variables.put("advertiser_name", advertiser.getFirstName());
        variables.put("comment", report.getComment());
        variables.put("penalties", customer.getPenalties().toString());

        sendEmail("report/approval-customer.html", variables, "You got a penalty!", customer.getUsername());
        sendEmail("report/approval-advertiser.html", variables, "Your customer got a penalty!", advertiser.getUsername());
    }

    public void sendReportRejectionEmail(ReservationReport report) {
        Customer customer = report.getReservation().getCustomer();
        Advertisement advertisement = report.getReservation().getAdvertisement();
        Advertiser advertiser = report.getReservation().getAdvertisement().getAdvertiser();

        HashMap<String, String> variables = new HashMap<>();
        variables.put("customer_name", customer.getFirstName());
        variables.put("ad_title", advertisement.getTitle());
        variables.put("advertiser_name", advertiser.getFirstName());
        variables.put("comment", report.getComment());
        variables.put("penalties", customer.getPenalties().toString());

        sendEmail("report/rejection-customer.html", variables, "You did not get a penalty", customer.getUsername());
        sendEmail("report/rejection-advertiser.html", variables, "Your customer did not get a penalty", advertiser.getUsername());
    }

    public void sendComplaintResponseToBothParties(Complaint complaint, ComplaintResponseDTO dto) {
        sendComplaintResponseToCustomer(complaint, dto);
        sendComplaintResponseToAdvertiser(complaint, dto);
    }

    private void sendComplaintResponseToAdvertiser(Complaint complaint, ComplaintResponseDTO dto) {
        Advertisement advertisement = complaint.getAdvertisement();
        Advertiser advertiser = advertisement.getAdvertiser();

        HashMap<String, String> variables = new HashMap<>();
        variables.put("name", advertiser.getFirstName());
        variables.put("customer_name", complaint.getCustomer().getFirstName());
        variables.put("ad_title", advertisement.getTitle());
        variables.put("complaint", complaint.getComment());
        variables.put("response", dto.getMessageToAdvertiser());

        sendEmail("complaint/advertiser.html", variables, "You got a complaint", advertiser.getUsername());
    }

    private void sendComplaintResponseToCustomer(Complaint complaint, ComplaintResponseDTO dto) {
        Advertisement advertisement = complaint.getAdvertisement();
        Customer customer = complaint.getCustomer();

        HashMap<String, String> variables = new HashMap<>();
        variables.put("name", customer.getFirstName());
        variables.put("ad_title", advertisement.getTitle());
        variables.put("complaint", complaint.getComment());
        variables.put("response", dto.getMessageToCustomer());

        sendEmail("complaint/customer.html", variables, "Admin response to your complaint", customer.getUsername());
    }


    public void sendNewReviewEmail(Review review, Double totalRating) {
        Advertisement advertisement = review.getAdvertisement();
        Advertiser advertiser = advertisement.getAdvertiser();
        Customer customer = review.getCustomer();

        HashMap<String, String> variables = new HashMap<>();
        variables.put("name", advertiser.getFirstName());
        variables.put("customer_name", customer.getFirstName());
        variables.put("ad_title", advertisement.getTitle());
        variables.put("rating", review.getRating().toString());
        variables.put("comment", review.getComment());
        variables.put("total_rating", totalRating.toString());

        sendEmail("review/new.html", variables, "You got a review!", advertiser.getUsername());
    }

    private String buildEmailFromTemplate(String filename, HashMap<String, String> variables) throws IOException {
        File file = templatesLocation.resolve(filename).toFile();
        String message = FileUtils.readFileToString(file, "UTF-8");

        String target, value;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            target = "\\{\\{ " + entry.getKey() + " \\}\\}";
            value = entry.getValue();

            message = message.replaceAll(target, value);
        }
        return message;
    }
}
