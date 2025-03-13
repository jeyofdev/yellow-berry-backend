package com.jeyofdev.yellow_berry.core.constant;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ClientUrl {

    @Value("${app.base-client-url}")
    private String baseUrl;

    private static String BASE_URL;

    @PostConstruct
    private void init() {
        BASE_URL = baseUrl;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }
}
