package com.afreen.supplychain_iq.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final S3Presigner presigner;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3Service(S3Client s3Client, S3Presigner presigner) {
        this.s3Client = s3Client;
        this.presigner = presigner;
    }

    public String uploadFile(MultipartFile file, String folder) throws IOException {
        String key = folder + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
        return key;
    }

    public String getPresignedUrl(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .getObjectRequest(getObjectRequest)
                .build();

        return presigner.presignGetObject(presignRequest).url().toString();
    }

    public void deleteFile(String key) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.deleteObject(request);
    }
}