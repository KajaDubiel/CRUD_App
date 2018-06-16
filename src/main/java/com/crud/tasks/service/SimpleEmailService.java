package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Service
public class SimpleEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCreatorService mailCreatorService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    public void send(final Mail mail) {
        LOGGER.info("Starting email preparation..");
        try {
            //SimpleMailMessage mailMessage = createMailMessage(mail);
            //javaMailSender.send(mailMessage);

            javaMailSender.send(createMimeMessageTrelloCard(mail));
            LOGGER.info("Email has been sent.");
//            if (mail.getToCc() != null) {
//                LOGGER.info("Carbon copy has been send");
//            }
            ofNullable(mail.getToCc()).ifPresent(cc -> LOGGER.info("Carbon copy has been send"));

        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: ", e.getMessage(), e);
        }
    }

    public void sendScheduleMail(Mail mail) {
        LOGGER.info("Starting daily mail preparation...");
        try {
            javaMailSender.send(createScheduleMimeMessage(mail));
            LOGGER.info("Email has been sent.");
        } catch (MailException e) {
            LOGGER.error("Failed to process sending daily mail", e.getMessage(), e);
        }
    }

    public SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
//        if (mail.getToCc() != null) {
//            mailMessage.setCc(mail.getToCc());
//        }
        ofNullable(mail.getToCc()).ifPresent(mcc -> mailMessage.setCc(mcc));
        return mailMessage;
    }

    public MimeMessagePreparator createMimeMessageTrelloCard(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
        };
    }

    public MimeMessagePreparator createScheduleMimeMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildTrelloScheduleMail(mail.getMessage()), true);
        };
    }
}
