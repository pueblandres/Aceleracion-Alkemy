package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.OrganizationEntity;

import com.alkemy.ong.data.entities.SlidesEntity;
import com.alkemy.ong.data.repositories.OrganizationRepository;
import com.alkemy.ong.data.repositories.SlidesRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.organizations.Organization;
import com.alkemy.ong.domain.organizations.OrganizationGateway;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
public class DefaultOrganizationGateway implements OrganizationGateway {


    private final OrganizationRepository organizationRepository;
    private final SlidesRepository slidesRepository;

    public DefaultOrganizationGateway(OrganizationRepository organizationRepository, SlidesRepository slidesRepository) {
        this.organizationRepository = organizationRepository;
        this.slidesRepository = slidesRepository;
    }

    @Override
    public Organization findById(Long id) {
        Optional<OrganizationEntity> organizationEntity = organizationRepository.findById(id);
        organizationEntity.orElseThrow(() -> new ResourceNotFoundException("ID"));
        List<SlidesEntity> slidesEntity = slidesRepository.findByOrganizationOrderBySlideOrderAsc(organizationEntity.get());
        OrganizationEntity organizationEntityNew = organizationEntity.get();
        return Organization.builder()
                .name(organizationEntityNew.getName())
                .phone(organizationEntityNew.getPhone())
                .image(organizationEntityNew.getImage())
                .address(organizationEntityNew.getAddress())
                .facebook(organizationEntityNew.getFacebook())
                .instagram(organizationEntityNew.getInstagram())
                .linkedin(organizationEntityNew.getLinkedin())
                .slides((slidesEntity)
                        .stream()
                        .map(slide -> DefaultSlideGateway.toModelBasic(slide))
                        .collect(toList()))
                .build();

    }

    @Override
    public Organization toUpdate(Long id, Organization organization){
        OrganizationEntity organizationEntity=organizationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID was not found"));
        organizationEntity.setAddress(organization.getAddress());
        organizationEntity.setName(organization.getName());
        organizationEntity.setPhone(organization.getPhone());
        organizationEntity.setImage(organization.getImage());
        organizationEntity.setInstagram(organization.getInstagram());
        organizationEntity.setFacebook(organization.getFacebook());
        organizationEntity.setLinkedin(organization.getLinkedin());
        return toModel(organizationRepository.save(organizationEntity));
    }


    private OrganizationEntity toEntity(Organization organization){
        return OrganizationEntity.builder()
                .address(organization.getAddress())
                .name(organization.getName())
                .image(organization.getImage())
                .phone(organization.getPhone())
                .facebook(organization.getFacebook())
                .instagram(organization.getInstagram())
                .linkedin(organization.getLinkedin())
                .build();

    }

    public static Organization toModel(OrganizationEntity organizationEntity){
        return Organization.builder()
                .name(organizationEntity.getName())
                .phone(organizationEntity.getPhone())
                .image(organizationEntity.getImage())
                .address(organizationEntity.getAddress())
                .facebook(organizationEntity.getFacebook())
                .instagram(organizationEntity.getInstagram())
                .linkedin(organizationEntity.getLinkedin())
                .build();
    }

}
