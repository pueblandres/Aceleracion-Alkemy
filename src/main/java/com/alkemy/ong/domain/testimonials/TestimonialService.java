package com.alkemy.ong.domain.testimonials;

import com.alkemy.ong.domain.utils.PageModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TestimonialService {

    private TestimonialGateway testimonialGateway;

    public TestimonialService(TestimonialGateway testimonialGateway) {
        this.testimonialGateway = testimonialGateway;
    }

    public Testimonial save(Testimonial testimonial) {
        return testimonialGateway.create(testimonial);
    }

    public Testimonial update(Long id, Testimonial testimonial) {
        return testimonialGateway.update(id, testimonial);
    }

    public void delete(Long id) {
        testimonialGateway.delete(id);
    }

    @Value("10")
    int pageSize;

    public PageModel findByPage(int pageNumber) {
        return testimonialGateway.findByPage(pageNumber, pageSize);
    }

}
