package com.virtualarena.api.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static com.virtualarena.api.util.constant.CloudinaryConstants.*;

@Configuration
public class CloudinaryConfig {

    @Value("${virtual-arena.storage.cloudinary.cloud-name}")
    private String cloudName;

    @Value("${virtual-arena.storage.cloudinary.api-key}")
    private String apiKey;

    @Value("${virtual-arena.storage.cloudinary.api-secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(Map.of(
                CLOUD_NAME, cloudName,
                API_KEY, apiKey,
                API_SECRET, apiSecret
        ));
    }
}
