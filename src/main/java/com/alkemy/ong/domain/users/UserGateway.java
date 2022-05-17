package com.alkemy.ong.domain.users;

import com.alkemy.ong.web.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserGateway {
    List<Users> findAll();

    void delete(Long id);

    CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    Users findById(Long id);

    Users findByEmail(String email);

    Users create(Users users);

    Users update(Users users);
}
