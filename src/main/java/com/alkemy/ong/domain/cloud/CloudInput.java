package com.alkemy.ong.domain.cloud;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class CloudInput {
    private MultipartFile file;
}
