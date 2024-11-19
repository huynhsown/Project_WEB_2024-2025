package com.adidark.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dfjqteia2",        // Cloud name
                "api_key", "561334421234995",    // API key
                "api_secret", "fOvMoCdtTjUs6ZKDqEnYADp82E4" // API secret
        ));
    }
}
