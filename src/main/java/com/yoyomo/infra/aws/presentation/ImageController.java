package com.yoyomo.infra.aws.presentation;

import static com.yoyomo.infra.aws.presentation.constant.ResponseMessage.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yoyomo.global.common.dto.ResponseDto;
import com.yoyomo.infra.aws.s3.service.S3Service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "IMAGE")
@RestController("/image")
@RequiredArgsConstructor
public class ImageController {
	private final S3Service s3Service;

	@PostMapping(
		value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
	)
	@Operation(summary = "[Image] 이미지 업로드")
	public ResponseDto<List<String>> returnImage(@RequestPart("images") List<MultipartFile> images) {
		return ResponseDto.of(HttpStatus.OK.value(), IMAGE_SAVE_SUCCESS.getMessage(), s3Service.save(images));
	}
}
