package com.sparta.idg.selenium;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static org.hamcrest.Matchers.*;

public class SwagLabTests {
    //ChromeDriver
    //ChromeDriver Server
    //Write command in our test script.
    //Chrome Driver (webDriver) translate these commands into a WebDriver Write Protocol format(HTTP Requests)
    //Chrome Driver Service (which will be started before the tests run. It listens for commands on a specific port
    //Service is what communicates with the browser and returns and response to chromedriver

    private static final String DRIVER_LOCATION = "src/test/resources/chromedriver";
    private static final String BASE_URL = "https://www.saucedemo.com/";
    private static ChromeDriverService service;
    private WebDriver webDriver;

    //Options SET!!
    public static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
       // options.addArguments("--headless");
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
        service.start();;
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

    @Test
    @DisplayName("Check that the webdriver works")
    public void checkWebDriver(){
        webDriver.get(BASE_URL);
        Assertions.assertEquals(BASE_URL, webDriver.getCurrentUrl());
        Assertions.assertEquals("Swag Labs", webDriver.getTitle());
    }

    @Test
    @DisplayName("Given I enter a valid username and password, when I click login, then I should land on the inventory page")
    public void successfulLogin(){
        //Arrange
        Wait<WebDriver> webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        webDriver.get(BASE_URL);
        WebElement userNameField = webDriver.findElement(By.name("user-name"));
        WebElement passwordField = webDriver.findElement(By.name("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));

        // Act
        userNameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();


        webDriverWait.until(driver -> driver.getCurrentUrl().contains("/inventory"));
        MatcherAssert.assertThat(webDriver.getCurrentUrl(), is(BASE_URL + "inventory.html"));
    }

    // Test the sad path (i.e. when the error message appears
    @Test
    @DisplayName("Given I enter an invalid username or password, when I click login, then I should see an error message")
    public void unsuccessfulLogin() {
        // Arrange
        webDriver.get(BASE_URL);
        WebElement userNameField = webDriver.findElement(By.id("user-name"));
        WebElement passwordField = webDriver.findElement(By.id("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));

        // Act
        userNameField.sendKeys("invalid_user");
        passwordField.sendKeys("invalid_password");
        loginButton.click();

        // Assert
        WebElement errorMessage = webDriver.findElement(By.cssSelector("[data-test='error']"));
        MatcherAssert.assertThat(errorMessage.isDisplayed(), is(true));
        MatcherAssert.assertThat(errorMessage.getText(), containsString("Epic sadface: Username and password do not match any user in this service"));
    }


    // Test that when you login there are 6 items on the inventory page
    @Test
    @DisplayName("Given I successfully login, when I'm on the inventory page, then I should see 6 items")
    public void inventoryPageHasSixItems() {
        // Arrange
        webDriver.get(BASE_URL);
        WebElement userNameField = webDriver.findElement(By.id("user-name"));
        WebElement passwordField = webDriver.findElement(By.id("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));

        // Act
        userNameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        // Wait for the inventory page to load
        Wait<WebDriver> wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(driver -> driver.getCurrentUrl().contains("/inventory"));

        // Find all inventory items
        List<WebElement> inventoryItems = webDriver.findElements(By.className("inventory_item"));

        // Assert
        MatcherAssert.assertThat(inventoryItems.size(), is(6));
    }

}
