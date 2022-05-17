package com.alkemy.ong.domain.cloud;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CloudOutput {
    private String key;
    private String url;
}