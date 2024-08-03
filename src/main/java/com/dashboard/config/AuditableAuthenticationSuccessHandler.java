package com.dashboard.config;

import com.dashboard.service.kafka.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuditableAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private KafkaSender kafkaSender;

    private final AuthenticationSuccessHandler defaultHandler = new SavedRequestAwareAuthenticationSuccessHandler();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Ваше дополнительное действие, например, запись в лог
        kafkaSender.sendMessage(authentication.getName(),"login-audit");
        System.out.println("User " + authentication.getName() + " has logged in.");

        // Делегирование обработчику по умолчанию для сохранения стандартного поведения
        defaultHandler.onAuthenticationSuccess(request, response, authentication);
    }
}