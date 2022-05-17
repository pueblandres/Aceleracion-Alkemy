package com.alkemy.ong.domain.testimonials;

import com.alkemy.ong.domain.utils.PageModel;

public interface TestimonialGateway {
    Testimonial create(Testimonial testimonial);

    Testimonial update(Long id, Testimonial testimonial);

    PageModel findByPage(int number, int size);

    void delete(Long id);
}
