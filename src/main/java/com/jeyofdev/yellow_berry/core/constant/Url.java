package com.jeyofdev.yellow_berry.core.constant;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Url {

    @Value("${app.base-url}")
    private String baseUrl;

    @Value("${app.base-version}")
    private String apiVersion;

    private static String BASE_URL;
    private static String API_VERSION;

    @PostConstruct
    private void init() {
        BASE_URL = baseUrl;
        API_VERSION = apiVersion;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getApiVersion() {
        return BASE_URL;
    }

    public static String getFullBaseUrl() {
        return BASE_URL + "/" + API_VERSION;
    }
}
