package com.team4.isamrs.security;

import com.team4.isamrs.model.user.Customer;
import com.team4.isamrs.model.user.RegistrationRequest;
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

        sendEmail("confirmation.html", variables, "Registration Confirmation", customer.getUsername());
    }

    public void sendApprovalEmail(RegistrationRequest registrationRequest) {
        HashMap<String, String> variables = new HashMap<>();
        variables.put("name", registrationRequest.getFirstName());
        variables.put("link", "http://localhost:3000/login");

        sendEmail("approval.html", variables, "Your request has been APPROVED", registrationRequest.getUsername());
    }

    public void sendRejectionEmail(RegistrationRequest registrationRequest) {
        HashMap<String, String> variables = new HashMap<>();
        variables.put("name", registrationRequest.getFirstName());
        variables.put("reason", registrationRequest.getRejectionReason());

        sendEmail("rejection.html", variables, "Your request has been REJECTED", registrationRequest.getUsername());
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
