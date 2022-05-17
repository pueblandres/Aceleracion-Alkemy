package com.alkemy.ong.domain.email;

public interface EmailGateway {

    String sendmail(String to,String subject, String body, String title);
}
