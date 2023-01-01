package com.epam.hospital.services;

import com.epam.hospital.data_access_layer.models.Patient;
import com.epam.hospital.utils.ApplicationPropertiesLoader;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class EmailServiceImpl implements EmailService {


    @Override
    public void sendHospitalCard(Patient patient) {
        Session session = getSession();
        Message message = new MimeMessage(session);
        try {
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(patient.getEmail()));
            message.setSubject("Hospital Card Information");
            String text = "Dear, " + patient.getFirstName() + ", your hospital card is in attachments.";
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(text, "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            MimeBodyPart attachment = new MimeBodyPart();
            File file = new File(ApplicationPropertiesLoader.load().getProperty("hospitalCard.storagePath"));
            attachment.attachFile(file);
            multipart.addBodyPart(attachment);

            message.setContent(multipart);
            Transport.send(message);
            log.info("Mail is sent to patient: {}", patient.getEmail());
        } catch (MessagingException | IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    private Session getSession() {
        Properties properties = ApplicationPropertiesLoader.load();
        String address = properties.getProperty("mail.address");
        String password = properties.getProperty("mail.password");
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(address, password);
            }
        });
    }
}
