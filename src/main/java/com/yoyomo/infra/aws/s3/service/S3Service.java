package com.yoyomo.infra.aws.s3.service;

import com.yoyomo.domain.club.exception.UnavailableSubdomainException;
import com.yoyomo.infra.aws.exception.FileNotFoundException;
import com.yoyomo.infra.aws.exception.ImageSaveFailureException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.Delete;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import software.amazon.awssdk.services.s3.model.PublicAccessBlockConfiguration;
import software.amazon.awssdk.services.s3.model.PutBucketPolicyRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;


@Service
@RequiredArgsConstructor
public class S3Service {

    private static final String S3_URL_FORMAT = "https://%s.s3.amazonaws.com/%s";
    private final S3Client s3Client;
    @Value("${application.bucket.name}")
    private String BUCKETNAME;

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
    }

    public void upload(String bucketName) {
        Path distPath = findFilePath();
        validateFilePath(distPath);
        try {
            Stream<Path> paths = findEachFilePath(distPath);
            List<Path> filePaths = paths.filter(Files::isRegularFile).toList();

            filePaths.parallelStream().forEach(filePath ->
                    uploadFileToS3(bucketName, filePath, distPath));

        } catch (S3Exception e) {
            throw new UnavailableSubdomainException(e.statusCode(), e.getMessage());
        }
    }

    private void validateFilePath(Path path) {
        if (!Files.exists(path)) {
            throw new FileNotFoundException();
        }
    }

    private Stream<Path> findEachFilePath(Path path) {
        try {
            return Files.walk(path);
        } catch (IOException e) {
            throw new com.yoyomo.infra.aws.exception.FileNotFoundException();
        }
    }

    private Path findFilePath() {
        try {
            String projectPath = "app/notion-to-site";
            String distFolderPath = new File(projectPath, "out").getCanonicalPath();
            return Paths.get(distFolderPath);
        } catch (IOException e) {
            throw new com.yoyomo.infra.aws.exception.FileNotFoundException();
        }
    }

    private void uploadFileToS3(String bucketName, Path filePath, Path distFolderPathString) {

        String key = distFolderPathString.relativize(filePath).toString().replace(File.separator, "/");

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .cacheControl("no-cache, no-store, must-revalidate")
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromFile(filePath.toFile()));
    }

    public String delete(String subDomain) {

        deleteAllElements(subDomain);

        deleteBucket(subDomain);

        return subDomain;
    }

    private void deleteAllElements(String bucketName) {
        ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response listObjectsResponse;

        do {

            listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest);

            List<ObjectIdentifier> objectsToDelete = listObjectsResponse.contents().stream()
                    .map(s3Object -> ObjectIdentifier.builder().key(s3Object.key()).build())
                    .collect(Collectors.toList());

            if (!objectsToDelete.isEmpty()) {

                DeleteObjectsRequest deleteObjectsRequest = DeleteObjectsRequest.builder()
                        .bucket(bucketName)
                        .delete(Delete.builder().objects(objectsToDelete).build())
                        .build();
                s3Client.deleteObjects(deleteObjectsRequest);
            }

            listObjectsRequest = listObjectsRequest.toBuilder()
                    .continuationToken(listObjectsResponse.nextContinuationToken())
                    .build();

        } while (listObjectsResponse.isTruncated());
    }

    private void deleteBucket(String bucketName) {
        s3Client.deleteBucket(DeleteBucketRequest.builder()
                .bucket(bucketName)
                .build());
    }

    public List<String> save(List<MultipartFile> images) {
        List<String> urls = new ArrayList<>();

        for (MultipartFile image : images) {
            String fileName = getFileName(image);
            String key = "image/" + fileName;

            try (InputStream inputStream = image.getInputStream()) {
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(BUCKETNAME)
                        .key(key)
                        .acl(ObjectCannedACL.PUBLIC_READ)
                        .contentType(image.getContentType())
                        .build();

                s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, image.getSize()));

                String url = String.format(S3_URL_FORMAT, BUCKETNAME, key);
                urls.add(url);

            } catch (IOException | S3Exception e) {
                throw new ImageSaveFailureException();
            }

        }

        return urls;
    }

    private String getFileName(MultipartFile image) {
        String originalFilename = image.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(index + 1);

        String storeFileName = UUID.randomUUID() + "." + ext;
        return storeFileName;
    }

}
