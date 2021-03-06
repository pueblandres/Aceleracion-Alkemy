package com.alkemy.ong.domain.members;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Members {

    private Long id;
    private String name;
    private String facebookUrl;
    private String instagramUrl;
    private String linkedinUrl;
    private String image;
    private String description;

}
