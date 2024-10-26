package com.yoyomo.infra.aws.s3.service;

import com.yoyomo.infra.aws.exception.FileNotFoundException;
import com.yoyomo.domain.club.exception.UnavailableSubdomainException;
import com.yoyomo.infra.aws.exception.ImageSaveFailureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

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



@Service
@RequiredArgsConstructor
public class S3Service {

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

    public void upload(String bucketName) throws IOException {

        String projectPath = "app/notion-to-site";
        String distFolderPath = new File(projectPath, "out").getCanonicalPath();
        Path distPath = Paths.get(distFolderPath);

        if (!Files.exists(distPath)) {
            throw new FileNotFoundException();
        }

        try (Stream<Path> paths = Files.walk(distPath)) {
            List<Path> filePaths = paths.filter(Files::isRegularFile).collect(Collectors.toList());

            filePaths.parallelStream().forEach(filePath -> {
                try {
                    uploadFileToS3(bucketName, filePath, distFolderPath);
                } catch (IOException e) {
                    throw new com.yoyomo.infra.aws.exception.FileNotFoundException();
                }
            });

        } catch (S3Exception e) {
            throw new UnavailableSubdomainException(e.statusCode(),e.getMessage());
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

    public void delete(String subDomain) {

        deleteAllElements(subDomain);

        deleteBucket(subDomain);

        System.out.println("S3 bucket deleted: " + subDomain);
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
                        .build();

                s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, image.getSize()));
            } catch (IOException | S3Exception e) {
                throw new ImageSaveFailureException();
            }

            String url = s3Client.utilities().getUrl(b -> b.bucket(BUCKETNAME).key(key)).toExternalForm();
            urls.add(url);
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
