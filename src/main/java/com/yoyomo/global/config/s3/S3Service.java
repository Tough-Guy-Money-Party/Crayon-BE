package com.yoyomo.global.config.s3;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.PublicAccessBlockConfiguration;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3Service {

    private static final Logger log = LoggerFactory.getLogger(S3Service.class);
    private final S3Client s3Client;

    public void createBucket(String bucketName) {
        CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();

        s3Client.createBucket(createBucketRequest);

        s3Client.putPublicAccessBlock(b -> b.bucket(bucketName)
                .publicAccessBlockConfiguration(PublicAccessBlockConfiguration.builder()
                        .blockPublicAcls(false)
                        .ignorePublicAcls(false)
                        .blockPublicPolicy(false)
                        .restrictPublicBuckets(false)
                        .build()));

        log.info("Bucket created: " + bucketName);
    }

    public void save(String bucketName) throws IOException {

        log.info("현재 경로" + System.getProperty("user.dir"));
        String distFolderPath = "../vite-notion-to-site/dist";
        File distDir = new File(distFolderPath);

        String canonicalPath = distDir.getCanonicalPath();
        Path distPath = Paths.get(canonicalPath);

        if (!Files.exists(distPath)) {
            log.info("해당 경로에 파일이 존재하지 않습니다. " + distPath.toAbsolutePath());
            return;
        }

        try (Stream<Path> paths = Files.walk(distPath)) {
            List<Path> filePaths = paths.filter(Files::isRegularFile).collect(Collectors.toList());

            S3Service.log.info("버킷에 파일 업로드 중");
            for (Path filePath : filePaths) {
                uploadFileToS3(bucketName, filePath, canonicalPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadFileToS3(String bucketName, Path filePath, String distFolderPathString) {

        Path distFolderPath = Paths.get(distFolderPathString);
        String key = distFolderPath.relativize(filePath).toString().replace("\\", "/");

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromFile(filePath.toFile()));

        S3Service.log.info("Uploaded: " + filePath + " to S3 with key: " + key);
    }
}
