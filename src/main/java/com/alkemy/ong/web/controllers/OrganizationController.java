package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.organizations.Organization;
import com.alkemy.ong.domain.organizations.OrganizationService;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/organization")
public class OrganizationController {


    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }


    @GetMapping("/public/{id}")
    public ResponseEntity<OrganizationPublicDTO> findOrganizationById(@PathVariable Long id){

        Organization organization= organizationService.findById(id);
        OrganizationPublicDTO organizationPublicDataDTO=toDTOComplete(organization);
        return ResponseEntity.ok(organizationPublicDataDTO);
    }

    @PutMapping("/public/{id}")
    public ResponseEntity<OrganizationPublicDTO> update(@PathVariable Long id,@RequestBody OrganizationPublicDTO dto){
        Organization organization=organizationService.update(id,toModel(dto));

        return new ResponseEntity<>(toDTO(organization), HttpStatus.OK);
    }
    public static OrganizationPublicDTO toDTO(Organization organization){
        return OrganizationPublicDTO.builder()
                .address(organization.getAddress())
                .name(organization.getName())
                .image(organization.getImage())
                .phone(organization.getPhone())
                .facebook(organization.getFacebook())
                .instagram(organization.getInstagram())
                .linkedin(organization.getLinkedin())
                .build();
    }
    public Organization toModel(OrganizationPublicDTO organizationPublicDataDTO){
        return Organization.builder()
                .address(organizationPublicDataDTO.getAddress())
                .name(organizationPublicDataDTO.getName())
                .image(organizationPublicDataDTO.getName())
                .phone(organizationPublicDataDTO.getPhone())
                .linkedin(organizationPublicDataDTO.getLinkedin())
                .instagram(organizationPublicDataDTO.getInstagram())
                .facebook(organizationPublicDataDTO.getFacebook())
                .build();
    }
    @Data
    @Builder
    public static class OrganizationPublicDTO{
        private String name;
        private String image;
        private String address;
        private Integer phone;
        private String facebook;
        private String linkedin;
        private String instagram;
        private List<SlideController.SlideOrgDTO> slides;
    }

    public OrganizationPublicDTO toDTOComplete(Organization organization){
        return OrganizationPublicDTO.builder()
                .address(organization.getAddress())
                .name(organization.getName())
                .image(organization.getImage())
                .phone(organization.getPhone())
                .facebook(organization.getFacebook())
                .instagram(organization.getInstagram())
                .linkedin(organization.getLinkedin())
                .slides(organization.getSlides()
                        .stream()
                        .map(slide -> SlideController.toOrgDTO(slide))
                        .collect(toList()))
                .build();
    }
}
