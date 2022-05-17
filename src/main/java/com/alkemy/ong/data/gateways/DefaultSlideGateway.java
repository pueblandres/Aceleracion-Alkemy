package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.OrganizationEntity;
import com.alkemy.ong.data.entities.SlidesEntity;
import com.alkemy.ong.data.repositories.OrganizationRepository;
import com.alkemy.ong.data.repositories.SlidesRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.slide.Slide;
import com.alkemy.ong.domain.slide.SlideGateway;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
public class DefaultSlideGateway implements SlideGateway {

    private final SlidesRepository slidesRepository;
    private final OrganizationRepository organizationRepository;

    public DefaultSlideGateway(SlidesRepository slidesRepository, OrganizationRepository organizationRepository){
        this.slidesRepository = slidesRepository;
        this.organizationRepository = organizationRepository;
    }


    @Override
    public Slide findById(Long id) {
        SlidesEntity slidesEntity = slidesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID"));
        return toModel(slidesEntity);
    }

    @Override
    public void delete(Long id) {
        SlidesEntity slidesEntity = slidesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID"));
        slidesEntity.setIsDeleted(true);
        slidesRepository.save(slidesEntity);
    }

    @Override
    public List<Slide> findAll() {
        return slidesRepository.findAll()
                .stream()
                .map(this::toModel)
                .collect(toList());
    }

    public Slide save(Slide slide,Long organizationId) {
        Optional<OrganizationEntity> organizationEntity = organizationRepository.findById(organizationId);
        organizationEntity.orElseThrow(() -> new ResourceNotFoundException("ID"));
        SlidesEntity slideEntity = toEntity(slide);
        slideEntity.setOrganization(organizationEntity.get());


        return toModel(slidesRepository.save(slideEntity));
    }

    public Slide toModel(SlidesEntity slidesEntity){
        return Slide.builder()
                .id(slidesEntity.getId())
                .imageUrl(slidesEntity.getImageUrl())
                .text(slidesEntity.getText())
                .order(slidesEntity.getSlideOrder())
                .organization(DefaultOrganizationGateway.toModel(slidesEntity.getOrganization()))
                .build();
    }
    private SlidesEntity toEntity(Slide slide){
        return SlidesEntity.builder()
                .imageUrl(slide.getImageUrl())
                .slideOrder(slide.getOrder())
                .organization(OrganizationEntity.builder().build())
                .build();
    }



    public static Slide toModelBasic(SlidesEntity slidesEntity){
        return Slide.builder()
                .id(slidesEntity.getId())
                .imageUrl(slidesEntity.getImageUrl())
                .text(slidesEntity.getText())
                .order(slidesEntity.getSlideOrder())
                .build();
    }

    public Slide toUpdate(Long id, Slide slide,Long organizationId) {
        OrganizationEntity findOrganization= organizationRepository.findById(organizationId).orElseThrow(() -> new ResourceNotFoundException("slide with id " + id));
        SlidesEntity findSlide = slidesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("slide with id " + id));
        refreshValues(findSlide, slide,findOrganization);
        SlidesEntity saved = slidesRepository.save(findSlide);
        return toModel(saved);
    }

    private void refreshValues(SlidesEntity slidesEntity, Slide slide,OrganizationEntity organizationEntity) {
        slidesEntity.setSlideOrder(slide.getOrder());
        slidesEntity.setImageUrl(slide.getImageUrl());
        slidesEntity.setOrganization(organizationEntity);

    }


}
