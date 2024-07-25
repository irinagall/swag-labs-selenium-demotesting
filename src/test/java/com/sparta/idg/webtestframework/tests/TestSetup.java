package com.sparta.idg.webtestframework.tests;

import com.sparta.idg.webtestframework.pages.Website;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.time.Duration;



public class TestSetup {
    private static ChromeDriverService service;
    private WebDriver webDriver;

    private static final String DRIVER_LOCATION = "src/test/resources/chromedriver";

    public static ChromeOptions getChromeOptions(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        options.setImplicitWaitTimeout(Duration.ofSeconds(10));
        return options;
    }

    @BeforeAll
    public static void beforeAll() throws IOException {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(DRIVER_LOCATION))
                .usingAnyFreePort()
                .build();
        service.start();
    }

    @AfterAll
    static void afterAll(){
        service.stop();
    }

    @BeforeEach
    void setup(){
        webDriver = new RemoteWebDriver(service.getUrl(), getChromeOptions());
    }

    @AfterEach
    void afterEach(){
        webDriver.close();
    }

    Website getWebsite(String url){
        webDriver.get(url);
        return new Website(webDriver);
    }
}

