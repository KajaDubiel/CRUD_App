package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {
    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private AdminConfig adminConfig;

    public String buildTrelloCardEmail(String message) {
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/KajaDubiel.github.io/");
        context.setVariable("button", "Visit vebsite");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("greetings", "have nice day");
        context.setVariable("company", "Crud App");
        context.setVariable("admin_mail", adminConfig.getAdminMail());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("/mail/created-trello-card-mail", context);
        //przed mail dać /
    }

    public String buildTrelloScheduleMail(String message){
        List<String> functionality = new ArrayList<>();
        functionality.add("Add more tasks");
        functionality.add("Manage tasks which you have done using trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/KajaDubiel.github.io/");
        context.setVariable("button", "Check on website");
        context.setVariable("greetings", "Remember to check tasks for today:)");
        context.setVariable("admin_config", adminConfig);
        context.setVariable("show_button", true);
        context.setVariable("functionality", functionality);
        return templateEngine.process("/mail/schedule-mail", context);
    }
}
