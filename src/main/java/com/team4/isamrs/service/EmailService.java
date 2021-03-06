package com.team4.isamrs.service;

import com.team4.isamrs.dto.updation.ComplaintResponseDTO;
import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.advertisement.BoatAd;
import com.team4.isamrs.model.advertisement.Photo;
import com.team4.isamrs.model.advertisement.ResortAd;
import com.team4.isamrs.model.complaint.Complaint;
import com.team4.isamrs.model.reservation.QuickReservation;
import com.team4.isamrs.model.reservation.Reservation;
import com.team4.isamrs.model.reservation.ReservationReport;
import com.team4.isamrs.model.review.Review;
import com.team4.isamrs.model.user.*;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@AllArgsConstructor
public class EmailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender emailSender;

    private final Path templatesLocation = Paths.get("templates");

    @Autowired
    private PhotoService photoService;

    @Async
    public void sendRegistrationEmail(Customer customer, String token) {
        HashMap<String, String> variables = new HashMap<>();
        variables.put("name", customer.getFirstName());
        variables.put("link", "http://www.pecaj.ga/confirm-registration/" + token);
        variables.put("image_data", "cid:logo.png");

        sendEmailWithImage("registration/confirmation.html", variables, "Registration Confirmation", customer.getUsername(), new String[]{"logo.png"});
    }

    @Async
    public void sendRegistrationApprovalEmail(RegistrationRequest registrationRequest) {
        HashMap<String, String> variables = new HashMap<>();
        variables.put("name", registrationRequest.getFirstName());
        variables.put("link", "http://www.pecaj.ga/login");
        variables.put("image_data", "cid:logo.png");

        sendEmailWithImage("registration/approval.html", variables, "Your request has been APPROVED", registrationRequest.getUsername(), new String[]{"logo.png"});
    }

    @Async
    public void sendRegistrationRejectionEmail(RegistrationRequest registrationRequest) {
        HashMap<String, String> variables = new HashMap<>();
        variables.put("name", registrationRequest.getFirstName());
        variables.put("reason", registrationRequest.getRejectionReason());
        variables.put("image_data", "cid:logo.png");

        sendEmailWithImage("registration/rejection.html", variables, "Your request has been REJECTED", registrationRequest.getUsername(), new String[]{"logo.png"});
    }

    @Async
    public void sendAdministratorRegistrationEmail(Administrator administrator) {
        HashMap<String, String> variables = new HashMap<>();
        variables.put("name", administrator.getFirstName());
        variables.put("link", "http://www.pecaj.ga/login");
        variables.put("image_data", "cid:logo.png");

        sendEmailWithImage("registration/administrator.html", variables, "Activate your administrator account", administrator.getUsername(), new String[]{"logo.png"});
    }

    @Async
    public void sendRemovalApprovalEmail(RemovalRequest removalRequest) {
        HashMap<String, String> variables = new HashMap<>();
        variables.put("name", removalRequest.getUser().getFirstName());
        variables.put("image_data", "cid:logo.png");

        sendEmailWithImage("removal/approval.html", variables, "Your account has been DELETED", removalRequest.getUser().getUsername(), new String[]{"logo.png"});
    }

    @Async
    public void sendRemovalRejectionEmail(RemovalRequest removalRequest) {
        HashMap<String, String> variables = new HashMap<>();
        variables.put("name", removalRequest.getUser().getFirstName());
        variables.put("reason", removalRequest.getRejectionReason());
        variables.put("image_data", "cid:logo.png");

        sendEmailWithImage("removal/rejection.html", variables, "Your request for account removal has been REJECTED", removalRequest.getUser().getUsername(), new String[]{"logo.png"});
    }

    @Async
    public void sendReportApprovalEmail(ReservationReport report, Advertisement advertisement, Advertiser advertiser, Customer customer) {
        HashMap<String, String> variables = new HashMap<>();
        variables.put("customer_name", customer.getFirstName());
        variables.put("ad_title", advertisement.getTitle());
        variables.put("advertiser_name", advertiser.getFirstName());
        variables.put("advertiser_surname", advertiser.getLastName());
        variables.put("comment", report.getComment());
        variables.put("penalties", customer.getPenalties().toString());
        variables.put("image_data", "cid:" + advertiser.getAvatar().getStoredFilename());
        variables.put("logo_image_data", "cid:logo.png");

        sendEmailWithImage("report/approval-customer.html", variables, "You got a penalty!", customer.getUsername(), new String[]{"logo.png", advertiser.getAvatar().getStoredFilename()});
        sendEmailWithImage("report/approval-advertiser.html", variables, "Your customer got a penalty!", advertiser.getUsername(), new String[]{"logo.png", advertiser.getAvatar().getStoredFilename()});
    }

    @Async
    public void sendReportRejectionEmail(ReservationReport report, Advertisement advertisement, Advertiser advertiser, Customer customer) {
        HashMap<String, String> variables = new HashMap<>();
        variables.put("customer_name", customer.getFirstName());
        variables.put("ad_title", advertisement.getTitle());
        variables.put("advertiser_name", advertiser.getFirstName());
        variables.put("advertiser_surname", advertiser.getLastName());
        variables.put("comment", report.getComment());
        variables.put("penalties", customer.getPenalties().toString());
        variables.put("image_data", "cid:" + advertiser.getAvatar().getStoredFilename());
        variables.put("logo_image_data", "cid:logo.png");

        sendEmailWithImage("report/rejection-customer.html", variables, "You did not get a penalty", customer.getUsername(), new String[]{"logo.png", advertiser.getAvatar().getStoredFilename()});
        sendEmailWithImage("report/rejection-advertiser.html", variables, "Your customer did not get a penalty", advertiser.getUsername(), new String[]{"logo.png", advertiser.getAvatar().getStoredFilename()});
    }

    @Async
    public void sendComplaintResponseToBothParties(Complaint complaint, ComplaintResponseDTO dto, Advertisement advertisement, Advertiser advertiser, Customer customer, String storedFilename) {
        sendComplaintResponseToCustomer(complaint, dto, advertisement, customer, storedFilename);
        sendComplaintResponseToAdvertiser(complaint, dto, advertisement, advertiser, customer, storedFilename);
    }

    @Async
    public void sendComplaintResponseToAdvertiser(Complaint complaint, ComplaintResponseDTO dto, Advertisement advertisement, Advertiser advertiser, Customer customer, String storedFilename) {

        HashMap<String, String> variables = new HashMap<>();
        variables.put("name", advertiser.getFirstName());
        variables.put("customer_name", customer.getFirstName());
        variables.put("customer_surname", customer.getLastName());
        variables.put("ad_title", advertisement.getTitle());
        variables.put("complaint", complaint.getComment());
        variables.put("response", dto.getMessageToAdvertiser());
        variables.put("image_data", "cid:" + storedFilename);

        sendEmailWithImage("complaint/advertiser.html", variables, "You got a complaint", advertiser.getUsername(), new String[]{customer.getAvatar().getStoredFilename()});
    }

    @Async
    public void sendComplaintResponseToCustomer(Complaint complaint, ComplaintResponseDTO dto, Advertisement advertisement, Customer customer, String storedFilename) {
        HashMap<String, String> variables = new HashMap<>();
        variables.put("name", customer.getFirstName());
        variables.put("customer_name", customer.getFirstName());
        variables.put("customer_surname", customer.getLastName());
        variables.put("ad_title", advertisement.getTitle());
        variables.put("complaint", complaint.getComment());
        variables.put("response", dto.getMessageToCustomer());
        variables.put("image_data", "cid:" + storedFilename);

        sendEmailWithImage("complaint/customer.html", variables, "Admin response to your complaint", customer.getUsername(), new String[]{customer.getAvatar().getStoredFilename()});
    }

    @Async
    public void sendNewReviewEmail(Review review, Advertisement advertisement, Advertiser advertiser, Customer customer, Integer totalRating) {
        HashMap<String, String> variables = new HashMap<>();
        variables.put("name", advertiser.getFirstName());
        variables.put("customer_name", customer.getFirstName() + " " + customer.getLastName());
        variables.put("ad_title", advertisement.getTitle());
        variables.put("rating", review.getRating().toString());
        variables.put("comment", review.getComment());
        variables.put("total_rating", totalRating.toString());
        variables.put("image_data", "cid:" + customer.getAvatar().getStoredFilename());
        variables.put("logo_image_data", "cid:logo.png");

        sendEmailWithImage("review/new.html", variables, "You got a review!", advertiser.getUsername(), new String[]{"logo.png", customer.getAvatar().getStoredFilename()});
    }

    @Async
    public void sendReservationConfirmationEmail(Reservation reservation, Photo photo) {
        Advertisement ad = reservation.getAdvertisement();
        String type = ad instanceof BoatAd ? "boat" :
                ad instanceof ResortAd ? "resort" : "adventure";

        HashMap<String, String> variables = new HashMap<>();
        variables.put("title", ad.getTitle());
        variables.put("currency", ad.getCurrency());
        variables.put("city", ad.getAddress().getCity());
        variables.put("description", ad.getDescription());
        variables.put("attendees", reservation.getAttendees().toString());
        Locale l = new Locale("", ad.getAddress().getCountryCode());
        variables.put("country", l.getDisplayCountry());
        variables.put("new_price", reservation.getCalculatedPrice().toString());
        variables.put("from_date", reservation.getStartDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm")));
        variables.put("to_date", reservation.getEndDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm")));
        variables.put("image_data", "cid:" + photo.getStoredFilename());
        variables.put("link", "http://www.pecaj.ga/" + type + "/" + ad.getId());

        sendEmailWithImage("reservation/confirmation.html", variables,
                "Reservation successful", reservation.getCustomer().getUsername(),
                new String[]{photo.getStoredFilename()});

    }

    @Async
    public void sendDiscountNotificationEmails(QuickReservation quickReservation, Photo photo,
                                               Collection<Customer> subscribers) {
        Advertisement ad = quickReservation.getAdvertisement();
        String type = ad instanceof BoatAd ? "boat" :
                ad instanceof ResortAd ? "resort" : "adventure";

        HashMap<String, String> variables = new HashMap<>();
        variables.put("title", ad.getTitle());
        variables.put("currency", ad.getCurrency());
        variables.put("city", ad.getAddress().getCity());
        variables.put("description", ad.getDescription());
        variables.put("capacity", quickReservation.getCapacity().toString());
        Locale l = new Locale("", ad.getAddress().getCountryCode());
        variables.put("country", l.getDisplayCountry());
        variables.put("old_price", quickReservation.getCalculatedOldPrice().toString());
        variables.put("new_price", quickReservation.getNewPrice().toString());
        variables.put("from_date", quickReservation.getStartDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm")));
        variables.put("to_date", quickReservation.getEndDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm")));
        variables.put("image_data", "cid:" + photo.getStoredFilename());
        variables.put("link", "http://www.pecaj.ga/" + type + "/" + ad.getId());

        subscribers.forEach(subscriber -> sendEmailWithImage("subscription/newDiscount.html",
                variables, quickReservation.getAdvertisement().getTitle() + " is on discount now!",
                subscriber.getUsername(), new String[]{ photo.getStoredFilename() }));
    }

    public void sendEmailWithImage(String templateFilename, Map<String, String> variables, String subject, String sendTo, String[] fileNames) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setText(buildEmailFromTemplate(templateFilename, variables), true);
            for (String fileName:fileNames) {
                Resource resource = photoService.getResource(fileName);
                helper.addInline(fileName, resource);
            }
            helper.setTo(sendTo);
            helper.setSubject(subject);
            helper.setFrom("gajba.na.vodi@gmail.com");
            emailSender.send(mimeMessage);

        } catch (MessagingException | IOException e) {
            LOGGER.error("Failed to send email", e);
        }
    }

    private String buildEmailFromTemplate(String filename, Map<String, String> variables) throws IOException {
        File file = templatesLocation.resolve(filename).toFile();
        String message = FileUtils.readFileToString(file, "UTF-8");

        String target;
        String value;

        for (Map.Entry<String, String> entry : variables.entrySet()) {
            target = "\\{\\{ " + entry.getKey() + " \\}\\}";
            value = entry.getValue();

            message = message.replaceAll(target, value);
        }
        return message;
    }
}
