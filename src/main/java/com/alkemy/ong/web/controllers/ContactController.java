package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.contacts.Contact;
import com.alkemy.ong.domain.contacts.ContactService;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("contacts")
public class ContactController {
    private final ContactService contactService;
    public ContactController(ContactService contactService){
        this.contactService = contactService;
    }

    @GetMapping
    public ResponseEntity<List<ContactDTO>> findAll() {
        return ResponseEntity.ok().body(contactService.findAll().stream().map(this::toDTO).collect(toList()));
    }

    @PostMapping
    public ResponseEntity<ContactDTO> save(@Valid @RequestBody ContactDTO contactDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(contactService.save(toModel(contactDTO))));
    }

    private ContactDTO toDTO(Contact contact) {
        return ContactDTO.builder()
                .id(contact.getId())
                .name(contact.getName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .message(contact.getMessage())
                .build();
    }

    private Contact toModel(ContactDTO contactDTO){
        return Contact.builder()
                .id(contactDTO.getId())
                .name(contactDTO.getName())
                .email(contactDTO.getEmail())
                .phone(contactDTO.getPhone())
                .message(contactDTO.getMessage())
                .build();
    }

    @Data
    @Builder
    private static class ContactDTO {
        private Long id;
        @NotEmpty(message = "The field Name must not be empty")
        private String name;
        @NotEmpty(message = "The field Phone must not be empty")
        private String phone;
        @NotEmpty(message = "The field Email must not be empty")
        private String email;
        @NotEmpty(message = "The field Message must not be empty")
        private String message;
    }
}
