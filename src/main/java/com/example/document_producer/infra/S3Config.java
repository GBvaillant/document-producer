//package com.example.document_producer.infra;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.s3.S3Client;
//
//import java.net.URI;
//
//@Configuration
//public class S3Config {
//
//    @Value("${aws.s3.endpoint}")
//    private String endpoint;
//
//    @Value("${aws.s3.region}")
//    private String region;
//
//    @Value("${aws.s3.accessKeyId}")
//    private String accessKeyId;
//
//    @Value("${aws.s3.secretAccessKey}")
//    private String secretAccessKey;
//
//    @Bean
//    public S3Client s3Client() {
//        return S3Client.builder()
//                .endpointOverride(URI.create(endpoint))
//                .region(Region.of(region))
//                .credentialsProvider(() -> AwsBasicCredentials.create(accessKeyId, secretAccessKey))
//                .build();
//
//    }
//}
