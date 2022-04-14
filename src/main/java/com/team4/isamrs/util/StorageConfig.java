package com.team4.isamrs.util;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.MediaType;

import java.util.Set;

@Getter
@ConfigurationProperties("storage")
public class StorageConfig {
    private final String uploadsLocation = "uploads";

    private final Set<String> allowedContentTypes = Set.of(
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_JPEG_VALUE
    );
}
