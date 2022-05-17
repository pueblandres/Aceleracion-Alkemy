package com.alkemy.ong.domain.testimonials;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Testimonial {
    private Long id;
    private String name;
    private String image;
    private String content;
}
