package com.alkemy.ong.domain.organizations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {


     private final OrganizationGateway organizationGateway;

    public OrganizationService(OrganizationGateway organizationGateway) {
        this.organizationGateway = organizationGateway;
    }

    public Organization findById(Long id){

        return organizationGateway.findById(id);
    }
    public  Organization update(Long id, Organization organization){

        return  organizationGateway.toUpdate(id,organization);
    }
}
