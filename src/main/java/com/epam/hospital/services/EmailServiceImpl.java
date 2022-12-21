package com.epam.hospital.services;

import com.epam.hospital.models.Patient;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class EmailServiceImpl implements EmailService {


    @Override
    public void sendHospitalCard(Patient patient, String filePath) {
        Session session = getSession();
        Message message = new MimeMessage(session);
        try {
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(patient.getEmail()));
            message.setSubject("Hospital Card Information");
            String text = "Dear, " + patient.getFirstName() + " ,your hospital card is in attachments";
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(text, "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            MimeBodyPart attachment = new MimeBodyPart();
            File file = new File(filePath);
            attachment.attachFile(file);
            multipart.addBodyPart(attachment);

            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    private Session getSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);

        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("testprojectsaddress@gmail.com", "wycbwglxbutwrhcg");
            }
        });
    }
}
