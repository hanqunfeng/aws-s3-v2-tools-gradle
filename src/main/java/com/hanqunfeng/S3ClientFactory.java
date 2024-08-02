package com.hanqunfeng;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.transfer.s3.S3TransferManager;

import java.time.Duration;

/**
 * <h1>S3ClientFactory</h1>
 * Created by hanqf on 2024/1/26 16:34.
 */


public class S3ClientFactory {

    private S3ClientFactory() {
    }
    
    private S3ClientConfig config;

    public S3ClientFactory(S3ClientConfig config) {
        this.config = config;
    }

    public static S3ClientFactory getInstance(S3ClientConfig config) {
        return new S3ClientFactory(config);
    }


    public S3Client getS3Client() {
        return S3Client.builder()
                .region(Region.of(config.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(config.getAws_access_key(), config.getAws_secret_key())))
                .httpClientBuilder(ApacheHttpClient.builder()
                        .maxConnections(S3ClientConfig.MAX_CONNECTIONS)
                        .connectionTimeout(Duration.ofSeconds(5))
                )
                .build();
    }

    public S3AsyncClient getS3AsyncClient() {
        return S3AsyncClient.crtBuilder()
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(config.getAws_access_key(), config.getAws_secret_key())))
                .region(Region.of(config.getRegion()))
                .targetThroughputInGbps(20.0)
                .minimumPartSizeInBytes(S3ClientConfig.MIN_PART_SIZE)
                .build();
    }

    public S3TransferManager getS3TransferManager() {
        return S3TransferManager.builder()
                .s3Client(getS3AsyncClient())
                .build();
    }
}
