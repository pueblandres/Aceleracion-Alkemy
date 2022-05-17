package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.TestimonialEntity;
import com.alkemy.ong.data.repositories.TestimonialRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.testimonials.Testimonial;
import com.alkemy.ong.domain.testimonials.TestimonialGateway;
import com.alkemy.ong.domain.utils.PageModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Component
public class DefaultTestimonialGateway implements TestimonialGateway {

    private final TestimonialRepository testimonialRepository;

    public DefaultTestimonialGateway(TestimonialRepository testimonialRepository) {
        this.testimonialRepository = testimonialRepository;
    }

    @Override
    public Testimonial create(Testimonial testimonial) {
        TestimonialEntity entity = toEntity(testimonial);
        return toModel(testimonialRepository.save(entity));
    }

    @Override
    public Testimonial update(Long id, Testimonial testimonial) {
        TestimonialEntity entity = testimonialRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID was not found"));
        entity.setName(testimonial.getName());
        entity.setImage(testimonial.getImage());
        entity.setContent(testimonial.getContent());
        return toModel(testimonialRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        TestimonialEntity entity = testimonialRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID was not found"));
        entity.setDeleted(true);
        testimonialRepository.save(entity);
    }

    @Override
    public PageModel findByPage(int pageNumber, int size) {
        Page<TestimonialEntity> page = testimonialRepository.findAll(PageRequest.of(pageNumber, size));
        return PageModel.builder()
                .totalPages(page.getTotalPages())
                .isLast(page.isLast())
                .isFirst(page.isFirst())
                .content(page.getContent()
                        .stream()
                        .map(this::toModel)
                        .collect(toList()))
                .build();
    }

    private Testimonial toModel(TestimonialEntity testimonialEntity) {
        return Testimonial.builder()
                .id(testimonialEntity.getId())
                .name(testimonialEntity.getName())
                .image(testimonialEntity.getImage())
                .content(testimonialEntity.getContent())
                .build();
    }

    private TestimonialEntity toEntity(Testimonial testimonial) {
        return TestimonialEntity.builder()
                .name(testimonial.getName())
                .content(testimonial.getContent())
                .image(testimonial.getImage())
                .build();
    }
}

