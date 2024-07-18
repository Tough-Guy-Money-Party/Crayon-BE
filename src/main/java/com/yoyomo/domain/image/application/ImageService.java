package com.yoyomo.domain.image.application;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.yoyomo.domain.image.exception.ImageSaveFailureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${application.bucket.name}")
    private String BUCKETNAME;

    private final AmazonS3Client s3Client;

    public List<String> save(List<MultipartFile> images) {
        List<String> urls = new ArrayList<>();

        for (MultipartFile image : images) {
            String fileName = getFileName(image);
            String key = "image/" + fileName;

            try (InputStream inputStream = image.getInputStream()) {
                s3Client.putObject(new PutObjectRequest(BUCKETNAME, key, inputStream, getMetadataByImage(image))
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                throw new ImageSaveFailureException();
            }

            urls.add(s3Client.getUrl(BUCKETNAME, key).getPath());
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

    private ObjectMetadata getMetadataByImage(MultipartFile image){
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(image.getContentType());
        objectMetadata.setContentLength(image.getSize());
        return objectMetadata;
    }

}

