package com.alkemy.ong.domain.slide;

import com.alkemy.ong.domain.organizations.Organization;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Slide {

    private Long id;
    private String imageUrl;
    private String text;
    private Integer order;
    private Organization organization;

}
