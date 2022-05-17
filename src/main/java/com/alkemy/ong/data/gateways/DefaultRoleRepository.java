package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.RoleEntity;
import com.alkemy.ong.data.repositories.RoleRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.roles.Role;
import com.alkemy.ong.domain.roles.RoleGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultRoleRepository implements RoleGateway {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role search(Long id) {
        RoleEntity roleEntity = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID"));
        roleRepository.getById(id);
        return toModel(roleRepository.getById(id));
    }

    private Role toModel(RoleEntity roleEntity) {
        return Role.builder()
                .id(roleEntity.getId())
                .name(roleEntity.getName())
                .description(roleEntity.getDescription())
                .build();
    }
}
