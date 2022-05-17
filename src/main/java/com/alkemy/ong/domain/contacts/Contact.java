package com.alkemy.ong.domain.contacts;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Contact {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String message;
}
