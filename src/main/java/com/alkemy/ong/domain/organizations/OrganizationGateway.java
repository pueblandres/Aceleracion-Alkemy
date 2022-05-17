package com.alkemy.ong.domain.organizations;

public interface OrganizationGateway {

    Organization findById(Long id);
    Organization toUpdate(Long id , Organization organization);
}
