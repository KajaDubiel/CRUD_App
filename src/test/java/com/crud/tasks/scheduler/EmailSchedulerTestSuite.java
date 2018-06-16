package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.MailCreatorService;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class EmailSchedulerTestSuite {

    @InjectMocks
    EmailScheduler emailScheduler;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    MailCreatorService mailCreatorService;

    @Mock
    JavaMailSender javaMailSender;

    @Test
    public void testSendInformationMail() {
        //Given
        Mail mail = new Mail("to@mail", "subject", "message");
        MimeMessagePreparator mimeMessagePreparator = simpleEmailService.createScheduleMimeMessage(mail);
        when(taskRepository.count()).thenReturn(7l);
        doNothing().when(simpleEmailService).send(mail);

        // When
        emailScheduler.sendInformationMail();

        //Then
        Mockito.verify(taskRepository, times(1)).count();
        Mockito.verify(simpleEmailService, times(1)).sendScheduleMail(any(Mail.class));
    }
}