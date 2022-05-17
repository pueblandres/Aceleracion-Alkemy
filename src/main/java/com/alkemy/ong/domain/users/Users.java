package com.alkemy.ong.domain.users;

import com.alkemy.ong.domain.roles.Role;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    Long id;

    String firstName;

    String lastName;

    String email;

    String password;

    String photo;

    Role role;
}
