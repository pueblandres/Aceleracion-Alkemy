package com.alkemy.ong.domain.cloud;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CloudService {

    private final CloudGateway cloudGateway;

    public CloudService(CloudGateway cloudGateway){
        this.cloudGateway = cloudGateway;
    }

    public CloudOutput save(CloudInput cloud) throws IOException {
        return cloudGateway.save(cloud);
    }


}
