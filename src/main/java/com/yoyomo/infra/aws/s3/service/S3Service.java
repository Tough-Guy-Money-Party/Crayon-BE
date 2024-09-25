package com.yoyomo.infra.aws.s3.service;

import com.yoyomo.domain.club.exception.UnavailableSubdomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final S3Client s3Client;
    @Value("${vite.uri}")
    private String VITE_PUBLIC_API_URL;

    public void createBucket(String bucketName) {
        try {
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

            //cloudfront배포를 위한 S3권한 해제
            applyBucketPolicy(bucketName);

        } catch (S3Exception e) {
            throw new UnavailableSubdomainException(e.statusCode(), e.getMessage());
        }
    }

    private void applyBucketPolicy(String bucketName) {
        String policyJson = String.format(
                "{\n" +
                        "  \"Version\": \"2012-10-17\",\n" +
                        "  \"Statement\": [\n" +
                        "    {\n" +
                        "      \"Sid\": \"PublicReadGetObject\",\n" +
                        "      \"Effect\": \"Allow\",\n" +
                        "      \"Principal\": \"*\",\n" +
                        "      \"Action\": \"s3:GetObject\",\n" +
                        "      \"Resource\": \"arn:aws:s3:::%s/*\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}", bucketName);

        PutBucketPolicyRequest policyRequest = PutBucketPolicyRequest.builder()
                .bucket(bucketName)
                .policy(policyJson)
                .build();

        s3Client.putBucketPolicy(policyRequest);
        System.out.println("Bucket policy applied for bucket: " + bucketName);
    }

    public void save(String bucketName) throws IOException {
        // 프로젝트 경로 계산
        String projectPath = "app/notion-to-site";
        String distFolderPath = new File(projectPath, "out").getCanonicalPath();
        Path distPath = Paths.get(distFolderPath);

        // 폴더가 존재하는지 체크
        if (!Files.exists(distPath)) {
            throw new FileNotFoundException("Distribution folder not found at " + distFolderPath);
        }

        // 파일 탐색 및 업로드
        try (Stream<Path> paths = Files.walk(distPath)) {
            List<Path> filePaths = paths.filter(Files::isRegularFile).collect(Collectors.toList());

            // 병렬 스트림으로 S3 업로드 처리
            filePaths.parallelStream().forEach(filePath -> {
                try {
                    uploadFileToS3(bucketName, filePath, distFolderPath);
                } catch (IOException e) {
                    // 파일 업로드 중 발생하는 예외를 처리
                    System.err.println("Failed to upload file: " + filePath + ", error: " + e.getMessage());
                }
            });

        } catch (IOException e) {
            // 파일 탐색 중 발생하는 예외를 처리
            throw new IOException("Error while walking through the dist folder", e);
        }
    }

    private void uploadFileToS3(String bucketName, Path filePath, String distFolderPathString) throws IOException {
        Path distFolderPath = Paths.get(distFolderPathString);


        String key = distFolderPath.relativize(filePath).toString().replace(File.separator, "/");


        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .cacheControl("no-cache, no-store, must-revalidate")
                .bucket(bucketName)
                .key(key)
                .build();


        s3Client.putObject(putObjectRequest, RequestBody.fromFile(filePath.toFile()));
    }
}
