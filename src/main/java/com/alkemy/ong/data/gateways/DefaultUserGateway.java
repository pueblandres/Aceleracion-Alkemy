package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.RoleEntity;
import com.alkemy.ong.data.entities.UserEntity;
import com.alkemy.ong.data.repositories.UserRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.roles.Role;
import com.alkemy.ong.domain.users.Users;
import com.alkemy.ong.domain.users.UserGateway;
import com.alkemy.ong.web.security.CustomUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public class DefaultUserGateway implements UserGateway {
    private final UserRepository userRepository;

    public DefaultUserGateway(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Users> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toModel)
                .collect(toList());
    }

    @Override
    public void delete(Long id) {
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Id"));
        userRepository.deleteById(id);
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        if (userEntity == null) {
            throw new NullPointerException("Username or password invalid");
        }

        Collection<? extends GrantedAuthority> authorities = userEntityRole2Collection(userEntity);

        return new CustomUserDetails(userEntity.getEmail(), userEntity.getPassword(), authorities, userEntity.getId());
    }

    @Override
    public Users findById(Long id) {
        UserEntity foundUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return toModel(foundUser);
    }

    @Override
    public Users findByEmail(String email) {
        return toModel(userRepository.findByEmail(email));
    }

    @Override
    public Users create(Users users) {
        UserEntity entidad = toEntity(users);
        return toModel(userRepository.save(entidad));
    }

    @Override
    public Users update(Users users) {
        UserEntity validar = userRepository.findById(users.getId()).orElseThrow(() -> new ResourceNotFoundException("Id not match"));
        UserEntity entidad = toEntity(users);
        return toModel(userRepository.save(entidad));
    }

    private UserEntity toEntity(Users user) {
        return UserEntity.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .photo(user.getPhoto())
                .role(roleEntity(user.getRole()))
                .build();
    }

    private Collection<? extends GrantedAuthority> userEntityRole2Collection(UserEntity userEntity) {
        Optional<UserEntity> user = Optional.ofNullable(userEntity);
        return user.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().getName().toUpperCase()))
                .collect(Collectors.toList());
    }

    private Users toModel(UserEntity userEntity) {
        return Users.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .photo(userEntity.getPhoto())
                .role(roleToModel(userEntity))
                .build();
    }

    private Role roleToModel(UserEntity userEntity) {
        RoleEntity role = userEntity.getRole();
        return Role.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }

    private RoleEntity roleEntity(Role role) {
        return RoleEntity.builder()
                .id(role.getId())
                .description(role.getDescription())
                .name(role.getName())
                .build();
    }
}
