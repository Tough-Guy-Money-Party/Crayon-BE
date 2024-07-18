package com.yoyomo.domain.image.application.validator;

import com.yoyomo.domain.image.application.annotation.ValidImage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ImageValidator implements ConstraintValidator<ValidImage, List<MultipartFile>> {
    private final List<String> imageformats = List.of("jpg","jpeg","png","pneg","heic","heics");
    @Override
    public void initialize(ValidImage constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<MultipartFile> images, ConstraintValidatorContext constraintValidatorContext) {
        for (MultipartFile image : images) {
            Long imageSize = image.getSize();
            String format = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
            if (imageSize.equals(0L) || !imageformats.contains(format)) {
                return false;
            }
        }
        return true;
    }
}
