package com.yoyomo.domain.image.presentation;

import com.yoyomo.domain.image.application.ImageService;
import com.yoyomo.domain.image.application.annotation.ValidImage;
import com.yoyomo.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/images")
    public ResponseDto<List<String>> returnImage(@Validated @RequestParam @ValidImage List<MultipartFile> images) {
        return ResponseDto.of(HttpStatus.OK.value(),"성공", imageService.save(images));
    }
}