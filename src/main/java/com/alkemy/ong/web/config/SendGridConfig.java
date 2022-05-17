package com.alkemy.ong.web.config;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridConfig {


    @Value("${sendgridProperties.emailApiKey}")
    private String emailApiKey;

    @Bean
    public SendGrid getSendgrid(){
        return new SendGrid(emailApiKey);
    }
}
