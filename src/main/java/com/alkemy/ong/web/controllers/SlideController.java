package com.alkemy.ong.web.controllers;



import com.alkemy.ong.data.entities.OrganizationEntity;
import com.alkemy.ong.domain.organizations.Organization;
import com.alkemy.ong.domain.slide.Slide;
import com.alkemy.ong.domain.slide.SlideService;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/slides")
public class SlideController {

    private final SlideService slideService;


    public SlideController(SlideService slideService){
        this.slideService = slideService;
    }

    @GetMapping("/{id}")
    private ResponseEntity<SlideDTO> getSlideById(@PathVariable Long id){
        Slide slide = slideService.findById(id);
        SlideDTO slideDTO = toDTO(slide);
        return ResponseEntity.ok().body(slideDTO);

    }

    @GetMapping
    public ResponseEntity<List<SlideBasicDTO>> findAll() {
        return ResponseEntity.ok().body(toListDto(slideService.findAll()));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> delete(@PathVariable Long id){
        slideService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
    @PostMapping()
    public ResponseEntity<SlideBasicDTO> save( @RequestBody SlideBasicDTO slideBasicDTO) throws IOException {
        Slide slide =  slideService.save(toBasicModel(slideBasicDTO), slideBasicDTO.getOrganizationId());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.toBasicDTO(slide));
    }

    @Data
    @Builder
    private static class SlideDTO{
        private Long id;
        private String imageUrl;
        private String text;
        private Integer order;
        private OrganizationController.OrganizationPublicDTO organizationPublicDTO;
    }

    @Data
    @Builder
    private static class SlideBasicDTO{
        private String imageUrl;
        private Integer order;
        private Long organizationId;

    }


    private SlideBasicDTO toBasicDTO(Slide slide){
        return SlideBasicDTO.builder()
                .imageUrl(slide.getImageUrl())
                .order(slide.getOrder())
                .build();
    }

    private Slide toBasicModel(SlideBasicDTO slideBasicDTO){
        return Slide.builder()
                .imageUrl(slideBasicDTO.getImageUrl())
                .order(slideBasicDTO.getOrder())
                .build();
    }

    private SlideDTO toDTO(Slide slide){

        return SlideDTO.builder()
                .id(slide.getId())
                .imageUrl(slide.getImageUrl())
                .text(slide.getText())
                .order(slide.getOrder())
                .organizationPublicDTO(OrganizationController.toDTO(slide.getOrganization()))
                .build();
    }

    private List<SlideBasicDTO> toListDto(List<Slide> slides) {
        return slides.stream()
                .map(this::toBasicDTO)
                .collect(toList());
    }

    @Data
    @Builder
    public static class SlideOrgDTO{
        private Long id;
        private String imageUrl;
        private String text;
        private Integer order;
    }

    public static SlideOrgDTO toOrgDTO(Slide slide){
        return SlideOrgDTO.builder()
                .id(slide.getId())
                .imageUrl(slide.getImageUrl())
                .text(slide.getText())
                .order(slide.getOrder())
                .build();
    }





    @PutMapping("/{id}")
    public ResponseEntity<SlideController.SlideBasicDTO> update(@PathVariable Long id, @RequestBody SlideController.SlideBasicDTO dto) throws IOException {
       Slide slide=slideService.update(id,toBasicModel(dto),dto.getOrganizationId());

        return new ResponseEntity<>(toBasicDTO(slide), HttpStatus.OK);
    }




}
