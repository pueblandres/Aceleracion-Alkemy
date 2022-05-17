package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.email.EmailService;
import com.alkemy.ong.domain.exceptions.CommunicationException;
import com.alkemy.ong.domain.roles.Role;
import com.alkemy.ong.domain.roles.RoleService;
import com.alkemy.ong.domain.users.UserService;
import com.alkemy.ong.domain.users.Users;
import com.alkemy.ong.web.security.CustomUserDetails;
import com.alkemy.ong.web.security.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder encoder;
    private final RoleService roleService;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, EmailService emailService, PasswordEncoder encoder,
                          RoleService roleService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.emailService = emailService;
        this.encoder = encoder;
        this.roleService = roleService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok().body(toListDto(userService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id, Authentication authentication) {
        if (hasRole(authentication, id.toString()) || hasRole(authentication, "ADMIN")) {
            return ResponseEntity.ok(toDto(userService.findById(id)));
        }
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(toDto(userService.findByEmail(user.getUsername())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> editUser(@RequestBody UserDto userDto, @PathVariable Long id) {
        Users users = createUser(userDto);
        users.setId(id);
        UserDto userSaved = toDto(userService.update(users));
        return new ResponseEntity<>(userSaved, HttpStatus.OK);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserBasicDto userBasicDto) {
        Users users = createUser(userBasicDto);
        UserDto userSaved = toDto(userService.save(users));
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(users.getEmail(), userBasicDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new CommunicationException("Incorrect credentials");
        }

        final CustomUserDetails userDetails = userService.loadUserByUsername(users.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthenticationResponse(jwt));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private List<UserDto> toListDto(List<Users> users) {
        return users.stream()
                .map(this::toDto)
                .collect(toList());
    }

    private boolean hasRole(Authentication authentication, String s) {
        return authentication.getAuthorities().contains(new SimpleGrantedAuthority(s));
    }

    private UserDto toDto(Users user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .photo(user.getPhoto())
                .role(roleToDto(user))
                .password(user.getPassword())
                .build();
    }

    private RoleDto roleToDto(Users user) {
        Role role = user.getRole();
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }

    @Builder
    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class RoleDto {
        private Long id;
        private String name;
        private String description;
    }

    @Builder
    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class UserDto {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String photo;
        private RoleDto role;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserBasicDto {
        private String firstName;
        private String lastName;
        private String email;
        private String password;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthenticationResponse {
        private String jwt;
    }

    private Users createUser(UserBasicDto userBasicDto) {
        Users users = new Users();
        users.setRole(roleService.searchRoleById(2L));
        users.setFirstName(userBasicDto.getFirstName());
        users.setLastName(userBasicDto.getLastName());
        users.setEmail(userBasicDto.getEmail());
        users.setPassword(encoder.encode(userBasicDto.getPassword()));
        users.setPhoto("No photo");
        return users;
    }

    private Users createUser(UserDto userDto) {
        Users users = new Users();
        users.setRole(roleService.searchRoleById(userDto.getId()));
        users.setFirstName(userDto.getFirstName());
        users.setLastName(userDto.getLastName());
        users.setEmail(userDto.getEmail());
        users.setPassword(encoder.encode(userDto.getPassword()));
        users.setPhoto(userDto.getPhoto());
        return users;
    }
}
