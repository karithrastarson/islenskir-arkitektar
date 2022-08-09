package com.karithrastarson.registry.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {

    public AWSCredentials credentials() {
        AWSCredentials credentials = new BasicAWSCredentials(
                "AKIAS3U2YTOQPHRR66EH",
                "h3PWF4AjIRep9R2ho6D3npYhoScbhWOqq7YbavOK"
        );
        return credentials;
    }

    @Bean
    public AmazonS3 amazonS3() {
        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials()))
                .withRegion(Regions.EU_WEST_1)
                .build();
        return s3client;
    }

}
