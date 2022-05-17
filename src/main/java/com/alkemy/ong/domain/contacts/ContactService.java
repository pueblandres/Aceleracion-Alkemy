package com.alkemy.ong.domain.contacts;

import com.alkemy.ong.domain.email.EmailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final String texto = "Gracias por contactarte con nosotros ";

    private final ContactGateway contactGateway;
    private final EmailService emailService;

    public ContactService(ContactGateway contactGateway, EmailService emailService){
        this.contactGateway = contactGateway;
        this.emailService = emailService;
    }

    public List<Contact> findAll() {
        return contactGateway.findAll();
    }

    public Contact save(Contact contact) {

        emailService.sendMail(contact.getEmail(), "Bienvenido a Alkemy " + contact.getName(), texto , "Bienvenido " + contact.getName());


        return contactGateway.save(contact);
    }
}
