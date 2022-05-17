package com.alkemy.ong.domain.roles;

import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleGateway roleGateway;

    public RoleService(RoleGateway roleGateway) {
        this.roleGateway = roleGateway;
    }

    public Role searchRoleById(Long id) {
        return roleGateway.search(id);
    }
}
