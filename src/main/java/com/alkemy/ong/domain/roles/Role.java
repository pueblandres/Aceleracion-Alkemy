package com.alkemy.ong.domain.roles;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    Long id;

    String name;

    String description;

}
