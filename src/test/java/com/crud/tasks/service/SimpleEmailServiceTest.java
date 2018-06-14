package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

//    @Test
//    public void shouldSendEmail() {
//        //Given
//        Mail mail = new Mail("testingappkaja@gmail.com", "subject", "message");
//
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(mail.getMailTo());
//        mailMessage.setSubject(mail.getSubject());
//        mailMessage.setText(mail.getMessage());
//        //When
//        simpleEmailService.send(mail);
//        //Then
//        verify(javaMailSender, times(1)).send(mailMessage);
//    }

    @Test
    public void shouldSetCc() {
        //Given
        Mail mail = new Mail("testingappkaja@gmail.com", "subject", "message", "carboncopy@test");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        //When
        simpleEmailService.send(mail);
        //Then
        assertEquals("carboncopy@test", mail.getToCc());
    }

    @Test
    public void shouldNotSetCc() {
        //Given
        Mail mail = new Mail("testingappkaja@gmail.com", "subject", "message");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());

        //When
        simpleEmailService.send(mail);

        //Then
        assertEquals(null, mail.getToCc());
    }
}