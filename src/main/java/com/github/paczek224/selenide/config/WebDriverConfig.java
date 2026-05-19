package com.github.paczek224.selenide.config;

import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
@ConfigurationProperties(prefix = "selenide")
public class WebDriverConfig {
    private String baseUrl;
    private String browser;
    private String browserSize;
    private boolean headless;
    private int timeout;

    @PostConstruct
    public void configureSelenide() {
        com.codeborne.selenide.Configuration.headless = headless;
        com.codeborne.selenide.Configuration.browserSize = browserSize;
        com.codeborne.selenide.Configuration.timeout = timeout;
        com.codeborne.selenide.Configuration.browser = browser;
        com.codeborne.selenide.Configuration.baseUrl = baseUrl;
        com.codeborne.selenide.Configuration.reportsFolder = "target/reports/screenshots";
        com.codeborne.selenide.Configuration.downloadsFolder = "target/downloads";
    }
}
