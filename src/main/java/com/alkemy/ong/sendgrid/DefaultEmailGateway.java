package com.alkemy.ong.sendgrid;

import com.alkemy.ong.domain.email.EmailGateway;
import com.alkemy.ong.domain.exceptions.CommunicationException;
import com.alkemy.ong.web.controllers.utils.MailUtil;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DefaultEmailGateway implements EmailGateway {

    private final SendGrid sendGrid;
    private MailUtil mailUtil;

    public DefaultEmailGateway(SendGrid sendGrid, MailUtil mailUtil){
        this.sendGrid = sendGrid;
        this.mailUtil = mailUtil;
    }

    @Value("${sendgridProperties.email}")
    private String senderMail;

    private static final Logger log = LoggerFactory.getLogger(DefaultEmailGateway.class);
    public String sendmail(String to, String subject, String body, String title) {

        Email email = new Email(senderMail);

        Mail mail = new Mail(email, subject, new Email(to), new Content( "text/html", mailUtil.mailTemplate(title, body)));


        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);

            log.info("{}", response.getStatusCode());
            log.info("{}", response.getBody());
            log.info("{}", response.getHeaders());

            return subject;

        } catch (IOException ex) {
            log.error("Error trying to send email");
            throw new CommunicationException("send mail");
        }
    }

}
