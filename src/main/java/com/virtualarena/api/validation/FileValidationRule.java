package com.virtualarena.api.validation;

import com.virtualarena.api.validation.base.ValidationResult;
import com.virtualarena.api.validation.base.ValidationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

import static com.virtualarena.api.util.constant.FileConstants.*;

@RequiredArgsConstructor
public class FileValidationRule extends ValidationRule<MultipartFile> {

    private final Long maxFileSizeBytes;
    private final List<String> allowedContentTypes;

    @Override
    protected ValidationResult validateRule(MultipartFile file) {
        if (Objects.isNull(file) || file.isEmpty()) {
            return ValidationResult.invalid(FILE_MISSING);
        }

        if (!isFileSizeAllowed(file.getSize())) {
            return ValidationResult.invalid(FILE_SIZE_EXCEEDED);
        }

        if (!isContentTypeValid(file.getContentType())) {
            return ValidationResult.invalid(INVALID_CONTENT_TYPE);
        }

        return ValidationResult.valid();
    }

    private boolean isFileSizeAllowed(Long size) {
        return size < maxFileSizeBytes;
    }

    private boolean isContentTypeValid(String contentType) {
        return allowedContentTypes.contains(contentType);
    }
}
