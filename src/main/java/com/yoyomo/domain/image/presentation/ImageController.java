package com.yoyomo.domain.image.presentation;

import com.yoyomo.domain.image.application.ImageService;
import com.yoyomo.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/image")
    public ResponseDto<String> returnImage(@RequestParam MultipartFile pic) {
        return ResponseDto.of(HttpStatus.OK.value(),"성공",imageService.save(pic));
    }
}