package com.alkemy.ong.data.repositories.gateway;

import com.alkemy.ong.domain.cloud.CloudInput;
import com.alkemy.ong.domain.cloud.CloudGateway;
import com.alkemy.ong.domain.cloud.CloudOutput;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.UUID;

@Component
public class DefaultCloudGateway implements CloudGateway {

    @Value("${amazonProperties.bucketName}")
    private String BUCKET;

    public CloudOutput save(CloudInput cloud) throws IOException {
        String extension = StringUtils.getFilenameExtension(cloud.getFile().getOriginalFilename());
        String key = String.format("%s.%s", UUID.randomUUID(), extension);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(cloud.getFile().getContentType());

        PutObjectRequest putObjectRequest =
                new PutObjectRequest(BUCKET, key, cloud.getFile().getInputStream(), objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead);

        accessAmazon(putObjectRequest).putObject(putObjectRequest);

        return toCloudOuput(key, getObjectUrl(key));
    }

    private String getObjectUrl(String key) {
        return String.format("https://%s.s3.amazonaws.com/%s", BUCKET, key);
    }

    private CloudOutput toCloudOuput(String key, String url) {
        return CloudOutput.builder()
                .key(key)
                .url(url)
                .build();
    }

    private AmazonS3 accessAmazon(PutObjectRequest putObjectRequest){
        return AmazonS3ClientBuilder
                .standard()
                .withRegion("us-east-1")
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .build();
    }
}

