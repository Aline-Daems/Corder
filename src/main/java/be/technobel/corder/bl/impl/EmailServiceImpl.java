package be.technobel.corder.bl.impl;

import be.technobel.corder.dal.models.Participation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {
    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(Participation participation, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("info@corder.com");
            helper.setTo(participation.getParticipantEmail());
            helper.setSubject(subject);

            String htmlContent = "<h1>Bravo " + participation.getParticipantFirstName() + " !</h1>";
            htmlContent += "<p>" + text +"</p>";
            helper.setText(htmlContent, true); // `true` indicates that this is HTML content

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
