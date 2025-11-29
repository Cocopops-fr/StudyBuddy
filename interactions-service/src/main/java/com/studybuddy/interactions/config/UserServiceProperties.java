package com.studybuddy.interactions.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "users.service")
public class UserServiceProperties {

    /**
     * Base URL of the user-service REST API.
     */
    private String url = "http://localhost:8081";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}