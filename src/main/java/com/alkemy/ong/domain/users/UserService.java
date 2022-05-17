package com.alkemy.ong.domain.users;

import com.alkemy.ong.domain.email.EmailService;
import com.alkemy.ong.web.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserGateway userGateway;
    private final EmailService emailService;
    private final String texto = "Gracias por registrarte con nosotros ";

    public UserService(UserGateway userGateway, EmailService emailService) {
        this.userGateway = userGateway;
        this.emailService = emailService;
    }

    public List<Users> findAll() {
        return userGateway.findAll();
    }

    public void delete(Long id) {
        userGateway.delete(id);
    }

    public Users findByEmail(String email) {
        return userGateway.findByEmail(email);
    }

    public Users save(Users users) {
        emailService.sendMail(users.getEmail(), "Bienvenido a Alkemy " + users.getFirstName(), texto + users.getFirstName() + "" + users.getLastName(), "Bienvenido " + users.getFirstName());
        return userGateway.create(users);
    }

    public Users update(Users users) {
        return userGateway.update(users);
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userGateway.loadUserByUsername(username);
    }

    public Users findById(Long id) {
        return userGateway.findById(id);
    }

}
