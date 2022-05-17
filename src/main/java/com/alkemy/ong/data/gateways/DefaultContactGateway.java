package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.ContactEntity;
import com.alkemy.ong.data.repositories.ContactRepository;
import com.alkemy.ong.domain.contacts.Contact;
import com.alkemy.ong.domain.contacts.ContactGateway;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class DefaultContactGateway implements ContactGateway {
    private final ContactRepository contactRepository;

    public DefaultContactGateway(ContactRepository contactRepository){
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> findAll() {
        return contactRepository.findAll()
                .stream()
                .map(this::toModel)
                .collect(toList());
    }

    @Override
    public Contact save(Contact contact) {
        return toModel(contactRepository.save(toEntity(contact)));
    }

    private ContactEntity toEntity(Contact contact) {
        return ContactEntity.builder()
                .name(contact.getName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .message(contact.getMessage())
                .build();
    }

    private Contact toModel(ContactEntity contactEntity){
        return Contact.builder()
                .id(contactEntity.getId())
                .name(contactEntity.getName())
                .email(contactEntity.getEmail())
                .phone(contactEntity.getPhone())
                .message(contactEntity.getMessage())
                .build();
    }
}
