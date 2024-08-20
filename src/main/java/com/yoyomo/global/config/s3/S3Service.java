package com.yoyomo.global.config.s3;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.FileWriter;
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
    @Value("${vite.uri}")
    private String VITE_PUBLIC_API_URL;

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

    public void save(String bucketName , String notionPageId) throws IOException {

        log.info("현재 경로" + System.getProperty("user.dir"));
        // 1. .env 파일 생성
        String envFilePath = "/app/vite-notion-to-site/.env";
        String canonicalEnvPath = new File(envFilePath).getCanonicalPath();
        createEnvFile(canonicalEnvPath, notionPageId);

        // 2. 빌드 작업 수행
        String projectPath = "/app/vite-notion-to-site";
        String canonicalProjectPath = new File(projectPath).getCanonicalPath();
        buildProject(canonicalProjectPath);

        // 3. dist 디렉토리로 빌드 파일 이동 및 S3 업로드
        log.info("현재 경로: " + System.getProperty("user.dir"));
        String distFolderPath = canonicalProjectPath + "/dist";
        File distDir = new File(distFolderPath);

        String canonicalDistPath = distDir.getCanonicalPath();
        Path distPath = Paths.get(canonicalDistPath);

        if (!Files.exists(distPath)) {
            log.info("해당 경로에 파일이 존재하지 않습니다: " + distPath.toAbsolutePath());
            return;
        }

        try (Stream<Path> paths = Files.walk(distPath)) {
            List<Path> filePaths = paths.filter(Files::isRegularFile).collect(Collectors.toList());

            S3Service.log.info("버킷에 파일 업로드 중");
            for (Path filePath : filePaths) {
                uploadFileToS3(bucketName, filePath, canonicalDistPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createEnvFile(String envFilePath , String notionPageId) throws IOException {
        try (FileWriter writer = new FileWriter(envFilePath)) {
            writer.write("VITE_PUBLIC_ROOT_PAGE=" + notionPageId + "\n");
            writer.write("VITE_PUBLIC_API_URL=" + VITE_PUBLIC_API_URL + "\n");
            // 필요한 다른 환경 변수들도 추가할 수 있습니다.
        }
        log.info(".env 파일 생성 완료: " + envFilePath);
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

    private void buildProject(String canonicalProjectPath) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("npm", "run", "build");
            processBuilder.directory(new File(canonicalProjectPath));
            processBuilder.inheritIO();
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                log.info("프로젝트 빌드 성공");
            } else {
                log.error("프로젝트 빌드 실패, 종료 코드: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            log.error("빌드 중 오류 발생", e);
        }
    }
}
