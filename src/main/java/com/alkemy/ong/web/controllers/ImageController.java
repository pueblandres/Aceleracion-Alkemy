package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.cloud.CloudInput;
import com.alkemy.ong.domain.cloud.CloudOutput;
import com.alkemy.ong.domain.cloud.CloudService;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {

    private CloudService cloudService;

    public ImageController(CloudService cloudService){
        this.cloudService = cloudService;
    }

    @PostMapping("${amazonProperties.endpointUrl}")
    public ResponseEntity<CloudOutputDTO> uploader(@RequestParam("file") MultipartFile multipartFile) throws IOException{
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toCloudDTO(
                        cloudService.save(
                                toCloudInput(multipartFile))));
    }

    private CloudInput toCloudInput(MultipartFile multipartFile){
        return CloudInput.builder().file(multipartFile).build();
    }

    private CloudOutputDTO toCloudDTO(CloudOutput cloudOutput){
        return CloudOutputDTO.builder().key(cloudOutput.getKey()).url(cloudOutput.getUrl()).build();
    }

    @Data
    @Builder
    private static class CloudInputDTO{
        private MultipartFile file;
    }

    @Data
    @Builder
    private static class CloudOutputDTO{
        private String key;
        private String url;
    }

}
