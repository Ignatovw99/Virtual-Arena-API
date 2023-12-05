package com.virtualarena.api.service.implementation;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.virtualarena.api.exception.InvalidResourceStateException;
import com.virtualarena.api.service.contract.FileService;
import com.virtualarena.api.validation.FileValidationRule;
import com.virtualarena.api.validation.base.ValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CloudinaryFileServiceImpl implements FileService {

    private final Cloudinary cloudinary;

    @Value("${spring.servlet.multipart.max-file-size}")
    private DataSize maxFileSize;

    @Override
    public String uploadFile(MultipartFile file) {
        ValidationResult validationResult = new FileValidationRule(maxFileSize.toBytes(), List.of("image/png"))
                .validate(file);

        if (validationResult.notValid()) {
            throw new InvalidResourceStateException(validationResult.getMessage());
        }

        try {
            return cloudinary.uploader()
                    .upload(file.getBytes(), ObjectUtils.emptyMap())
                    .get("secure_url")
                    .toString();
        } catch (IOException e) {
            throw new InvalidResourceStateException(e.getMessage());
        }
    }
}
